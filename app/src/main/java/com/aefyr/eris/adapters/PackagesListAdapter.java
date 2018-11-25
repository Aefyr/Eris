package com.aefyr.eris.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.aefyr.eris.R;
import com.aefyr.eris.data.packages.EPackage;
import com.aefyr.eris.data.packages.PackagesConfig;
import com.aefyr.eris.themeengine.ThemedRecyclerViewAdapter;
import com.aefyr.eris.themeengine.core.ThemeColor;
import com.aefyr.eris.themeengine.core.ThemeCore;
import com.aefyr.eris.themes.DarkTheme;
import com.aefyr.eris.themes.DefaultTheme;
import com.aefyr.eris.themes.RgbTheme;
import com.aefyr.eris.utils.ThemeUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PackagesListAdapter extends ThemedRecyclerViewAdapter<RecyclerView.ViewHolder> {
    private ArrayList<EPackage> mData;
    private LayoutInflater mInflater;

    public PackagesListAdapter(Context c) {
        mInflater = LayoutInflater.from(c);
    }

    public void setData(ArrayList<EPackage> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new HeaderViewHolder(mInflater.inflate(R.layout.item_packages_header, parent, false));
        } else if (viewType == 1) {
            return new PackageViewHolder(mInflater.inflate(R.layout.item_package, parent, false));
        } else {
            return new RecyclerView.ViewHolder(mInflater.inflate(R.layout.item_packages_footer, parent, false)) {
            };
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rholder, int position) {
        if (getItemViewType(position) == 0) {
            HeaderViewHolder holder = (HeaderViewHolder) rholder;
            holder.mode.setText(PackagesConfig.getInstance(mInflater.getContext()).getConfigMode() == PackagesConfig.Mode.Whitelist ? R.string.mode_whitelist : R.string.mode_blacklist);
        } else if (getItemViewType(position) == 1) {
            PackageViewHolder holder = (PackageViewHolder) rholder;
            EPackage ep = mData.get(position - 1);
            holder.appName.setText(ep.appName());
            holder.packageName.setText(ep.packageName());

            holder.mSwitchPaused = true;
            holder.aSwitch.setChecked(ep.isSelected());
            holder.mSwitchPaused = false;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;

        if (position == getItemCount() - 1)
            return 2;

        return 1;
    }

    @Override
    public int getItemCount() {
        return (mData == null ? 0 : mData.size()) + 2;
    }

    @Override
    public long getItemId(int position) {
        if (getItemViewType(position) != 1) {
            if (position == 0)
                return -Long.MAX_VALUE;
            else
                return Long.MAX_VALUE;
        }
        return mData.get(position - 1).packageName().hashCode();
    }

    @Override
    public void applyColorToViewHolder(String colorName, int color, RecyclerView.ViewHolder viewHolder) {
        switch (viewHolder.getItemViewType()) {
            case 0:
                HeaderViewHolder hHolder = (HeaderViewHolder) viewHolder;
                switch (colorName) {
                    case ThemeColor.textPrimary:
                        hHolder.mode.setTextColor(color);
                        break;
                    case ThemeColor.textSecondary:
                        hHolder.hint.setTextColor(color);
                        break;
                    case ThemeColor.accent:
                        hHolder.darkModeToggle.setColorFilter(color);
                        break;
                    case ThemeColor.background:
                        hHolder.card.setCardBackgroundColor(color);
                        hHolder.card2.setCardBackgroundColor(color);
                        break;
                }
                break;
            case 1:
                PackageViewHolder pHolder = (PackageViewHolder) viewHolder;
                switch (colorName) {
                    case ThemeColor.textPrimary:
                        pHolder.appName.setTextColor(color);
                        break;
                    case ThemeColor.textSecondary:
                        pHolder.packageName.setTextColor(color);
                        break;
                    case ThemeColor.accent:
                        ThemeUtils.colorSwitch(pHolder.aSwitch, ThemeCore.getInstance().getColor(ThemeColor.switchTrackOff), ThemeCore.getInstance().getColor(ThemeColor.switchTrackOn),
                                ThemeCore.getInstance().getColor(ThemeColor.switchThumbOff), ThemeCore.getInstance().getColor(ThemeColor.switchThumbOn));
                        break;
                }
                break;
        }
    }

    @Override
    public String[] getObservedColors() {
        return new String[]{ThemeColor.background, ThemeColor.accent, ThemeColor.textPrimary, ThemeColor.textSecondary};
    }

    class PackageViewHolder extends RecyclerView.ViewHolder {
        private TextView appName;
        private TextView packageName;
        private Switch aSwitch;

        private boolean mSwitchPaused;

        private PackageViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.tv_app_name);
            packageName = itemView.findViewById(R.id.tv_package_name);
            aSwitch = itemView.findViewById(R.id.switch_package);

            aSwitch.setOnCheckedChangeListener((v, checked) -> {
                if (mSwitchPaused)
                    return;

                mData.get(getAdapterPosition() - 1).setSelected(checked);
            });

            itemView.findViewById(R.id.container_package).setOnClickListener((v) -> aSwitch.toggle());
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mode;
        private CardView card;
        private TextView hint;
        private ImageButton darkModeToggle;
        private CardView card2;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            mode = itemView.findViewById(R.id.tv_mode_desc);
            mode.setSelected(true);
            card = itemView.findViewById(R.id.container_packages_header_card);
            hint = itemView.findViewById(R.id.tv_hint);

            darkModeToggle = itemView.findViewById(R.id.ib_dark_mode);
            card2 = itemView.findViewById(R.id.container_darkmode_toggle);

            itemView.findViewById(R.id.container_packages_header).setOnClickListener((v) -> {
                PackagesConfig.getInstance(v.getContext()).toggleMode();
                mode.setText(PackagesConfig.getInstance(v.getContext()).getConfigMode() == PackagesConfig.Mode.Whitelist ? R.string.mode_whitelist : R.string.mode_blacklist);
            });

            darkModeToggle.setOnClickListener((v) -> {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                if (ThemeCore.getInstance().getTheme() instanceof DefaultTheme) {
                    ThemeCore.getInstance().setTheme(DarkTheme.getInstance(v.getContext()));
                    prefs.edit().putInt("theme", 1).apply();
                } else {
                    ThemeCore.getInstance().setTheme(DefaultTheme.getInstance(v.getContext()));
                    prefs.edit().putInt("theme", 0).apply();
                }
            });

            darkModeToggle.setOnLongClickListener((v) -> {
                ThemeCore.getInstance().setTheme(RgbTheme.getInstance(v.getContext()));
                PreferenceManager.getDefaultSharedPreferences(v.getContext()).edit().putInt("theme", 2).apply();
                return true;
            });
        }
    }
}
