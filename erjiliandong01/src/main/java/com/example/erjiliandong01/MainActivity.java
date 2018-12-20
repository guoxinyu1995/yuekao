package com.example.erjiliandong01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.erjiliandong01.adaper.LeftAdaper;
import com.example.erjiliandong01.adaper.RightAdaper;
import com.example.erjiliandong01.api.Apis;
import com.example.erjiliandong01.bean.LeftBean;
import com.example.erjiliandong01.bean.RightBean;
import com.example.erjiliandong01.presenter.PresentImpl;
import com.example.erjiliandong01.view.Iview;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Iview {

    private PresentImpl present;
    private RecyclerView recycle_left;
    private LeftAdaper leftAdaper;
    private RecyclerView recycle_right;
    private RightAdaper rightAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        present = new PresentImpl(this);
        initRecycleLeft();
        initRecycleRight();
        initData();
    }

    /**
     * 获取左侧列表数据
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        present.startRequest(Apis.URL_PRODUCT_GET_CATAGORY, map, LeftBean.class);
    }

    /**
     * 初始化右侧recyclerView,加载右侧adapter
     */
    private void initRecycleRight() {
        recycle_right = findViewById(R.id.recycle_right);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycle_right.setLayoutManager(layoutManager);
        recycle_right.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rightAdaper = new RightAdaper(this);
        recycle_right.setAdapter(rightAdaper);
    }

    /**
     * 初始化左侧recyclerView,加载左侧adapter
     */
    private void initRecycleLeft() {
        recycle_left = findViewById(R.id.recycle_left);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycle_left.setLayoutManager(layoutManager);
        recycle_left.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        leftAdaper = new LeftAdaper(this);
        recycle_left.setAdapter(leftAdaper);
        //添加回调
        leftAdaper.setLeftCallBaack(new LeftAdaper.LeftCallBaack() {
            @Override
            public void callBack(int i, int cid) {
                //拿到cid之后，通过接口获得对应的数据，展示在右侧列表中
                initRightData(cid);
            }
        });
    }

    /**
     * 获取右侧列表数据
     */
    private void initRightData(int cid) {
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(cid));
        present.startRequest(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY, map, RightBean.class);
    }

    @Override
    public void requestData(Object o) {
        if (o instanceof LeftBean) {
            LeftBean leftBean = (LeftBean) o;
            if (leftBean == null || !leftBean.isSuccess()) {
                Toast.makeText(MainActivity.this, leftBean.getMsg(), Toast.LENGTH_SHORT).show();
            } else {
                leftAdaper.setmLeftData(leftBean.getData());
            }
        } else if (o instanceof RightBean) {
            RightBean rightBean = (RightBean) o;
            if (rightBean == null || !rightBean.isSuccess()) {
                Toast.makeText(MainActivity.this, rightBean.getMsg(), Toast.LENGTH_SHORT).show();
            } else {
                rightAdaper.setmData(rightBean.getData());
                recycle_right.scrollToPosition(0);
            }
        }
    }

    @Override
    public void requestFail(Object o) {
        if (o instanceof Exception) {
            Exception e = (Exception) o;
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.onDetach();
    }
}
