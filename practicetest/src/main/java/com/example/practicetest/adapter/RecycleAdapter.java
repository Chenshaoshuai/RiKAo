package com.example.practicetest.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.practicetest.R;
import com.example.practicetest.bean.UserBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean isLiear = true;
    private Context mContext;
    private List<UserBean.DataBean> mData;

    public RecycleAdapter(boolean isLiear, Context mContext) {
        this.isLiear = isLiear;
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder viewHolder = null;
        if(isLiear){
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_linear,viewGroup,false);

            viewHolder = new ViewHolder(v);
        }else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_grid,viewGroup,false);
            viewHolder = new ViewHolder(v);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String image = mData.get(i).getImages();
        String replac = image.split("\\|")[0].replace("https","http");
        holder.recycle_image.setImageURI(Uri.parse(replac));
        holder.recycle_title.setText(mData.get(i).getTitle());
        holder.recycle_price.setText(mData.get(i).getPrice()+"");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLayout.onClick(mData.get(i).getPid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setmData(List<UserBean.DataBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycle_image)
         SimpleDraweeView recycle_image;
        @BindView(R.id.recycle_title)
         TextView recycle_title;
        @BindView(R.id.recycle_price)
         TextView recycle_price;
        @BindView(R.id.layout)
         ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
    OnClickLayout onClickLayout;

    public void setOnClickLayout(OnClickLayout onClickLayout) {
        this.onClickLayout = onClickLayout;
    }

    public interface OnClickLayout{
        void onClick(int postion);
    }
}
