package com.company.Controller;

import com.company.View.MainView;
import com.company.View.OrderListViewPanel;
import com.company.View.ProductViewPanel;
import com.company.View.ViewManager;

import javax.swing.*;

public class OrderManageState implements State{ // 주문관리화면일 때 상태
    OrderListViewPanel listViewPanel;
    MainView mainView;

    @Override
    public void draw() {
    }

    @Override
    public void drawPanel() { // 고객관리화면, 재고관리화면을 안보이게 하고, 주문관리화면만 보이게 하기
        mainView = ViewManager.getInstance().getMainView();
        mainView.customerViewPanel.setVisible(false);
        mainView.productViewPanel.setVisible(false);
        mainView.orderListViewPanel.setVisible(true);
    }

    @Override
    public void applyListener() { // 주문관리화면에 있는 버튼에 리스너 달아주기
        listViewPanel = ViewManager.getInstance().getMainView().orderListViewPanel;
        mmsListener.getInstance().orderListViewPanelListener(listViewPanel);
    }

    @Override
    public void update() {

    }
}
