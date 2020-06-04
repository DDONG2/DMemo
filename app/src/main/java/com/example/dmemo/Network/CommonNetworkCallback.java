package com.example.dmemo.Network;

public interface CommonNetworkCallback<T> {

    void onSuccess(T result, int taskId);

    void onFailed(int taskId);

    void onCancel(int taskId);
}