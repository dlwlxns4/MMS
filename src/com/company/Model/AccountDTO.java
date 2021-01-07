package com.company.Model;

public class AccountDTO {
    private String id,password,userName; // 계정 id, password, 유저 이름
    private boolean isSupperUser,isStaff,isLogin; // 관리자 계정인지, 직원인지, 로그인 상태

    public boolean getIsLogin() { return isLogin; }

    public void setIsLogin(boolean login) { isLogin = login; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsSupperUser() {
        return isSupperUser;
    }

    public void setIsSupperUser(boolean supperUser) {
        isSupperUser = supperUser;
    }

    public boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean staff) {
        isStaff = staff;
    }
}
