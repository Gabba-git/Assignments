package com.arvind.moodleplus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
// adapter for custom swipe view

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs,Id;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
 
    @Override
    public Fragment getItem(int position) {

 
        switch (position) {

            case 0:
                
                Assignments tab = new Assignments();
                return tab;
            case 1:
                Threads tab1 = new Threads();
                return tab1;
			case 2:

                Grades tab2 = new Grades();
                return tab2;
            default:
                return null;
        }
    }
 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
