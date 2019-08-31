package com.jerry.control.bean;

/**
 * @author Jerry
 * @createDate 2019-08-31
 * @copyright www.aniu.tv
 * @description
 */
public class RequestUser {
    private String username;
    private String userpwd;
    private int level;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(final String userpwd) {
        this.userpwd = userpwd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }
}
