package com.example.dmemo;

import android.content.Context;

public interface BaseView {

    /**
     * 구현된 Activity의 Context 반환 함수
     * @return
     */
    Context getActivityContext();

    /**
     * 각 종 View 초기화 함수
     */
    void initView();

    /**
     * 뒤로가기 버튼 클릭 함수
     */
    void onClickClose();
}
