package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by RIPU on 24-Aug-16.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {

        super(fm);
        mContext=context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if(position==0){
            return new NumbersFragment();
        }
        else if(position==1)
        {
            return new FamilyFragment();
        }
        else if(position==2){
            return new ColorsFragment();
        }
        else{
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return mContext.getString(R.string.category_numbers);
        }
        else if(position==1){
            return mContext.getString(R.string.category_family);
        }
        else if(position==2){
            return mContext.getString(R.string.category_colors);
        }
        else{
            return mContext.getString(R.string.category_phrases);
        }
    }
}
