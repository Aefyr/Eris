package com.aefyr.eris.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;

import com.aefyr.eris.R;
import com.aefyr.eris.adapters.PackagesListAdapter;
import com.aefyr.eris.data.packages.PackagesListViewModel;
import com.aefyr.eris.themeengine.ThemedActivity;
import com.aefyr.eris.themeengine.core.ThemeColor;
import com.aefyr.eris.themeengine.core.ThemeCore;
import com.aefyr.eris.themes.DarkTheme;
import com.aefyr.eris.themes.DefaultTheme;
import com.aefyr.eris.themes.RgbTheme;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends ThemedActivity {

    private PackagesListAdapter mPackagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switch (PreferenceManager.getDefaultSharedPreferences(this).getInt("theme", 0)) {
            case 1:
                ThemeCore.getInstance().setTheme(DarkTheme.getInstance(this));
                break;
            case 2:
                ThemeCore.getInstance().setTheme(RgbTheme.getInstance(this));
                break;
            default:
                ThemeCore.getInstance().setTheme(DefaultTheme.getInstance(this));
                break;
        }

        RecyclerView packagesRecycler = findViewById(R.id.recycler_packages);
        packagesRecycler.setLayoutManager(new LinearLayoutManager(this));
        mPackagesAdapter = new PackagesListAdapter(this);
        mPackagesAdapter.setHasStableIds(true);
        packagesRecycler.setAdapter(mPackagesAdapter);

        PackagesListViewModel viewModel = ViewModelProviders.of(this).get(PackagesListViewModel.class);
        viewModel.getData().observe(this, data -> mPackagesAdapter.setData(data.getData()));
    }

    @Override
    public void onColorChanged(String colorName, int color) {
        findViewById(R.id.container_main).setBackgroundColor(color);
        setStatusBarColor(color);
        setNavbarColor(color);
    }

    @Override
    public String[] getObservedColors() {
        return new String[]{ThemeColor.background};
    }
}
