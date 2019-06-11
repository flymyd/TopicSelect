package com.tjrac.topicselect.bean.request;

import com.tjrac.topicselect.bean.CreateUserFromExcel;
import lombok.Data;

/**
 * @author myd
 */
@Data
public class AddUsersFromExcelReq {
    private String adminPwd;
    private CreateUserFromExcel[] excelJson;
}
