var userToken=conf.storage.getItem("userToken");
window.onload = function(){
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if (conf.storage.getItem("userPermission")!="2"){
        alert("权限不正确，请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    var purl=conf.ur+"/api/student/listAuditedTopics";
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
                    tabledetail+="<td>"+dt[i].selectednum+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-primary' onclick='showTopicDetail(\""+dt[i].topicdetail+"\")'>详情</button>"+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-success' onclick='stuApplyTopicAction("+dt[i].id+")'>申请课题</button>"+"</td>>";
                    tabledetail+="</tr>"
                }
                $("#topicls").append(tabledetail);
            }else {
                alert(data.msg);
                window.location.href=conf.ur+"/student";
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

function stuApplyTopicAction(topicid) {
    if (userToken==null||userToken===""){
        alert("请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    if (conf.storage.getItem("userPermission")!="2"){
        alert("权限不正确，请先登录！");
        window.location.href=conf.ur+"/login";
        return;
    }
    var purl=conf.ur+"/api/student/stuApplyTopicAction";
    if(!window.confirm('确定申请此课题吗？')){
        return;
    }else if(!window.confirm('请再次确认是否要申请该课题！')){
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
        data:JSON.stringify({"topicId":topicid}),
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("申请课题成功！");
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