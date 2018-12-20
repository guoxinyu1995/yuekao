package com.example.yuekaomoni01.adaper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuekaomoni01.R;
import com.example.yuekaomoni01.bean.ShoppCarBean;
import com.example.yuekaomoni01.custom.CarCuctomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ShoppCarBean.DataBean.ListBean> list;
    //存放商家id和商家名称的map集合
    private Map<String,String> map = new HashMap<>();
    public ShoppAdaper(Context mContext) {
        this.mContext = mContext;
    }
    /**
     * 添加数据并更新显示
     * */
    public void add(ShoppCarBean shoppBean){
        //传进来的是bean对象
        if(list == null){
            list = new ArrayList<>();
        }
        //第一层遍历商家和商品
        for(ShoppCarBean.DataBean bean:shoppBean.getData()){
            //把商品的id和商品的名称添加到map集合里 ,,为了之后方便调用
            map.put(bean.getSellerid(),bean.getSellerName());
            //第二层遍历里面的商品
            for(int i = 0;i<bean.getList().size();i++){
                //添加到list集合里
                list.add(bean.getList().get(i));
            }
        }
        //调用方法 设置显示或隐藏 商铺名
        setFirst(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.shopp_car_item1,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        /**
         * 设置商铺的 shop_checkbox和商铺的名字 显示或隐藏
         * */
        final MyViewHolder holder = (MyViewHolder) viewHolder;
        if(list.get(i).getIsFirst() == 1){
            //显示商家
            holder.shop_check.setVisibility(View.VISIBLE);
            holder.shopp_commodity.setVisibility(View.VISIBLE);
            //设置选中状态
            holder.shop_check.setChecked(list.get(i).isShop_check());
            holder.shopp_commodity.setText(map.get(String.valueOf(list.get(i).getSellerid())));
        }else{
            //隐藏
            holder.shop_check.setVisibility(View.GONE);
            holder.shopp_commodity.setVisibility(View.GONE);
        }
        //拆分images字段
        String[] str = list.get(i).getImages().split("\\|");
        //设置商品的图片
        Glide.with(mContext).load(str[0]).into(holder.item_image);
        //控制商品的checkbox，根据字段改变
        holder.item_check.setChecked(list.get(i).isItem_check());
        holder.item_name.setText(list.get(i).getTitle());
        holder.item_price.setText(list.get(i).getPrice()+"");
        //调用customview里的方法设置加减号中间的数字
        holder.custom.setEditText(list.get(i).getNum());
        //商铺的shop_checkbox点击事件 ,控制商品的item_checkbox
        holder.shop_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的shop_check
                list.get(i).setShop_check(holder.shop_check.isChecked());
                for(int a = 0;a<list.size();a++){
                    //如果是同一家商铺的 都给成相同状态
                    if(list.get(i).getSellerid() == list.get(a).getSellerid()){
                        //当前条目的选中状态 设置成 当前商铺的选中状态
                        list.get(a).setItem_check(holder.shop_check.isChecked());
                    }
                }
                //刷新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });
        //商品的item_checkbox点击事件,控制商铺的shop_checkbox
        holder.item_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的item_checkbox
                list.get(i).setItem_check(holder.item_check.isChecked());
                //反向控制商铺的shop_checkbox
                for(int s = 0;s<list.size();s++){
                    for(int j = 0;j<list.size();j++){
                        //如果两个商品是同一家店铺的 并且 这两个商品的item_checkbox选中状态不一样
                        if(list.get(s).getSellerid() == list.get(j).getSellerid()&&!list.get(j).isItem_check()){
                            //就把商铺的shop_checkbox改成false
                            list.get(s).setShop_check(false);
                            break;
                        }else{
                            //同一家商铺的商品 选中状态都一样,就把商铺shop_checkbox状态改成true
                            list.get(s).setShop_check(true);
                        }
                    }
                }
                //更新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(list);
            }
        });
        //删除条目的点击事件
        holder.item_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //移除集合中的当前数据
                list.remove(i);
                //删除完当前的条目 重新判断商铺的显示隐藏
                setFirst(list);
                //调用重新求和
                sum(list);
                notifyDataSetChanged();
            }
        });
        //加减号的监听,
        holder.custom.setCustomCallBack(new CarCuctomView.CustomCallBack() {
            @Override
            public void callBack(int count) {
                //改变数据源中的数量
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }

            @Override
            public void shuruzhi(int count) {
                list.get(i).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });
    }
    /**
     * 设置数据源,控制是否显示商家
     * */
    private void setFirst(List<ShoppCarBean.DataBean.ListBean> list) {
        if(list.size()>0){
            //如果是第一条数据就设置isFirst为1
            list.get(0).setIsFirst(1);
            //从第二条开始遍历
            for(int i=1;i<list.size();i++){
                //如果和前一个商品是同一家商店的
                if(list.get(i).getSellerid() == list.get(i-1).getSellerid()){
                    //设置成2不显示商铺
                    list.get(i).setIsFirst(2);
                }else{
                    //设置成1显示商铺
                    list.get(i).setIsFirst(1);
                    //如果当前条目选中,把当前的商铺也选中
                    if(list.get(i).isItem_check() == true){
                        list.get(i).setShop_check(list.get(i).isItem_check());
                    }
                }
            }
        }
    }
    //求和的方法
    private void sum(List<ShoppCarBean.DataBean.ListBean> list) {
        //初始的总价为0
        int totalNum = 0;
        double totalPrice = 0.0;
        boolean allCheck = true;
        for(int i = 0;i<list.size();i++){
            //把 已经选中的 条目 计算价格
            if(list.get(i).isItem_check()){
                totalNum += list.get(i).getNum();
                totalPrice+= list.get(i).getNum()*list.get(i).getPrice();
            }else{
                //如果有个未选中,就标记为false
                allCheck = false;
            }
        }
        //接口回调出去 把总价 总数量 和allcheck 传给view层
        updateListener.setTotal(totalPrice+"",totalNum+"",allCheck);
    }
    //view层调用这个方法, 点击quanxuan按钮的操作
    public void queryAll(boolean checked){
        for(int i = 0;i<list.size();i++){
            list.get(i).setShop_check(checked);
            list.get(i).setItem_check(checked);
        }
        notifyDataSetChanged();
        sum(list);
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public final CheckBox shop_check;
        public final TextView shopp_commodity;
        public final CheckBox item_check;
        public final ImageView item_image;
        public final TextView item_name;
        public final TextView item_price;
        public final CarCuctomView custom;
        public final ImageView item_del;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shop_check = itemView.findViewById(R.id.shop_check);
            shopp_commodity = itemView.findViewById(R.id.shopp_commodity);
            item_check = itemView.findViewById(R.id.item_check);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            custom = itemView.findViewById(R.id.custom);
            item_del = itemView.findViewById(R.id.item_del);
        }
    }
    private UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    //定义接口
    public interface UpdateListener{
        void setTotal(String total,String num,boolean allCheck);
    }
}
