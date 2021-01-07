package com.company.Controller;

import com.company.View.LoginViewPanel;
import com.company.View.MainView;
import com.company.View.ViewManager;

import javax.swing.*;

public class LoginState implements State{ // 로그인 화면일때의 상태

    LoginViewPanel loginViewPanel;
    MainView mainView;

    public void draw(){ // 시스템이 시작될때만 호출되는 메소드
        ViewManager.getInstance().getMainView().drawView(); // 메인 프레임을 생성하고 그려주고
        ViewManager.getInstance().getMainView().drawLoginPanel(); // 로그인패널을 생성하고 메인 프레임에 붙여준다.
        applyListener(); // 로그인 버튼들에 리스너를 붙여준다.
    }

    @Override
    public void drawPanel() { // 다른 화면에서 로그인 화면으로 바뀔때 호출되는 메소드
        mainView = ViewManager.getInstance().getMainView();
        if(mainView.mainViewPanel != null ) mainView.mainViewPanel.setVisible(false);
        mainView.loginViewPanel.txtId.setText("");
        mainView.loginViewPanel.txtPw.setText("");
        mainView.loginViewPanel.setVisible(true);
    } // 메인 패널을 안 보이게 하고 로그인 화면을 보이게 한다.

    @Override
    public void applyListener() { // 로그인 화면의 버튼들에 리스너를 붙여준다.
        loginViewPanel = ViewManager.getInstance().getMainView().loginViewPanel;
        mmsListener.getInstance().loginPanelListener(loginViewPanel);
    }

    @Override
    public void update() {

    }
}
