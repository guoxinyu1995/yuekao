package com.example.shoppingcart02.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shoppingcart02.R;
import com.example.shoppingcart02.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class MearchantAdapaer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopBean.DataBean> mData;
    private Context mContext;

    public MearchantAdapaer(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void setmData(List<ShopBean.DataBean> datas){
        mData.addAll(datas);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.merchant_item,viewGroup,false);
        ViewHolderMearchant holderMearchant = new ViewHolderMearchant(view);
        return holderMearchant;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolderMearchant holderMearchant = (ViewHolderMearchant) viewHolder;
        //设置商家
        holderMearchant.mearchant_text.setText(mData.get(i).getSellerName());
        final ShopAdaper shopAdaper = new ShopAdaper(mContext,mData.get(i).getList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        holderMearchant.mearchant_recycle.setLayoutManager(layoutManager);
        holderMearchant.mearchant_recycle.setAdapter(shopAdaper);
        holderMearchant.mearchant_check.setChecked(mData.get(i).isCheck());

        shopAdaper.setShopCallBack(new ShopAdaper.ShopCallBack() {
            @Override
            public void callBack() {
                //从商品里回调回来
                if(mearchantCallBack!=null){
                    mearchantCallBack.callBack(mData);
                }
                List<ShopBean.DataBean.ListBean> list = mData.get(i).getList();
                //创建一个临时标志位
                boolean isAllCheck = true;
                for (ShopBean.DataBean.ListBean bean:list){
                    if(!bean.isCheck()){
                        //只要有一个商品未选中，标志位设置成false，并且跳出循环
                        isAllCheck = false;
                        break;
                    }
                }
                //刷新商家状态
                holderMearchant.mearchant_check.setChecked(isAllCheck);
                mData.get(i).setCheck(isAllCheck);
            }
        });

        //监听checkBox的点击事件
        //目的是改变旗下所有商品的选中状态
        holderMearchant.mearchant_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首先改变自己的标志位
                mData.get(i).setCheck(holderMearchant.mearchant_check.isChecked());
                //调用产品adapter的方法，用来全选和反选
                shopAdaper.selectOrRemoveAll(holderMearchant.mearchant_check.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class ViewHolderMearchant extends RecyclerView.ViewHolder{
            public final CheckBox mearchant_check;
            public final TextView mearchant_text;
            public final RecyclerView mearchant_recycle;
        public ViewHolderMearchant(@NonNull View itemView) {
            super(itemView);
            mearchant_check = itemView.findViewById(R.id.mearchant_check);
            mearchant_text = itemView.findViewById(R.id.mearchant_text);
            mearchant_recycle = itemView.findViewById(R.id.mearchant_recycle);
        }
    }

    private MearchantCallBack mearchantCallBack;
    public void setMearchantCallBack(MearchantCallBack mearchantCallBack){
        this.mearchantCallBack = mearchantCallBack;
    }
    //定义接口
    public interface MearchantCallBack{
        void callBack(List<ShopBean.DataBean> list);
    }
}
