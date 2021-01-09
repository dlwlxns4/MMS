package com.company.Controller;

import com.company.View.*;

import javax.swing.*;

public class CustomerManageState implements State { // 고객관리화면일때의 상태
    MainView mainView;
    CustomerViewPanel customerViewPanel;
    @Override
    public void draw() {

    }

    @Override
    public void drawPanel() { // mainView에 부착된 다른 패널들을 안보이게 하고 customerPanel만 보이게 한다.
        mainView = ViewManager.getInstance().getMainView();
        mainView.orderListViewPanel.setVisible(false);
        mainView.productViewPanel.setVisible(false);
        mainView.customerViewPanel.setVisible(true);
    }


    @Override
    public void applyListener() { // customerPanel에 리스너를 달아준다.

        customerViewPanel = ViewManager.getInstance().getMainView().customerViewPanel;
        mmsListener.getInstance().customerViewPanelListener(customerViewPanel);

    }

    @Override
    public void update() {


    }
}
