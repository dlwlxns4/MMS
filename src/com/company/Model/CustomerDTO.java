package com.company.Model;

public class CustomerDTO {
    String cName; // 고객 이름
    String phoneNum; // 고객 폰 번호
    int cPoint; // 고객 포인트

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getCPoint() {
        return cPoint;
    }

    public void setCPoint(int point) {
        this.cPoint = point;
    }
}
