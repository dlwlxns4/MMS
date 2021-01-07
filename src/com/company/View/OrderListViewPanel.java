package com.company.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderListViewPanel extends JPanel {
    JPanel ListPanel;
    JPanel revenuePanel;
    JLabel revenueMonth; //월별 매출
    JLabel revenueDay; //요일별 매출
    JLabel maxMonth; // 최고월 표기라벨
    JLabel minMonth; // 최저월 표기라벨
    JLabel maxDay; //최고요일 표기라벨
    JLabel minDay; //최저요일 표기라벨
    JLabel revenueTotal; // 총합 금액
    JLabel total;//총합금액표기라벨
    JTable jt;

    // --------- 최상단 라벨을 콤보 박스로.. ---------
    public JComboBox cb;
    public JButton btnSerach;

    public JTable orderTable, orderHistoryTable;
    public JScrollPane orderScroll, orderHistoryScroll;
    public String orderHeader[] = { "orderCode","entryPrice","cName","phoneNum","buyDate"}, orderContents[][];
    public String orderHistoryHeader[] = { "HistoryId", "orderCode","prCode","prName", "prCount", "prPrice"}, orderHistoryContents[][];
    public DefaultTableModel orderModel;
    public DefaultTableModel orderHistoryModel;
    // -------------------------------------------

    public OrderListViewPanel() {

        //ListPanel
        ListPanel = new JPanel();

        //OrderLab
        String menu[] = {"주문 내역", "주문 상세 내역"};
        cb = new JComboBox(menu); // 상품을 콤보박스에서 조회할 수 있도록 생성
        btnSerach = new JButton("조회");

        //orderHistory View
        orderModel = new DefaultTableModel(orderHeader,0);
        orderHistoryModel = new DefaultTableModel(orderHistoryHeader,0);
        orderTable = new JTable(orderModel);
        orderHistoryTable = new JTable(orderHistoryModel);
        orderScroll = new JScrollPane(orderTable);
        orderHistoryScroll = new JScrollPane(orderHistoryTable);

        //revenue panel + Label
        revenuePanel = new JPanel();
        revenueDay = new JLabel("");
        minDay = new JLabel("");
        maxDay = new JLabel("");
        revenueMonth = new JLabel("");
        minMonth = new JLabel("");
        maxMonth = new JLabel("");
        revenueTotal = new JLabel("");
        total = new JLabel("");

    }

    public void drawView(){

        this.setPreferredSize(new Dimension(1200,500));
        this.setLayout(null);

        //ListPanel
        ListPanel.setLayout(null);
        ListPanel.setBackground(Color.gray);
        ListPanel.setBounds(10,10,1170,50);

        cb.setBounds(30,5,130,40);
        btnSerach.setBounds(170, 5, 70, 40);

        orderScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        orderScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderScroll.setBounds(10, 70,800,500);
        orderScroll.setVisible(true);

        orderHistoryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        orderHistoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderHistoryScroll.setBounds(10, 70,800,500);
        orderHistoryScroll.setVisible(false);

        //revenuePanel
        revenuePanel.setBackground(Color.lightGray);
        revenuePanel.setBounds(820,70,360,500);
        revenuePanel.setLayout(null);
        //revenue panel + Label
        revenueDay.setBounds(20,20,320,30);
        minDay.setBounds(20,60,320,30);
        maxDay.setBounds(20,100,320,30);
        revenueMonth.setBounds(20,140,320,30);
        minMonth.setBounds(20,180,320,30);
        maxMonth.setBounds(20,220,320,30);
        revenueTotal.setBounds(20,260,320,30);
        total.setBounds(20,300,320,30);

        revenueDay.setFont(new Font("",Font.BOLD,20));
        revenueMonth.setFont(new Font("",Font.BOLD,20));
        revenueTotal.setFont(new Font("",Font.BOLD,20));
        total.setFont(new Font("", Font.BOLD,15));


        //add
        this.add(ListPanel);
        ListPanel.add(cb);
        ListPanel.add(btnSerach);
        this.add(orderScroll);
        this.add(orderHistoryScroll);
        this.add(revenuePanel);
        revenuePanel.add(revenueDay);
        revenuePanel.add(revenueMonth);
        revenuePanel.add(revenueTotal);
        revenuePanel.add(minDay); revenuePanel.add(maxDay);
        revenuePanel.add(minMonth); revenuePanel.add(maxMonth);
        revenuePanel.add(total);

    }

}

