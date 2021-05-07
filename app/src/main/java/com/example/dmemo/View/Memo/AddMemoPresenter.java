package com.example.dmemo.View.Memo;


import com.example.dmemo.Model.main.AddMemoRepository;
import com.example.dmemo.Model.main.MemoDetailRepository;
import com.example.dmemo.Network.CommonNetworkCallback;

public class AddMemoPresenter implements AddMemoContract.Presenter, CommonNetworkCallback {

    private AddMemoContract.View view;
    private AddMemoRepository repository;

    public AddMemoPresenter(AddMemoContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartPresenter() {
        repository = new AddMemoRepository(view.getActivityContext());
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
    public boolean callDetailAddDATA(String memoTitle, String memoContent) {
        return repository.callDetailAddDATA(memoTitle, memoContent);
    }
}
