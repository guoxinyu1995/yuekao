package com.example.shoppingcart02.presenter;

import com.example.shoppingcart02.activity.MainActivity;
import com.example.shoppingcart02.model.ModelImpl;
import com.example.shoppingcart02.model.MyCallBack;
import com.example.shoppingcart02.view.Iview;

import java.util.Map;

public class PresenterImpl implements Ipresenter {
    private Iview mIview;
    private ModelImpl model;

    public PresenterImpl(Iview iview) {
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
        } if(mIview!=null){
            mIview = null;
        }

    }
}
