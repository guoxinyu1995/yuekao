package com.example.erjiliandong01.presenter;


import com.example.erjiliandong01.MainActivity;
import com.example.erjiliandong01.model.ModelImpl;
import com.example.erjiliandong01.model.MyCallBack;
import com.example.erjiliandong01.view.Iview;

import java.util.Map;

public class PresentImpl implements Ipresenter {
    private Iview mIview;
    private ModelImpl model;

    public PresentImpl(Iview iview) {
        mIview = iview;
        model = new ModelImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> map, Class clazz) {
        model.getRequest(url, map, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                mIview.requestData(data);
            }
        });
    }
    public void onDetach(){
        if(model!=null){
            model = null;
        }
        if(mIview!=null){
            mIview = null;
        }
    }
}
