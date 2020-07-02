package com.example.dmemo.View.Search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmemo.Adapter.MemoAdapter;
import com.example.dmemo.R;
import com.example.dmemo.Utils.DBHelper;
import com.example.dmemo.View.Main.MainPresenter;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchMemoActivity extends AppCompatActivity implements SearchMemoContract.View {

    private SearchMemoPresenter presenter;

    @BindView(R.id.rc_search_memo_list)
    RecyclerView rc_search_memo_list;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.tv_empty)
    TextView tv_empty;

    private MemoAdapter adapter;

    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();


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

            if (s.length() != 0) {

                DBHelper helper = new DBHelper(getActivityContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select _id, title, content, date, imagepath from mytable where title like '%" + s + "%' or content like '%" + s + "%' order by _id desc", null);
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
                db.close();
            }
                if (memoList.size() <= 0) {
                    adapter.setFeedList(memoList);
                    adapter.notifyDataSetChanged();
                    tv_empty.setVisibility(View.VISIBLE);
                } else {
                    adapter.setFeedList(memoList);
                    adapter.notifyDataSetChanged();
                    tv_empty.setVisibility(View.GONE);

                }

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

}
