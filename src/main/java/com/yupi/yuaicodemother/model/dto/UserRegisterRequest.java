package com.yupi.yuaicodemother.model.dto;

import java.io.Serializable;

public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    /**
     * 校验密码
     */
    private String checkPassword;
}
