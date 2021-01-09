package com.company.Controller;

import com.company.Model.*;
import com.company.View.OrderListViewPanel;
import com.company.View.ShoppingView;
import com.company.View.ViewManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class OrderController {

    public ArrayList<OrderDTO> orderDatas;
    public ArrayList<OrderHistoryDTO> orderHistoryDatas;

    OrderHistoryDTO orderHistoryDTO;
    OrderHistoryDAO orderHistoryDAO;
    OrderDTO orderDTO;
    OrderDAO orderDAO;
    public boolean status = true; // 물품 재고 여부 판단해 구매 가능 및 주문, 주문 히스토리에 넣을 지 여부 결정
                                  // 아니면 product controller에서 제어한 다음 구매 가능여부만 shoppingframe에 표기하면 될듯?..


    public OrderController() {

        orderDAO = new OrderDAO();
        orderHistoryDAO = new OrderHistoryDAO();
    }

    public void searchOrder(OrderListViewPanel pn) {

        // 선택된 정보 가져오기
        String s = (String) pn.cb.getSelectedItem();

        if (s.equals("주문 내역")) {
            try {
                refreshDataOrder(pn); // OrderList 패널에서 콤보 박스에 "주문 내역"이 선택 되어 있는 경우 해당 함수 실행
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        } else if (s.equals("주문 상세 내역")) {
            try {
                refreshDataOrderHistory(pn); // OrderList 패널에서 콤보 박스에 "주문 상세 내역"이 선택 되어 있는 경우 해당 함수 실행
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }

    // Order 테이블에 있는 모든 행들의 정보를 패널에 나타냄
    public void refreshDataOrder(OrderListViewPanel pn) throws SQLException, ClassNotFoundException {
        orderDatas = orderDAO.getAll();
        Object record[] = new Object[5];
        pn.orderModel.setNumRows(0); // 다시붙일때 테이블 로우 초기화
        pn.orderHistoryScroll.setVisible(false);
        pn.orderScroll.setVisible(true);

        if( orderDatas != null){

            OrderDTO orderDTO;
            for(OrderDTO o : orderDatas) {
                record[0] = o.getOrder_code();
                record[1] = o.getEntry_price();
                record[2] = o.getC_name();
                record[3] = o.getPhone_num();
                record[4] = o.getBuy_date();
                pn.orderModel.addRow(record);

            }
        }
    }

    // OrderHistory 테이블에 있는 모든 행들의 정보를 패널에 나타냄
    public void refreshDataOrderHistory(OrderListViewPanel pn) throws SQLException, ClassNotFoundException {
        orderHistoryDatas = orderHistoryDAO.getAll();
        Object record[] = new Object[6];
        pn.orderHistoryModel.setNumRows(0); // 다시붙일때 테이블 로우 초기화
        pn.orderScroll.setVisible(false);
        pn.orderHistoryScroll.setVisible(true);

        if( orderHistoryDatas != null){

            OrderDTO orderDTO;
            for(OrderHistoryDTO o : orderHistoryDatas) {
                record[0] = o.getHistory_id();
                record[1] = o.getOrder_code();
                record[2] = o.getPr_code();
                record[3] = o.getPr_name();
                record[4] = o.getPr_count();
                record[5] = o.getPr_price();
                pn.orderHistoryModel.addRow(record);

            }
        }
    }

    public void OrderItems(ShoppingView shoppingView, int totalPrice) {

        String addOrderMsg = "insert into Orders(entry_price, c_name, phone_num, buy_date) ";

        int rowCount = shoppingView.productTable2.getRowCount(); // myList에 담긴 물품 즉, 행의 개수 가져오기
        System.out.println("--" + rowCount);
        int tmp = rowCount;
        int orderCode, prCode, prPrice, prCount;
        orderCode = 0; // 초기화

        String cName, cPhone[], prName;
        cName = ViewManager.getInstance().getShoppingView().txtName.getText(); // 이름 추출
        cPhone = shoppingView.lblCphoneNum.getText().split(" : "); // 번호 추출

        // ----- 현재 시간 가져오기 -----
        Date now = new Date();
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // 1월이 0을 반환하므로 +1
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        String date = year + "-" + month + "-" + day;

        addOrderMsg += "value(" + totalPrice + " ," + "'" + cName + "'" + " ," + "'" + cPhone[1] + "'" + " ," +  "'" + date + "'" + ")"; // 서버로 보내 실행할 쿼리문 msg

        Message msg = new Message(" ", " ", addOrderMsg, 11);
        ProgramManager.getInstance().getMainController().msgSend(msg); // addOrder 요청

        while(rowCount != 0) {

            String addOrderHistoryMsg = "insert into OrderHistory(order_code, pr_code, pr_name, pr_count, pr_price) ";

            prCode = Integer.parseInt(shoppingView.tableModel2.getValueAt(rowCount-1,0).toString()); // 물품을 넣는 순서를 역순으로 넣음
            prName = (String)(shoppingView.tableModel2.getValueAt(rowCount-1,1)); // 구매리스트의 상품 이름 저장
            prCount = Integer.parseInt(shoppingView.tableModel2.getValueAt(rowCount-1,3).toString()); // 구매리스트의 각 상품 구매 개수 저장
            prPrice = Integer.parseInt(shoppingView.tableModel2.getValueAt(rowCount-1,2).toString()); // 구매 리스트의 각 상품의 가격 저장


            addOrderHistoryMsg += "value(" + "/" + " ," + "'" + prCode + "'" + " ," + "'" + prName + "'" + " ," + "'" + prCount + "'" + " ,"+ "'" + prPrice + "'"  + ")"; // 서버로 보내 실행할 쿼리문 msg
            msg = new Message(" ", " ", addOrderHistoryMsg, 16);
            ProgramManager.getInstance().getMainController().msgSend(msg); // addOrder 요청

            rowCount--;
        }

    }

}
