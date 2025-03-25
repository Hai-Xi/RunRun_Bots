package com.test.runrunbots.model.dto.unifiedGlobalResponse;

public enum ReturnCode {
    /**Operation successful**/
    RC100(100,"Operation successful"),
    /**Operation failed**/
    RC999(999,"Operation failed"),
    /**Service flow control**/
    RC200(200,"Service flow control protection enabled, please try again later!"),
    /**Service degradation**/
    RC201(201,"Service degradation protection enabled, please try again later!"),
    /**Hot parameter flow control**/
    RC202(202,"Hot parameter flow control, please try again later!"),
    /**System rules not met**/
    RC203(203,"System rules requirements not met, please try again later!"),
    /**Authorization rules not passed**/
    RC204(204,"Authorization rules not passed, please try again later!"),
    /**access_denied**/
    RC403(403,"No access permission, please contact administrator for authorization"),
    /**access_denied**/
    RC401(401,"Exception when anonymous user accesses restricted resources"),
    /**Service exception**/
    RC500(500,"System exception, please try again later"),

    INVALID_TOKEN(2001,"Access token invalid"),
    ACCESS_DENIED(2003,"No permission to access this resource"),
    CLIENT_AUTHENTICATION_FAILED(1001,"Client authentication failed"),
    USERNAME_OR_PASSWORD_ERROR(1002,"Username or password incorrect"),
    UNSUPPORTED_GRANT_TYPE(1003, "Unsupported authentication mode");



    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
    private final String message;

    ReturnCode(int code, String message){
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
