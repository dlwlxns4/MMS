package com.company.Controller;

import java.sql.SQLException;

/*
 * State 인터페이스는 변경될 각 상태에서
 * 쓰일 메소드의 틀을 갖고 있는 클래스이다.
 * State는 총 4가지로
 * LoginState, MainState, OrderManageState, CustomerManageState 이다.
 */

public interface State {

    public void draw(); // 처음 스테이트가 생성될때만 쓰인다.

    public void drawPanel() throws SQLException, ClassNotFoundException; // 패널을 다시 보여줄 때 쓰인다.

    public void applyListener(); // 리스너를 붙여줄 때 쓰인다.

    public void update(); // 화면을 업데이트할 때 쓰인다.

}
