package com.example.dmemo;

public interface BasePresenter {
    /**
     * 최초 시작 부분 호출 메서드
     * 주로 첫 network를 요청하거나 data 호출작업을 실행
     */
    void onStartPresenter();

}

