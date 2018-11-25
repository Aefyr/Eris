package com.aefyr.eris.data.packages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class EPackage {
    private boolean mSelected;
    private String mPackageName;
    private String mAppName;
    private Context mContext;

    public EPackage(Context c, PackageManager manager, PackageInfo packageInfo) {
        mContext = c.getApplicationContext();

        mAppName = manager.getApplicationLabel(packageInfo.applicationInfo).toString();
        mPackageName = packageInfo.packageName;
        mSelected = PackagesConfig.getInstance(mContext).isPackageSelected(mPackageName);
    }

    public String appName() {
        return mAppName;
    }

    public String packageName() {
        return mPackageName;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
        PackagesConfig.getInstance(mContext).setPackageSelected(mPackageName, mSelected);
    }

    public boolean isSelected() {
        return mSelected;
    }
}
