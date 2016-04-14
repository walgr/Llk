package com.wpf.llk.Utlis;

import android.view.View;

import com.wpf.llk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpf on 4-2-0002.
 * 配置文件
 */

public class Config {
    public static int[] pics = new int[]{R.mipmap.pic_2,R.mipmap.pic_4,R.mipmap.pic_6,R.mipmap.pic_8,
            R.mipmap.pic_10,R.mipmap.pic_12,R.mipmap.pic_14,R.mipmap.pic_16,R.mipmap.pic_18,
            R.mipmap.pic_20,R.mipmap.pic_22,R.mipmap.pic_24,R.mipmap.pic_26,R.mipmap.pic_28,
            R.mipmap.pic_30,R.mipmap.pic_32,R.mipmap.pic_34,R.mipmap.pic_36,R.mipmap.pic_38,
            R.mipmap.pic_40,R.mipmap.pic_42,R.mipmap.pic_44,R.mipmap.pic_46,R.mipmap.pic_48,
            R.mipmap.pic_50,R.mipmap.pic_52,R.mipmap.pic_54,R.mipmap.pic_56,R.mipmap.pic_58,
            R.mipmap.pic_60,R.mipmap.pic_62,R.mipmap.pic_64,R.mipmap.pic_66,R.mipmap.pic_68,
            R.mipmap.pic_70,R.mipmap.pic_72,R.mipmap.pic_74,R.mipmap.pic_76,R.mipmap.pic_78,
            R.mipmap.pic_80,R.mipmap.pic_82,R.mipmap.pic_84,R.mipmap.pic_86,R.mipmap.pic_88,
            R.mipmap.pic_90,R.mipmap.pic_92,R.mipmap.pic_94,R.mipmap.pic_96,R.mipmap.pic_98,
            R.mipmap.pic_100,R.mipmap.pic_102,R.mipmap.pic_104,R.mipmap.pic_106,R.mipmap.pic_108,
            R.mipmap.pic_110,R.mipmap.pic_112,R.mipmap.pic_114,R.mipmap.pic_116,R.mipmap.pic_118,
            R.mipmap.pic_120};
    public static int xLen = 6;
    public static int yLen = 8;
    public static int ji = 30;


    public static List<View> clicked = new ArrayList<>();

    //选中的2个能否消除的规则
    public static boolean rule() {
        boolean canRemove = false;
        if(((ViewConfig)clicked.get(0).getTag()).id == ((ViewConfig)clicked.get(1).getTag()).id)
            canRemove = true;
        return canRemove;
    }
}
