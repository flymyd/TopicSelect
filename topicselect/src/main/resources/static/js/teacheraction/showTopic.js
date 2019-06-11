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
    var purl=conf.ur+"/api/teacher/getMyTopic";
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
                    tabledetail+="<td style='word-wrap:break-word;word-break:break-all;'>"+dt[i].topictitle+"</td>>";
                    tabledetail+="<td>"+dt[i].sumnum+"</td>>";
                    tabledetail+="<td>"+dt[i].availablenum+"</td>>";
                    tabledetail+="<td>"+dt[i].level1+"</td>>";
                    tabledetail+="<td>"+dt[i].address+"</td>>";
                    tabledetail+="<td>"+dt[i].technology+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-primary' onclick='showTopicDetail(\""+dt[i].topicdetail+"\")'>详情</button>"+"</td>>";
                    switch (dt[i].status) {
                        case 0:
                            tabledetail+="<td>"+"<button type='button' class='btn btn-warning'>未审核</button>"+"</td>>";
                            tabledetail+="<td>"+"<button type='button' class='btn btn-danger' onclick='deleteTopic("+dt[i].id+")'>取消申请</button>"+"</td>>";
                            break;
                        case 1:
                            tabledetail+="<td>"+"<button type='button' class='btn btn-success'>已审核</button>"+"</td>>";
                            tabledetail+="<td>"+"<button type='button' class='btn btn-dark' disabled='disabled' onclick=''>无</button>"+"</td>>";
                            break;
                        case 2:
                            tabledetail+="<td>"+"<button type='button' class='btn btn-primary'>已失效</button>"+"</td>>";
                            tabledetail+="<td>"+"<button type='button' class='btn btn-dark' disabled='disabled' onclick=''>无</button>"+"</td>>";
                    }
                    tabledetail+="</tr>"
                }
                $("#topicls").append(tabledetail);
            }else {
                alert(data.msg);
                window.location.href=conf.ur+"/teacher";
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

function deleteTopic(topicid) {
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
    var purl=conf.ur+"/api/teacher/deleteMyTopic";
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
                alert("取消课题成功！");
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