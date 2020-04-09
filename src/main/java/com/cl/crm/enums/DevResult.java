package com.cl.crm.enums;

public enum DevResult {
    //未开发
    UNDEV(0),
    //开发中
    DEVING(1),
    //开发成功
    DEV_SUCCESS(2),
    //开发失败
    DEV_FAILED(3);

    private Integer type;

    DevResult(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
