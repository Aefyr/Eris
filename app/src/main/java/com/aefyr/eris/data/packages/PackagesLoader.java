package com.aefyr.eris.data.packages;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PackagesLoader extends Thread {
    private OnLoadedListener mListener;
    private PackageManager mPackageManager;
    private Context mContext;
    private boolean mCancelled;

    interface OnLoadedListener {
        void onPackagesLoaded(ArrayList<EPackage> packages);
    }

    PackagesLoader(Context c) {
        mPackageManager = c.getPackageManager();
        mContext = c;
    }

    void load(OnLoadedListener listener) {
        mListener = listener;
        start();
    }

    void cancel() {
        mCancelled = true;
        mListener = null;
        mContext = null;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        List<PackageInfo> packageInfoList = mPackageManager.getInstalledPackages(0);
        ArrayList<EPackage> ePackages = new ArrayList<>(packageInfoList.size() + 4);
        for (PackageInfo pi : packageInfoList)
            ePackages.add(new EPackage(mContext, mPackageManager, pi));

        Collections.sort(ePackages, (o1, o2) -> o1.appName().compareToIgnoreCase(o2.appName()));

        Log.d("Eris", String.format("Loading packages took %d ms", System.currentTimeMillis() - start));

        if (!mCancelled && mListener != null) {
            new Handler(Looper.getMainLooper()).post(() -> mListener.onPackagesLoaded(ePackages));
        }
    }
}
