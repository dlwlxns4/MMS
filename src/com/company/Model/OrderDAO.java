package com.company.Model;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {

    Connection con;
    PreparedStatement pstmt;
    Statement stmt;
    ResultSet rs;
    String userid = "jaewon";
    String pwd = "wlfkf132";
    String sql;
    String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://mms.crgsa3qt3jqa.ap-northeast-2.rds.amazonaws.com/mms?user=jaewon&password=wlfkf132";
    public OrderDAO() {

    }

    // DB 접속을 위한 함수
    public void connectDB() {
        try {
            Class.forName(jdbcDriver);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("데이터베이스 연결 준비...");
            con = DriverManager.getConnection(jdbcUrl, userid, pwd);
            System.out.println("데이터베이스 연결 성공");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    // DB 종료를 위한 함수
    public void closeDB() {
        try {
            pstmt.close();
            if(rs!=null) rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Order 테이블에서 모든 행 반환
    public ArrayList<OrderDTO> getAll() {
        connectDB();
        sql = "select * from Orders";

        ArrayList<OrderDTO> datas = new ArrayList<OrderDTO>();

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrder_code(rs.getInt("order_code"));
                order.setEntry_price(rs.getInt("entry_price"));
                order.setC_name(rs.getString("c_name"));
                order.setPhone_num(rs.getString("phone_num"));
                order.setBuy_date(rs.getString("buy_date"));
                datas.add(order);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        closeDB();

        if(datas.isEmpty()) return null;
        else return datas;

    }

    // Order 테이블에서 인자로 받은 ordercode에 해당하는 행 반환
    public OrderDTO getOrder(int orderCode) {
        connectDB();
        sql = "select * from Orders where order_code = ?";
        OrderDTO order = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderCode);
            rs = pstmt.executeQuery();
            rs.next();
            order = new OrderDTO();
            order.setOrder_code(rs.getInt("order_code"));
            order.setEntry_price(rs.getInt("entry_price"));
            order.setC_name(rs.getString("c_name"));
            order.setPhone_num(rs.getString("phone_num"));
            order.setBuy_date(rs.getString("buy_date"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeDB();

        return order;
    }

    // ------- 아래 함수는 사용 안함(서버에서 처리함) -----------------------
    public boolean addOrder(String msg) { // 날짜는 추후 수정...
        connectDB();
        //sql = "insert into Orders(entry_price, c_name, phone_num, buy_date) value(?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(msg);
            //pstmt.setInt(1, order.getEntry_price());
            //pstmt.setString(2, order.getC_name());
            //pstmt.setString(3, order.getPhone_num());
            //pstmt.setString(4, order.getBuy_date());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        closeDB();

        return true;
    }

    // ------- 아래 함수는 사용 안함(서버에서 처리함) -----------------------
    public boolean updateOrder(OrderDTO order) { // 파라미터의 객체 정보로 업데이트
        connectDB();
        sql = "update Orders set entry_price = ? where order_code = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, order.getEntry_price());
            pstmt.setInt(2, order.getOrder_code());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        closeDB();

        return true;
    }

    // ------- 아래 함수는 사용 안함(서버에서 처리함) -----------------------
    public boolean updateOrder(String msg) { // 파라미터의 객체 정보로 업데이트
        connectDB();
        sql = "update Orders set entry_price = ? where order_code = ?";

        try {
            pstmt = con.prepareStatement(sql);
            //pstmt.setInt(1, order.getEntry_price());
            //pstmt.setInt(2, order.getOrder_code());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        closeDB();

        return true;
    }

    // ------- 아래 함수는 사용 안함(서버에서 처리함) -----------------------
    public boolean delOrder(int orderCode) {
        connectDB();
        sql = "delete from Orders where order_code = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, orderCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        closeDB();

        return true;
    }


}

