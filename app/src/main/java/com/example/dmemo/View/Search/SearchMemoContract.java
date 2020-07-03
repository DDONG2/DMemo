package com.example.dmemo.View.Search;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public interface SearchMemoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * 검색 메모 리스트
         */
        ArrayList<memoListDTO> callSearchInfoDATA(String searchText);

        /**
         * 검색 메모 삭제
         */
        void  callDeleteListDATA(boolean AllCheck, ArrayList<memoListDTO> memoList, ArrayList<String> checkOneList);


    }
}
