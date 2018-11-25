package com.aefyr.eris.themeengine.core;

import java.util.Map;

//Basically a colors container
abstract class Theme {
    private ThemeObserver mObserver;

    interface ThemeObserver {
        void onColorChanged(String colorName, int fromColor, int toColor, boolean animate);
    }

    void setObserver(ThemeObserver observer) {
        mObserver = observer;
    }

    void removeObserver() {
        mObserver = null;
    }


    private void notifyColorChanged(String colorName, int fromColor, int toColor, boolean animate) {
        if (mObserver != null)
            mObserver.onColorChanged(colorName, fromColor, toColor, animate);
    }

    /**
     * Changes color with a transition animation
     */
    protected void notifyColorChanged(String colorName, int fromColor, int toColor) {
        notifyColorChanged(colorName, fromColor, toColor, true);
    }

    /**
     * Changes color immediately
     */
    protected void notifyColorChanged(String colorName, int color) {
        notifyColorChanged(colorName, 0, color, false);
    }

    public abstract int getColor(String colorName);

    public abstract Map<String, Integer> getAllColors();

    public abstract void onRemoved();

    public abstract void onApplied();

}
