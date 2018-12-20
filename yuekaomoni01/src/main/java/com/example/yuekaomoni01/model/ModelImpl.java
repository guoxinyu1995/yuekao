package com.example.yuekaomoni01.model;

import com.example.yuekaomoni01.util.ICallBack;
import com.example.yuekaomoni01.util.OkHttpUtils;

import java.util.Map;

public class ModelImpl implements Imodel {
    private MyCallBack myCallBack;
    @Override
    public void requestData(String url, Map<String, String> map, Class clazz, final MyCallBack myCallBack) {
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
