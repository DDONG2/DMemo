package com.example.dmemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dmemo.Main.MainActivity;
import com.example.dmemo.Memo.MemoDetailActivity;
import com.example.dmemo.R;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

    private Context context;

    private ArrayList<memoListDTO> feedList = new ArrayList<>();

    private boolean isEditMode = false;

    private View.OnLongClickListener onLongClickListener;

    public void setFeedList(ArrayList<memoListDTO> feedList) {
        this.feedList = feedList;
    }

    public void setCheckItemlistener(View.OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_list, parent, false);

        return new MemoViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final MemoViewHolder holder, int position) {

        final memoListDTO item = feedList.get(position);


        holder.tv_title.setText(item.getTitle());
        holder.tv_date.setText(item.getDate());
        holder.cb_edit_selected.setVisibility(isEditMode ? View.VISIBLE : View.GONE);

        holder.ll_memo_list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }

    public boolean getEditMode() {
        return isEditMode;
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
