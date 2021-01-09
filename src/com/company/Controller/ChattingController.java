package com.company.Controller;

import com.company.Model.Message;
import com.company.View.ChattingView;
import com.company.View.ViewManager;

public class ChattingController {
    ChattingView chattingView;

    public void exitChatting() { // 채팅창 닫기
        chattingView = ViewManager.getInstance().getChattingView();
        chattingView.setVisible(false);
    }

    public void sendTextMessage(String text) { // 메시지창의 텍스트필드에 입력된 문자열을 다른 직원들의 채팅창으로 전달해달라고 서버에 요청
        ProgramManager.getInstance().getMainController().msgSend(new Message(ProgramManager.getInstance().id,"",text,4));
    }
}
