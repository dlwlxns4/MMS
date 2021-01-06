package com.company.Controller;

import com.company.Model.CustomerDAO;
import com.company.Model.CustomerDTO;
import com.company.Model.Message;
import com.company.View.CustomerManageView;
import com.company.View.CustomerViewPanel;
import com.company.View.MainView;
import com.company.View.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CustomerController extends Thread{
    public CustomerDAO cdao = new CustomerDAO(); // CustomerDAO를 사용하기 위한 인스턴스
    String bufferedString = null; // JTable에서 클릭된 행의 첫번째 칸의 값을 담는 변수
    CustomerDTO customer = null; // customer DTO를 임시저장하는 변수
    CustomerManageView cmv;
    CustomerViewPanel cvp;
    boolean search = false, register = false, update = false, delete = false, isClick = false; //true 값으로 바뀌면 해당 동작을 지시하는 플래그

    // true로 변환된 플래그 값에 따라 해당 기능을 동작시키는 메소드를 실행시켜주는 메소드
    public void appMain() {
        cvp = ViewManager.getInstance().getMainView().customerViewPanel;
        cmv = ViewManager.getInstance().getCustomerManageView();

        // 고객 조회 메서드 실행
        if(search) {
            cvp.initDTModel(); // CustomerPanel에 부착된 TabelModel 초기화
            String phoneNum = cvp.txtPhoneNum.getText(); // TextField에 입력된 전화번호 받아와서
            if(phoneNum.equals("")) { // null 값이면 모든 고객 조회
                searchAllCustomer();
            } else searchCustomer(phoneNum); // 아니면 해당 번호의 고객 조회
            search = false;
        }
        // 고객 등록 메서드 실행
        if(register) {
            registerCustomer();
            register = false;
        }
        if(update) { // Table Model 클릭으로 저장된 기존 번호로 등록된 고객 정보를 업데이트
            updateCustomer(bufferedString);
            update = false;
        }
        if(delete) { // 고객 삭제 메서드 실행
            deleteCustomer();
            delete = false;
        }
        if(isClick) { // Table Model이 클릭됐을 때 클릭된 행의 첫번째값(전화번호(PK))을 저장
            int row = cvp.tblCustomerList.getSelectedRow();
            bufferedString = (String)cvp.dtmodel.getValueAt(row, 0);
            isClick = false;
        }
    }

    public CustomerManageView makeCustomerManageView() { // 고객 등록 창을 생성하고 그려줌
        cmv = new CustomerManageView();
        cmv.drawView();

        return cmv;
    }

    public void searchAllCustomer() { // 모든 고객을 조회하는 메서드
        ArrayList<CustomerDTO> datas = cdao.getAll();
        if(datas != null) {
            for(CustomerDTO c : datas) { // Customer DTO 하나씩 Table Model에 한 줄씩 붙여줌
                String line[] = {c.getPhoneNum(), c.getCName(), String.valueOf(c.getCPoint())};
                cvp.addRowToTable(line);
            }
        }
    }

    public void searchCustomer(String phoneNum) { // 특정 고객을 조회하는 메서드
        String cName, cPoint;
        customer = cdao.getCustomer(phoneNum);
        if(customer != null) { // 특정 번호로 얻은 DTO가 있다면 그 정보를 Table Model에 한 줄 붙여줌
            cName = customer.getCName();
            cPoint = String.valueOf(customer.getCPoint());
            String row[] = {phoneNum, cName, cPoint};
            cvp.addRowToTable(row);
        } else { // 널값이면 경고창
            JOptionPane.showMessageDialog(cvp, "등록되지 않은 회원 입니다.");
        }
    }

    public void updateCustomer(String bufferedString ) { // 수정하기 전 휴대폰 번호로 고객 업데이트하는 메서드
        int row = cvp.tblCustomerList.getSelectedRow(); // 선택된 행 인덱스 저장
        if(row == -1 || bufferedString == null) { // 선택되지 않았거나 저장된 전화번호가 없다면 경고창
            JOptionPane.showMessageDialog(cvp, "수정할 정보를 선택해 주세요.");
        } else { // 저장된 전화번호로 서버에 수정한 정보를 업데이트 해달라고 요청
            String phoneNum = (String)cvp.dtmodel.getValueAt(row, 0);
            String cName = (String)cvp.dtmodel.getValueAt(row, 1);
            int cPoint = Integer.parseInt((String)cvp.dtmodel.getValueAt(row, 2));
            String tmp = "'" + bufferedString + "'";
            String msg = "update Customer set phone_num = " + "'" + phoneNum + "'" + ", c_name = " + "'" + cName + "'" + ", c_point = " + cPoint + " where phone_num = " + tmp;

            ProgramManager.getInstance().getMainController().msgSend(new Message("","",msg,9));
        }
    }

    public void deleteCustomer() { // 고객 삭제 메서드
        int row = cvp.tblCustomerList.getSelectedRow(); // 선택된 행 인덱스 저장
        if(row == -1) { // 선택되지 않았다면 경고창
            JOptionPane.showMessageDialog(cvp, "삭제할 정보를 조회 후 선택해 주세요.");
        } else { // 선택된 행의 데이터를 삭제해달라고 서버에 요청
            String phoneNum = (String)cvp.dtmodel.getValueAt(row, 0);
            String cName = (String)cvp.dtmodel.getValueAt(row, 1);
            String msg = "delete from Customer where phone_num = " + "'" + phoneNum + "'";
            ProgramManager.getInstance().getMainController().msgSend(new Message("","",msg,10));
        }
    }

    public void registerCustomer() { // 고객 신규 추가 메서드
        String cName = cmv.txtName.getText();
        String phoneNum = cmv.txtPhone.getText();
        String cPoint = cmv.txtPoint.getText(); // 텍스트필드에 입력된 값 저장
        if (cName.equals("") || phoneNum.equals("") || cPoint.equals("")) { // 셋 중 하나라도 빈칸이면 경고창
            JOptionPane.showMessageDialog(cmv, "빈 칸을 채워 주세요.");
        }else { // 모두 입력되었으면 해당 값으로 새로운 고객 추가해달라고 서버에 요청
            int point = Integer.parseInt(cPoint);
            String msg = cName + "/" + "insert into Customer(phone_num, c_name, c_point) values('" + phoneNum + "', " + "'" + cName + "'" + ", " + cPoint + ")";
            System.out.println(msg);
            ProgramManager.getInstance().getMainController().msgSend(new Message("","",msg, 8));
            cmv.refreshTextField();
        }
    }

    public void savePoint(String phone, int point) { // 장바구니에서 결제완료 되면 해당 고객에게 포인트를 적립해주는 메서드

        customer = cdao.getCustomer(phone); // 저장된 폰 번호의 고객 데이터 저장
        if(customer != null) { // 널값이 아니라면 포인트기 쌓인다.
            point += customer.getCPoint();
        } else { // 널값이면 경고창
            JOptionPane.showMessageDialog(cvp, "등록되지 않은 회원 입니다.");
        }
        // 고객 정보를 업데이트하여 쌓인 포인트로 변경해달라고 서버에 요청
        String savePointMsg = "update Customer set c_point =" + point + " where phone_num =" + "'" + phone + "'";
        Message msg = new Message(" ", " ", savePointMsg, 9);
        ProgramManager.getInstance().getMainController().msgSend(msg); // addOrder 요청
    }

}
