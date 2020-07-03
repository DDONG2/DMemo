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


    private MemoAdapter adapter;

    /**
     * 모든 메모 리스트
     */
    private ArrayList<memoListDTO> memoList = new ArrayList<memoListDTO>();


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

//    private ArrayList<GsonDTO.Price> priceList = new ArrayList<GsonDTO.Price>();

//    private ArrayList<GsonDTO.Price> selectPriceList = new ArrayList<GsonDTO.Price>();

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


// ★제이슨 연습용★
//        String a = "{\"ret_code\":\"1000\",\"ret_msg\":\"처리 결과 응답 메시지\"" +
//                ",\"data\":{\"noti_seq\":\"1\",\"noti_title\":\"공지사항 제목\",\"ver\":\"1000\",\"ctn_count\":2,\"service_count\":2" +
//                ",\"price\":[{\"ctn\":\"20대\",\"seq\":\"2\",\"url\":\"여자\",\"pic_default\":\"심플\",\"list_count\":0}" +
//                ",{\"ctn\":\"10대\",\"seq\":\"1\",\"url\":\"남자\",\"pic_default\":\"심플\",\"list_count\":0}" +
//                ",{\"ctn\":\"20대\",\"seq\":\"2\",\"url\":\"남자\",\"pic_default\":\"모던\",\"list_count\":0}" +
//                ",{\"ctn\":\"20대\",\"seq\":\"3\",\"url\":\"여자\",\"pic_default\":\"유니크\",\"list_count\":0}" +
//                ",{\"ctn\":\"30대\",\"seq\":\"4\",\"url\":\"여자\",\"pic_default\":\"유니크\",\"list_count\":0}" +
//                ",{\"ctn\":\"10대\",\"seq\":\"5\",\"url\":\"남자\",\"pic_default\":\"모던\",\"list_count\":0}" +
//                ",{\"ctn\":\"20대\",\"seq\":\"6\",\"url\":\"여자\",\"pic_default\":\"유니크\",\"list_count\":0}" +
//                ",{\"ctn\":\"10대\",\"seq\":\"7\",\"url\":\"여자\",\"pic_default\":\"심플\",\"list_count\":0}" +
//                ",{\"ctn\":\"40대\",\"seq\":\"8\",\"url\":\"여자\",\"pic_default\":\"유니크\",\"list_count\":0}" +
//                ",{\"ctn\":\"10대\",\"seq\":\"9\",\"url\":\"여자\",\"pic_default\":\"모던\",\"list_count\":0}" +
//                ",{\"ctn\":\"30대\",\"seq\":\"10\",\"url\":\"여자\",\"pic_default\":\"0\",\"list_count\":0}" +
//                ",{\"ctn\":\"20대\",\"seq\":\"11\",\"url\":\"여자\",\"pic_default\":\"유니크\",\"list_count\":0}" +
//                ",{\"ctn\":\"10대\",\"seq\":\"12\",\"url\":\"여자\",\"pic_default\":\"심플\",\"list_count\":0}" +
//                ",{\"ctn\":\"20대\",\"seq\":\"13\",\"url\":\"여자\",\"pic_default\":\"심플\",\"list_count\":0}]}" +
//                ",\"token\":\"\"}";
//        Gson gson = new Gson();
//        final GsonDTO price = gson.fromJson(a, GsonDTO.class);
//
//        for(int i= 0; i < price.getData().getPrice().size(); i++){
//            priceList.add(price.getData().getPrice().get(i));
//        }
//
//        for(int i= 0; i < priceList.size(); i++){
//            if(priceList.get(i).getCtn().equalsIgnoreCase("10대"))
//            {
//                selectPriceList.add(priceList.get(i));
//            }
//        }



        initAnimationView();

    }

// ★제이슨 연습용★
//    public class GsonDTO {
//        String ret_code;
//        String ret_msg;
//        Data data;
//
//        public String getRet_code() {
//            return ret_code;
//        }
//
//        public void setRet_code(String ret_code) {
//            this.ret_code = ret_code;
//        }
//
//        public String getRet_msg() {
//            return ret_msg;
//        }
//
//        public void setRet_msg(String ret_msg) {
//            this.ret_msg = ret_msg;
//        }
//
//        public Data getData() {
//            return data;
//        }
//
//        public void setData(Data data) {
//            this.data = data;
//        }
//
//        public class Data {
//            String noti_seq;
//            String noti_title;
//            String ver;
//            String ctn_count;
//            String service_count;
//            ArrayList<Price> price;
//
//            public String getNoti_seq() {
//                return noti_seq;
//            }
//
//            public void setNoti_seq(String noti_seq) {
//                this.noti_seq = noti_seq;
//            }
//
//            public String getNoti_title() {
//                return noti_title;
//            }
//
//            public void setNoti_title(String noti_title) {
//                this.noti_title = noti_title;
//            }
//
//            public String getVer() {
//                return ver;
//            }
//
//            public void setVer(String ver) {
//                this.ver = ver;
//            }
//
//            public String getCtn_count() {
//                return ctn_count;
//            }
//
//            public void setCtn_count(String ctn_count) {
//                this.ctn_count = ctn_count;
//            }
//
//            public String getService_count() {
//                return service_count;
//            }
//
//            public void setService_count(String service_count) {
//                this.service_count = service_count;
//            }
//
//            public ArrayList<Price> getPrice() {
//                return price;
//            }
//
//            public void setPrice(ArrayList<Price> price) {
//                this.price = price;
//            }
//        }
//
//        public class Price {
//            String ctn;
//            String seq;
//            String url;
//            String pic_default;
//            int list_count;
//
//            public String getCtn() {
//                return ctn;
//            }
//
//            public void setCtn(String ctn) {
//                this.ctn = ctn;
//            }
//
//            public String getSeq() {
//                return seq;
//            }
//
//            public void setSeq(String seq) {
//                this.seq = seq;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//
//            public String getPic_default() {
//                return pic_default;
//            }
//
//            public void setPic_default(String pic_default) {
//                this.pic_default = pic_default;
//            }
//
//            public int getList_count() {
//                return list_count;
//            }
//
//            public void setList_count(int list_count) {
//                this.list_count = list_count;
//            }
//        }
//
//    }

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

        memoList.clear();
        memoList = presenter.callMainInfoDATA();

        adapter = new MemoAdapter();
        //메모 데이터가 있을때
        if (memoList != null && memoList.size() > 0) {
            tv_list_conut.setText("노트" + " " + String.valueOf(memoList.size()) + "개");
            adapter.setFeedList(memoList);
            adapter.setLongClickItemlistener(longClickListener); // 어뎁터에 롱클릭 리스너 등록
            adapter.setCheckItemlistener(itemCheckListener); // 어뎁터에 낱개 선택 리스너 등록
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
                if (checkOne.size() > 0 && checkOne.size() == memoList.size()) {

                    cb_edit_selected_all.setChecked(true);

                    adapter.setIsAllClick(true);
                    //전체선택 true
                }
                //전체선택 false
            }else{
                check.setChecked(false);
                checkOne.remove(Integer.toString(memoId));

                cb_edit_selected_all.setChecked(false);

                adapter.setIsAllClick(false);

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
            checkOne.clear();
            adapter.setIsAllClick(false);
            adapter.setEditMode(false);
            adapter.notifyDataSetChanged();
            cb_edit_selected_all.setChecked(false);
            ll_edit_select_bar.startAnimation(anim_up);
            cb_edit_selected_all.setVisibility(View.GONE);

        }
    }

    /**
     * 삭제버튼
     */
    @OnClick(R.id.btn_main_delete)
    public void onClicksubmit() {

        if(checkOne.size()>0) {
            DBHelper helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            if (cb_edit_selected_all.isChecked()) { //전체선택이 되어있을땐 모든 데이터를 지운다.
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
            cb_edit_selected_all.setVisibility(View.GONE);

            checkOne.clear(); // 선택된 체크리스트 초기화
            initView();
            ll_edit_select_bar.startAnimation(anim_up);
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
