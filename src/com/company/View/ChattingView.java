package com.company.View;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChattingView extends JFrame{
    JPanel chatNamePanel; // 고객 이름이 적힌 라벨이 부착될 곳
    JLabel chatNameLabel; // 고객 이름이 적힌 라벨
    JPanel msgPanel; // 메시지 내용이 출력될 msgOut이 부착될 곳
    public JTextField msgInput; // 채팅 텍스트 입력할 곳
    public JButton exitButton; // 채팅 창 닫기 버튼
    JTextArea msgOut;

    public ChattingView() { // 채팅 창 생성해주기
        setTitle("::멀티채팅::");
        setLayout(new BorderLayout());

        chatNamePanel = new JPanel();
        chatNamePanel.setLayout(new BorderLayout());

        chatNameLabel = new JLabel("대화명:");

        msgOut = new JTextArea(10,30);

        msgPanel = new JPanel();
        msgPanel.setLayout(new BorderLayout());

        msgInput = new JTextField();
        exitButton = new JButton("닫기");
    }

    public void drawView() { // 채팅 창 그려주기

        chatNamePanel.add(chatNameLabel, BorderLayout.WEST);
        add(chatNamePanel, BorderLayout.PAGE_START);

        msgOut.setEditable(false);

        JScrollPane scroll = new JScrollPane(msgOut);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scroll, BorderLayout.CENTER);

        msgPanel.add(msgInput, BorderLayout.CENTER);
        msgPanel.add(exitButton, BorderLayout.EAST);

        add(msgPanel, BorderLayout.PAGE_END);

        setSize(400,300);
    }

    public void refreshData(String msg) { // 점원들이 전달한 메시지 내용 출력
        msgOut.append(msg + "\n");
    }
}
