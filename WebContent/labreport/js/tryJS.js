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
                $("#login-dialog").dialog("close");
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
                $("#register-dialog").dialog("close");
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

});