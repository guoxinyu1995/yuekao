package com.example.erjiliandong01.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.erjiliandong01.R;
import com.example.erjiliandong01.bean.RightBean;

import java.util.ArrayList;
import java.util.List;

public class RightAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RightBean.DataBean> mData;
    private Context mContext;

    public RightAdaper(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void setmData(List<RightBean.DataBean> datas){
       mData = datas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.right_item,viewGroup,false);
        ViewHolderRight holderRight = new ViewHolderRight(view);
        return holderRight;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderRight holderRight = (ViewHolderRight) viewHolder;
        holderRight.type_name.setText(mData.get(i).getName());

        RightItemAdaper itemAdaper = new RightItemAdaper(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        holderRight.right_item_recycle.setLayoutManager(layoutManager);
        holderRight.right_item_recycle.setAdapter(itemAdaper);
        holderRight.right_item_recycle.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        itemAdaper.setmList(mData.get(i).getList());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolderRight extends RecyclerView.ViewHolder{
        public final TextView type_name;
        public final RecyclerView right_item_recycle;
        public ViewHolderRight(@NonNull View itemView) {
            super(itemView);
            type_name = itemView.findViewById(R.id.type_name);
            right_item_recycle = itemView.findViewById(R.id.right_item_recycle);
        }
    }
}
