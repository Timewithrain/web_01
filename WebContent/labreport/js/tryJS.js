$(function(){
    
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
                            console.log(re);
                            console.log(re.length);
                            var name = data;
                            var login = $("#login");
                            login.empty();
                            login.append('<a href="#" id="bnt-login">'+name+'</a> | '+
                            '<a href="#" id="bnt-signout">退出</a>');
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

    $(".avatar").tooltip({
        items: "img",
        content: function(){
            return "<img class='avatar-tooltip' src='" + $(this).attr('src') + "' alt='" + $(this).attr('alt') + "'>";
        },
        position:{my:"left+30 center",at:"right center"}
    });

    function createPost(name,avatar,os,title,like){
        var postDIV = '<div id="post" class="post clear-fix">'+
                        '<div id="avatar" class="avatar">'+
                            '<img src="'+avatar+'" alt="">'+
                        '</div>'+
                        '<div class="post-content">'+
                            '<dic class="post-title" title="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestiae dolor magni quasi.">'+
                                title+'</dic>'+
                            '<div class="post-infor">'+
                                os + ' ' + name + ' 分钟前 回复来自ID23333 <span class="reply-count">' + like + '</span>'+
                            '</div>'+
                        '</div>'+
                    '</div>'
        $("#posts").append(postDIV);
    }

    $(function (){
        var users = new Array();
        var topics = new Array();

        $.post("indexServlet",{infor:"user"},function(data){
            users = data;
            $.post("indexServlet",{infor:"topic"},function(data){
                topics = data[0];
                console.log(users);
                console.log(topics);
                // 当user以及topic都返回以后再进行显示
                var title = topics["title"];
                var like = topics["likes"];
                console.log(title+" "+like)
                for(var i=0;i<users.length;i++){
                    var user = users[i];
                    var name = user["name"];
                    var avatar = user["avatar"];
                    var os = user["os"];
                    console.log(name);
                    createPost(name,avatar,os,title,like);
                }
            },"json");
        },"json");
    
        
        
        
    });

});