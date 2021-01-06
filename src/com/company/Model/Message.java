package com.company.Model;

public class Message {

    private String id; // 서버에 접속한 계정의 id
    private String passwd; // 서버의 접속한 계정의 pw
    private String msg; // 전달할 메시지 내용
    private int type; // 메세지 유형(로그인, 로그아웃, 메세지 전달 등)

    public Message() {
        id = "";
        passwd = "";
        msg = "";
        type = 0;
    }

    public Message(String id, String passwd, String msg, int type) {
        this.id = id;
        this.passwd = passwd;
        this.msg = msg;
        this.type = type;
    }

    public int getType() { return type; }
    public String getId() { return id; }
    public String getMsg() { return msg; }
    public String getPasswd() { return passwd; }

}
