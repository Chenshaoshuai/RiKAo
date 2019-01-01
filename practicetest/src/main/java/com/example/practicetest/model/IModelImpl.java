package com.example.practicetest.model;

import com.example.practicetest.callback.MyCallBack;
import com.example.practicetest.network.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void RequestData(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().post(url,params).result(new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                Object o = new Gson().fromJson(data, clazz);
                callBack.onSuccess(o);
            }

            @Override
            public void onFail(String erroe) {
              callBack.onSuccess(erroe);
            }
        });
    }
}
