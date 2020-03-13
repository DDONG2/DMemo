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

    /**
     * 애니메이션
     */
    private Animation anim_down;
    private Animation anim_up;

    private  ArrayList<String> checkedIds = new ArrayList<>();
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
        initView();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {


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
        }
        db.close();

        if (memoList != null && memoList.size() > 0) {
            tv_list_conut.setText("노트" + " " + String.valueOf(memoList.size()) +"개");
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

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // 오랫동안 눌렀을 때 이벤트가 발생됨
            Toast.makeText(getApplicationContext(),
                    "삭제할 목록을 선택하세요.", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClickClose() {

    }


    @OnClick(R.id.btn_main_delete)
    public void onClicksubmit() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 0; i < checkedIds.size(); i++) {
            String sql = "delete from  mytable where _id = '" + checkedIds.get(i) + "'";
            db.execSQL(sql);
        }
        checkedIds.clear();
        initView();
        ll_edit_select_bar.startAnimation(anim_up);
    }


    @OnClick(R.id.btn_add_memo)
    @Override
    public void onClickAddMemo() {
        Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
        startActivity(intent);
    }

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

    @Override
    public void onBackPressed() {
        if (!adapter.getEditMode()) {
            finish();
        } else {
            adapter.setEditMode(false);
            adapter.notifyDataSetChanged();
            ll_edit_select_bar.startAnimation(anim_up);

        }
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

    private CompoundButton.OnCheckedChangeListener itemCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getTag().toString().indexOf(memoListDTO.class.getSimpleName()) > 0) {
                memoListDTO memolist = (memoListDTO) buttonView.getTag();
                    if (isChecked) {
                        checkedIds.add(Integer.toString(memolist.getId()));
                    } else {
                        checkedIds.remove(Integer.toString(memolist.getId()));
                    }

            }

        }
    };



}
