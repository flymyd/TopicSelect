// var conf={
//     ur:"http://"+window.location.host,
//     storage:window.localStorage
// };
var conf={
    ur:"http://www.tjracoj.com:9999",
    storage:window.localStorage
};
function logoutAction() {
    var userToken=conf.storage.getItem("userToken");
    conf.storage.setItem("userToken","");
    conf.storage.setItem("userPermission","");
    var purl=conf.ur+"/logout";
    $.ajax({
        url: purl,
        type: 'get',
        dataType: 'json',
        contentType: "application/json",
        headers: {"x-auth-token":userToken},
        // async:false,
        complete: function( xhr ){
            window.location.href=conf.ur+"/login";
        }
    });
}