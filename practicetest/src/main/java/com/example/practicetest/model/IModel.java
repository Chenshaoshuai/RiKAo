package com.example.practicetest.model;

import com.example.practicetest.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void RequestData(String url, Map<String,String> params, Class clazz, MyCallBack callBack);
}
