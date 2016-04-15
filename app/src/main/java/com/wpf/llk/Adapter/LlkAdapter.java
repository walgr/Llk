package com.wpf.llk.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wpf.llk.MyImageView;
import com.wpf.llk.R;
import com.wpf.llk.Utlis.Config;
import com.wpf.llk.Utlis.Get;
import com.wpf.llk.Utlis.ViewConfig;

import java.util.ArrayList;
import java.util.List;

public class LlkAdapter extends RecyclerView.Adapter<LlkAdapter.LlkViewHolder> {

    private Context context;
    private AdapterOnClickListener adapterOnClickListener;
    private List<ViewConfig> viewConfigs = new ArrayList<>();

    public LlkAdapter(List<ViewConfig> viewConfigs) {
        this.viewConfigs = viewConfigs;
    }

    @SuppressLint("InflateParams")
    @Override
    public LlkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LlkViewHolder(LayoutInflater.from(context).inflate(R.layout.pic,null,false));
    }

    @Override
    public void onBindViewHolder(LlkViewHolder holder, int position) {
        ViewConfig viewConfig = viewConfigs.get(position);
//        holder.img.setImageURI(Uri.parse(viewConfig.isRemove?"":"res:///"+viewConfig.id));
        if(viewConfig.isRemove)
            Glide.clear(holder.img);
        else
            Glide.with(context).load(viewConfig.id).into(holder.img);
        holder.img.setVisibility(viewConfig.isRemove?View.INVISIBLE:View.VISIBLE);
        holder.itemView.setOnClickListener(viewConfig.isRemove?null:holder);
    }

    @Override
    public int getItemCount() {
        return Config.xLen*Config.yLen;
    }

    public void setAdapterOnClickListener(AdapterOnClickListener adapterOnClickListener) {
        this.adapterOnClickListener = adapterOnClickListener;
    }

    public class LlkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyImageView img;

        public LlkViewHolder(View itemView) {
            super(itemView);
            img = (MyImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                int position = getAdapterPosition();
                ViewConfig viewConfig = viewConfigs.get(position);
                if(!viewConfig.isUp) {
                    v.setBackground(Get.getDrawable(context,R.mipmap.pic_click));
                    //v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.up));
                    viewConfig.isUp = true;
                    v.setTag(viewConfig);
                    Config.clicked.add(v);
                } else {
                    v.setBackground(null);
                    //v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.down));
                    viewConfig.isUp = false;
                    Config.clicked.remove(v);
                }
                if(!viewConfig.isRemove)
                    adapterOnClickListener.onClick(position);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
