package com.wpf.llk.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wpf.llk.R;
import com.wpf.llk.Utlis.Config;
import com.wpf.llk.Utlis.ViewConfig;

public class LlkAdapter extends RecyclerView.Adapter<LlkAdapter.LlkViewHolder> {

    private Context context;
    private AdapterOnClickListener adapterOnClickListener;
    private ViewConfig[] viewConfigs = new ViewConfig[Config.xLen*Config.yLen];

    public LlkAdapter(ViewConfig[] viewConfigs) {
        this.viewConfigs = viewConfigs;
    }

    @Override
    public LlkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LlkViewHolder(LayoutInflater.from(context).inflate(R.layout.pic,null,false));
    }

    @Override
    public void onBindViewHolder(LlkViewHolder holder, int position) {
        holder.sd.setImageURI(Uri.parse(viewConfigs[position].isRemove?"":"res:///"+viewConfigs[position].id));
        holder.sd.setVisibility(viewConfigs[position].isRemove?View.INVISIBLE:View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return Config.xLen*Config.yLen;
    }

    public void setAdapterOnClickListener(AdapterOnClickListener adapterOnClickListener) {
        this.adapterOnClickListener = adapterOnClickListener;
    }

    public class LlkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SimpleDraweeView sd;

        public LlkViewHolder(View itemView) {
            super(itemView);
            sd = (SimpleDraweeView) itemView.findViewById(R.id.sd);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                int position = getAdapterPosition();
                if(!viewConfigs[position].isUp) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.up));
                    viewConfigs[position].isUp = true;
                    v.setTag(viewConfigs[position]);
                    Config.clicked.add(v);
                } else {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.down));
                    viewConfigs[position].isUp = false;
                    Config.clicked.remove(v);
                }
                if(!viewConfigs[position].isRemove)
                    adapterOnClickListener.onClick(position);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
