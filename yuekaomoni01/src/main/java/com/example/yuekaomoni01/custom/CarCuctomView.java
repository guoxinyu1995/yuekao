package com.example.yuekaomoni01.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yuekaomoni01.R;

public class CarCuctomView extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private ImageButton jian;
    private ImageButton add;
    private EditText ed_num;
    private int mCount = 1;
    public CarCuctomView(Context context) {
        super(context);
        initView(context);
    }

    public CarCuctomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {
        this.context = context;
        View view = View.inflate(context,R.layout.custom_item1,null);
        jian = view.findViewById(R.id.jian);
        add = view.findViewById(R.id.add);
        ed_num = view.findViewById(R.id.ed_num);
        jian.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                String counten = ed_num.getText().toString().trim();
                Integer count = Integer.valueOf(counten)+1;
                mCount = count;
                ed_num.setText(mCount+"");
                if(customCallBack!=null){
                    customCallBack.callBack(mCount);
                }
                break;
            case R.id.jian:
                String counten1 = ed_num.getText().toString().trim();
                Integer count1 = Integer.valueOf(counten1);
                if(count1>1){
                    mCount = count1-1;
                    ed_num.setText(mCount+"");
                    if(customCallBack!=null){
                        customCallBack.callBack(mCount);
                    }
                }
                break;
                default:
                    break;
        }
    }


    private CustomCallBack customCallBack;
    public void setCustomCallBack(CustomCallBack customCallBack){
        this.customCallBack = customCallBack;
    }
    //定义接口
    public interface CustomCallBack{
        void callBack(int count);
        void shuruzhi(int count);
    }
    //这个方法共adaper设置数量时调用
    public void setEditText(int num){
        if(ed_num!=null){
            ed_num.setText(num+"");
        }
    }
}
