package com.example.practicetest.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.practicetest.api.Apis;
import com.example.practicetest.R;
import com.example.practicetest.adapter.RecycleAdapter;
import com.example.practicetest.bean.UserBean;
import com.example.practicetest.present.IPresentImpl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView ,View.OnClickListener{
    private IPresentImpl iPresent;
    private boolean isLiear = true;
    private RecycleAdapter adapter;
    @BindView(R.id.btn_toggle)
     Button btn_toggle;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iPresent = new IPresentImpl(this);
        recyclerView = findViewById(R.id.recycle);


        loadData();
        getsponse();
    }

    private void loadData() {
        Map<String,String> params = new HashMap<>();
        params.put("keywords","手机");
        params.put("page","1");
        iPresent.RequestData(Apis.HOME_DATA,params,UserBean.class);
    }

    private void getsponse() {
        if(isLiear){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        adapter = new RecycleAdapter(isLiear,this);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickLayout(new RecycleAdapter.OnClickLayout() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent(MainActivity.this,LogActivity.class);

                intent.putExtra("keyIn",postion);
                startActivity(intent);
            }
        });

        isLiear=!isLiear;
    }

    @Override
    public void onSuccess(Object data) {
        if(data instanceof  UserBean){
            UserBean userBean = (UserBean) data;
            adapter.setmData(userBean.getData());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresent.onDetach();
    }
    @OnClick(R.id.btn_toggle)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
             case R.id.btn_toggle:
                 getsponse();
                 loadData();
                 break;
        }
    }
}
