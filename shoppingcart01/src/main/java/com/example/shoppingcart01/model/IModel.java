package com.example.shoppingcart01.model;

import java.util.Map;

public interface IModel {
    void getRequest(String url, Map<String, String> map, Class clazz, MyCallBack myCallBack);
}
