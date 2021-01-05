package com.company.Controller;

import com.company.Model.Message;
import com.company.Model.ProductDAO;
import com.company.Model.ProductDTO;
import com.company.View.ShoppingView;
import com.company.View.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShoppingController {
    public ArrayList<ProductDTO> datas ;
    public ProductDAO dao;

    public ArrayList<ProductDTO> datas2 ; //마이리스트 출력 어레이리스트
    public int total=0;

    public ShoppingController() throws SQLException, ClassNotFoundException {
        dao=new ProductDAO();
        datas2 = new ArrayList<ProductDTO>();
    }

    // 마이리스트 즉, 구매내역에 담긴 상품들의 총 가격 반환
    public int getTotal() {return total;}

    // 마이리스트에 선택한 물품 담기
    public void addMyList(ShoppingView v) throws SQLException, ClassNotFoundException {

        ProductDTO p = null;

        if(v.jtfSearch.getText() != null && v.jtfCount.getText() != null) {
            p = dao.getProduct(Integer.parseInt(v.jtfSearch.getText()));

            if( p.getAmount() < Integer.parseInt(v.jtfCount.getText())){ // 수량비교
                v.lblstate.setText("상태 : 재고 초과 입력"); // 재고보다 많은 수량을 선택한 경우 메세지 출력
            }
            else{
                p.setAmount(Integer.parseInt(v.jtfCount.getText()));
                datas2.add(p); // 마이리스트에 해당 품목 추가
                total = p.getAmount() * p.getPrice() + total; // 마이리스트에 담긴 총액 업데이트

                refreshData2(v); // 화면 리프레쉬

            }
            v.jtfSearch.setText("");
            v.jtfCount.setText("");
            v.lblMsg.setText("결제 금액 : " + total +"원"); // 현재 총 결제 금액을 표시
        }


    }//상품담기

    // 고객이 결제하기 버튼을 눌렀을 경우 호출되는 메서드
    public void payment(ShoppingView v) throws SQLException, ClassNotFoundException {

        String updateEntireProduct = "";

        int prCode = 0;
        int amount, buyAmount = 0;
        int count = 0;

        for(ProductDTO p : datas2){ // 담은 리스트
            for(ProductDTO p2 : datas) { // 재고 리스트

                if (p.getPrCode() == p2.getPrCode()) {
                    prCode = p2.getPrCode(); // 업데이트 할 물품 코드
                    amount = p2.getAmount() - p.getAmount() ;// 현재 재고량 - 담은 재고량
                    buyAmount = p.getAmount();
                    count ++;
                    String updateProduct = amount + "/" + buyAmount + "/" + prCode; // 3쌍(업데이트 될 수량, 구매 수량, 물품 코드) 단위로 add
                    updateEntireProduct += updateProduct + "/";
                }
            }
        }

        updateEntireProduct += "@" + count; // 3쌍씩 물품개수 만큼 정보가 담긴 후 마지막으로 총 구매수량을 나타내는 count값 add

        Message msg = new Message(ProgramManager.getInstance().id, " ", updateEntireProduct, 17);
        ProgramManager.getInstance().getMainController().msgSend(msg); // addOrder 요청

    } // 상품결제

    // 결제 성공 시 수행하는 함수
    public void payComplete(ShoppingView v) throws SQLException, ClassNotFoundException {

        refreshData(v); // itemList JTable 리프레쉬
        datas2.clear();
        refreshData2(v);

        total=0;
        v.lblMsg.setText("결제 금액 : 0원");
    }

    // 결제 실패 시 수행하는 함수
    public void payFailed(ShoppingView v) throws SQLException, ClassNotFoundException {

        refreshData(v); // itemList JTable 리프레쉬
        datas2.clear();
        refreshData2(v);

        total=0;
        v.lblstate.setText("상태 : 결제 실패");
        v.lblMsg.setText("결제 금액 : 0원");
    }

    // 구매가능 리스트 리프레쉬
    public void refreshData(ShoppingView v) throws SQLException, ClassNotFoundException {

        datas = dao.getAll();
        Object record[] = new Object[5];
        v.tableModel.setNumRows(0); // 다시붙일때 테이블 로우 초기화

        if( datas != null){

            for(ProductDTO p : datas) {
                record[0] = p.getPrCode();
                record[1] = p.getPrName();
                record[2] = p.getPrice();
                record[3] = p.getAmount();
                record[4] = p.getState();
                v.tableModel.addRow(record);

            }
        }
    }//위에 현재재고 리프레쉬

    // 마이리스트 테이블 리프레쉬
    public void refreshData2(ShoppingView v) throws SQLException, ClassNotFoundException {
        System.out.println("test2");

        Object record[] = new Object[4];

        v.tableModel2.setNumRows(0); // 다시붙일때 테이블 로우 초기화

        if( datas2 != null){

            for(ProductDTO p : datas2) {
                record[0] = p.getPrCode();
                record[1] = p.getPrName();
                record[2] = p.getPrice();
                record[3] = p.getAmount();

                v.tableModel2.addRow(record);

            }
        }
    }//아래 my데이터 리프레쉬

    // 마이리스트에서 가장 최근에 담긴 물품 삭제 후 리프레쉬
    public void deleteMy(ShoppingView v) throws SQLException, ClassNotFoundException {
        ProductDTO p = datas2.get(datas2.size()-1);
        total -= (p.getPrice()*p.getAmount());
        v.lblMsg.setText("결제 금액 : " + total +"원");
        datas2.remove(datas2.size()-1);
        refreshData2(v);
    }


}
