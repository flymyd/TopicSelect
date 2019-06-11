var conf={
    ur:"http://"+window.location.host,
    storage:window.localStorage
};
function getCaptcha(){
    var purl=conf.ur+"/getCaptcha";
    $.ajax({
        url: purl,
        type: 'get',
        dataType: 'json',
        headers: {
            "Content-Type":"application/json"
        },
        success: function (data) {
            $("#captchaQuestion").html(data.data.question);
            $("#captchaAnswer").val(data.data.answer);
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}
window.onload = function(){
    getCaptcha();
};
function loginAct() {
    var postdata=JSON.stringify({"username":$("#username").val(),"password":$("#pass").val()});
    var purl=conf.ur+"/loginAction";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        contentType: "application/json",
        // async:false,
        data: postdata,
        complete: function( xhr ){
            // 获取相关Http Response header
            conf.storage.setItem("userToken",xhr.getResponseHeader('x-auth-token'));
            var userpermission=xhr.getResponseHeader('permission');
            conf.storage.setItem("userPermission",userpermission);
            redirectAjax(userpermission,xhr.getResponseHeader('x-auth-token'));
        },
        error:function(XMLHttpRequest){
            if (XMLHttpRequest.status==401){
                alert("用户名或密码错误！");
            }
        }
    });
}

function redirectAjax(permission,token) {
    console.log("执行跳转");
    var permissionurl=conf.ur;
    switch (permission) {
        case "0":
            permissionurl+="/admin";
            break;
        case "1":
            permissionurl+="/teacher";
            break;
        case "2":
            permissionurl+="/student";
            break;
    }
    $.ajax({
        url: permissionurl,
        type: 'get',
        dataType: 'json',
        contentType: "application/json",
        headers: {"x-auth-token":token},
        // async:false,
        complete: function( xhr ){
            window.location.href=permissionurl;
        }
    });
}