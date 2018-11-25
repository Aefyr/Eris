package com.aefyr.eris.data;

public class LoadableData<T> {
    public enum LoadingState {
        LOADING, LOADED, ERROR //This state represents the state of actual data, not the cached one
    }

    public enum DataState {
        NO_DATA, OK_LIVE, OK_CACHED
    }

    private LoadingState mLoadingState;
    private DataState mDataState;
    private T mData;

    public LoadableData setData(T data) {
        mData = data;
        return this;
    }

    public T getData() {
        return mData;
    }

    public LoadableData setLoadingState(LoadingState loadingState) {
        mLoadingState = loadingState;
        return this;
    }

    public LoadingState getLoadingState() {
        return mLoadingState;
    }

    public LoadableData setDataState(DataState dataState) {
        mDataState = dataState;
        return this;
    }

    public DataState getDataState() {
        return mDataState;
    }
}
