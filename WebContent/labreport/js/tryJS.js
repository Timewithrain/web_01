$(function(){
    var users = new Array();
    var posts = new Array();
    var comments = new Array();
    var titleForComment;

    $("#login-dialog").dialog({
        title: "用户登录",
        width: 380,
        height: 260,
        autoOpen: false,
        // 设置定位为登录按钮右下角打开登录框
        position: {my:"right top",of:"#bnt-login",at:"right bottom"},
        // 设置不可改变大小
        resizeable: false,
        // 设置不可拖拽
        dragable: false,
        // 设置弹窗动画
        show: {effect:"slideDown",duration:"normal"},
        hide: {effect:"slideUp",duration:"normal"},
        buttons:{
            登录:function(){
                var username = $("#login-username").val();
                if(username==""){
                    $(".error-box3").append('<p class="error">用户不能为空</p>');
                    return;
                }
                $.ajax({
                    url:  "loginServlet",
                    type: "post",
                    data: {
                        "username": username,
                        "password": $("#login-password").val()
                    },
                    success:function(data){
                        $(".error-box3").empty();
                        $(".error-box4").empty();
                        var re = $.trim(data);
                        if(re=="0"){
                            $(".error-box3").append('<p class="error">用户不存在</p>');
                        }else if(re=="1"){
                            $(".error-box4").append('<p class="error">密码错误</p>');
                        }else {
                            var name = data;
                            var login = $("#login");
                            //修改登录按钮
                            login.empty();
                            login.append('<a href="#" id="bnt-login">'+name+'</a> | '+
                            '<a href="#" id="bnt-signout">退出</a>'+
                            '<a href="#" class="my-post">我的发帖</a>');
                            //添加发帖按钮
                            $("#nav").append('<a href="#" class="do-post">发帖</a>');
                            // 添加评论按钮
                            $(".do-comment-btn").append('<a href="#" class="do-comment">查看评论</a>');
                            $("#login-dialog").dialog("close");
                        }
                    },
                    dataType: "text",
                });
            },
            取消:function(){
                $(".error-box3").empty();
                $(".error-box4").empty();
                $("#login-dialog").dialog("close");
            }
        }
    });
    
    $("#bnt-login").click(function(){
        $("#login-dialog").dialog("open");
    });

    $("#register-dialog").dialog({
        title: "用户注册",
        width: 390,
        height: 300,
        autoOpen: false,
        buttons:{
            注册:function(){
                $(".error-box1").empty();
                $(".error-box2").empty();
                var username = $("#register-username").val();
                var email = $("#register-email").val();
                var password = $("#register-password").val();
                var checkPassword = $("#register-check-password").val();
                if(username==""){
                    $(".error-box1").append('<p class="error">用户名不能为空</p>');
                    return
                }
                if(password!=checkPassword){
                    $(".error-box2").append('<p class="error">密码不一致</p>');
                    return;
                }
                //发送注册请求
                $.post("registerServlet",{
                    "username": username,
                    "email": email,
                    "password": password,
                    "check-password": checkPassword
                },function(data){
                    var str = $.trim(data);
                    if(str=="OK"){
                        $("#register-dialog").dialog("close")
                    }else if(str=="userExist"){
                        $(".error-box1").append('<p class="error">用户已存在</p>');
                    }
                },"text");
            },
            取消:function(){
                $(".error-box1").empty();
                $(".error-box2").empty();
                $("#register-dialog").dialog("close");
            }
        }        
    });

    $("#bnt-register").click(function(){
        $("#register-dialog").dialog("open");
    });

    /****************************辅助功能函数****************************/

    //通过用户名获取头像
    function getUserAttr(userName,attribute,users){
        for(var i=0;i<users.length;i++){
            var user = users[i];
            if(user["name"]==userName){
                //当用户没有头像时加载默认图像作为头像
                if(attribute=="avatar"&&user[attribute]==null){
                    return "img/default.jpg";
                }else{
                    return user[attribute];
                }
            }
        }
    }

    /****************************帖子页面函数***************************/

    function createPost(title,content,avatar,userName,likes){
        var postDIV = '<div class="post">'+
                        '<div class="post-title">'+
                            '<h3>'+title+'</h3>'+
                        '</div>'+
                        '<div class="post-content">'+
                            '<p>'+content+'</p>'+
                        '</div>'+
                        '<div class="post-infor">'+
                            '<div class="post-avatar">'+
                                '<img src="'+avatar+'" alt="">'+
                                '<div class="post-poster-name">'+
                                    userName+
                                '</div>'+
                            '</div>'+
                            '<div class="do-comment-btn">'+
                            '</div>'+
                            '<div class="likes">'+
                                '<span class="reply-count">'+likes+'</span>'+
                            '</div>'+
                        '</div>'+
                    '</div>';
        $("#posts").append(postDIV);
    }


    /****************************评论页面函数**************************/

    //显示评论栏
    function showCommentArea(){
        var commentAreaDIV = '<div class="add-comment">'+
                                '<div class="comment-body">'+
                                    '<textarea name="" class="comment-body-content" cols="30" rows="7" placeholder="此处输入评论内容..."></textarea>'+
                                '</div>'+
                                '<div class="post-footer">'+
                                    '<button class="post-button" id="comment-publish">评论</button>'+
                                '</div>'+
                            '</div>';
        $("#posts").append(commentAreaDIV);
    }

    //生成评论
    function createComment(name,avatar,os,comment,like){
        var commentDIV = '<div id="comment" class="comment clear-fix">'+
                        '<div id="avatar" class="avatar">'+
                            '<img src="'+avatar+'" alt="">'+
                        '</div>'+
                        '<div class="comment-content">'+
                            '<dic class="comment-title" title="'+comment+'">'+
                                comment+'</dic>'+
                            '<div class="comment-infor">'+
                                os + ' ' + name + ' 回复 <span class="reply-count">' + like + '</span>'+
                            '</div>'+
                        '</div>'+
                    '</div>'
        $("#posts").append(commentDIV);
    }

    //传入评论的帖子的标题，为按钮绑定提交功能，准备提交
    $("#posts").on("click","#comment-publish",function(){
        var comment = $(".comment-body-content").val();
        var title = titleForComment;
        console.log(comment);
        if(comment!=""){
            $.post("indexServlet",{
                infor: "addComment",
                title: title,
                comment: comment
            },function(data){
                window.location.reload();
            },"json");
        }else{
            alert("评论内容不能为空！");
        }
    });

    $("body").on("click",".do-comment",function(){
        //获取评论按钮对应的帖子的标题
        var aim = $(event.target).parents(".post").children();
        var h3 = aim.children()[0];
        titleForComment = h3.innerHTML;
        console.log(titleForComment);
        $.post("indexServlet",{
            infor: "getComment",
            title: h3.innerHTML
        },function(data){
            comments = data;
            $("#posts").empty();
            showCommentArea();
            for(var i=0;i<comments.length;i++){
                var comment = comments[i];
                var commentContent = comment["comment"];
                var name = comment["userName"];
                var likes = comment["likes"];
                var avatar = getUserAttr(name,"avatar",users);
                var os = getUserAttr(name,"OS",users);
                createComment(name,avatar,os,commentContent,likes);
            }
        },"json");
    });

    /******************************发帖功能*********************************/
    var isOpenPost = false;
    $("#nav").on("click",".do-post",function(){
        //检测发帖栏是否开启，若已开启则不再添加
        if(isOpenPost==false){
            var doPostDIV = '<div class="add-post">'+
                                '<div class="post-head">'+
                                    '<input type="text" name="" class="post-head-content" placeholder="此处输入标题">'+
                                '</div>'+
                                '<div class="post-body">'+
                                    '<textarea name="" class="post-body-content" cols="30" rows="7" placeholder="此处输入帖子内容..."></textarea>'+
                                '</div>'+
                                '<div class="post-footer">'+
                                    '<button class="post-button" id="post-cancel">取消</button>'+
                                    '<button class="post-button" id="post-publish">发布</button>'+
                                '</div>'+
                            '</div>';
            var postarea = $("#posts .post:first-child")[0];
            if(postarea!=null){
                $("#posts .post:first-child").before(doPostDIV);
                isOpenPost = true;
            }
        }
    });

    //为发帖按钮绑定点击提交帖子事件
    $("#posts").on("click","#post-publish",function(){
        var title = $(".post-head-content").val();
        var content = $(".post-body-content").val();
        if(title!=""&&content!=""){
            $.post("indexServlet",{
                infor: "addPost",
                title: title,
                content: content
            },function(data){
                window.location.reload();
            },"json");
        }else{
            alert("发帖内容不能为空！");
        }
    });

    //为取消按钮绑定点击取消事件，点击关闭发帖栏
    $("#posts").on("click","#post-cancel",function(){
        $(".add-post").remove();
        isOpenPost = false;
    });

    /****************************页面刷新调用函数*******************************/

    function createPraise(praise){
        var praiseDIV = '<div class="praise">'+
                            '<img src="img/praise'+praise+'.png" alt="">'+
                        '</div>';
        $(".likes .reply-count").before(praiseDIV);
    }

    function showPraise(){
        $.post("indexServlet",{
            infor: "getPraise",
        },function(data){
            for(var i=0;i<posts.length;i++){
                var topic = posts[i];
                var title = topic["topicName"];
                var isPraise = 0;
                for(var j=0;j<data.length;j++){
                    var praise = data[j];
                    if(title==praise["topicname"]){
                        isPraise = 1;
                    }else{
                        isPraise = 0;
                    }
                }
                createPraise(isPraise);
            }
        },"json");
    }

    function getLoginStatus(){
        $.post("indexServlet",{infor: "getLoginStatus"},function(data){
            //若未登录则返回notLogin，若登录则在页面显示登录信息
            if($.trim(data["infor"])=="notLogin"){
                return
            }else{
                var name = data["infor"];
                var login = $("#login");
                //修改登录按钮
                login.empty();
                login.append('<a href="#" id="bnt-login">'+name+'</a> | '+
                '<a href="#" id="bnt-signout">退出</a>'+
                '<a href="#" class="my-post">我的发帖</a>');
                //添加发帖按钮
                var postbtn = $(".do-post")[0];
                if(postbtn==null){
                    $("#nav").append('<a href="#" class="do-post">发帖</a>');
                }
                // 添加评论按钮
                $(".do-comment-btn").append('<a href="#" class="do-comment">查看评论</a>');
                //添加点赞按钮
                // showPraise();
            }
        },"json");
        $.post("indexServlet",{
            infor: "getInfor"
        },function(data){
            var infor = data;
            var userNum = infor["userNum"];
            var topicNum = infor["topicNum"];
            var commentNum = infor["commentNum"];
            $(".user-value")[0].innerHTML = userNum;
            $(".topic-value")[0].innerHTML = topicNum;
            $(".comment-value")[0].innerHTML = commentNum;
        },"json");
    }

    function createHotPost(avatar,title,likes){
        var hotPostDIV = '<div class="hot-post clear-fix">'+
                            '<div class="hot-avatar">'+
                                '<img src="'+avatar+'" alt="avatar">'+
                            '</div>'+
                            '<div class="hot-content">'+title+'</div>'+
                            '<div class="hot-infor">'+
                                '<span class="reply-count">'+likes+'</span>'+
                            '</div>'+
                        '</div>';
        $("#hot-posts").append(hotPostDIV);
    }

    function showHotPost(){
        $.post("indexServlet",{
            infor: "getTopicOrder"
        },function(data){
            console.log(data);
            $("#hot-posts").empty();
            //输出热度前五的帖子
            for(var i=0;i<data.length||i<5;i++){
                hotTopic = data[i];
                var avatar = getUserAttr(hotTopic["posterName"],"avatar",users);
                var title = hotTopic["topicName"];
                var likes = hotTopic["likes"];
                createHotPost(avatar,title,likes);
            }
        },"json");
    }

    //获取user以及topic
    function start(){
        isOpenPost = false;
        $.post("indexServlet",{infor: "getUser"},function(data){
            users = data;
            $.post("indexServlet",{infor: "getTopic"},function(data){
                posts = data;
                for(var i=0;i<posts.length;i++){
                    var post = posts[i];
                    var title = post["topicName"];
                    var content = post["title"];
                    var likes = post["likes"];
                    var poster = post["posterName"];
                    var avatar = getUserAttr(poster,"avatar",users);
                    createPost(title,content,avatar,poster,likes);
                }
                getLoginStatus();
                showHotPost();
            },"json");
        },"json");
    }
    $(start());

    $("#nav").on("click",".return-post",function(){
        $("#posts").empty();
        start();
    });

    $("body").on("click","#bnt-signout",function(){
        $.post("exit",function(){
            //退出以后刷新页面
            window.location.reload();
        });
    });
    
    //放大显示照片
    $(".avatar").tooltip({
        items: "img",
        content: function(){
            return "<img class='avatar-tooltip' src='" + $(this).attr('src') + "' alt='" + $(this).attr('alt') + "'>";
        },
        position:{my:"left+30 center",at:"right center"}
    });
    
    /*****************************修改发帖*****************************/
    //显示可修改的帖子
    function createEditPost(title,content){
        var postDIV = '<div class="post">'+
                        '<div class="post-title">'+
                            '<h3>'+title+'</h3>'+
                        '</div>'+
                        '<div class="post-content">'+
                            '<p>'+content+'</p>'+
                        '</div>'+
                        '<div class="post-edit-infor">'+
                            '<div class="do-edit-btn">'+
                                '<a href="#" class="do-edit">修改</a>'+
                                '<a href="#" class="do-delete">删除</a>'+
                            '</div>'+
                        '</div>'+
                    '</div>';
        $("#posts").append(postDIV);
    }

    $("#login").on("click",".my-post",function(){
        $.post("indexServlet",{
            infor: "getTopics"
        },function(data){
            $("#posts").empty();
            for(var i=0;i<data.length;i++){
                var topic = data[i];
                var title = topic["topicName"];
                var content = topic["title"];
                createEditPost(title,content);
            }
        },"json");
    });

    //进入帖子修改页面
    function editPost(title,content){
        var editDIV = '<div class="add-post">'+
                            '<div class="post-title">'+
                                '<h3>'+title+'</h3>'+
                            '</div>'+
                            '<div class="post-edit-body">'+
                                '<textarea name="" class="post-edit-content" cols="30" rows="7">'+content+'</textarea>'+
                            '</div>'+
                            '<div class="post-footer">'+
                                '<button class="post-button" id="post-cancel">取消</button>'+
                                '<button class="post-button" id="post-edit">发布</button>'+
                            '</div>'+
                        '</div>';
        $("#posts").append(editDIV);
    }

    $("#posts").on("click",".do-edit",function(){
        var postcontent = $(event.target).parents(".post").children();
        var p = postcontent.children()[1];
        var posttitle = $(event.target).parents(".post").children();
        var h3 = posttitle.children()[0];
        $("#posts").empty();
        editPost(h3.innerHTML,p.innerHTML);
    })

    $("#posts").on("click","#post-edit",function(){
        var h3 = $(".post-title").children()[0];
        var title = h3.innerHTML;
        var content = $(".post-edit-content").val();
        if(content!=""){
            $.post("indexServlet",{
                infor: "updateTopic",
                title: title,
                content: content
            },function(data){
                window.location.reload();
            },"json");
        }else{
            alert("修改内容不能为空！");
        }
    });

    /*******************************删除帖子***********************************/
    $("#posts").on("click",".do-delete",function(){
        var posttitle = $(event.target).closest(".post").children();
        var h3 = posttitle.children()[0];
        var title = h3.innerHTML;
        $.post("indexServlet",{
            infor: "deleteTopic",
            title: title
        },function(data){
            window.location.reload();
        },"json");
    });

    /*******************************点赞功能*************************************/
    $("#posts").on("click",".praise",function(){
        var src = $(event.target)[0].src;
        var posttitle = $(event.target).closest(".post").children();
        var h3 = posttitle.children()[0];
        var title = h3.innerHTML;
        console.log(title);
        if(src=="http://localhost:8080/web_1/labreport/img/praise1.png"){
            $(event.target).attr("src","img/praise0.png");
        }else{
            $(event.target).attr("src","img/praise1.png");
            // $.post("indexServlet",{
            //     infor: "addPraise",
            //     title: title
            // },function(data){
            // },"json")
        }
    });

});