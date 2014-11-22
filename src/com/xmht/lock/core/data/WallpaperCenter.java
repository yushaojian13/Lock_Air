
package com.xmht.lock.core.data;

import com.xmht.lockair.R;

public class WallpaperCenter {
    public static final int[] WALLPAPERS = new int[] {
            R.drawable.chunv, R.drawable.shuangzi, R.drawable.jiniu,
            R.drawable.baiyang, R.drawable.mojie,
            R.drawable.sheshou, R.drawable.shizi,
            R.drawable.shuangyu, R.drawable.shuiping, R.drawable.tianping,
            R.drawable.tianxie, R.drawable.juxie
    };
    
    public static int getCount() {
        return WALLPAPERS.length;
    }
    
    public static int getRes(int style) {
        return WALLPAPERS[style];
    }
}
