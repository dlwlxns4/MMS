package com.company.View;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
/*
 * 메인 프레임은 시스템의 프레임이다.
 * 로그인패널과 메인패널이 메인 프레임 위에 붙여진다.
 * 메인패널 위에 공통으로 작업되는 버튼들이 붙여지며, 메인패널 중앙에 재고관리화면, 주문관리화면, 고객관리화면이 붙여진다.
 */
public class MainView extends JFrame {

    public LoginViewPanel loginViewPanel;
    public OrderListViewPanel orderListViewPanel;
    public CustomerViewPanel customerViewPanel;
    public ProductViewPanel productViewPanel;
    public MainViewPanel mainViewPanel;
    public MainView() { }

    public void drawView() { // 시스템 프레임 생성

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MMS");
        setSize(1200, 800);
        setLayout(new BorderLayout());

        setVisible(true);
    }


    public void drawLoginPanel() { // 시스템 프레임에 로그인 화면 붙이고 그리기
        if(loginViewPanel == null) loginViewPanel = new LoginViewPanel();
        loginViewPanel.drawView();
        getContentPane().add(loginViewPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public void drawOrderListViewPanel(){ // 시스템 프레임에 주문관리화면 붙이고 그리기
         if(orderListViewPanel == null) orderListViewPanel = new OrderListViewPanel();

         orderListViewPanel.drawView();
         mainViewPanel.add(orderListViewPanel,BorderLayout.CENTER);
         setVisible(true);
    }
    public void drawCustomerViewPanel(){ // 시스템 프레임에 고객관리화면 붙이고 그리기
        if(customerViewPanel == null) customerViewPanel = new CustomerViewPanel();
        customerViewPanel.drawView();
        mainViewPanel.add(customerViewPanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public void drawProductViewPanel(){ // 시스템 프레임에 재고관리화면 붙이고 그리기
        if(productViewPanel == null) productViewPanel = new ProductViewPanel();
        try {
            productViewPanel.drawView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mainViewPanel.add(productViewPanel, BorderLayout.CENTER);
        setVisible(true);

    }
    public void drawMainPanel() { // 시스템 프레임에 메인패널 붙이고 그리기
        if(mainViewPanel == null) {
            mainViewPanel = new MainViewPanel();
            mainViewPanel.drawView();
            add(mainViewPanel);

        }  setVisible(true);



    }


}
