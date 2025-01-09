package io.github.javpower.vectorexclient.res;


public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),

    NO_AUTH(401,"NO_AUTH"),;

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
