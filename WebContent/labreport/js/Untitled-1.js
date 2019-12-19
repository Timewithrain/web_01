$(function(){
    $("#register-dialog").dialog({
        title: "用户注册",
        modal: true,
        autoOpen:false,

    });
    
    $("#btn-register").click(function(){
        $("#register-dialog").dialog("open");
    });
});