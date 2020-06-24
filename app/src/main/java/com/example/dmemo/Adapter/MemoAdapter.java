package com.example.dmemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmemo.View.Main.MainActivity;
import com.example.dmemo.View.Main.MainContract;
import com.example.dmemo.View.Memo.MemoDetailActivity;
import com.example.dmemo.R;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private Context context;

    private ArrayList<memoListDTO> feedList = new ArrayList<>();


    private int memoId;
    /**
     * 롱클릭 후 수정 모드 여부
     */
    private boolean isEditMode = false;

    /**
     * 메인 에서 전체 선택 여부
     */
    private boolean isAllClick = false;
    /**
     * 롱클릭 리스너
     */
    private View.OnLongClickListener onLongClickListener;
    /**
     * 낱개 선택 리스너
     */
    private CompoundButton.OnClickListener checkItemlistener;
    /**
     * 리스트 아이템 판별을 위한 DTO (_id)
     */
    private memoListDTO memoListForCheckBox;
    /**
     * 체크박스 낱개
     */
    private ArrayList<String> checkOne = new ArrayList<>();

    public void setFeedList(ArrayList<memoListDTO> feedList) {
        this.feedList = feedList;
    }

    public void setLongClickItemlistener(View.OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public void setCheckItemlistener(CompoundButton.OnClickListener checkItemlistener) {
        this.checkItemlistener = checkItemlistener;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_list, parent, false);

        return new MemoViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final MemoViewHolder holder, int position) {

        Log.d("도은아", position + "");
        final memoListDTO item = feedList.get(position);

        memoListForCheckBox = feedList.get(position);

        holder.tv_title.setText(item.getTitle());
        holder.tv_contents.setText(item.getContent());
        holder.tv_date.setText(item.getDate());
        holder.cb_edit_selected.setTag(memoListForCheckBox.getId());
        holder.cb_edit_selected.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        holder.cb_edit_selected.setOnClickListener(checkItemlistener);


        if(isEditMode && isAllClick){     // 모두선택이면 모든체크박스 true
            holder.cb_edit_selected.setChecked(true);
        }else if(isEditMode && !isAllClick && checkOne(holder)){ // 모두선택이 아니고(체크박스 낱개로 해제시) 그려지지않은 부분은 true
            holder.cb_edit_selected.setChecked(true);
        }else{
            holder.cb_edit_selected.setChecked(false);           // 모두선택이 아니면 false
        }

        holder.ll_memo_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isEditMode) {
                    return;
                }
                Intent intent = new Intent(context, MemoDetailActivity.class);
                intent.putExtra("_id", item.getId());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("content", item.getContent());
                intent.putExtra("date", item.getDate());
                context.startActivity(intent);
            }
        });


        holder.ll_memo_list.setOnLongClickListener(onLongClickListener);


    }
    /**
     * 전체선택 후 1개 리스트를 빼고 스크롤하면 아래쪽 체크리스트가 풀리는 오류를 해결하기 위한 플래그 매소드
     */
    public boolean checkOne(MemoViewHolder holder){
        boolean b = false;
        for(int i = 0; i < checkOne.size(); i++){
            Log.d("도은아", holder.cb_edit_selected.getTag().toString() + "////"+ checkOne.get(i));
            if(holder.cb_edit_selected.getTag().toString().equals(checkOne.get(i))){
            return true;
         }
     }
     return false;
    }

    public boolean getEditMode() {
        return isEditMode;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public boolean getIsAllClick() {
        return isAllClick;
    }

    public void setIsAllClick(boolean allClick) {
        isAllClick = allClick;
    }

    public void setCheckOne(ArrayList<String> checkOneList) {
        checkOne = checkOneList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }


    public class MemoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_contents)
        TextView tv_contents;

        @BindView(R.id.tv_date)
        TextView tv_date;

        @BindView(R.id.iv_indicator)
        ImageView iv_indicator;

        @BindView(R.id.ll_memo_list)
        LinearLayout ll_memo_list;

        @BindView(R.id.cb_edit_selected)
        CheckBox cb_edit_selected;


        public MemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
