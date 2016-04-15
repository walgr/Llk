package com.wpf.llk.Utlis;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.wpf.llk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpf on 4-2-0002.
 * 配置文件
 */

public class Config {
    public static int[] pic1s = new int[]{R.mipmap.pic1_1,R.mipmap.pic1_2,R.mipmap.pic1_3,
            R.mipmap.pic1_4,R.mipmap.pic1_5,R.mipmap.pic1_6,R.mipmap.pic1_7,R.mipmap.pic1_8,
            R.mipmap.pic1_9,R.mipmap.pic1_10,R.mipmap.pic1_11,R.mipmap.pic1_12,R.mipmap.pic1_13,
            R.mipmap.pic1_14,R.mipmap.pic1_15,R.mipmap.pic1_16,R.mipmap.pic1_17,R.mipmap.pic1_18,
            R.mipmap.pic1_19,R.mipmap.pic1_20,R.mipmap.pic1_21,R.mipmap.pic1_22,R.mipmap.pic1_23,
            R.mipmap.pic1_24,R.mipmap.pic1_25,R.mipmap.pic1_26,R.mipmap.pic1_27,R.mipmap.pic1_28,
            R.mipmap.pic1_29,R.mipmap.pic1_30};

    public static int[] pic2s = new int[]{R.mipmap.pic2_1,R.mipmap.pic2_2,R.mipmap.pic2_3,
            R.mipmap.pic2_4,R.mipmap.pic2_5,R.mipmap.pic2_6,R.mipmap.pic2_7,R.mipmap.pic2_8,
            R.mipmap.pic2_9,R.mipmap.pic2_10,R.mipmap.pic2_11,R.mipmap.pic2_12,R.mipmap.pic2_13,
            R.mipmap.pic2_14,R.mipmap.pic2_15,R.mipmap.pic2_16,R.mipmap.pic2_17,R.mipmap.pic2_18,
            R.mipmap.pic2_19,R.mipmap.pic2_20,R.mipmap.pic2_21,R.mipmap.pic2_22,R.mipmap.pic2_23,
            R.mipmap.pic2_24,R.mipmap.pic2_25,R.mipmap.pic2_26,R.mipmap.pic2_27,R.mipmap.pic2_28,
            R.mipmap.pic2_29,R.mipmap.pic2_30};

    public static int xLen = 8;
    public static int yLen = 8;
    public static int ji = 10;
    public static int difficulty = 2;
    public static List<ViewConfig> viewConfigs;
    public static List<View> clicked = new ArrayList<>();
    public static List<Integer> line = new ArrayList<>();
    private static boolean stop;

    //选中的2个能否消除的规则
    public static boolean rule() {
        return selectDif();
    }

    private static boolean selectDif() {
        int id0 = ((ViewConfig)clicked.get(0).getTag()).Id;
        boolean type0 = ((ViewConfig)clicked.get(0).getTag()).type;
        int id1 = ((ViewConfig)clicked.get(1).getTag()).Id;
        boolean type1 = ((ViewConfig)clicked.get(1).getTag()).type;
        if(type0 != type1 && id0 == id1) {
            int position0 = ((ViewConfig) clicked.get(0).getTag()).position;
            int position1 = ((ViewConfig) clicked.get(1).getTag()).position;
            switch (difficulty) {
                case 1:
                    return true;
                case 2:
                    if(Get.positionIsBorderLine(position0,position1))
                        return true;
                case 3:
                    clearAll();
                    findLine(position0);
                    Log.d("结果", Get.getListString());
                    return line.contains(position0) && line.contains(position1);
            }
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    private static void findLine(int position) {
        if(position < 0 || position > Config.xLen * Config.yLen -1) {
            return;
        }
        ViewConfig viewConfig = viewConfigs.get(position);
        if(viewConfig.dir == 4) return;
        if(!line.contains(position))
            line.add(position);
        for(int i = viewConfig.dir;i<=4;++i) {
            int nexPosition = Get.getPosition(position,i);
            if(nexPosition < 0 || nexPosition > Config.xLen * Config.yLen -1) continue;
            ViewConfig viewConfig1 = viewConfigs.get(nexPosition);
            if (Get.positionIsNext(position,nexPosition)
                    && viewConfig1.position == ((ViewConfig) clicked.get(1).getTag()).position) {
                if(!line.contains(nexPosition))
                    line.add(nexPosition);
                stop = true;
                break;
            }
        }
        if(stop) {
            stop = false;
        } else {
            for(int i = viewConfig.dir;i <= 4;++i) {
                viewConfig.dir = i;
                int nexPosition = Get.getPosition(position, i);
                if(nexPosition < 0 || nexPosition > Config.xLen * Config.yLen - 1) continue;
                ViewConfig viewConfig1 = viewConfigs.get(nexPosition);
                if (Get.positionIsNext(position,nexPosition) &&
                        viewConfig1.isRemove && !line.contains(nexPosition))
                    findLine(nexPosition);
            }
        }
    }

    private static void clearAll() {
        for(Integer position : line) {
            viewConfigs.get(position).dir = 1;
        }
        line.clear();
    }
}
