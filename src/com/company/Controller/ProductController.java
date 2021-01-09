
package com.company.Controller;

import com.company.Model.Message;
import com.company.Model.ProductDAO;
import com.company.Model.ProductDTO;
import com.company.View.MainView;
import com.company.View.ProductViewPanel;
import com.company.View.ProductCRUDView;
import com.company.View.ViewManager;

import javax.naming.ServiceUnavailableException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ProductController extends Thread{
    public ProductViewPanel v;
    ProductDAO dao;
    public ArrayList<ProductDTO> datas;
    MainView mainView ;
    public boolean isClick = false;
    public int bufferedInt =0;
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분");


    public ProductController(ProductViewPanel v) throws SQLException, ClassNotFoundException {
        this.v=v;
        dao=new ProductDAO();
        refreshData();
        mainView = new MainView();
        start();
        v.smallAMountArea.setText("코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n");
        v.almostExpiredArea.setText("코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n");
    }

    public void appMain(){

        if(isClick){
            int row = v.productTable.getSelectedRow();
            bufferedInt = Integer.parseInt(v.tableModel.getValueAt(row, 0).toString());
            isClick = false;
        } // 클릭시 prCode 가져와주는 로직
    }

    public void deleteProduct(ProductViewPanel panel){
        String add_msg="";
        int row = ViewManager.getInstance().getMainView().productViewPanel.productTable.getSelectedRow();
        if(row == -1 ){
            JOptionPane.showMessageDialog(ViewManager.getInstance().getMainView().productViewPanel, " 삭제할 정보를 조회 후 선택해 주세요.");

        }else {
            int prCode = Integer.parseInt(ViewManager.getInstance().getMainView().productViewPanel.tableModel.getValueAt(row, 0).toString()); //삭제하고 곳
            add_msg = add_msg + "delete from Product where pr_code = " + prCode; //삭제 쿼리문
        }

        ProgramManager.getInstance().getMainController().msgSend(new Message("", "", add_msg, 7)); //쿼리문 메세지 보내기기
        panel.SUDLab.setText("검색 정보 :");
    }//테이블 누르고 삭제

    public void searchProduct(ProductDAO dao, boolean editMode,ProductViewPanel panel){
        try {

            ProductDTO p = dao.getProduct(Integer.parseInt(panel.txtSearch.getText()));
            if (p.getPrCode() != -1) {
                panel.SUDtxt.setText("");
                panel.SUDtxt.append("코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n" +
                        Integer.toString(p.getPrCode()) + "\t" + p.getPrName() + "\t" + p.getPrice() + "\t" + p.getLocation() + "\t" + p.getExpDate() + "\t" + p.getAmount() + "\t"
                        + p.getState());

                editMode = true; //찾았으면 수정,삭제가능
            } else {

                panel.SUDtxt.setText("검색하는 코드에 대한 정보가 없음");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        panel.SUDLab.setText("검색 정보 :                                                         EditMode : " + editMode);
    }//search한후 텍스트area에 정보띄우기

    public void addProduct(){
        ViewManager.getInstance().getProductCRUDView().drawView();
        ViewManager.getInstance().getProductCRUDView().chk = 1;
    } //CRUD창 추가후 버튼리스너 추가

    public void updateProduct() {
        ProductViewPanel panel = ViewManager.getInstance().getMainView().productViewPanel;

        int row = panel.productTable.getSelectedRow();
        if(row == -1 ) {
            JOptionPane.showMessageDialog(panel, "수정할 정보를 선택해 주세요.");
        } else {
            int prcode = Integer.parseInt((panel.tableModel.getValueAt(row, 0).toString()));
            String prName = (String)panel.tableModel.getValueAt(row, 1);
            int price = Integer.parseInt(panel.tableModel.getValueAt(row, 2).toString());
            String location = (String)panel.tableModel.getValueAt(row, 3);
            Date date = Date.valueOf(panel.tableModel.getValueAt(row, 4).toString());
            int amount = Integer.parseInt(panel.tableModel.getValueAt(row, 5).toString());
            String state = (String)panel.tableModel.getValueAt(row, 6);


            String prstate = (String)panel.tableModel.getValueAt(row, 6);;

            DefaultTableModel dt = panel.tableModel;

            String add_msg = "update Product set pr_name = " + "'" + prName + "'" +
                    ",  PRICE = "+ price + ", location = "+ "'" + location +"'" +
                    ", exp_date = "+ "'" + date + "'" + ", amount = "+ amount + ", state = "+ "'" + prstate + "'" + "where pr_code = " + bufferedInt;
            //update하기 위한 쿼리문



            ProgramManager.getInstance().getMainController().msgSend(new Message("", "", add_msg, 6)); //메세지 보내기

            panel.SUDtxt.setText("수정이 완료되었습니다.");
        }
    }

    public void refreshData() throws SQLException, ClassNotFoundException {
        datas = dao.getAll();
        Object record[] = new Object[7];
        v.tableModel.setNumRows(0); // 다시붙일때 테이블 로우 초기화
        if( datas != null){

            for(ProductDTO p : datas) { // 상품 전체 조회
                record[0] = p.getPrCode();
                record[1] = p.getPrName();
                record[2] = p.getPrice();
                record[3] = p.getLocation();
                record[4] = p.getExpDate();
                record[5] = p.getAmount();
                record[6] = p.getState();
                v.tableModel.addRow(record);

            }
        }//상품 초기화후 재등록
    }

    public void addProduct_inCRUD(ProductCRUDView CRUDv){

        String add_msg="insert into Product(pr_code, pr_name, price, location, exp_date, amount, state) ";
        String prstate = "판매";


        add_msg += "values(" + Integer.parseInt(CRUDv.codeText.getText()) +
                ", " + "'"+ CRUDv.nameText.getText() +"'"+
                ", " + Integer.parseInt(CRUDv.priceText.getText()) +
                ", " + "'" + CRUDv.locationText.getText() +"'"+
                ", " + "'" + CRUDv.expDateText.getText() + "'" +
                ", " + Integer.parseInt(CRUDv.countText.getText()) +
                ", " + "'"+ prstate + "'"+")"; // 상품 등록을 위한 쿼리문


        System.out.println("상품등록 완료");
        CRUDv.codeText.setText("");
        CRUDv.nameText.setText("");
        CRUDv.priceText.setText("");
        CRUDv.locationText.setText("");
        CRUDv.expDateText.setText("");
        CRUDv.countText.setText(""); // 상품 등록후 공간 초기화 하기




        ProgramManager.getInstance().getMainController().msgSend(new Message("", "", add_msg, 5)); // 쿼리문 메세지 보내기


        ViewManager.getInstance().getMainView().productViewPanel.SUDLab.setText("검색 정보 :                                                      ");

    } //addProduct_inCRUD complete누르면 정보 추가


    public void run(){

        String txt="코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n";
        String txt2="코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n";

        ArrayList<ProductDTO> data = null;

        while(true){//while
            java.util.Date now = new java.util.Date();

            Calendar time = Calendar.getInstance();
            String format_time = format.format(time.getTime());
            ViewManager.getInstance().getMainView().mainViewPanel.timeLabel.setText(format_time); // 시간을 나타내주는 로직

            try {
                data=dao.getAll();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } //daoGeoAll try

            if(data!=null) {
                for (ProductDTO p : data) { // 모든상품 검색
                    boolean chk1=false,chk2=false;

                    long diff = p.getExpDate().getTime() - now.getTime();
                    long sec = diff / 1000;  //초 단위로로 변환하기

                    if ( sec < 604800) {//시간측정 일주일
                        p.setState("유통기한임박");
                        try {
                            dao.updateProduct(p); // 상품 업데이트하기 state변경
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        txt += p.getPrCode() + "\t" + p.getPrName() + "\t" + p.getPrice() + "\t" + p.getLocation() +
                                "\t" + p.getExpDate() + "\t" + p.getAmount() +"\t" + p.getState() + "\n";
                        chk1 =true;
                    }//if 시간측정

                    if( p.getAmount() < 10){//재고측정
                        p.setState("재고부족임박");
                        try {
                            dao.updateProduct(p); // 상품 업데이트하기 state변경
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        txt2 += p.getPrCode() + "\t" + p.getPrName() + "\t" + p.getPrice() + "\t" + p.getLocation() +
                                "\t" + p.getExpDate() + "\t" + p.getAmount() +"\t" + p.getState() + "\n";
                        chk2 = true;
                    }//재고측정

                    if(chk1){
                        if(chk2){
                            p.setState("재고유통기한임박");
                            try {
                                dao.updateProduct(p); // 상품 업데이트하기 state변경
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }//상태변환 재고 유통기한 둘다 측정할떄 변화
                    chk1 =false; chk2 =false;
                }

            }try {
                sleep(5000); // 쓰레드 5초간격으로 실행하기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            v.smallAMountArea.setText(txt); // 유통기한 조금남은 재품TextArea에 붙이기
            v.almostExpiredArea.setText(txt2); // 재고 조금남은 재품TextArea에 붙이기
            txt="코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n";
            txt2="코드\t이름\t가격\t위치\t유통기한\t재고\t상태\n";


        }
    }

}