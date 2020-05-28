package com.example.dmemo.Main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.Memo.AddMemoActivity;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {


    private MemoAdapter adapter;

    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();


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

    /**
     * 애니메이션
     */
    private Animation anim_down;
    private Animation anim_up;
    /**
     * 체크박스 낱개
     */
    private ArrayList<String> checkOne = new ArrayList<>();

    /**
     * 체크박스 전체선택 리스트
     */
    private ArrayList<String> checkAll = new ArrayList<>();

    /**
     * 체크박스 전체선택 플래그 false 되는 시점은 전체선택후 1개만 체크 해제시 false 로 만든다.
     */
    private boolean isAllCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initAnimationView();
        initView();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {
        cb_edit_selected_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    adapter.setIsAllClick(true);
                    adapter.notifyDataSetChanged();
                    isAllCheck = true;
                } else {
                    if (isAllCheck) {
                        adapter.setIsAllClick(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
 
        memoList.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select _id, title, content, date, imagepath from mytable" + "         order by _id desc", null);
        //결국 cursor 에 select한 값이 들어온다!!
        while (cursor.moveToNext()) {
            memoListDTO memo = new memoListDTO();
            memo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
            memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            memo.setContent(cursor.getString(cursor.getColumnIndex("content")));
            memo.setDate(cursor.getString(cursor.getColumnIndex("date")));
            //memo.setImagePath(cursor.getString(cursor.getColumnIndex("imagepath")));
            memoList.add(memo);
            checkAll.add(Integer.toString(memo.getId()));  // 전체선택 리스트를 넣어둔다.
        }
        db.close();

        if (memoList != null && memoList.size() > 0) {
            tv_list_conut.setText("노트" + " " + String.valueOf(memoList.size()) + "개");
            adapter = new MemoAdapter();
            adapter.setFeedList(memoList);
            adapter.setLongClickItemlistener(longClickListener);
            adapter.setCheckItemlistener(itemCheckListener);
            rcMemoList.setAdapter(adapter);

            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rcMemoList.setLayoutManager(manager);

            rcMemoList.setVisibility(View.VISIBLE);
            tv_empty.setVisibility(View.GONE);

        } else {
            rcMemoList.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
            tv_list_conut.setText("0");
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
     * 리스트 체크박스 리스너
     */
    private CompoundButton.OnCheckedChangeListener itemCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getTag().toString().indexOf(memoListDTO.class.getSimpleName()) > 0) {
                memoListDTO memolist = (memoListDTO) buttonView.getTag();
                if (isChecked) {
                    checkOne.add(Integer.toString(memolist.getId()));
                    if (checkOne.size() > 0 && checkOne.size() == checkAll.size()) {   // 낱개로 선택해서 전체갯수가 될때 전체선택 체크박스를 트루로 만든다.
                        cb_edit_selected_all.setChecked(true);
                    }
                } else {
                    checkOne.remove(Integer.toString(memolist.getId()));
                    isAllCheck = false;                                     // 낱개가 하나라도 없어질경우 전체선택이 아님으로 전체선택 플래그를 false로 만든다.
                    cb_edit_selected_all.setChecked(false);

                }
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
            adapter.setEditMode(true);
            adapter.notifyDataSetChanged();
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
        if (!adapter.getEditMode()) {
            finish();
        } else {
            adapter.setEditMode(false);
            adapter.notifyDataSetChanged();
            ll_edit_select_bar.startAnimation(anim_up);
            cb_edit_selected_all.setVisibility(View.GONE);

        }
    }

    /**
     * 삭제버튼
     */
    @OnClick(R.id.btn_main_delete)
    public void onClicksubmit() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (cb_edit_selected_all.isChecked()) { //전체선택이 되어있을땐 모든 데이터를 지운다.
            for (int i = 0; i < checkAll.size(); i++) {
                String sql = "delete from  mytable where _id = '" + checkAll.get(i) + "'";
                db.execSQL(sql);
            }
        } else {
            for (int i = 0; i < checkOne.size(); i++) {  //전체선택이 아닐경우 선택된 데이터만 지운다.
                String sql = "delete from  mytable where _id = '" + checkOne.get(i) + "'";
                db.execSQL(sql);
            }
        }
        cb_edit_selected_all.setVisibility(View.GONE);
        checkAll.clear();
        checkOne.clear();
        initView();
        ll_edit_select_bar.startAnimation(anim_up);
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


}
