package com.example.dmemo.View.Search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.View.Main.MainActivity;
import com.example.dmemo.View.Main.MainPresenter;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SearchMemoActivity extends AppCompatActivity implements SearchMemoContract.View {

    private SearchMemoPresenter presenter;

    @BindView(R.id.rc_search_memo_list)
    RecyclerView rc_search_memo_list;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.tv_empty)
    TextView tv_empty;

    @BindView(R.id.cb_edit_search_selected_all)
    CheckBox cb_edit_search_selected_all;

    @BindView(R.id.ll_edit_search_select_bar)
    LinearLayout ll_edit_search_select_bar;

    private MemoAdapter adapter;

    /**
     * 검색 후 나온 전체 리스트 ( 검색 할 때마다 바뀜)
     */
    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();

    /**
     * 체크박스 선택된 낱개 리스트
     */
    private ArrayList<String> checkOne = new ArrayList<>();
    /**
     * 애니메이션
     */
    private Animation anim_down;
    private Animation anim_up;

    private String searchText;
    private int memoId;

    private TextWatcher memoWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            memoList.clear();
            searchText = s.toString();
            onRefreshAdapter();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_memo);

        ButterKnife.bind(this);

        presenter = new SearchMemoPresenter(this);
        //  presenter.onStartPresenter();
        initView();

        initAnimationView();

    }

    @Override
    public void onBackPressed() {
        if (!adapter.getEditMode()) {
            finish();
        } else {
            checkOne.clear();
            adapter.setIsAllClick(false);
            adapter.setEditMode(false);
            adapter.notifyDataSetChanged();
            cb_edit_search_selected_all.setChecked(false);
            ll_edit_search_select_bar.startAnimation(anim_up);
            cb_edit_search_selected_all.setVisibility(View.GONE);

        }
    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void initView() {

        et_search.addTextChangedListener(memoWatcher);

        adapter = new MemoAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_search_memo_list.setLayoutManager(manager);

        rc_search_memo_list.setAdapter(adapter);


    }

    @Override
    public void onClickClose() {

    }

    /**
     * 애니메이션 효과 설정 메소드
     */
    public void initAnimationView() {
        anim_up = AnimationUtils.loadAnimation(this, R.anim.push_up);
        anim_up.setAnimationListener(new SearchMemoActivity.FlowAnimationListener());

        anim_down = AnimationUtils.loadAnimation(this, R.anim.push_down);
        anim_down.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                ll_edit_search_select_bar.setVisibility(View.VISIBLE);
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
     * 롱클릭 리스너
     */
    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // 오랫동안 눌렀을 때 이벤트가 발생됨
            Toast.makeText(getApplicationContext(),
                    "삭제할 목록을 선택하세요.", Toast.LENGTH_SHORT).show();
            cb_edit_search_selected_all.setVisibility(View.VISIBLE);
            adapter.setEditMode(true);
            adapter.notifyDataSetChanged();
            ll_edit_search_select_bar.startAnimation(anim_down);
            // 리턴값이 있다
            // 이메서드에서 이벤트에대한 처리를 끝냈음
            //    그래서 다른데서는 처리할 필요없음 true
            // 여기서 이벤트 처리를 못했을 경우는 false
            return true;
        }
    };

    /**
     * 애너매니션 콜백 클래스
     */
    private final class FlowAnimationListener implements
            Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            if (ll_edit_search_select_bar.getVisibility() == View.VISIBLE)
                ll_edit_search_select_bar.setVisibility(View.GONE);
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
            if (check.isChecked()) {
                check.setChecked(true);
                checkOne.add(Integer.toString(memoId));
                if (checkOne.size() > 0 && checkOne.size() == memoList.size()) {

                    cb_edit_search_selected_all.setChecked(true);

                    adapter.setIsAllClick(true);
                    //전체선택 true
                }
                //전체선택 false
            } else {
                check.setChecked(false);
                checkOne.remove(Integer.toString(memoId));

                cb_edit_search_selected_all.setChecked(false);

                adapter.setIsAllClick(false);

            }
        }
    };

    /**
     * 검색어 입력,삭제 후 어뎁터 갱신하는 메소드
     */
    public void onRefreshAdapter() {
        if (searchText.length() != 0) {

            DBHelper helper2 = new DBHelper(getActivityContext());
            SQLiteDatabase db2 = helper2.getWritableDatabase();
            Cursor cursor = db2.rawQuery("select _id, title, content, date, imagepath from mytable where title like '%" + searchText + "%' or content like '%" + searchText + "%' order by _id desc", null);
            //결국 cursor 에 select한 값이 들어온다!!
            while (cursor.moveToNext()) {
                memoListDTO memo = new memoListDTO();
                memo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
                memo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                memo.setContent(cursor.getString(cursor.getColumnIndex("content")));
                memo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                //memo.setImagePath(cursor.getString(cursor.getColumnIndex("imagepath")));
                memoList.add(memo);
                //checkAll.add(Integer.toString(memo.getId()));  // 전체선택 리스트를 넣어둔다.
            }
            db2.close();
        }
        if (memoList.size() <= 0) {
            adapter.setFeedList(memoList);
            adapter.notifyDataSetChanged();
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            adapter.setFeedList(memoList);
            adapter.setLongClickItemlistener(longClickListener);
            adapter.notifyDataSetChanged();
            tv_empty.setVisibility(View.GONE);

            adapter.setCheckItemlistener(itemCheckListener);

            cb_edit_search_selected_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_edit_search_selected_all.isChecked()) {
                        checkOne.clear();
                        for (int i = 0; i < memoList.size(); i++) { // 전체선택일경우 checkOne 클리어 후 모든 리스트를 add 해준다. (* 리사이클러뷰 뷰홀더 이슈 그려지지 않은 리스트는 갱신이 되지 않음, 데이터리스트에 들어오지 않음)
                            checkOne.add(Integer.toString(memoList.get(i).getId()));
                        }
                        adapter.setIsAllClick(true);
                        adapter.setCheckOne(checkOne);
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {  // 쓰레드 post 를 해준 이유는 리사이클러뷰 UI 쓰레드 이슈 떄문
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        };
                        handler.post(r);

                    } else {
                        checkOne.clear();
                        adapter.setIsAllClick(false);
                        adapter.setCheckOne(checkOne);
                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        };
                        handler.post(r);
                    }
                }
            });
        }
    }

    /**
     * 삭제버튼
     */
    @OnClick(R.id.btn_main_delete)
    public void onClicksubmit() {

        if (checkOne.size() > 0) {
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            if (cb_edit_search_selected_all.isChecked()) { //전체선택이 되어있을땐 모든 데이터를 지운다.
                for (int i = 0; i < memoList.size(); i++) {
                    String sql = "delete from  mytable where _id = '" + memoList.get(i).getId() + "'";
                    db.execSQL(sql);
                }
            } else {
                for (int i = 0; i < checkOne.size(); i++) {  //전체선택이 아닐경우 선택된 데이터만 지운다.
                    String sql = "delete from  mytable where _id = '" + checkOne.get(i) + "'";
                    db.execSQL(sql);
                }
            }
            cb_edit_search_selected_all.setVisibility(View.GONE);

            checkOne.clear();
            initView();
            ll_edit_search_select_bar.startAnimation(anim_up);

            memoList.clear();

            onRefreshAdapter();

        } else {
            Toast.makeText(getApplicationContext(),
                    "삭제할 목록을 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }


}
