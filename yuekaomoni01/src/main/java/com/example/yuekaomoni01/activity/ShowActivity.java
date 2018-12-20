package com.example.yuekaomoni01.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuekaomoni01.R;
import com.example.yuekaomoni01.adaper.ShowAdaper;
import com.example.yuekaomoni01.api.Apis;
import com.example.yuekaomoni01.bean.AddCarBean;
import com.example.yuekaomoni01.bean.ShowBean;
import com.example.yuekaomoni01.presenter.PresenterImpl;
import com.example.yuekaomoni01.view.Iview;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowActivity extends AppCompatActivity implements Iview,View.OnClickListener {

    private PresenterImpl presenter;
    private EditText editText;
    private ImageView imageView;
    private XRecyclerView recyclerView;
    private int mPage;
    private ShowAdaper adaper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        presenter = new PresenterImpl(this);
        initView();
        getData();
    }

    private void initData() {
        Map<String,String> map = new HashMap<>();
        map.put("page",String.valueOf(mPage));
        map.put("keywords",editText.getText().toString());
        map.put("sort",String.valueOf(0));
        presenter.startRequest(Apis.URL_SHOW,map,ShowBean.class);
    }

    private void getData() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
        Map<String,String> map = new HashMap<>();
        map.put("page",String.valueOf(mPage));
        map.put("keywords",str);
        map.put("sort",String.valueOf(0));
        presenter.startRequest(Apis.URL_SHOW,map,ShowBean.class);
    }

    private void initView() {
        mPage =1;
        //获取资源id
        editText = findViewById(R.id.show_edit);
        imageView = findViewById(R.id.show_seach);
        recyclerView = findViewById(R.id.x_recycle);
        imageView.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adaper = new ShowAdaper(this);
        recyclerView.setAdapter(adaper);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                initData();
            }
        });
        //点击加入购物车
        adaper.setShowCallBack(new ShowAdaper.ShowCallBack() {
            @Override
            public void CallBack(int pid) {
                Map<String,String> map = new HashMap<>();
                map.put("uid","23013");
                map.put("pid",String.valueOf(pid));
                presenter.startRequest(Apis.URL_CAR,map,AddCarBean.class);
            }
        });
        //点击跳转到购物车
        adaper.setShowClickCallBack(new ShowAdaper.ShowClickCallBack() {
            @Override
            public void CallBack(int position) {
                Intent intent = new Intent(ShowActivity.this,ShoppCarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void requestData(Object o) {
        if(o instanceof ShowBean){
            ShowBean bean = (ShowBean) o;
            if(bean==null ||!bean.isSuccess()){
                Toast.makeText(ShowActivity.this,bean.getMsg(),Toast.LENGTH_SHORT ).show();
            }else{
                if(mPage == 1){
                    adaper.setmData(bean.getData());
                }else{
                    adaper.addmData(bean.getData());
                }
                mPage++;
                recyclerView.refreshComplete();
                recyclerView.loadMoreComplete();
            }
        }else if(o instanceof AddCarBean){
            AddCarBean carBean = (AddCarBean) o;
            if(carBean == null || !carBean.isSuccess()){
                Toast.makeText(ShowActivity.this,carBean.getMsg(),Toast.LENGTH_SHORT ).show();
            }else{
                Toast.makeText(ShowActivity.this,carBean.getMsg(),Toast.LENGTH_SHORT ).show();
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if(o instanceof Exception){
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(ShowActivity.this,"网络请求错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_seach:
                initView();
                initData();
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
}
