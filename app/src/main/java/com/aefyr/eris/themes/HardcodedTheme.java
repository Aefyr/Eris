package com.aefyr.eris.themes;

import android.graphics.Color;

import com.aefyr.eris.themeengine.core.CustomTheme;
import com.aefyr.eris.themeengine.core.ThemeCore;

import java.util.HashMap;
import java.util.Map;

abstract class HardcodedTheme extends CustomTheme {
    private HashMap<String, Integer> mColors;
    private boolean mColorsInitialized;

    public HardcodedTheme() {
        ThemeCore.getInstance().setAnimationEnabled(true);
        mColors = new HashMap<>();
        fillThemeWithColors();
    }

    @Override
    public int getColor(String colorName) {
        if (!mColors.containsKey(colorName))
            return Color.MAGENTA;

        return mColors.get(colorName);
    }

    public void setColor(String colorName, int color) {
        int oldColor = getColor(colorName);
        mColors.put(colorName, color);
        notifyColorChanged(colorName, oldColor, color);
    }

    public void setColorWithoutAnimation(String colorName, int color) {
        mColors.put(colorName, color);
        notifyColorChanged(colorName, color);
    }

    @Override
    public void onApplied() {
        if (!mColorsInitialized) {
            fillThemeWithColors();
            mColorsInitialized = true;
        }
    }

    @Override
    public Map<String, Integer> getAllColors() {
        return mColors;
    }

    protected abstract void fillThemeWithColors();
}
