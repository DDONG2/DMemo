package com.example.dmemo.View.Memo;

import android.text.Editable;


import com.example.dmemo.Model.main.MemoDetailRepository;
import com.example.dmemo.Network.CommonNetworkCallback;


public class MemoDetailPresenter implements MemoDetailContract.Presenter, CommonNetworkCallback {

    private MemoDetailContract.View view;
    private MemoDetailRepository repository;

    public MemoDetailPresenter(MemoDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartPresenter() {
        repository = new MemoDetailRepository(view.getActivityContext());
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
    public boolean callDetailSubmitDATA(String memoTitle, String memoContent, int memoId) {
        return repository.callDetailSubmitDATA(memoTitle, memoContent, memoId);
    }
}

