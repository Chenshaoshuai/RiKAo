package com.example.practicetest.present;

import com.example.practicetest.callback.MyCallBack;
import com.example.practicetest.model.IModelImpl;
import com.example.practicetest.view.IView;

import java.util.Map;

public class IPresentImpl implements IPresent {
    private IView iView;
    private IModelImpl iModel;

    public IPresentImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void RequestData(String url, Map<String, String> params, Class clazz) {
        iModel.RequestData(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.onSuccess(data);
            }
        });
    }
    public void onDetach(){
        if(iModel!=null){
            iModel = null;
        }else if(iView!=null){
            iView=null;
        }
    }
}
