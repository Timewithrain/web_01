$(function(){
    var users = new Array();
    var posts = new Array();
    var comments = new Array();
    var titleForComment;

    $("#login-dialog").dialog({
        title: "用户登录",
        width: 380,
        height: 240,
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
                $.ajax({
                    url:  "loginServlet",
                    type: "post",
                    data: {
                        "username": $("#login-username").val(),
                        "password": $("#login-password").val()
                    },
                    success:function(data){
                        var re = data;
                        if(re.charAt(0)=="0"){
                            alert("用户名不存在");
                        }else if(re.charAt(0)=="1"){
                            alert("用户名与密码不匹配");
                        }else if(re.length>3){
                            // console.log(re);
                            // console.log(re.length);
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
        height: 260,
        autoOpen: false,
        buttons:{
            注册:function(){
                //发送注册请求
                $.post("registerServlet",{
                    "username": $("#register-username").val(),
                    "email": $("#register-email").val(),
                    "password": $("#register-password").val(),
                    "check-password": $("#register-check-password").val()
                },function(data){
                    $("#register-dialog").dialog("close")
                    console.log(data);
                    //当返回信息为0提示错误信息
                    if(data==0){
                        alert("信息错误！");
                    }
                },"text");
            },
            取消:function(){
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
                return user[attribute];
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
        console.log(title);
        if(comment!=null){
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
            infor: "comment",
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
        if(title!=null&&content!=null){
            $.post("indexServlet",{
                infor: "doPost",
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

    function getLoginStatus(){
        $.post("indexServlet",{infor: "loginStatus"},function(data){
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
            }
        },"json");
        $.post("indexServlet",{
            infor: "getInfor"
        },function(data){
            var infor = data;
            console.log(infor);
            var userNum = infor["userNum"];
            var topicNum = infor["topicNum"];
            var commentNum = infor["commentNum"];
            $(".user-value")[0].innerHTML = userNum;
            $(".topic-value")[0].innerHTML = topicNum;
            $(".comment-value")[0].innerHTML = commentNum;
        },"json");
    }

    //获取user以及topic
    function start(){
        isOpenPost = false;
        $.post("indexServlet",{infor: "user"},function(data){
            users = data;
            $.post("indexServlet",{infor :"topic"},function(data){
                posts = data;
                for(var i=0;i<posts.length;i++){
                    var post = posts[i];
                    var title = post["topicName"];
                    var content = post["title"];
                    var likes = post["likes"];
                    var poster = post["posterName"];
                    // console.log(post);
                    // console.log(title);
                    var avatar = getUserAttr(poster,"avatar",users);
                    createPost(title,content,avatar,poster,likes);
                }
                getLoginStatus();
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
    
    /*****************************显示可修改帖子*****************************/
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
                            '</div>'+
                        '</div>'+
                    '</div>';
        console.log(title);
        $("#posts").append(postDIV);
    }

    $("#login").on("click",".my-post",function(){
        $.post("indexServlet",{
            infor: "getTopics"
        },function(data){
            $("#posts").empty();
            console.log(data);
            for(var i=0;i<data.length;i++){
                var topic = data[i];
                var title = topic["topicName"];
                var content = topic["title"];
                createEditPost(title,content);
            }
        },"json");
    });

});