package com.example.erjiliandong01.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.erjiliandong01.R;
import com.example.erjiliandong01.bean.LeftBean;

import java.util.ArrayList;
import java.util.List;

public class LeftAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LeftBean.DataBean> mLeftData;
    private Context mContext;

    public LeftAdaper(Context mContext) {
        this.mContext = mContext;
        mLeftData = new ArrayList<>();
    }
    public void setmLeftData(List<LeftBean.DataBean> datas){
        mLeftData = datas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.left_item,viewGroup,false);
        ViewHolderLeft holderLeft = new ViewHolderLeft(view);
        return holderLeft;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderLeft holderLeft = (ViewHolderLeft) viewHolder;
        holderLeft.left_text.setText(mLeftData.get(i).getName());
        holderLeft.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leftCallBaack!=null){
                    leftCallBaack.callBack(i,mLeftData.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLeftData.size();
    }
    class ViewHolderLeft extends RecyclerView.ViewHolder{
        public final TextView left_text;
        public final ConstraintLayout constraintLayout;
        public ViewHolderLeft(@NonNull View itemView) {
            super(itemView);
            left_text = itemView.findViewById(R.id.left_text);
            constraintLayout = itemView.findViewById(R.id.con);
        }
    }


    private LeftCallBaack leftCallBaack;
    public void setLeftCallBaack(LeftCallBaack leftCallBaack){
        this.leftCallBaack = leftCallBaack;
    }
    //定义接口
    public interface LeftCallBaack{
        void callBack(int i,int cid);
    }
}
