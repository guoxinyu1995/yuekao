package com.example.yuekaomoni01.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yuekaomoni01.R;
import com.example.yuekaomoni01.custom.TitalBarView;
import com.example.yuekaomoni01.custom.WaterContents;

public class MainActivity extends AppCompatActivity {

    private WaterContents contents;
    private WaterContents contents1;
    private TitalBarView titalBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //获取资源id
        contents = findViewById(R.id.watercon);
        contents1 = findViewById(R.id.watercon01);
        titalBarView = findViewById(R.id.tital);
        //点击
        titalBarView.setTitalCallBack(new TitalBarView.TitalCallBack() {
            @Override
            public void callBack(String str) {
                TextView textView = new TextView(MainActivity.this);
                textView.setText(str);
                textView.setTextSize(20);
                textView.setBackgroundResource(R.drawable.text_shape);
                contents.addView(textView);
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("str",str);
                startActivity(intent);
            }
        });
        String[] str = new String[]{
                "水煮肉片", "锅包肉", "鱼香肉丝", "水煮鱼", "木须肉", "地三鲜", "尖椒干豆腐"
                , "干锅土豆片", "汉堡", "炸鸡", "可乐", "啤酒", "火腿", "米饭"
        };
        for (int i = 0; i < str.length; i++) {
            TextView textView1 = new TextView(MainActivity.this);
            textView1.setText(str[i]);
            textView1.setTextSize(20);
            textView1.setBackgroundResource(R.drawable.text_shape);
            contents1.addView(textView1);
        }
    }

}
