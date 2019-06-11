var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
};
function changePasswordAction() {
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if ($("#newpwd1").val()!=$("#newpwd2").val()){
        $("#pwdhint").css("display","inline");
        return;
    }else {
        $("#pwdhint").css("display","none");
    }
    if ($("#oldpwd").val()===""||$("#newpwd1").val()===""||$("#newpwd2").val()===""){
        alert("请勿留空！");
        return;
    }
    if ($("#oldpwd").val()===$("#newpwd1").val()){
        $("#oldhint").css("display","inline");
        return;
    }
    var purl=conf.ur+"/api/user/changePasswordAction";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        data: JSON.stringify({"newPassword":$("#newpwd1").val(),"oldPassword":$("#oldpwd").val()}),
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("修改成功!");
                window.location.href=conf.ur+"/logout";
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}