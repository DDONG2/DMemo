package com.example.dmemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmemo.R;
import com.example.dmemo.dateDTO.memoListDTO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder> {

        private Context context;

        private ArrayList<memoListDTO> feedList = new ArrayList<>();



        public void setFeedList(ArrayList<memoListDTO> feedList){
                this.feedList = feedList;
        }

        @Override
        public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                context = parent.getContext();

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_list, parent, false);

                return new MemoViewHolder(view);

        }



        @Override
        public void onBindViewHolder(final MemoViewHolder holder, int position) {

//                holder.tv_subject.setText(feedList.get(position).getSubject());
//                holder.tv_date.setText(feedList.get(position).getRegdate().substring(0, 10));
//                holder.iv_indicator.setText(R.string.faq_content);




        }

        @Override
        public int getItemCount() {
                return feedList.size();
        }




        public class MemoViewHolder extends RecyclerView.ViewHolder{

                @BindView(R.id.tv_subject)
                TextView tv_subject;

                @BindView(R.id.tv_date)
                TextView tv_date;

                @BindView(R.id.iv_indicator)
                ImageView iv_indicator;

                public MemoViewHolder(View itemView) {
                        super(itemView);

                        ButterKnife.bind(this, itemView);
                }



        }

        }
