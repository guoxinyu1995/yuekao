package com.example.erjiliandong01.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.erjiliandong01.R;
import com.example.erjiliandong01.bean.RightBean;

import java.util.ArrayList;
import java.util.List;

public class RightItemAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RightBean.DataBean.ListBean> mList;
    private Context mContext;

    public RightItemAdaper(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }
    public void setmList(List<RightBean.DataBean.ListBean> lists){
        mList = lists;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reigeh_item_type,viewGroup,false);
        ViewHolderRigthItem holderRigthItem = new ViewHolderRigthItem(view);
        return holderRigthItem;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderRigthItem holderRigthItem = (ViewHolderRigthItem) viewHolder;
        Glide.with(mContext).load(mList.get(i).getIcon()).into(holderRigthItem.right_image);
        holderRigthItem.right_name.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolderRigthItem extends RecyclerView.ViewHolder{
        public final ImageView right_image;
        public final TextView right_name;
        public ViewHolderRigthItem(@NonNull View itemView) {
            super(itemView);
            right_image = itemView.findViewById(R.id.right_image);
            right_name = itemView.findViewById(R.id.right_name);
        }
    }
}
