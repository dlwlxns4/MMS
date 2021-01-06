package com.company.View;

import com.company.Controller.CustomerController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerViewPanel extends JPanel {

    public JPanel optionPanel, cusInfoPanel, newCustomerPanel;
    public JTextField txtPhoneNum;
    public JTable tblCustomerList;
    public JButton searchButton, addButton, updateButton, deleteButton;
    public JTextArea taNewCustomer;
    public JLabel lblInfoMessage, lblNewCustomer;
    public JScrollPane jsp;
    String header[] = {"휴대폰 번호", "고객 이름", "누적 포인트"};
    public DefaultTableModel dtmodel;
    public Font fnt;

    public CustomerViewPanel() { // 고객 등록 창 생성

        // 폰트 설정
        fnt = new Font("Dialog", Font.BOLD, 15);

        // -------optionPanel--------
        optionPanel = new JPanel(); // 상단 조회 및 관리 패널

        txtPhoneNum = new JTextField(15);
        searchButton = new JButton("조회");
        addButton = new JButton("등록");
        updateButton = new JButton("수정");
        deleteButton = new JButton("삭제");

        // ------- cusInfoPanel -------
        cusInfoPanel = new JPanel(); // 고객 칼럼 라벨들 add 할 패널

        lblInfoMessage = new JLabel("고객 정보 리스트");

        // ------- tblCustomerList -------
        dtmodel = new DefaultTableModel(null, header);
        tblCustomerList = new JTable(dtmodel);
        jsp = new JScrollPane(tblCustomerList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // -------newCustomerPanel-------
        newCustomerPanel = new JPanel();
        lblNewCustomer = new JLabel("신규 고객");
        taNewCustomer = new JTextArea(" ", 5, 5);

    }

    public void drawView() { // 고객 등록 창 그려주기

        setSize(new Dimension(1200,700));
        setLayout(new BorderLayout());

        // ------- optionPanel --------
        optionPanel.setLayout(new FlowLayout());

        optionPanel.add(txtPhoneNum);
        optionPanel.add(searchButton);
        optionPanel.add(addButton);
        optionPanel.add(updateButton);
        optionPanel.add(deleteButton);

        // ------- cusInfoPanel -------
        cusInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        jsp.setPreferredSize(new Dimension(800,600));

        newCustomerPanel.setPreferredSize(new Dimension(200, 400));
        newCustomerPanel.setLayout(new BorderLayout());

        lblNewCustomer.setFont(fnt);
        newCustomerPanel.add(lblNewCustomer, BorderLayout.PAGE_START);
        newCustomerPanel.add(taNewCustomer, BorderLayout.CENTER);

        cusInfoPanel.add(jsp);
        cusInfoPanel.add(newCustomerPanel);

        add(optionPanel, BorderLayout.PAGE_START);
        add(cusInfoPanel, BorderLayout.CENTER);

    }

    public void drawTextArea(String text) { // 신규 고객 안내 창에 추가될 내용 추가해주기
        taNewCustomer.append(text + "\n");
    }

    public void initDTModel() { // TableModel 초기화 해주기
        dtmodel.setNumRows(0);
    }

    public void addRowToTable(String line[]) { // Table Model에 전달 된 내용으로 새로운 줄 추가
        dtmodel.addRow(line);
    }
}

