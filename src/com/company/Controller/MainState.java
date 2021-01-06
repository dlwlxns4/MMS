package com.company.Controller;

import com.company.View.MainView;
import com.company.View.ProductViewPanel;
import com.company.View.ViewManager;

import javax.swing.*;

import java.sql.SQLException;

public class MainState implements State{

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
        //패널 생성후 리스너 적용하기
    }

    @Override
    public void drawFrame() {

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
        mainView = ViewManager.getInstance().getMainView();
        mmsListener.getInstance().mainViewPanelListener(mainView.mainViewPanel); // mainViewPanel에 리스너 달기
        mmsListener.getInstance().productViewPanelListener(mainView.productViewPanel); // ProductViewPanel에 리스너 달기
        mmsListener.getInstance().productCRUDViewListener(ViewManager.getInstance().getProductCRUDView()); // CRUDPanel에 리스너달기
        mmsListener.getInstance().chattingViewListener(ViewManager.getInstance().getChattingView()); // cHATTINGvIEW에 리스너달기
    }

    @Override
    public void update() {

    }
}
