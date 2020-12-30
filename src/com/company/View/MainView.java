package com.company.View;

import javax.swing.*;
import java.awt.*;


public class MainView extends JFrame {
    JPanel buttonPanel, bottomPanel, centerPanel;
    JButton productButton, orderListButton, customerButton, shoppingButton, chatButton;
    JLabel messageLabel, timeLabel, blinkLabel;
    String date, time;

    LoginViewPanel loginViewPanel;
    OrderListViewPanel orderListViewPanel;
    public static void main(String[] args) {
        MainView app = new MainView(); // 메인뷰 프레임 생성
        app.drawView();
//        app.drawLoginPanel();
        app.drawMainPanel();
        app.drawOrderListViewPanel();
        new productCRUDView().drawView();
    }

    public MainView() {

        buttonPanel = new JPanel();
        productButton = new JButton("Product");
        orderListButton = new JButton("OrderList");
        customerButton = new JButton("Customer");
        blinkLabel = new JLabel();
        shoppingButton = new JButton("Shopping");
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        messageLabel = new JLabel("메시지: ");
        chatButton = new JButton("채팅");
        timeLabel = new JLabel("현재 시간: ");

    }

    public void drawView() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MMS");
        setSize(1200, 800);
        setLayout(new BorderLayout());

        setVisible(true);
    }


    public void drawLoginPanel() {
        loginViewPanel = new LoginViewPanel();
        loginViewPanel.drawView();
        getContentPane().add(loginViewPanel);
        setVisible(true);
    }

    public void drawOrderListViewPanel(){
         orderListViewPanel = new OrderListViewPanel();
         orderListViewPanel.drawView();;
         getContentPane().add(orderListViewPanel,BorderLayout.CENTER);
         setVisible(true);
    }

    public void drawMainPanel() {

        buttonPanel.setLayout(new GridLayout(1, 5));

        buttonPanel.add(productButton);
        buttonPanel.add(orderListButton);
        buttonPanel.add(customerButton);
        buttonPanel.add(blinkLabel);
        buttonPanel.add(shoppingButton);

        add(buttonPanel, BorderLayout.PAGE_START);

        centerPanel.setSize(1200, 700);
        bottomPanel.setPreferredSize(new Dimension(1200, 30));
        bottomPanel.setLayout(null);

        add(bottomPanel, BorderLayout.PAGE_END);
        messageLabel.setBounds(20, 0, 600, 30);

        chatButton.setBounds(620, 0, 100, 30);
        timeLabel.setBounds(800, 0, 500, 30);

        bottomPanel.add(messageLabel);
        bottomPanel.add(chatButton);
        bottomPanel.add(timeLabel);




    }

}
