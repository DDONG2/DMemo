package com.example.dmemo.View.Main;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public interface MainContract {
    interface View extends BaseView {

        /**
         * 메모 추가 클릭
         */
        void onClickAddMemo();
        /**
         * 메모 검색 클릭
         */
        void onClickSearchMemo();
        /**
         * 메모 삭제 클릭
         */
        void onClickDelete();
    }

    interface Presenter extends BasePresenter {
        /**
         * 메모 조회
         */
        ArrayList<memoListDTO> callMainInfoDATA();
        /**
         * 메모 삭제
         */
        boolean callMainDeleteDATA(boolean isCheck, ArrayList<memoListDTO> allMemoList, ArrayList<String> checkOneMemoList);

    }


}
