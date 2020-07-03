package com.example.dmemo.View.Search;

import com.example.dmemo.Model.search.SearchMemoRepository;
import com.example.dmemo.Network.CommonNetworkCallback;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public class SearchMemoPresenter implements SearchMemoContract.Presenter, CommonNetworkCallback {

    private SearchMemoContract.View view;
    private SearchMemoRepository repository;


    public SearchMemoPresenter(SearchMemoContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartPresenter() {
        repository = new SearchMemoRepository(view.getActivityContext());
        repository.setCallback(this);
    }

    @Override
    public void onSuccess(Object result, int taskId) {

    }

    @Override
    public void onFailed(int taskId) {

    }

    @Override
    public void onCancel(int taskId) {

    }

    @Override
    public ArrayList<memoListDTO> callSearchInfoDATA(String searchText) {
        return repository.callSearchInfoDATA(searchText);
    }

    @Override
    public void callDeleteListDATA(boolean AllCheck, ArrayList<memoListDTO> memoList, ArrayList<String> checkOneList) {
        repository.callDeleteList(AllCheck, memoList, checkOneList);
    }
}
