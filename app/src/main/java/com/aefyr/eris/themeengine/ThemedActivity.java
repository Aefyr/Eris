package com.aefyr.eris.themeengine;

import android.os.Build;
import android.view.View;

import com.aefyr.eris.themeengine.core.ThemeChangeObserver;
import com.aefyr.eris.themeengine.core.ThemeCore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

public abstract class ThemedActivity extends AppCompatActivity implements ThemeChangeObserver {

    @Override
    protected void onStart() {
        super.onStart();
        ThemeCore.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ThemeCore.getInstance().unregister(this);
    }

    public void setStatusBarColor(int color) {
        getWindow().setStatusBarColor(color);

        if (Build.VERSION.SDK_INT >= 23) {
            boolean lightStatusBar = ColorUtils.calculateLuminance(color) > 0.5;
            int flags = getWindow().getDecorView().getSystemUiVisibility();

            if ((lightStatusBar && ((flags & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)) || (!lightStatusBar && ((flags & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)))
                return;


            if (lightStatusBar)
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            else
                flags ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

            getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    }

    public void setNavbarColor(int color) {
        getWindow().setNavigationBarColor(color);

        if (Build.VERSION.SDK_INT >= 26) {
            boolean lightStatusBar = ColorUtils.calculateLuminance(color) > 0.5;
            int flags = getWindow().getDecorView().getSystemUiVisibility();

            if ((lightStatusBar && ((flags & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) == View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)) || (!lightStatusBar && ((flags & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)))
                return;


            if (lightStatusBar)
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            else
                flags ^= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;

            getWindow().getDecorView().setSystemUiVisibility(flags);
        }
    }

}
