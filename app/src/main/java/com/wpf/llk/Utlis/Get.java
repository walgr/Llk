package com.wpf.llk.Utlis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Get {

    public static List getID() {
        int[] jis = new int[Config.ji];
        for(int i=0;i<Config.ji;++i) {
            int sj = (int) (Math.random()*1000%Config.pics.length);
            jis[i] = Config.pics[sj];
        }
        int[] list_pic = new int[Config.xLen*Config.yLen];
        for(int i=0;i<list_pic.length;++i) {
            if(i<list_pic.length/2) {
                int sj = (int) (Math.random()*100 % Config.ji);
                list_pic[i] = jis[sj];
            } else {
                //匹配规则
                list_pic[i] = list_pic[i-list_pic.length/2];
            }
        }
        List list = intArrayAsList(list_pic);
        Collections.shuffle(list);
        return list;
    }

    public static ViewConfig[] getViewConfigs() {
        ViewConfig[] viewConfigs = new ViewConfig[Config.xLen*Config.yLen];
        List list = getID();
        for(int i=0;i<viewConfigs.length;++i) {
            viewConfigs[i] = new ViewConfig();
            viewConfigs[i].id = (int) list.get(i);
            viewConfigs[i].position = i;
        }
        return viewConfigs;
    }

    public static boolean isFinish(ViewConfig[] viewConfigs) {
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
}
