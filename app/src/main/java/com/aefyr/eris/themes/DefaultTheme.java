package com.aefyr.eris.themes;

import android.content.Context;
import android.graphics.Color;

import com.aefyr.eris.R;
import com.aefyr.eris.themeengine.core.ThemeColor;

public class DefaultTheme extends HardcodedTheme {
    private static DefaultTheme sInstance;

    private int BACKGROUND;
    private int ACCENT;
    private int TEXT_DARK;
    private int TEXT_LIGHT;
    private int FADED_ACCENT;

    public static DefaultTheme getInstance(Context c) {
        return sInstance == null ? new DefaultTheme(c) : sInstance;
    }

    private DefaultTheme(Context c) {
        BACKGROUND = c.getColor(R.color.colorPureWhite);
        ACCENT = c.getColor(R.color.colorAccent);
        TEXT_DARK = c.getColor(R.color.colorBasicallyBlack);
        TEXT_LIGHT = c.getColor(R.color.colorFadingGray);

        float[] hsv = new float[3];
        Color.colorToHSV(ACCENT, hsv);
        hsv[1] /= 2f;
        FADED_ACCENT = Color.HSVToColor(hsv);

        sInstance = this;
    }


    @Override
    protected void fillThemeWithColors() {
        setColor(ThemeColor.background, BACKGROUND);
        setColor(ThemeColor.textPrimary, TEXT_DARK);
        setColor(ThemeColor.textSecondary, TEXT_LIGHT);
        setColor(ThemeColor.accent, ACCENT);

        setColor(ThemeColor.switchThumbOff, BACKGROUND);
        setColor(ThemeColor.switchThumbOn, ACCENT);
        setColor(ThemeColor.switchTrackOff, TEXT_DARK);
        setColor(ThemeColor.switchTrackOn, FADED_ACCENT);
    }
}
