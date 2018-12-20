package com.example.shoppingcart01.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.shoppingcart01.R;

public class CustomView extends LinearLayout {

    private ImageButton jian;
    private ImageButton add;
    private EditText editText;
    private int mCount = 1;
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context,R.layout.custom_item,this);
        jian = view.findViewById(R.id.jian);
        add = view.findViewById(R.id.add);
        editText = view.findViewById(R.id.ed_num);
        //减
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                Integer count = Integer.valueOf(content);
                if(count>1){
                    mCount = count-1;
                    editText.setText(mCount+"");
                    //回调给adapter里面
                    if(customListener!=null){
                        customListener.jianjian(mCount);
                    }
                }
            }
        });
        //加
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString().trim();
                Integer count = Integer.valueOf(content)+1;
                mCount = count;
                editText.setText(mCount+"");
                //回调给adapter里面
                if(customListener!=null){
                    customListener.jianjian(mCount);
                }
            }
        });
        //输入框
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private CustomListener customListener;
    public void setCustomListener(CustomListener customListener){
        this.customListener = customListener;
    }
    //定义接口加减
    public interface CustomListener{
        void jianjian(int count);
        void shuruzhi(int count);
    }
    //这个方法共adaper设置数量时调用
    public void setEditText(int num){
        if(editText!=null){
            editText.setText(num+"");
        }
    }
}
