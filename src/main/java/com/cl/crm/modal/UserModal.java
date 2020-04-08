package com.cl.crm.modal;

public class UserModal {
    private String userIdStr;
    private String userName;
    private String trueName;

    public UserModal() {
    }

    public UserModal(String userIdStr, String userPwd, String trueName) {
        this.userIdStr = userIdStr;
        this.userName = userPwd;
        this.trueName = trueName;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
