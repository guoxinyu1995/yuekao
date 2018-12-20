package com.example.yuekaomoni01.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuekaomoni01.R;
import com.example.yuekaomoni01.bean.ShowBean;

import java.util.ArrayList;
import java.util.List;

public class ShowAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShowBean.DataBean> mData;
    private Context mContext;

    public ShowAdaper(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void setmData(List<ShowBean.DataBean> datas){
        mData.clear();
        if(datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addmData(List<ShowBean.DataBean> datas){
        if(datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.show_item,viewGroup,false);
        ViewHolderShow holderShow = new ViewHolderShow(view);
        return holderShow;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderShow holderShow = (ViewHolderShow) viewHolder;
        String url = mData.get(i).getImages().split("\\|")[0].replace("https","http");
        holderShow.title.setText(mData.get(i).getTitle());
        holderShow.price.setText("价格:"+mData.get(i).getPrice());
        Glide.with(mContext).load(url).into(holderShow.image);
        holderShow.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showCallBack!=null){
                    showCallBack.CallBack(mData.get(i).getPid());
                }
            }
        });
        holderShow.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showClickCallBack!=null){
                    showClickCallBack.CallBack(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolderShow extends RecyclerView.ViewHolder{
        public final ImageView image;
        public final TextView title;
        public final TextView price;
        public final Button btn;
        public final ConstraintLayout constraintLayout;
        public ViewHolderShow(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.show_item_image);
            title = itemView.findViewById(R.id.show_item_title);
            price = itemView.findViewById(R.id.show_item_price);
            btn = itemView.findViewById(R.id.show_item_button);
            constraintLayout = itemView.findViewById(R.id.con);
        }
    }


    //加入购物车点击按钮
    private ShowCallBack showCallBack;
    public void setShowCallBack(ShowCallBack showCallBack){
        this.showCallBack = showCallBack;
    }
    //定义接口
    public interface ShowCallBack{
        void CallBack(int pid);
    }

    //点击跳转
    private ShowClickCallBack showClickCallBack;
    public void setShowClickCallBack(ShowClickCallBack showClickCallBack){
        this.showClickCallBack = showClickCallBack;
    }
    //定义接口
    public interface ShowClickCallBack{
        void CallBack(int position);
    }
}
