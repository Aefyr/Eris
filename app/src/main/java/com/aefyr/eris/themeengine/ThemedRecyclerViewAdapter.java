package com.aefyr.eris.themeengine;

import com.aefyr.eris.themeengine.core.ThemeChangeObserver;
import com.aefyr.eris.themeengine.core.ThemeCore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ThemedRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> implements ThemeChangeObserver {
    private ArrayList<T> mActiveHolders;

    public ThemedRecyclerViewAdapter() {
        mActiveHolders = new ArrayList<>();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ThemeCore.getInstance().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ThemeCore.getInstance().unregister(this);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull T holder) {
        super.onViewAttachedToWindow(holder);
        mActiveHolders.add(holder);

        for (String colorName : getObservedColors())
            applyColorToViewHolder(colorName, ThemeCore.getInstance().getColor(colorName), holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull T holder) {
        super.onViewDetachedFromWindow(holder);
        mActiveHolders.remove(holder);
    }

    @Override
    public void onColorChanged(String colorName, int color) {
        for (T viewHolder : mActiveHolders)
            applyColorToViewHolder(colorName, color, viewHolder);
    }

    public abstract void applyColorToViewHolder(String colorName, int color, T viewHolder);
}
