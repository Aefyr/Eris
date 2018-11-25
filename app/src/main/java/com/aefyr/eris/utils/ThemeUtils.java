package com.aefyr.eris.utils;

import android.content.res.ColorStateList;
import android.widget.Switch;

import androidx.core.graphics.drawable.DrawableCompat;

public class ThemeUtils {
    public static void colorSwitch(Switch s, int trackOff, int trackOn, int thumbOff, int thumbOn) {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked},
        };

        int[] thumbColors = new int[]{thumbOff, thumbOn};
        int[] trackColors = new int[]{trackOff, trackOn};

        DrawableCompat.setTintList(DrawableCompat.wrap(s.getThumbDrawable()), new ColorStateList(states, thumbColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(s.getTrackDrawable()), new ColorStateList(states, trackColors));
    }
}
