$(function(){
    
    $("#login-bnt").click(function(){
        $("#login-dialog").dialog("open");
    });

    $("#login-dialog").dialog({
        title: "用户登录",
        width: 380,
        hegiht: 240,
        autoOpen: false,
        // buttons:{
        //     登录:function(){
        //         var form = $("#login-dialog").children();
        //         alert(form[0].innerHTML);
        //         form[0].submit();
        //         $("#login-dialog").dialog("close");
        //     },
        //     取消:function(){
        //         $("#login-dialog").dialog("close");
        //     }
        // }
    });

    $("#login-close").click(function(){
        $("#login-dialog").dialog("close");        
    });

    $("#register-bnt").click(function(){
        $("#register-dialog").dialog("open");
    });

    $("#register-dialog").dialog({
        title: "用户注册",
        width: 390,
        hegiht: 260,
        autoOpen: false,
        // buttons:{
        //     注册:function(){
        //         var form = $("#register-dialog").children();
        //         alert(form[0].innerHTML);
        //         $.ajax({
        //             url: form.attr("action"),
        //             type: form.attr("method"),
        //             data: form.serialize(),
        //             dataType: "json",
        //         });
        //         $("#register-dialog").dialog("close");
        //     },
        //     取消:function(){
        //         $("#register-dialog").dialog("close");
        //     }
        // }
    });

    $("#register-close").click(function(){
        $("#register-dialog").dialog("close");        
    });

    // 设置app被选中的效果,并且将其绑定在document上使后添加的app也能使用
    $(document).on("click",".app",function(){
        if($(this).hasClass("app-choose")){
            $(this).removeClass("app-choose");
        }else{
            $(this).addClass("app-choose");
        }
    });

    // 设施服务器被选中的效果
    $(".server-check").click(function(){
        if($(this).parent().hasClass("server-choose")){
            $(this).parent().removeClass("server-choose");
        }else{
            $(this).parent().addClass("server-choose");
        }
    });

    $("#add").click(function(){
        // 获取选中的app的内容
        var addapp = $(".app-choose")[0];
        var oriservers = $(".server-choose").next();
        //当服务和服务器都被选中时才进行添加
        if( addapp!=null && oriservers.is("div")){
            //获取添加服务的内容
            var str = addapp.innerText;
            var first = oriservers.children("div");
            // 判断服务是否被添加
            var i = 0
            while(i<first.length){
                if(first[i].innerHTML==str){
                    alert("此服务已经被添加");
                    return;
                }
                i++;
            }
            var newApp = '<div class="app">'+str+'</div>';
            oriservers.append(newApp);
            //当两个服务器都添加了同一个app时将左边的app删去
            if(isBothAdd(str)){
                $("#app-content")[0].removeChild(addapp);
            }
        }else{
            // var newSpan = document.createElement("span");
            // newSpan.innerText = str;
            alert("没有选择服务或服务器");
        }
    });

    //两个服务器都添加了同一个服务则将左边的服务中的app删去
    var isBothAdd = function(str){
        // 获取所有已经添加的app
        var apps = $(".oriservers").children(".app");
        var isboth = false;
        var count = 0;
        var i = 0;
        while(i<apps.length){
            // 依次比较添加的app的内容是否有相同的
            if(apps[i].innerHTML==str){
                count++;
            }
            i++;
        }
        if(count>1){
            isboth = true;
        }
        return isboth;
    }

    $("#del").click(function(){
        //依次删除两个oriserver中的服务
        var oriservers = $("#oriservers1");
        var toremove = oriservers.children(".app-choose");
        var j = 0;
        while(j<toremove.length){
            oriservers[0].removeChild(toremove[j]);
            var str = toremove[j].innerHTML;
            if(isBothDelete(str)){
                var newApp = '<div class="app">'+str+'</div>';
                $("#app-content").append(newApp);
            }
        }
        var oriservers = $("#oriservers2");
        var toremove = oriservers.children(".app-choose");
        var j = 0;
        while(j<toremove.length){
            oriservers[0].removeChild(toremove[j]);
            var str = toremove[j].innerHTML;
            if(isBothDelete(str)){
                var newApp = '<div class="app">'+str+'</div>';
                $("#app-content").append(newApp);    
            }
        }
        
    });

    //判断app在两个oriserver中是否被彻底删除
    var isBothDelete = function(str){
        var isDelete = false;
        var apps = $(".oriservers").children(".app");
        var count = 0;
        var i = 0;
        while(i<apps.length){
            if(apps[i].innerHTML==str){
                count++;
            }
            i++;
        }
        if(count==0){
            isDelete = true;
        }
        return isDelete;
    }

});