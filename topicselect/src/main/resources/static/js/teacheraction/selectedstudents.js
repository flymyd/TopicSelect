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
    var purl=conf.ur+"/api/teacher/selectedStudentsShow";
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
                    tabledetail+="<td>"+dt[i].stutruename+"</td>>";
                    tabledetail+="<td>"+dt[i].stuusername+"</td>>";
                    tabledetail+="<td style='word-wrap:break-word;word-break:break-all;'>"+dt[i].topictitle+"</td>>";
                    tabledetail+="<td>"+dt[i].createtime+"</td>>";
                    tabledetail+="<td>"+dt[i].roundname+"</td>>";
                    tabledetail+="<td>"+"<button type='button' class='btn btn-primary' onclick='showTopicDetail(\""+dt[i].topicdetail+"\")'>详情</button>"+"</td>>";
                    if (dt[i].roundname==="一轮选题"){
                        tabledetail+="<td>"+"<button type='button' class='btn btn-danger' onclick='rejectStudentFirstRound("+dt[i].stuid+","+dt[i].topicid+","+1+")'>打回选择</button>"+"</td>>";
                    } else {
                        tabledetail+="<td>"+"<button type='button' class='btn btn-danger' onclick='rejectStudentFirstRound("+dt[i].stuid+","+dt[i].topicid+","+2+")'>打回选择</button>"+"</td>>";
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

function rejectStudentFirstRound(stuid,topicid,rejectround) {
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
    if(!window.confirm('确定将此学生打回重选吗？本条课题将会允许被其它学生选择。该操作十分危险！')){
        return;
    }else if(!window.confirm('请再次确认是否要将此学生打回重选！本条课题将会允许被其它学生选择。该操作十分危险！')){
        return;
    }else if(!window.confirm('请最后一次确认是否要将此学生打回重选！本条课题将会允许被其它学生选择。该操作十分危险！')){
        return;
    }
    var purl=conf.ur+"/api/teacher/rejectStudentFirstRound";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token":userToken,
            "Content-Type":"application/json"
        },
        data:JSON.stringify({"studentId":stuid,"topicId":topicid,"selectedRound":rejectround}),
        async:false,
        success: function (data) {
            if (data.code==0){
                alert("打回重选成功！");
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