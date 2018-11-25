package com.aefyr.eris.data.packages;

import com.aefyr.eris.BuildConfig;

import de.robv.android.xposed.XSharedPreferences;

public class PackagesConfigProvider {

    public static boolean shouldInjectIntoPackage(String packageName) {
        XSharedPreferences prefs = new XSharedPreferences(BuildConfig.APPLICATION_ID, "config");
        if (prefs.getInt("mode", 0) == 0)
            return !prefs.getBoolean(packageName, false);
        else
            return prefs.getBoolean(packageName, false);
    }
}
