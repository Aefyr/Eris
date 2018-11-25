package com.aefyr.eris.data.packages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.aefyr.eris.data.LoadableData;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;

public class PackagesLiveData extends LiveData<LoadableData<ArrayList<EPackage>>> {
    private Context mContext;
    private PackagesLoader mOngoingLoader;

    public PackagesLiveData(Context c) {
        setValue(new LoadableData<>());
        mContext = c.getApplicationContext();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadData();
            }
        }, intentFilter);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActive() {
        if (getValue().getLoadingState() != LoadableData.LoadingState.LOADED)
            loadData();
    }

    //I'm pretty sure this will break if you try to reload packages too rapidly, but whatever for now
    @SuppressWarnings("unchecked")
    private void loadData() {
        if (mOngoingLoader != null)
            mOngoingLoader.cancel();

        mOngoingLoader = new PackagesLoader(mContext);
        mOngoingLoader.load((packages -> {
            setValue(getValue()
                    .setLoadingState(LoadableData.LoadingState.LOADED)
                    .setDataState(LoadableData.DataState.OK_LIVE)
                    .setData(packages));
            mOngoingLoader = null;
        }));
    }
}
