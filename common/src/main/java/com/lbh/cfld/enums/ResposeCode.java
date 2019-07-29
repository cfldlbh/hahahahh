package com.lbh.cfld.enums;

public enum  ResposeCode {

    SUCCESS(200),
    FAIL(500),
    LGOINFAIL(4002);
    private Integer i ;
    ResposeCode(Integer i){
        this.i = i;
    }

    public Integer getI() {
        return i;
    }
}
