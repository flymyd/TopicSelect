var userToken = conf.storage.getItem("userToken");
window.onload = function () {
    if (userToken == null || userToken === "") {
        alert("请先登录！");
        window.location.href = conf.ur + "/login";
        return;
    }
    if (conf.storage.getItem("userPermission") != "1") {
        alert("权限不正确，请先登录！");
        window.location.href = conf.ur + "/login";
        return;
    }
    var purl = conf.ur + "/api/teacher/getFirstRoundSelected";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        async:false,
        headers: {
            "x-auth-token": userToken,
            "Content-Type": "application/json"
        },
        success: function (data) {
            if (data.code == 0) {
                console.log(JSON.stringify(data));
                var titlearr=data.data.titleList;
                var trth="<tr><th>姓名</th><th>学号</th><th>选课时间</th><th>操作</th></tr>";
                var panelfoot="</tbody></table></div></div></div>";
                for (var i = 0; i < titlearr.length; i++) {
                    var panelhead="<div class='panel panel-success'><div class='panel-heading'><h4 class='panel-title'>";
                    var panelbody="";
                    panelhead+="<a data-toggle='collapse' data-parent='#firstRoundView' href='#collapse"+i+"'>"+titlearr[i]+"</a></h4></div>";
                    panelhead+="<div id='collapse"+i+"' class='panel-collapse collapse'><div class='panel-body'>";
                    panelhead+="<table class='table table-striped'><thead>"+trth+"</thead><tbody>";
                    for (var j=0;j<data.data.resultTable[titlearr[i]].length;j++){
                        panelbody+="<tr>";
                        panelbody+="<td>"+data.data.resultTable[titlearr[i]][j].truename+"</td>";
                        panelbody+="<td>"+data.data.resultTable[titlearr[i]][j].username+"</td>";
                        panelbody+="<td>"+data.data.resultTable[titlearr[i]][j].selecttime+"</td>";
                        panelbody+="<td><button type='button' class='btn btn-success' onclick='chooseStudentFirstRound("+data.data.resultTable[titlearr[i]][j].userid+","+data.data.resultTable[titlearr[i]][j].topicid+")'>选择该学生</button></td>";
                        panelbody+="</tr>";
                    }
                    $("#firstRoundView").append(panelhead+panelbody+panelfoot);
                }
            } else {
                alert(data.msg);
                window.location.href = conf.ur + "/teacher";
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
};

function chooseStudentFirstRound(userid,topicid) {
    if (userToken == null || userToken === "") {
        alert("请先登录！");
        window.location.href = conf.ur + "/login";
        return;
    }
    if (conf.storage.getItem("userPermission") != "1") {
        alert("权限不正确，请先登录！");
        window.location.href = conf.ur + "/login";
        return;
    }
    if(!window.confirm('确定选择此学生吗？')){
        return;
    }else if(!window.confirm('请再次确认是否要选择此学生！')){
        return;
    }
    var purl = conf.ur + "/api/teacher/chooseStudentFirstRound";
    $.ajax({
        url: purl,
        type: 'post',
        dataType: 'json',
        headers: {
            "x-auth-token": userToken,
            "Content-Type": "application/json"
        },
        data: JSON.stringify({"userid": userid,"topicid":topicid}),
        async:false,
        success: function (data) {
            if (data.code == 0) {
                alert("选择学生成功！");
                window.location.reload();
            } else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("请重试！");
        }
    });
}