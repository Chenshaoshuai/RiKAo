package com.example.practicetest.present;

import java.util.Map;

public interface IPresent {
    void RequestData(String url, Map<String,String> params, Class clazz);
}
