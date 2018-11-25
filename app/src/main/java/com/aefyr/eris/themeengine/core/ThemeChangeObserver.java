package com.aefyr.eris.themeengine.core;

public interface ThemeChangeObserver {

    void onColorChanged(String colorName, int color);

    String[] getObservedColors();

}
