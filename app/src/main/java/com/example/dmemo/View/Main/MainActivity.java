package com.example.dmemo.View.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.View.Memo.AddMemoActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.View.Search.SearchMemoActivity;
import com.example.dmemo.dateDTO.memoListDTO;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {


    private MemoAdapter mAdapter = new MemoAdapter();

    /**
     * 모든 메모 리스트
     */
    private ArrayList<memoListDTO> mMemoList = new ArrayList<memoListDTO>();


    @BindView(R.id.btn_option3)
    Button btn_option3;

    @BindView(R.id.btn_add_memo)
    Button btn_add_memo;

    @BindView(R.id.rc_memo_list)
    RecyclerView rcMemoList;

    @BindView(R.id.tv_empty)
    TextView tv_empty;

    @BindView(R.id.tv_list_conut)
    TextView tv_list_conut;

    @BindView(R.id.ll_edit_select_bar)
    LinearLayout ll_edit_select_bar;

    @BindView(R.id.btn_main_delete)
    Button btn_main_delete;

    @BindView(R.id.cb_edit_selected_all)
    CheckBox cb_edit_selected_all;

    //마이크 권한 체크
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;

    private int memoId;

    /**
     * 애니메이션
     */
    private Animation anim_down;
    private Animation anim_up;
    /**
     * 체크박스 선택된 낱개 리스트
     */
    private ArrayList<String> checkOne = new ArrayList<>();


    /**
     * 메인 프레젠터
     */
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.onStartPresenter();

        //마이크 권한 체크
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO
                );
            }
        }

        initAnimationView();

    }
    

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {

        cb_edit_selected_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_edit_selected_all.isChecked()) {
                    checkOne.clear();
                    for (int i = 0; i < mMemoList.size(); i++) { // 전체선택일경우 checkOne 클리어 후 모든 리스트를 add 해준다. (* 리사이클러뷰 뷰홀더 이슈 그려지지 않은 리스트는 갱신이 되지 않음, 데이터리스트에 들어오지 않음)
                        checkOne.add(Integer.toString(mMemoList.get(i).getId()));
                    }
                    mAdapter.setIsAllClick(true);
                    mAdapter.setCheckOne(checkOne);
                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {  // 쓰레드 post 를 해준 이유는 리사이클러뷰 UI 쓰레드 이슈 떄문
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    };
                    handler.post(r);

                } else {
                    checkOne.clear();
                    mAdapter.setIsAllClick(false);
                    mAdapter.setCheckOne(checkOne);
                    Handler handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    };
                    handler.post(r);
                }
            }
        });

        mMemoList.clear();
        //callDATA 메모 리스트 호출 프레젠터
        mMemoList = presenter.callMainInfoDATA();

        //기본은 editmode = false 이다.
        mAdapter.setEditMode(false);

        //메모 데이터가 있을때
        if (mMemoList != null && mMemoList.size() > 0) {
            tv_list_conut.setText("노트" + " " + String.valueOf(mMemoList.size()) + "개");
            mAdapter.setFeedList(mMemoList);
            mAdapter.setLongClickItemlistener(longClickListener); // 어뎁터에 롱클릭 리스너 등록
            mAdapter.setCheckItemlistener(itemCheckListener); // 어뎁터에 낱개 선택 리스너 등록

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rcMemoList.setLayoutManager(manager);

            rcMemoList.setAdapter(mAdapter);

            rcMemoList.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);

        } else {
            rcMemoList.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
            tv_list_conut.setText("노트 0개");
        }
    }

    /**
     * 애니메이션 효과 설정 메소드
     */
    public void initAnimationView() {

        anim_up = AnimationUtils.loadAnimation(this, R.anim.push_up);
        anim_up.setAnimationListener(new FlowAnimationListener());

        anim_down = AnimationUtils.loadAnimation(this, R.anim.push_down);
        anim_down.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                ll_edit_select_bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }

    /**
     * 애너매니션 콜백 클래스
     */
    private final class FlowAnimationListener implements
            Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            if (ll_edit_select_bar.getVisibility() == View.VISIBLE)
                ll_edit_select_bar.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }


    }

    /**
     * 리스트 낱개 체크박스 리스너
     */
    private CompoundButton.OnClickListener itemCheckListener = new CompoundButton.OnClickListener() {
        @Override
        public void onClick(View v) {

            memoId = (int) v.getTag();

            CheckBox check = (CheckBox) v;
            if(check.isChecked()) {
                check.setChecked(true);
                checkOne.add(Integer.toString(memoId));
                if (checkOne.size() > 0 && checkOne.size() == mMemoList.size()) {

                    cb_edit_selected_all.setChecked(true);
                    //전체선택 true
                    mAdapter.setIsAllClick(true);

                }

            }else{
                check.setChecked(false);
                checkOne.remove(Integer.toString(memoId));

                cb_edit_selected_all.setChecked(false);

                //전체선택 false
                mAdapter.setIsAllClick(false);

            }
        }
    };

    /**
     * 롱클릭 리스너
     */
    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // 오랫동안 눌렀을 때 이벤트가 발생됨
            Toast.makeText(getApplicationContext(),
                    "삭제할 목록을 선택하세요.", Toast.LENGTH_SHORT).show();
            cb_edit_selected_all.setVisibility(View.VISIBLE);
            mAdapter.setEditMode(true);
            mAdapter.notifyDataSetChanged();
            ll_edit_select_bar.startAnimation(anim_down);
            // 리턴값이 있다
            // 이메서드에서 이벤트에대한 처리를 끝냈음
            //    그래서 다른데서는 처리할 필요없음 true
            // 여기서 이벤트 처리를 못했을 경우는 false
            return true;
        }
    };


    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 클릭 이벤트* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */


    @Override
    public void onClickClose() {

    }

    @Override
    public void onBackPressed() {
        if (!mAdapter.getEditMode()) {
            finish();
        } else { // 수정모드일때
            checkOne.clear();
            mAdapter.setIsAllClick(false);
            mAdapter.setEditMode(false);
            mAdapter.notifyDataSetChanged();
            cb_edit_selected_all.setChecked(false);
            ll_edit_select_bar.startAnimation(anim_up);
            cb_edit_selected_all.setVisibility(View.GONE);

        }
    }

    /**
     * 삭제버튼
     */
    @OnClick(R.id.btn_main_delete)
    public void onClickDelete() {

        if(checkOne.size() > 0) {
            //callDATA 메모 리스트 삭제 프레젠터
            presenter.callMainDeleteDATA(cb_edit_selected_all.isChecked(), mMemoList, checkOne);

            cb_edit_selected_all.setVisibility(View.GONE);

            checkOne.clear(); // 선택된 체크리스트 초기화
            ll_edit_select_bar.startAnimation(anim_up);
            initView();

        }else{
            Toast.makeText(getApplicationContext(),
                    "삭제할 목록을 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 메모추가 버튼
     */
    @OnClick(R.id.btn_add_memo)
    @Override
    public void onClickAddMemo() {
        Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
        startActivity(intent);
    }

    /**
     * 검색 버튼
     */
    @OnClick(R.id.btn_option3)
    @Override
    public void onClickSearchMemo() {
        Intent intent = new Intent(MainActivity.this, SearchMemoActivity.class);
        startActivity(intent);
    }

}
