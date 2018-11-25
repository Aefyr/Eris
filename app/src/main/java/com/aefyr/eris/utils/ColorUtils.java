package com.aefyr.eris.utils;

import android.graphics.Color;

public class ColorUtils {

    public static int lerpColor(int color1, int color2, float a) {
        return Color.rgb((int) MathUtils.lerp(Color.red(color1), Color.red(color2), a), (int) MathUtils.lerp(Color.green(color1), Color.green(color2), a), (int) MathUtils.lerp(Color.blue(color1), Color.blue(color2), a));
    }


}
