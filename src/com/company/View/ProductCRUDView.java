package com.company.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductCRUDView extends JFrame {
    public JLabel codeLabel,nameLabel,priceLabel,locationLabel,expDateLabel,countLabel;
    public JTextField codeText,nameText,priceText,locationText,expDateText,countText;
    public JButton completeButton;
    public JPanel centerPanel,bottomPanel;
    public int chk=0;

    public boolean editMode;


    public ProductCRUDView(){
        codeLabel = new JLabel("Code");
        nameLabel = new JLabel("Name");
        priceLabel = new JLabel("Price");
        locationLabel = new JLabel("Location");
        expDateLabel = new JLabel("Expiration date");
        countLabel = new JLabel("Count");

        codeText = new JTextField(15);
        nameText = new JTextField(15);
        priceText = new JTextField(15);
        locationText = new JTextField(15);
        expDateText = new JTextField(15);
        countText = new JTextField(15);

        completeButton = new JButton("Complete");

        centerPanel = new JPanel();
        bottomPanel = new JPanel();
    }
    public void drawView(){

        setTitle("상품 등록");
        setSize(400,470);
        setLayout(new BorderLayout());

        centerPanel.setSize(400,470);
        centerPanel.setLayout(null);

        //상품코드라벨
        codeLabel.setBounds(10,10,190,50);
        codeLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(codeLabel);
        //상품코드텍스트필드
        codeText.setBounds(180,10,190,50);
        codeText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(codeText);

        //상품이름라벨
        nameLabel.setBounds(10,70,190,50);
        nameLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(nameLabel);
        //상품이름텍스트필드
        nameText.setBounds(180,70,190,50);
        nameText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(nameText);

        //상품가격라벨
        priceLabel.setBounds(10,130,190,50);
        priceLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(priceLabel);
        //상품가격텍스트필드
        priceText.setBounds(180,130,190,50);
        priceText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(priceText);

        //상품위치라벨
        locationLabel.setBounds(10,190,190,50);
        locationLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(locationLabel);
        //상품위치텍스트필드
        locationText.setBounds(180,190,190,50);
        locationText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(locationText);

        //상품유통기한라벨
        expDateLabel.setBounds(10,250,190,50);
        expDateLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(expDateLabel);
        //상품유통기한텍스트필드
        expDateText.setBounds(180,250,190,50);
        expDateText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(expDateText);

        //상품재고라벨
        countLabel.setBounds(10,310,190,50);
        countLabel.setFont(new Font("", Font.BOLD, 20));
        centerPanel.add(countLabel);
        //상품재고텍스트필드
        countText.setBounds(180,310,190,50);
        countText.setFont(new Font("", Font.BOLD, 17));
        centerPanel.add(countText);

        //완료버튼
        completeButton.setBounds(125, 370, 150,50);
        centerPanel.add(completeButton);
        add(centerPanel,BorderLayout.CENTER);

        setVisible(true);
    }
}
