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
    var purl=conf.ur+"/api/admin/listAuditedTopics";
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
                var tabledetail="";
                for (var i=0;i<dt.length;i++){
                    tabledetail+="<tr>";
                    tabledetail+="<td>"+dt[i].truename+"</td>";
                    tabledetail+="<td style='word-wrap:break-word;word-break:break-all;'>"+dt[i].topictitle+"</td>>";
                    tabledetail+="<td>"+dt[i].sumnum+"</td>>";
                    tabledetail+="<td>"+dt[i].availablenum+"</td>>";
                    tabledetail+="<td>"+dt[i].level1+"</td>>";
                    tabledetail+="<td>"+dt[i].address+"</td>>";
                    tabledetail+="<td>"+dt[i].technology+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-primary' onclick='showTopicDetail(\""+dt[i].topicdetail+"\")'>详情</button>"+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-danger' onclick='redoAudit("+dt[i].id+")'>打回审核</button>"+"</td>>";
                    tabledetail+="</tr>"
                }
                $("#topicls").append(tabledetail);
            }else {
                alert(data.msg);
                window.location.href=conf.ur+"/admin";
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};

function showTopicDetail(topicdetail) {
    alert(topicdetail);
}

function redoAudit(topicid) {
    var purl=conf.ur+"/api/admin/redoAudit";
    if(!window.confirm('确定将此课题打回审核吗？')){
        return;
    }
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        data:JSON.stringify({"id":topicid}),
        //async:false,
        success: function (data) {
            if (data.code==0){
                alert("打回审核成功！");
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