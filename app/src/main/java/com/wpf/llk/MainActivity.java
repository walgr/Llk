package com.wpf.llk;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wpf.llk.Adapter.AdapterOnClickListener;
import com.wpf.llk.Adapter.LlkAdapter;
import com.wpf.llk.Utlis.Config;
import com.wpf.llk.Utlis.Get;
import com.wpf.llk.Utlis.ViewConfig;

public class MainActivity extends AppCompatActivity implements AdapterOnClickListener {

    protected RecyclerView llk;
    private LlkAdapter llkAdapter;
    protected ViewConfig[] viewConfigs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        llk = (RecyclerView) findViewById(R.id.llk);

        llk.setLayoutManager(new GridLayoutManager(this, Config.xLen));

        viewConfigs = Get.getViewConfigs();
        llkAdapter = new LlkAdapter(viewConfigs);
        llkAdapter.setAdapterOnClickListener(this);
        llk.setAdapter(llkAdapter);
    }

    @Override
    public void onClick(int position) {
        if(Config.clicked.size() == 2) {
            if(Config.rule()) {
                for(View view : Config.clicked) {
                    viewConfigs[((ViewConfig)view.getTag()).position].isRemove = true;
                    viewConfigs[((ViewConfig)view.getTag()).position].isUp = false;
                    llkAdapter.notifyDataSetChanged();
                }
            } else {
                for(View view : Config.clicked) {
                    view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.down));
                    viewConfigs[((ViewConfig)view.getTag()).position].isUp = false;
                }
            }
            Config.clicked.clear();
        }
        if(Get.isFinish(viewConfigs)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("游戏结束");
            builder.setMessage("重新开始?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    viewConfigs = Get.getViewConfigs();
                    llkAdapter = new LlkAdapter(viewConfigs);
                    llkAdapter.setAdapterOnClickListener(MainActivity.this);
                    llk.setAdapter(llkAdapter);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }
}
