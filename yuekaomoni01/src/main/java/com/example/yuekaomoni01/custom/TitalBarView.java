package com.example.yuekaomoni01.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yuekaomoni01.R;

public class TitalBarView extends LinearLayout {
    private Context context;
    private EditText editText;
    private ImageView imageView;

    public TitalBarView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public TitalBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.title_item, null);
        editText = view.findViewById(R.id.ed_seach);
        imageView = view.findViewById(R.id.seach_image);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titalCallBack!=null){
                    titalCallBack.callBack(editText.getText().toString().trim());
                }
            }
        });
        addView(view);
    }

    private TitalCallBack titalCallBack;
    public void setTitalCallBack(TitalCallBack titalCallBack){
        this.titalCallBack = titalCallBack;
    }
    //定义接口
    public interface TitalCallBack {
        void callBack(String str);
    }
}
