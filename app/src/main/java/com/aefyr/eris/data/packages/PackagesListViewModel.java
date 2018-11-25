package com.aefyr.eris.data.packages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PackagesListViewModel extends AndroidViewModel {
    private PackagesLiveData mData;

    public PackagesListViewModel(@NonNull Application application) {
        super(application);
        mData = new PackagesLiveData(application.getApplicationContext());
    }

    public PackagesLiveData getData() {
        return mData;
    }
}
