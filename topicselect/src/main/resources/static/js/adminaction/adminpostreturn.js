var userToken=conf.storage.getItem("userToken");
var excelJSON=JSON.parse($("#excelJsonStorage").val());
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
    var resultDOM="";
    for (var i=0;i<excelJSON.length;i++){
        resultDOM+="<tr>";
        resultDOM+="<td>"+ excelJSON[i].username +"</td>";
        resultDOM+="<td>"+ excelJSON[i].truename +"</td>";
        resultDOM+="<td>"+ excelJSON[i].permission +"</td>";
        resultDOM+="</tr>";
    }
    $("#resultTbody").html(resultDOM);
};
function addUsersFromExcelAction() {
    var purl=conf.ur+"/api/admin/addUsersFromExcelAction";
    var postData={
      "adminPwd":$("#adminPwd").val(),
      "excelJson": excelJSON
    };
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
                alert("添加用户成功！");
                window.location.href=conf.ur+"/admin";
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("未知异常！请检查该表中的用户信息是否已经在数据库中存在！");
        }
    });
}