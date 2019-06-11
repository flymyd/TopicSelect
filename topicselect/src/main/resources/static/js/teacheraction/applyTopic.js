var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if (conf.storage.getItem("userPermission")!="1"){
        alert("权限不正确，请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
};
function postTopic() {
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if (conf.storage.getItem("userPermission")!="1"){
        alert("权限不正确，请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    var postData={
        topictitle:$("#topictitle").val(),
        level1:$("#level1").val(),
        sumnum:$("#sumnum").val(),
        availablenum:$("#availablenum").val(),
        technology:$("#technology").val(),
        address:$("#address").val(),
        topicdetail:$("#topicdetail").val()
    };
    var purl=conf.ur+"/api/teacher/applyTopicAction";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        data: JSON.stringify(postData),
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("申请成功!");
                window.location.href=conf.ur+"/teacher/showTopic";
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}