var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    var purl=conf.ur+"/api/user/showUserInfo";
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
                var dt=data.data;
                $("#phonenumber").val(dt.mobile);
                $("#email").val(dt.email);
            }else {
                alert(data.msg);
                window.history.back();
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};
function changeInfoAction() {
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if($("#phonenumber").val()==""){
        alert("手机号不能为空！");
        return;
    }
    else{
        var re = /^1\d{10}$/;
        if (!re.test($("#phonenumber").val())) {
            alert("手机号格式错误！");
            return;
        }
    }
    var purl=conf.ur+"/api/user/changeInfoAction";
    var postdata={
        "mobile":$("#phonenumber").val(),
        "email":$("#email").val()
    };
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        data: JSON.stringify(postdata),
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("修改成功!");
                window.history.back();
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}