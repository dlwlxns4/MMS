package com.company.Controller;

import com.company.Model.*;
import com.company.View.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.company.View.*;
import com.google.gson.Gson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class mmsListener {
    private Gson gson = new Gson();
    private Message msg;

    private static mmsListener s_Instance;
    public static mmsListener getInstance(){ // 싱글톤 패턴 적용
        if (s_Instance == null) s_Instance = new mmsListener();
        return s_Instance;
    }

    public void loginPanelListener(LoginViewPanel panel){ // 로그인 화면의 버튼에 리스너 달기
        panel.loginButton.addActionListener(e -> { // 로그인 메서드 실행
            ProgramManager.getInstance().getLoginController().login(panel);
        });
        panel.joinButton.addActionListener(e -> { // 회원가입 버튼에 리스너 달기
            ViewManager.getInstance().joinViewOpen();
            joinViewListener(ViewManager.getInstance().getJoinView());
        });
    }

    public void joinViewListener(JoinView frame){ // 회원가입 창의 등록 버튼에 리스너 달기
        frame.joinButton.addActionListener(e -> { // 조인 메서드 실행
            ProgramManager.getInstance().getLoginController().join(frame);
        });

    }

    public void mainViewPanelListener(MainViewPanel panel){ // 메인 패널에 존재하는 각 버튼이 수행하는 기능의 리스너달기

        MainView mainView = ViewManager.getInstance().getMainView();
        panel.productButton.addActionListener(e -> { // Product 버튼에 리스너 달기
            if(ProgramManager.getInstance().getState() instanceof OrderManageState){
                mainView.orderListViewPanel.setVisible(false);
                ProgramManager.getInstance().setMainState();
            } // 현재 주문관리상태였다면 주문관리화면 안 보이게 하고 재품관리 상태로 바꾸기
            else if(ProgramManager.getInstance().getState() instanceof CustomerManageState){
                mainView.customerViewPanel.setVisible(false);
                ProgramManager.getInstance().setMainState();
            } // 현재 고객관리상태였다면 고객관리화면 안 보이게 하고 재품관리 상태로 바꾸기
        });
        panel.orderListButton.addActionListener(e-> { // OrderList 버튼에 리스너 달기
            if(ProgramManager.getInstance().getState() instanceof MainState) {
                mainView.productViewPanel.setVisible(false);
                ProgramManager.getInstance().setOrderManageState();
            } // 현재 제품관리상태였다면 제품관리화면 안 보이게 하고 주문관리상태로 바꾸기
            else if(ProgramManager.getInstance().getState() instanceof CustomerManageState){
                mainView.customerViewPanel.setVisible(false);
                ProgramManager.getInstance().setOrderManageState();
            } // 현재 고객관리상태였다면 고객관리화면 안 보이게 하고 주문관리상태로 바꾸기
        });
        panel.customerButton.addActionListener(e->{ // Customer 버튼에 리스너 달기
            if(ProgramManager.getInstance().getState() instanceof MainState) {
                mainView.productViewPanel.setVisible(false);
                ProgramManager.getInstance().setCustomerManageState();
            } // 현재 제품관리상태였다면 제품관리화면 안 보이게 하고 고객관리상태로 바꾸기
            else if(ProgramManager.getInstance().getState() instanceof OrderManageState){
                mainView.orderListViewPanel.setVisible(false);
                ProgramManager.getInstance().setCustomerManageState();
            } // 현재 주문관리상태였다면 주문관리화면 안 보이게 하고 고객관리상태로 바꾸기
        });
        panel.shoppingButton.addActionListener(e -> { // Shopping 버튼에 리스너 달기
            ShoppingView shoppingView = ViewManager.getInstance().getShoppingView();
            shoppingView.setVisible(true);
            if(!ViewManager.getInstance().isShoppingViewOpen())
                shoppingViewListener(shoppingView);
        }); // 쇼핑 버튼 누르면 장바구니 창 나오게 하기
        panel.logoutButton.addActionListener(e -> { // Logout 버튼에 리스너 달기
            msg = new Message(ProgramManager.getInstance().id, ProgramManager.getInstance().pw,  "로그아웃", 3);
            ProgramManager.getInstance().getMainController().msgSend(msg);
            if(ProgramManager.getInstance().getState() instanceof MainState) {
                mainView.productViewPanel.setVisible(false);
            } else if(ProgramManager.getInstance().getState() instanceof CustomerManageState) {
                mainView.customerViewPanel.setVisible(false);
            } else if(ProgramManager.getInstance().getState() instanceof OrderManageState){
                mainView.orderListViewPanel.setVisible(false);
            }
            panel.setVisible(false);
            ProgramManager.getInstance().setLoginState();
        }); // 각 상태에 따라 해당 화면 안 보이게 하고 main패널을 보이지 않게 한 후 로그아웃상태로 바꾸기
        panel.chatButton.addActionListener(e -> { // chat 버튼에 리스너 달기
            ViewManager.getInstance().getChattingView().setVisible(true);
        }); // 채팅창 보이게 하기
    }

    public void productViewPanelListener(ProductViewPanel panel){
        ProductDAO dao = new ProductDAO();

        panel.addButton.addActionListener(e -> {
            try {
                ProgramManager.getInstance().getPC().addProduct();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }); //상품등록 버튼 리스너
        panel.searchButton.addActionListener(e -> {
            try {
                ProgramManager.getInstance().getPC().searchProduct(dao, panel.editMode, panel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            panel.editMode = true;
        }); //검색 버튼 리스너
        panel.deleteButton.addActionListener(e -> {
            try {
                ProgramManager.getInstance().getPC().deleteProduct(panel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }); //삭제버튼 리스너
        panel.updateButton.addActionListener(e -> {
            try {
                ProgramManager.getInstance().getPC().updateProduct();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }); // 수정버튼 리스너

        panel.productTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ProgramManager.getInstance().getPC().isClick =true;
                    ProgramManager.getInstance().getPC().appMain();  //클릭한 상품의 Pr코드 넣기
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        }); // 수정버튼에 사용할 마우스 리스너
    } //ProductViewPaneListener

    public void orderListViewPanelListener(OrderListViewPanel panel){

        panel.btnSerach.addActionListener(e -> {
            ProgramManager.getInstance().getOrderController().searchOrder(panel); // 주문 목록 조회
        });

    }

    public void customerViewPanelListener(CustomerViewPanel panel){ // customerVuewPanel의 각 버튼의 특정 역할의 리스너 부착

        panel.addButton.addActionListener(e -> { //CustomerPanel의 등록 버튼을 누르면 고객 등록 창을 띄워준다.
            CustomerManageView cmv = ProgramManager.getInstance().getCC().makeCustomerManageView();
            customerManageViewListener(cmv);
        });
        panel.searchButton.addActionListener(e -> { // 조회 버튼을 누르면 조회해주는 메서드 실행
            ProgramManager.getInstance().getCC().search = true;
            ProgramManager.getInstance().getCC().appMain();
        });
        panel.updateButton.addActionListener(e -> { // 수정 버튼을 누르면 수정해주는 메서드 실행
            ProgramManager.getInstance().getCC().update = true;
            ProgramManager.getInstance().getCC().appMain();
        });
        panel.deleteButton.addActionListener(e -> { // 삭제 버튼을 누르면 삭제해주는 메서드 실행
            ProgramManager.getInstance().getCC().delete =true;
            ProgramManager.getInstance().getCC().appMain();
        });

        panel.tblCustomerList.addMouseListener(new MouseListener() { // customerViewPanel에 부착된 Table이 클릭됐다면 해당 행의 폰 번호를 저장해주는 메서드 실행
            @Override
            public void mouseClicked(MouseEvent e) {
                ProgramManager.getInstance().getCC().isClick =true;
                ProgramManager.getInstance().getCC().appMain();
            }
            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });
    }

    public void productCRUDViewListener(ProductCRUDView frame){
        frame.completeButton.addActionListener(e -> {
            if(frame.chk==1)
                try {
                    ProgramManager.getInstance().getPC().addProduct_inCRUD(frame); //상품등록하기
                }catch (Exception e1){}
        });
    }//productCRUDViewListener

    public void shoppingViewListener(ShoppingView frame){

        frame.btnEnter.addActionListener(e -> { // 쇼핑하기 버튼을 처음 눌렀을 경우에 나타나는 고객 정보 입력 창에서 엔터 버튼을 눌렀을 경우 쇼핑 창으로 이동하는 이벤트

            String name = frame.txtName.getText();
            String phone = frame.txtPhone.getText();

            if(name != null && phone != null) {
                try {
                    ProgramManager.getInstance().getShoppingController().refreshData(frame); // 구매가능한 품목들 화면에 리프레쉬
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e2) {
                    e2.printStackTrace();
                }

                frame.lblCname.setText("고객이름 : " + name);
                frame.lblCphoneNum.setText("고객 번호 : " + phone);

                frame.pn1.setVisible(false);
                frame.pn2.setVisible(true);
            }
            else System.out.println("이름과 번호를 모두 입력하세요!");

        });

        frame.btnEnroll.addActionListener(e -> { // 쇼핑화면에서 장바구니에 원하는 물품을 담을 때(담기 버튼) 행해지는 이벤트
            try {
                ProgramManager.getInstance().getShoppingController().addMyList(frame); // 쇼핑화면의 MyList 내역에 추가됨
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        });

        frame.btnPay.addActionListener(e -> { // 쇼핑화면에서 결제하기 버튼을 눌렀을 때 행해지는 이벤트

            try {
                ProgramManager.getInstance().getShoppingController().payment(frame); // 구매 msg를 서버로 보내는 함수를 호출
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
        });

        frame.btnDelete.addActionListener(e -> { // 쇼핑화면에서 삭제하기 버튼을 눌렀을 때 행해지는 이벤트
            try {
                ProgramManager.getInstance().getShoppingController().deleteMy(frame); // 가장 최근에 넣은 품목 삭제
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }

        });

    }

    public void customerManageViewListener(CustomerManageView frame){ // 고객 등록 창의 각 버튼에 특정 역할을 부여하는 리스너 등록

        frame.btnRegister.addActionListener(e -> { // 등록 버튼을 누르면 입력된 정보로 고객 신규 등록하는 메서드 실행
            ViewManager.getInstance().setCustomerManageView(frame);
            ProgramManager.getInstance().getCC().register =true;
            ProgramManager.getInstance().getCC().appMain();
        });
        frame.btnExit.addActionListener(e -> { // 창 닫기 메서드 실행
            frame.dispose();
        });
    }

    public void chattingViewListener(ChattingView frame) { // 채팅 창의 각 버튼에 특정 역할을 부여해주는 리스너 등록
        frame.exitButton.addActionListener(e-> { // 채팅 창 닫기 메서드 실행
            ProgramManager.getInstance().getChattingController().exitChatting();
        });
        frame.msgInput.addActionListener((e -> { // 메시지 보내기 메서드 실행
            ProgramManager.getInstance().getChattingController().sendTextMessage(ProgramManager.getInstance().id + "/" + frame.msgInput.getText());
            ViewManager.getInstance().getChattingView().msgInput.setText("");
        }));
    }
}
