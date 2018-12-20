package com.example.shoppingcart02.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shoppingcart02.adaper.ShopAdaper;
import com.example.shoppingcart02.bean.ShopBean;
import com.example.shoppingcart02.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 加减数量
 */
public class CustomView extends RelativeLayout implements View.OnClickListener {

    private ImageView add;
    private ImageView jian;
    private EditText editText;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context mContext;

    private void init(Context context) {
        mContext = context;
        View view = View.inflate(mContext, R.layout.custom_item, null);
        add = view.findViewById(R.id.add_car);
        jian = view.findViewById(R.id.jian_car);
        editText = view.findViewById(R.id.edit_car);
        add.setOnClickListener(this);
        jian.setOnClickListener(this);
        addView(view);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                   num = Integer.valueOf(s.toString());
                   list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(1);
                }
                if(customCallBack!=null){
                    customCallBack.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_car:
                num++;
                editText.setText(num + "");
                list.get(position).setNum(num);
                if(customCallBack!=null){
                    customCallBack.callBack();
                }
                shopAdaper.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    Toast.makeText(mContext, "不能在减了", Toast.LENGTH_SHORT).show();
                }
                editText.setText(num + "");
                list.get(position).setNum(num);
                if(customCallBack!=null){
                    customCallBack.callBack();
                }
                shopAdaper.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    //传递数据
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private ShopAdaper shopAdaper;

    public void setData(ShopAdaper shopAdaper, List<ShopBean.DataBean.ListBean> list, int i) {
        this.list = list;
        this.shopAdaper = shopAdaper;
        position = i;
        num = list.get(i).getNum();
        editText.setText(num + "");
    }

    private CustomCallBack customCallBack;

    public void setCustomCallBack(CustomCallBack customCallBack) {
        this.customCallBack = customCallBack;
    }

    //定义接口
    public interface CustomCallBack {
        void callBack();
    }
}
