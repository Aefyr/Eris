package com.aefyr.eris.themeengine.core;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.Log;

import com.aefyr.eris.utils.ColorUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class ThemeCore implements Theme.ThemeObserver {
    private static final String TAG = "ThemeCore";
    private static ThemeCore sInstance;

    private HashMap<String, ArrayList<ThemeChangeObserver>> mObservers;
    private Theme mTheme;

    private boolean mAnimationEnabled = false;
    private static final int ANIMATION_DURATION = 300;
    private HashMap<String, ValueAnimator> mAnimators;
    private HashMap<String, Integer> mAnimatedColors; //Currently animated colors

    public static ThemeCore getInstance() {
        return sInstance == null ? new ThemeCore() : sInstance;
    }

    private ThemeCore() {
        mObservers = new HashMap<>();
        mAnimators = new HashMap<>();
        mAnimatedColors = new HashMap<>();

        sInstance = this;
    }

    public void register(ThemeChangeObserver observer) {
        for (String color : observer.getObservedColors()) {
            if (!mObservers.containsKey(color))
                mObservers.put(color, new ArrayList<>());

            mObservers.get(color).add(observer);
        }

        fullyApplyTheme(observer);
        Log.d(TAG, "Observer registered");
    }

    public void unregister(ThemeChangeObserver observer) {
        for (String color : observer.getObservedColors()) {
            mObservers.get(color).remove(observer);
        }
        Log.d(TAG, "Observer unregistered");
    }

    public void setTheme(Theme theme) {
        if (mTheme != null)
            mTheme.removeObserver();

        Theme oldTheme = mTheme;
        mTheme = theme;
        mTheme.setObserver(this);
        themeChanged(oldTheme);
    }

    public Theme getTheme() {
        return mTheme;
    }

    public int getColor(String colorName) {
        return mTheme.getColor(colorName);
    }

    public void setAnimationEnabled(boolean enabled) {
        mAnimationEnabled = enabled;
    }

    public boolean isAnimationEnabled() {
        return mAnimationEnabled;
    }

    private void notifyColorChanged(String colorName, int newColor) {
        if (mObservers.containsKey(colorName)) {
            for (ThemeChangeObserver listener : mObservers.get(colorName))
                listener.onColorChanged(colorName, newColor);
        }
    }

    private void fullyApplyTheme(ThemeChangeObserver observer) {
        for (String colorName : observer.getObservedColors())
            observer.onColorChanged(colorName, getColor(colorName));
    }

    private void themeChanged(Theme prevTheme) {
        if (mAnimationEnabled && prevTheme != null) {
            for (String colorName : mTheme.getAllColors().keySet())
                animateColorChange(colorName, prevTheme.getColor(colorName), getColor(colorName));
        } else {
            for (String colorName : mTheme.getAllColors().keySet())
                notifyColorChanged(colorName, getColor(colorName));
        }

        if (prevTheme != null)
            prevTheme.onRemoved();
        mTheme.onApplied();
    }

    @Override
    public void onColorChanged(String colorName, int fromColor, int toColor, boolean animate) {
        if (mAnimationEnabled && animate)
            animateColorChange(colorName, fromColor, toColor);
        else
            notifyColorChanged(colorName, toColor);
    }

    private void animateColorChange(String colorName, int fromColor, int toColor) {
        ValueAnimator animator = mAnimators.get(colorName);
        int aFromColor; //Actual starting color for the transtition animation, may differ from fromColor if animation is already in progress

        if (animator == null) {
            animator = ValueAnimator.ofFloat(0, 1);
            mAnimators.put(colorName, animator);

            aFromColor = fromColor;
        } else {
            animator.removeAllListeners();
            animator.removeAllUpdateListeners();
            animator.cancel();
            animator.setFloatValues(0, 1);

            aFromColor = mAnimatedColors.get(colorName);
        }

        //To free memory i guess
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimators.remove(colorName);
                mAnimatedColors.remove(colorName);
            }
        });

        animator.addUpdateListener((a) -> {
            int color = ColorUtils.lerpColor(aFromColor, toColor, (float) a.getAnimatedValue());
            mAnimatedColors.put(colorName, color);
            notifyColorChanged(colorName, color);
        });

        animator.setDuration(ANIMATION_DURATION);
        animator.start();
    }

}
