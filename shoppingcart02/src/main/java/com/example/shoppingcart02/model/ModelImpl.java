package com.example.shoppingcart02.model;

import com.example.shoppingcart02.util.ICallBack;
import com.example.shoppingcart02.util.OkHttpUtils;

import java.util.Map;

public class ModelImpl implements IModel {
    private MyCallBack myCallBack;
    @Override
    public void getRequest(String url, Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
        OkHttpUtils.getIntance().doPost(url, map, clazz, new ICallBack() {
            @Override
            public void success(Object o) {
                myCallBack.setData(o);
            }

            @Override
            public void faniled(Exception e) {
                myCallBack.setData(e.getMessage());
            }
        });

    }
}
