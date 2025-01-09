package io.github.javpower.vectorexserver.response;


public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    PARAM_ERROR(2,"PARAM_ERROR"),

    NO_AUTH(401,"NO_SYSTEM_AUTH"),
    ERR_AUTH(403,"ILLEGAL_ARGUMENT")
    ;

    private  final  int code;
    private final  String desc;

    ResponseCode(int code ,String desc){
        this.code = code;
        this.desc =desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
