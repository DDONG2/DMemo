package com.example.dmemo.View.Main;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.Model.main.MainRepository;
import com.example.dmemo.Network.CommonNetworkCallback;
import com.example.dmemo.Utils.recycleCallBack;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter, CommonNetworkCallback {

    private MainContract.View view;
    private MainRepository repository;

    private MemoAdapter mAdapter;


    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartPresenter() {
        repository = new MainRepository(view.getActivityContext());
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
    public ArrayList<memoListDTO> callMainInfoDATA() {
        return repository.callMainInfoDATA();
    }


}
