var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        return;
    }
    if (conf.storage.getItem("userPermission")!="2"){
        alert("权限不正确，请先登录！");
        return;
    }
    var purl=conf.ur+"/api/student/stuFinalCheck";
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
                $("#mytopictitle").text(data.data.topictitle);
                $("#teachername").text(data.data.teachername);
                $("#level1").text(data.data.level1);
                $("#topicaddress").text(data.data.topicaddress);
                $("#technology").text(data.data.technology);
                $("#topicdetail").text(data.data.topicdetail);
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};