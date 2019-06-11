package com.tjrac.topicselect.config;

/**
 * @author myd
 */
public final class Const {
    /**
     * @date 2019年2月6日
     * 根据后端服务器操作系统的不同，这里的路径要变！
     * 目前用的是Linux/Unix系服务器。如果以后迁移到Windows服务器，记得把路径改成具有可写入权限的Temp文件夹。
     * 注意：这里涉及对服务器文件系统的直接读写，务必要做好安全防护。
     */
    //Linux/Unix
    public final static String UPLOADED_FOLDER = "/tmp/";
    //Windows
//    public static final String UPLOADED_FOLDER = "E:\\";

    /**
     * MD5盐常量
     */
    public static final String MD5_PWD_SALT="Tjrac.2@18";
    /**
     * MD5加密DIGITS
     */
    public static final String[] HEX_DIGITS = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
    /**
     * 空指针异常
     */
    public static final String NULL_POINTER_EXCEPTION="java.lang.NullPointerException";
    /**
     * 重复键异常
     */
    public static final String DUPLICATE_KEY_EXCEPTION="Duplicate entry";
    //        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    /**
     * 0管理员 1教师 2学生
     */
    public static final String UserPermissionAdmin ="0";
    public static final String UserPermissionTeacher ="1";
    public static final String UserPermissionStudent ="2";
}
