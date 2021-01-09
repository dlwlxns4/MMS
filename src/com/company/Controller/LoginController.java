package com.company.Controller;

import com.company.Model.Message;
import com.company.View.JoinView;
import com.company.View.LoginViewPanel;

import javax.swing.*;

public class LoginController{

    public void login(LoginViewPanel panel) { // 입력된 계정으로 로그인 해달라고 서버에 메시지 보내기
        String id = panel.txtId.getText();
        String pw = panel.txtPw.getText();
        String msg = "select * from Accounts where id = "
                + "'" + id + "'" + "and passwd = " + "'" + pw + "'";
        ProgramManager.getInstance().getMainController().msgSend(new Message(id, pw, msg, 2));
    }

    public void join(JoinView frame) { // 입력된 문자열로 계정을 만들어 달라고 서버에 메시지 보내기
        String id = frame.txtId.getText();
        String pw = frame.txtPw.getText();
        String name = frame.txtName.getText();
        String msg= "insert into Accounts(id, passwd, user_name, is_login) values('"
                + id + "', '" + pw + "', '" + name + "', " + 0 + ")";
        if(id != null && pw != null && name != null) {
            ProgramManager.getInstance().getMainController().msgSend(new Message(id, pw, msg, 1));
            frame.dispose();
        }
        else {
            JOptionPane.showMessageDialog(frame, "잘못된 양식입니다.");
        }
    }
}
