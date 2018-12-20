package com.example.shoppingcart02.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoppingcart02.R;
import com.example.shoppingcart02.bean.ShopBean;
import com.example.shoppingcart02.custom.CustomView;
import java.util.List;
/**
 * 商品
 * */
public class ShopAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ShopBean.DataBean.ListBean> mList;

    public ShopAdaper(Context mContext, List<ShopBean.DataBean.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item,viewGroup,false);
        ViewHolderShop holderShop = new ViewHolderShop(view);
        return holderShop;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolderShop holderShop = (ViewHolderShop) viewHolder;
        String url = mList.get(i).getImages().split("\\|")[0].replace("https","http");
        Glide.with(mContext).load(url).into(holderShop.shop_image);
        holderShop.shop_title.setText(mList.get(i).getTitle());
        holderShop.shop_price.setText("价格:"+mList.get(i).getPrice()+"元");
        //根据我记录的状态，改变勾选
        holderShop.shop_check.setChecked(mList.get(i).isCheck());
        //添加商品的选中的监听
        holderShop.shop_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //优选改变自己的状态
                mList.get(i).setCheck(isChecked);
                //回调
                if(shopCallBack!=null){
                    shopCallBack.callBack();
                }
            }
        });
        //设置自定义view里的edit
        holderShop.shop_custom.setData(this,mList,i);
        holderShop.shop_custom.setCustomCallBack(new CustomView.CustomCallBack() {
            @Override
            public void callBack() {
                if(shopCallBack!=null){
                    shopCallBack.callBack();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolderShop extends RecyclerView.ViewHolder{
        public final ImageView shop_image;
        public final TextView shop_title;
        public final TextView shop_price;
        public final CheckBox shop_check;
        public final CustomView shop_custom;
        public ViewHolderShop(@NonNull View itemView) {
            super(itemView);
            shop_check = itemView.findViewById(R.id.shop_check);
            shop_custom = itemView.findViewById(R.id.shop_custom);
            shop_image = itemView.findViewById(R.id.shop_image);
            shop_price = itemView.findViewById(R.id.shop_price);
            shop_title = itemView.findViewById(R.id.shop_title);
        }
    }

    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */
    public void selectOrRemoveAll(boolean isSelectAll){
        for(ShopBean.DataBean.ListBean listBean:mList){
            listBean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }
    private ShopCallBack shopCallBack;
    public void setShopCallBack(ShopCallBack shopCallBack){
        this.shopCallBack = shopCallBack;
    }
    //定义接口
    public interface ShopCallBack{
        void callBack();
    }
}
