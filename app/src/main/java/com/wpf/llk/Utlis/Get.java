package com.wpf.llk.Utlis;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Get {

    public static int[] getID() {
        int[] jis = new int[Config.ji];
        for(int i=0;i<Config.ji;++i) {
            int sj = (int) (Math.random()*1000%Config.pic1s.length);
            jis[i] = Config.pic1s[sj];
        }
        int[] list_pic = new int[Config.xLen*Config.yLen];
        for(int i=0;i<list_pic.length;++i) {
            if(i<list_pic.length/2) {
                int sj = (int) (Math.random()*100 % Config.ji);
                list_pic[i] = sj;
            } else {
                //匹配规则
                list_pic[i] = list_pic[i-list_pic.length/2];
            }
        }
        return list_pic;
    }

    public static List<ViewConfig> getViewConfigs() {
        List<ViewConfig> viewConfigs = new ArrayList<>();
        int[] list = getID();
        for(int i=0;i<list.length;++i) {
            ViewConfig viewConfig = new ViewConfig();
            viewConfig.id = (i<list.length/2)?Config.pic1s[list[i]]:Config.pic2s[list[i]];
            viewConfig.Id = list[i];
            viewConfig.type = (i<list.length/2);
            viewConfigs.add(viewConfig);
        }
        Collections.shuffle(viewConfigs);
        for(int i = 0;i<viewConfigs.size();++i) {
            viewConfigs.get(i).position = i;
        }
        return viewConfigs;
    }

    public static boolean isFinish(List<ViewConfig> viewConfigs) {
        boolean finish = true;
        for(ViewConfig viewConfig : viewConfigs) {
            if(!viewConfig.isRemove) {
                finish = false;
                break;
            }
        }
        return finish;
    }

    public static List<Integer> intArrayAsList(final int[] a){
        if(a == null)
            throw new NullPointerException();
        List<Integer> list = new ArrayList<>();
        for (int anA : a) {
            list.add(anA);
        }
        return list;
    }

    public static boolean positionIsNext(int position0, int position1) {
        return positionIsLine(position0,position1)
                && (Math.abs(position1 - position0) == 1
                || Math.abs(position1 - position0) == Config.xLen);
    }

    public static boolean positionIsLine(int position0, int position1) {
        return (position0/Config.xLen == position1/Config.xLen
                || position0%Config.xLen  == position1%Config.xLen);
    }

    public static boolean positionIsBorderLine(int position0, int position1) {
        return positionIsBorder(position0) && positionIsBorder(position1) ;
    }

    private static boolean positionIsBorder(int position) {
        return position / Config.xLen == 0
                || position % Config.yLen == 0
                || position / Config.xLen == Config.yLen -1
                || position % Config.yLen == Config.xLen -1;
    }

    public static int getPosition(int position,int dir) {
        switch (dir) {
            case -1:     //上
                return position+Config.xLen;
            case -2:     //右
                return position-1;
            case -3:     //下
                return position-Config.xLen;
            case -4:     //左
                return position+1;
            case 1:     //上
                //Log.d("走","上");
                return position-Config.xLen;
            case 2:     //右
                //Log.d("走","右");
                return position+1;
            case 3:     //下
                //Log.d("走","下");
                return position+Config.xLen;
            case 4:     //左
                //Log.d("走","左");
                return position-1;
        }
        return 0;
    }

    public static String getListString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<Config.line.size() -1;++i) {
            sb.append(Config.line.get(i)).append("-");
        }
        if(!Config.line.isEmpty())
            sb.append(Config.line.get(Config.line.size() -1));
        return sb.toString();
    }

    /**
     *  根据系统取出相应的资源文件
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, null);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
