package com.pku.system.exception;

public class UnauthorizedException extends RuntimeException {
    private String retCd ;  //异常对应的返回码
    private String msgDes;  //异常对应的描述信息

    public UnauthorizedException(String retCd, String msgDes) {
        super();
        this.retCd = retCd;
        this.msgDes = msgDes;
    }

    public String getRetCd() {
        return retCd;
    }

    public String getMsgDes() {
        return msgDes;
    }

    @Override
    public String toString() {
        return "UnauthorizedException{" +
                "retCd='" + retCd + '\'' +
                ", msgDes='" + msgDes + '\'' +
                '}';
    }
}
