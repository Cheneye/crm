package com.cl.crm.enums;

public enum CustomerServeStatus  {
    // 创建
    CREATED("fw_001"),
    // 分配
    ASSIGNED("fw_002"),
    // 处理
    PROCED("fw_003"),
    // 反馈
    FEED_BACK("fw_004"),
    // 归档
    ARCHIVED("fw_005");

    private String status;

    CustomerServeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
