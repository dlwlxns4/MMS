package com.company.Controller;

import com.company.View.MainView;
import com.company.View.ProductViewPanel;
import com.company.View.ViewManager;

import javax.swing.*;

import java.sql.SQLException;

public class MainState implements State{ // 재고관리화면일때의 상태

    ProgramManager manager;
    MainView mainView;
    ProductViewPanel productViewPanel;

    @Override
    public void draw() {
        mainView = ViewManager.getInstance().getMainView();
        mainView.drawMainPanel();
        mainView.drawCustomerViewPanel();
        mainView.drawOrderListViewPanel();
        mainView.drawProductViewPanel();
        mainView.drawMainPanel();
        applyListener();
        // 메인 뷰 프레임에 붙여질 모든 패널 생성후 리스너 붙여주는 메소드 호출
    }

    @Override
    public void drawPanel() {
        mainView = ViewManager.getInstance().getMainView(); // MainView 그리기
        mainView.mainViewPanel.setVisible(true); // mainView 보이게 하기
        mainView.loginViewPanel.setVisible(false); // LoginView 감추기
        mainView.customerViewPanel.setVisible(false); // customerView 감추기
        mainView.orderListViewPanel.setVisible(false); // orderListViewPanel 감추기
        mainView.productViewPanel.setVisible(true); // ProductView나오게 하기
        ViewManager.getInstance().getChattingView().drawView(); // ChattingView 그리기
    }

    @Override
    public void applyListener() {
        mainView = ViewManager.getInstance().getMainView();
        mmsListener.getInstance().mainViewPanelListener(mainView.mainViewPanel); // mainViewPanel에 리스너 달기
        mmsListener.getInstance().productViewPanelListener(mainView.productViewPanel); // ProductViewPanel에 리스너 달기
        mmsListener.getInstance().productCRUDViewListener(ViewManager.getInstance().getProductCRUDView()); // CRUDPanel에 리스너달기
        mmsListener.getInstance().chattingViewListener(ViewManager.getInstance().getChattingView()); // Chatting View에 리스너달기
    }

    @Override
    public void update() {

    }
}
