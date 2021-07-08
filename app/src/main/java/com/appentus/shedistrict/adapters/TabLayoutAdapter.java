package com.appentus.shedistrict.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.appentus.nutrition.ui.fragments.AnnouncementFragment;
import com.appentus.nutrition.ui.fragments.YourAnnouncementFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public TabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AnnouncementFragment announcementFragment = new AnnouncementFragment();
                return announcementFragment;
            case 1:
                YourAnnouncementFragment yourAnnouncementFragment= new YourAnnouncementFragment();
                return yourAnnouncementFragment;
            default:
                return new AnnouncementFragment();
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}