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

        void onCheckAll(boolean isCheck);

        void onCheckOneList(ArrayList<String> checkOneList);
    }

    interface Presenter extends BasePresenter {

        ArrayList<memoListDTO> callMainInfoDATA();


    }


}
