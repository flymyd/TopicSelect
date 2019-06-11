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
    var purl=conf.ur+"/api/admin/selectAllUsers";
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
                var thead="<table class='table table-striped'>" +
                    "<thead><tr><th>用户ID</th><th>用户名</th><th>手机号</th><th>电子邮箱</th><th>权限</th><th>状态</th><th>最后登录</th></tr></thead>" +
                    "<tbody>";
                var tfoot="</tbody></table>";
                var tbody="";
                for (var i=0;i<data.data.length;i++){
                    tbody+="<tr>";
                    tbody+="<td><span id='id"+data.data[i].id+"'>"+data.data[i].id+"</span></td>";
                    tbody+="<td><span id='username"+data.data[i].id+"'>"+data.data[i].username+"</span></td>";
                    tbody+="<td><span id='mobile"+data.data[i].id+"'>"+data.data[i].mobile+"</span></td>";
                    tbody+="<td><span id='email"+data.data[i].id+"'>"+data.data[i].email+"</span></td>";
                    switch (data.data[i].permission) {
                        case 0:
                            tbody+="<td><span id='permission"+data.data[i].id+"'>管理员</span></td>";
                            break;
                        case 1:
                            tbody+="<td><span id='permission"+data.data[i].id+"'>教师</span></td>";
                            break;
                        case 2:
                            tbody+="<td><span id='permission"+data.data[i].id+"'>学生</span></td>";
                            break;
                    }
                    tbody+="<td><span id='status"+data.data[i].id+"'>"+data.data[i].status+"</span></td>";
                    tbody+="<td><span id='lastlogin"+data.data[i].id+"'>"+data.data[i].lastlogin+"</span></td>";
                    tbody+="<td><button type='button' class='btn btn-danger' onclick='deleteUserAction("+data.data[i].id+")'>删除用户</button></td>";
                    tbody+="</tr>";
                }
                var tablewrite=thead+tbody+tfoot;
                $("#genTable").append(tablewrite);
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};

function deleteUserAction(userid) {
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
    if(!window.confirm('确定要删除这个用户吗？')){
        return;
    }
    var purl=conf.ur+"/api/admin/deleteUser";
    var usernameid="username"+userid;
    var postDelData={
        "id":userid,
        "username":document.getElementById(usernameid).innerHTML
    };
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        data: JSON.stringify(postDelData),
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        success: function (data) {
            if (data.code==0){
                alert("删除成功");
                window.location.reload();
            }else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });

}