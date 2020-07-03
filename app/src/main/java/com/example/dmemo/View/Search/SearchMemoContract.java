package com.example.dmemo.View.Search;

import com.example.dmemo.BasePresenter;
import com.example.dmemo.BaseView;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public interface SearchMemoContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        ArrayList<memoListDTO> callSearchInfoDATA(String searchText);

    }
}
