package com.tjrac.topicselect.bean.response;

/**
 * @author myd
 */
public class PostExcelResp {
    private String excelJsonStorage="";
    //<#include "/include/vuetifyheader.html">
    private final String htmlImport= "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui\">\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/googlefonts.css\"/>\n" +
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/vuetify.min.css\"/>\n" +
            "<script src=\"/js/public/conf.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
            "<script src=\"/js/public/vue.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
            "<script src=\"/js/public/vuetify.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
            "<script src=\"/js/public/axios.min.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\n" +
            "";
    //<#include "/include/nav-admin.html">
    private final String htmlNavbar = "<v-navigation-drawer fixed v-model=\"drawer\" left app>\n" +
            "    <v-list>\n" +
            "        <v-list-tile @click=\"naviAdminHome()\">\n" +
            "            <v-list-tile-action>\n" +
            "                <v-icon>home</v-icon>\n" +
            "            </v-list-tile-action>\n" +
            "            <v-list-tile-content>\n" +
            "                <v-list-tile-title>首页</v-list-tile-title>\n" +
            "            </v-list-tile-content>\n" +
            "        </v-list-tile>\n" +
            "        <!-- 系统开放时间 -->\n" +
            "        <v-list-group prepend-icon=\"event\" value=\"true\">\n" +
            "            <v-list-tile slot=\"activator\">\n" +
            "                <v-list-tile-title>系统开放时间管理</v-list-tile-title>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviSysOpenTimeShow()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>insert_drive_file</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>系统开放时间查询</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviSysOpenTimeModify()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>系统开放时间修改</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "        </v-list-group>\n" +
            "        <!-- 审核课题 -->\n" +
            "        <v-list-group prepend-icon=\"spellcheck\" value=\"true\">\n" +
            "            <v-list-tile slot=\"activator\">\n" +
            "                <v-list-tile-title>审核课题</v-list-tile-title>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviToauditTopics()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>insert_drive_file</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>待审核课题</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "                <v-list-tile @click=\"naviAuditedTopics()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>已审核课题</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "        </v-list-group>\n" +
            "        <!-- 选题情况 -->\n" +
            "        <v-list-group prepend-icon=\"check_circle\" value=\"true\">\n" +
            "            <v-list-tile slot=\"activator\">\n" +
            "                <v-list-tile-title>选题情况</v-list-tile-title>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviselectTopicResults()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>insert_drive_file</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>选题结果</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviunChoseStudents()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>未被选择的学生</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviunChoseTopics()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>未被选择的课题</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "        </v-list-group>\n" +
            "        <!-- 用户管理 -->\n" +
            "        <v-list-group prepend-icon=\"group\" value=\"true\">\n" +
            "            <v-list-tile slot=\"activator\">\n" +
            "                <v-list-tile-title>用户管理</v-list-tile-title>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviBatchCreateUsers()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>insert_drive_file</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>批量添加用户</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviCreateUser()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>手动添加用户</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviManageUsers()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>用户管理</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "        </v-list-group>\n" +
            "        <!-- 数据导出 -->\n" +
            "        <v-list-group prepend-icon=\"insert_drive_file\" value=\"true\">\n" +
            "            <v-list-tile slot=\"activator\">\n" +
            "                <v-list-tile-title>数据导出</v-list-tile-title>\n" +
            "            </v-list-tile>\n" +
            "            <v-list-tile @click=\"naviexportTopicResult()\" dark>\n" +
            "                <v-list-tile-action>\n" +
            "                    <v-icon>create</v-icon>\n" +
            "                </v-list-tile-action>\n" +
            "                <v-list-tile-content>\n" +
            "                    <v-list-tile-title>导出选题结果</v-list-tile-title>\n" +
            "                </v-list-tile-content>\n" +
            "            </v-list-tile>\n" +
            "        </v-list-group>\n" +
            "\n" +
            "\n" +
            "    </v-list>\n" +
            "</v-navigation-drawer>\n" +
            "<v-toolbar color=\"teal\" dark fixed app>\n" +
            "    <!-- 把LOGO移动到右边，取消下一行注释即可 -->\n" +
            "    <!-- <v-spacer></v-spacer> -->\n" +
            "    <v-toolbar-title>选题系统</v-toolbar-title>\n" +
            "    <v-toolbar-side-icon @click.stop=\"drawer = !drawer\"></v-toolbar-side-icon>\n" +
            "    <v-spacer></v-spacer>\n" +
            "    <v-btn icon @click=\"naviChangeInfo()\">\n" +
            "        <v-icon>person</v-icon>\n" +
            "    </v-btn>\n" +
            "    <v-btn icon @click=\"naviLogout()\">\n" +
            "        <v-icon>exit_to_app</v-icon>\n" +
            "    </v-btn>\n" +
            "</v-toolbar>\n" +
            "<v-footer color=\"teal\" app>\n" +
            "    <span class=\"white--text\">&emsp;&copy; 2018-2019 天津大学仁爱学院计算机系</span>\n" +
            "</v-footer>";
//    <#include "/include/nav-jump-admin.html">
    private final String htmlJump = "naviAdminHome(){\n" +
        "    let nurl = conf.ur + \"/admin\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviChangeInfo(){\n" +
        "    let nurl = conf.ur + \"/changeinfo\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviLogout(){\n" +
        "    logoutAction();\n" +
        "},\n" +
        "naviSysOpenTimeShow(){\n" +
        "    let nurl = conf.ur + \"/admin/timeshow\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviSysOpenTimeModify(){\n" +
        "    let nurl = conf.ur + \"/admin/timepick\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviToauditTopics(){\n" +
        "    let nurl = conf.ur + \"/admin/toaudittopics\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviAuditedTopics(){\n" +
        "    let nurl = conf.ur + \"/admin/auditedtopics\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviBatchCreateUsers(){\n" +
        "    let nurl = conf.ur + \"/admin/adminuploaduser\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "navidownloadExampleUser(){\n" +
        "    let nurl = conf.ur + \"/examples/users.xlsx\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviCreateUser(){\n" +
        "    let nurl = conf.ur + \"/admin/adminadduser\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviManageUsers(){\n" +
        "    let nurl = conf.ur + \"/admin/manageusers\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviselectTopicResults(){\n" +
        "    let nurl = conf.ur + \"/admin/selecttopicresults\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviunChoseStudents(){\n" +
        "    let nurl = conf.ur + \"/admin/unchosestudents\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviunChoseTopics(){\n" +
        "    let nurl = conf.ur + \"/admin/unchosetopics\";\n" +
        "    window.location.href = nurl;\n" +
        "},\n" +
        "naviexportTopicResult(){\n" +
        "    let nurl = conf.ur + \"/admin/exporttopicresult\";\n" +
        "    window.location.href = nurl;\n" +
        "},";
    private final String htmlHeader = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>管理员控制台</title>\n" +
            htmlImport +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"app\">\n" +
            "    <v-app id=\"inspire\">\n" +
            htmlNavbar +
            "        <v-content>\n" +
            "            <v-container fluid>\n" +
            "                <v-layout justify-center align-center>\n" +
            "                    <v-flex text-xs-center>\n" +
            "                        <v-card>\n" +
            "                            <v-card-title>批量添加用户确认\n" +
            "                                <v-spacer></v-spacer>\n" +
            "                                <v-text-field v-model=\"search\" append-icon=\"search\" label=\"搜索用户名\" single-line\n" +
            "                                              hide-details></v-text-field>\n" +
            "                            </v-card-title>\n" +
            "                            <v-data-table :headers=\"headers\" :items=\"desserts\" :search=\"search\">\n" +
            "                                <template slot=\"items\" slot-scope=\"props\">\n" +
            "                                    <td>{{ props.item.username }}</td>\n" +
            "                                    <td>{{ props.item.truename }}</td>\n" +
            "                                    <td>{{ props.item.permission }}</td>\n" +
            "                                </template>\n" +
            "                                <v-alert slot=\"no-results\" :value=\"true\" color=\"error\" icon=\"warning\">\n" +
            "                                    你所搜索的 \"{{ search }}\" 没有结果。\n" +
            "                                </v-alert>\n" +
            "                            </v-data-table>\n" +
            "                        </v-card>\n" +
            "                        <br/>\n" +
            "                        <div v-if=\"fileok\">\n" +
            "                            <h2>查验无问题后输入你的管理员密码以导入用户</h2>\n" +
            "                            <v-text-field label=\"密码\" type=\"password\" v-model=\"adminpwd\"></v-text-field>\n" +
            "                            <br/>\n" +
            "                            <v-btn color=\"success\" @click=\"addUsers()\">提交</v-btn>\n" +
            "                        </div>\n" +
            "                        <div v-else>\n" +
            "                            <h1>发生错误！请检查上传的文件是否为正确的Excel文件或是否未按规定模板进行编辑！</h1>\n" +
            "                        </div>\n" +
            "                    </v-flex>\n" +
            "                </v-layout>\n" +
            "            </v-container>\n" +
            "        </v-content>\n" +
            "    </v-app>\n" +
            "</div>\n" +
            "<script>\n" +
            "    let userToken = conf.storage.getItem(\"userToken\");\n" +
            "    var vue = new Vue({\n" +
            "        el: '#app',\n" +
            "        data: () => ({\n" +
            "            drawer: null,\n" +
            "            search: '',\n" +
            "            adminpwd: null,\n" +
            "            fileok: true,\n"+
            "            headers: [\n" +
            "                {\n" +
            "                    text: '用户名',\n" +
            "                    align: 'center',\n" +
            "                    sortable: false,\n" +
            "                    value: 'username'\n" +
            "                },\n" +
            "                {\n" +
            "                    text: '姓名',\n" +
            "                    align: 'center',\n" +
            "                    value: 'truename'\n" +
            "                },\n" +
            "                {\n" +
            "                    text: '权限',\n" +
            "                    align: 'center',\n" +
            "                    value: 'permission'\n" +
            "                }\n" +
            "            ],\n" +
            "            desserts: [],\n" +
            "            excelJsonStorage: ";

    private final String htmlFooter = "}),\n" +
            "watch: {\n" +
            "          excelJsonStorage: function () {\n" +
            "              if (this.excelJsonStorage == null){\n" +
            "                    this.fileok = false;\n" +
            "              }\n" +
            "          }  \n" +
            "        },\n" +
            "        mounted: function () {\n" +
            "        if (this.excelJsonStorage == null){\n" +
            "                this.fileok = false;\n" +
            "            }\n" +
            "            let that = this;\n" +
            "            if (userToken == null || userToken === \"\") {\n" +
            "                alert(\"请先登录！\");\n" +
            "                window.location.href = conf.ur + \"/login\";\n" +
            "                return;\n" +
            "            }\n" +
            "            if (conf.storage.getItem(\"userPermission\") != \"0\") {\n" +
            "                alert(\"权限不正确，请先登录！\");\n" +
            "                window.location.href = conf.ur + \"/login\";\n" +
            "                return;\n" +
            "            }\n" +
            "            let dt = this.excelJsonStorage;\n" +
            "            for (let i = 0; i < dt.length; i++) {\n" +
            "                let permis = \"\";\n" +
            "                switch (parseInt(dt[i].permission)) {\n" +
            "                    case 0:\n" +
            "                        permis = \"管理员\";\n" +
            "                        break;\n" +
            "                    case 1:\n" +
            "                        permis = \"教师\";\n" +
            "                        break;\n" +
            "                    case 2:\n" +
            "                        permis = \"学生\";\n" +
            "                        break;\n" +
            "                    default:\n" +
            "                        permis = \"未知错误\";\n" +
            "                        break;\n" +
            "                }\n" +
            "                let tempdata = {\n" +
            "                    username: dt[i].username,\n" +
            "                    truename: dt[i].truename,\n" +
            "                    permission: permis,\n" +
            "                };\n" +
            "                that.desserts.push(tempdata);\n" +
            "            }\n" +
            "        },\n" +
            "        props: {\n" +
            "            source: String\n" +
            "        },\n" +
            "        methods: {\n" +
            htmlJump +
            "            addUsers() {\n" +
            "                let that = this;\n" +
            "                if (userToken == null || userToken === \"\") {\n" +
            "                    alert(\"请先登录！\");\n" +
            "                    window.location.href = conf.ur + \"/login\";\n" +
            "                    return;\n" +
            "                }\n" +
            "                if (conf.storage.getItem(\"userPermission\") != \"0\") {\n" +
            "                    alert(\"权限不正确，请先登录！\");\n" +
            "                    window.location.href = conf.ur + \"/login\";\n" +
            "                    return;\n" +
            "                }\n" +
            "                let purl0 = conf.ur + \"/api/admin/addUsersFromExcelAction\";\n" +
            "                axios({\n" +
            "                    method: 'post',\n" +
            "                    url: purl0,\n" +
            "                    headers: {\n" +
            "                        \"x-auth-token\": userToken,\n" +
            "                        \"Content-Type\": \"application/json\"\n" +
            "                    },\n" +
            "                    data: {\n" +
            "                        adminPwd: that.adminpwd,\n" +
            "                        excelJson: that.excelJsonStorage\n" +
            "                    }\n" +
            "                }).then(function (response) {\n" +
            "                    if (response.data.code == 0) {\n" +
            "                        alert(\"添加成功！\");\n" +
            "                        window.location.href = conf.ur + \"/admin\";\n" +
            "                    } else {\n" +
            "                        alert(response.data.msg);\n" +
            "                        window.location.href = conf.ur + \"/admin\";\n" +
            "                    }\n" +
            "                }).catch(function (error) {\n" +
            "                    if (error.response.status === 400) {\n" +
            "                        alert(\"你的请求有误！\");\n" +
            "                        window.location.href = conf.ur + \"/\";\n" +
            "                    } else if (error.response.status === 401) {\n" +
            "                        alert(\"登录态失效，请重新登录！\");\n" +
            "                        window.location.href = conf.ur + \"/login\";\n" +
            "                    } else {\n" +
            "                        alert(\"内部错误！\");\n" +
            "                        window.location.href = conf.ur + \"/\";\n" +
            "                    }\n" +
            "                });\n" +
            "            }\n" +
            "        }\n" +
            "    })\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";

    public PostExcelResp(String excelJsonStorage){
        this.excelJsonStorage = excelJsonStorage;
    }

    public String getPostExcelHtmlResp() {
        return this.htmlHeader +  this.excelJsonStorage  + this.htmlFooter;
    }
}
