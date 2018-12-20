package com.example.shoppingcart02.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingcart02.R;
import com.example.shoppingcart02.adaper.MearchantAdapaer;
import com.example.shoppingcart02.api.Apis;
import com.example.shoppingcart02.bean.ShopBean;
import com.example.shoppingcart02.presenter.PresenterImpl;
import com.example.shoppingcart02.view.Iview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview,View.OnClickListener {

    private PresenterImpl presenter;
    private List<ShopBean.DataBean> mList;
    private CheckBox all_check;
    private TextView all_price;
    private TextView sum_price_text;
    private RecyclerView recycle_view;
    private MearchantAdapaer adapaer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PresenterImpl(this);
        initView();
        initData();
    }

    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","71");
        presenter.startRequest(Apis.URL_SHOPP_CART,map,ShopBean.class);
    }

    private void initView() {
        //获取资源id
        all_check = findViewById(R.id.all_check);
        all_price = findViewById(R.id.all_price);
        sum_price_text = findViewById(R.id.sum_price_text);
        recycle_view = findViewById(R.id.recycle_view);
        all_check.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycle_view.setLayoutManager(layoutManager);
        //创建适配器
        adapaer = new MearchantAdapaer(this);
        recycle_view.setAdapter(adapaer);

        adapaer.setMearchantCallBack(new MearchantAdapaer.MearchantCallBack() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                //在这里重新遍历已经改状态后的数据，
                // 这里不能break跳出，因为还需要计算后面点击商品的价格和数目，所以必须跑完整个循环
                double totalPrice = 0;
                //勾选商品的数量，不是该商品购买的数量
                int num = 0;
                //所有商品总数，和上面的数量做比对，如果两者相等，则说明全选
                int totalNum = 0;
                for(int a = 0;a<list.size();a++){
                    //获取商家里商品
                    List<ShopBean.DataBean.ListBean> list1 = list.get(a).getList();
                    for(int i = 0;i<list1.size();i++){
                        totalNum  = totalNum + list1.get(i).getNum();
                        //取选中的状态
                        if(list1.get(i).isCheck()){
                            totalPrice = totalPrice+(list1.get(i).getPrice()*list1.get(i).getNum());
                            num = num + list1.get(i).getNum();
                        }
                    }
                }
                if(num<totalNum){
                    //不是全部选中
                    all_check.setChecked(false);
                }else{
                    //全部选中
                    all_check.setChecked(true);
                }
                all_price.setText("合计:"+totalPrice+"元");
                sum_price_text.setText("去结算("+num+")");
            }
        });
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof ShopBean){
            ShopBean bean = (ShopBean) o;
            if(bean == null){
                Toast.makeText(MainActivity.this,bean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
               mList = bean.getData();
               if(mList!=null){
                   mList.remove(0);
                   adapaer.setmData(mList);
               }
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if(o instanceof Exception){
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this,"网络请求错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_check:
                checkSeller(all_check.isChecked());
                adapaer.notifyDataSetChanged();
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
    /**
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean b){
        double totalPrice = 0;
        int num = 0;
        for(int a = 0;a<mList.size();a++){
            //遍历商家，改变状态
            ShopBean.DataBean dataBean = mList.get(a);
            dataBean.setCheck(b);
            List<ShopBean.DataBean.ListBean> list = mList.get(a).getList();
            for(int i = 0;i<list.size();i++){
                //遍历商品，改变状态
                list.get(i).setCheck(b);
                totalPrice = totalPrice+(list.get(i).getPrice()*list.get(i).getNum());
                num = num + list.get(i).getNum();
            }
        }
        if(b){
            all_price.setText("合计:"+totalPrice+"元");
            sum_price_text.setText("去结算("+num+")");
        }else{
            all_price.setText("合计:0.00元");
            sum_price_text.setText("去结算(0)");
        }
    }
}
