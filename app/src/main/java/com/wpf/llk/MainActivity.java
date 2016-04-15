package com.wpf.llk;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wpf.llk.Adapter.AdapterOnClickListener;
import com.wpf.llk.Adapter.LlkAdapter;
import com.wpf.llk.Utlis.Config;
import com.wpf.llk.Utlis.Get;
import com.wpf.llk.Utlis.ViewConfig;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        AdapterOnClickListener {

    protected RecyclerView llk;
    private LlkAdapter llkAdapter;
    private FloatingActionButton button_restart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llk = (RecyclerView) findViewById(R.id.llk);
        llk.setLayoutManager(new GridLayoutManager(this, Config.xLen));
        button_restart = (FloatingActionButton) findViewById(R.id.button_restart);
        button_restart.setOnClickListener(this);
        reStart();
    }

    @Override
    public void onClick(int position) {
        if(Config.clicked.size() == 2) {
            if(Config.rule()) {
                for(final View view : Config.clicked) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setBackground(null);
                        }
                    },200);
                    Config.viewConfigs.get(((ViewConfig)view.getTag()).position).isRemove = true;
                    Config.viewConfigs.get(((ViewConfig)view.getTag()).position).isUp = false;
                    llkAdapter.notifyItemChanged(((ViewConfig)view.getTag()).position);
                }
            } else {
                for(final View view : Config.clicked) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setBackground(null);
                        }
                    },200);
                    //view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.down));
                    Config.viewConfigs.get(((ViewConfig)view.getTag()).position).isUp = false;
                }
            }

            Config.clicked.clear();
        }
        if(Get.isFinish(Config.viewConfigs)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("游戏结束");
            builder.setMessage("重新开始?");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reStart();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_restart:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("重新开始?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reStart();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
        }
    }

    private void reStart() {
        Config.viewConfigs = Get.getViewConfigs();
        llkAdapter = new LlkAdapter(Config.viewConfigs);
        llkAdapter.setAdapterOnClickListener(MainActivity.this);
        llk.setAdapter(llkAdapter);
    }
}
