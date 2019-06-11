var userToken=conf.storage.getItem("userToken");
window.onload = function() {
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
};
function submitTimePick() {
    var postData={
        uploadstart:$("#upsta").val(),
        uploadend:$("#upend").val(),
        choosestart:$("#chosta").val(),
        chooseend:$("#choend").val()
    };
    var purl=conf.ur+"/api/admin/postTime";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        data: JSON.stringify(postData),
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("修改成功!");
                window.location.href=conf.ur+"/admin/timeshow";
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}