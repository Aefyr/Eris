package com.aefyr.eris.data.packages;

import android.content.Context;
import android.content.SharedPreferences;

public class PackagesConfig {
    public enum Mode {
        Whitelist, Blacklist
    }

    private static PackagesConfig sInstance;

    private SharedPreferences mPrefs;

    public static PackagesConfig getInstance(Context c) {
        return sInstance == null ? new PackagesConfig(c) : sInstance;
    }

    private PackagesConfig(Context c) {
        mPrefs = c.getSharedPreferences("config", Context.MODE_WORLD_READABLE);

        sInstance = this;
    }


    public void setConfigMode(Mode mode) {
        mPrefs.edit().putInt("mode", mode == Mode.Blacklist ? 0 : 1).apply();
    }

    public void toggleMode() {
        if (getConfigMode() == Mode.Whitelist)
            setConfigMode(Mode.Blacklist);
        else
            setConfigMode(Mode.Whitelist);
    }

    public void setPackageSelected(String packageName, boolean selected) {
        if (selected)
            mPrefs.edit().putBoolean(packageName, true).apply();
        else
            mPrefs.edit().remove(packageName).apply();
    }

    public boolean isPackageSelected(String packageName) {
        return mPrefs.getBoolean(packageName, false);
    }

    public Mode getConfigMode() {
        return mPrefs.getInt("mode", 0) == 0 ? Mode.Blacklist : Mode.Whitelist;
    }
}
