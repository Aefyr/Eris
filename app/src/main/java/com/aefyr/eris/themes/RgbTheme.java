package com.aefyr.eris.themes;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;

import com.aefyr.eris.R;
import com.aefyr.eris.themeengine.core.ThemeColor;

public class RgbTheme extends HardcodedTheme {
    private static RgbTheme sInstance;

    private int BACKGROUND;
    private int ACCENT;
    private int TEXT_DARK;
    private int TEXT_LIGHT;
    private int FADED_ACCENT;

    private float[] hsv = {0, 0.5f, 1};
    private float[] hsv2 = {0, 0.5f, 1};
    private ValueAnimator mAnimator;

    public static RgbTheme getInstance(Context c) {
        return sInstance == null ? new RgbTheme(c) : sInstance;
    }

    private RgbTheme(Context c) {
        BACKGROUND = c.getColor(R.color.colorBasicallyBlack);
        ACCENT = c.getColor(R.color.colorAccent);
        TEXT_DARK = c.getColor(R.color.colorPureWhite);
        TEXT_LIGHT = c.getColor(R.color.colorLightGray);
        FADED_ACCENT = fadeColor(ACCENT);


        sInstance = this;
    }

    @Override
    public void onApplied() {
        super.onApplied();

        mAnimator = ValueAnimator.ofFloat(0, 360);
        mAnimator.addUpdateListener((a) -> {
            hsv[0] = (float) a.getAnimatedValue();
            int color = Color.HSVToColor(hsv);

            setColorWithoutAnimation(ThemeColor.switchTrackOn, fadeColor(color));
            setColorWithoutAnimation(ThemeColor.switchThumbOn, color);
            setColorWithoutAnimation(ThemeColor.accent, color);
        });
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(8000);
        mAnimator.start();
    }

    private int fadeColor(int color) {
        Color.colorToHSV(color, hsv2);
        hsv2[1] /= 2f;
        return Color.HSVToColor(hsv2);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        mAnimator.end();
        mAnimator = null;
    }

    @Override
    protected void fillThemeWithColors() {
        setColor(ThemeColor.background, BACKGROUND);
        setColor(ThemeColor.textPrimary, TEXT_DARK);
        setColor(ThemeColor.textSecondary, TEXT_LIGHT);
        setColor(ThemeColor.accent, ACCENT);

        setColor(ThemeColor.switchThumbOff, TEXT_LIGHT);
        setColor(ThemeColor.switchThumbOn, ACCENT);
        setColor(ThemeColor.switchTrackOff, TEXT_DARK);
        setColor(ThemeColor.switchTrackOn, FADED_ACCENT);
    }
}
