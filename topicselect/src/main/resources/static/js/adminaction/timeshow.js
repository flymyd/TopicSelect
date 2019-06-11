var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if (conf.storage.getItem("userPermission")!="0"){
        alert("权限不正确，请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    var purl=conf.ur+"/api/admin/getSysTime";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        success: function (data) {
            if (data.code==0){
                $("#upsta").html(data.data.uploadstart);
                $("#upend").html(data.data.uploadend);
                $("#chosta").html(data.data.choosestart);
                $("#choend").html(data.data.chooseend);
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};