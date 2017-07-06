package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by arajesh on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;

    private HomeTimelineFragment timelineFragment;
    private MentionsTimelineFragment mentionsFragment;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        timelineFragment = new HomeTimelineFragment();
        mentionsFragment = new MentionsTimelineFragment();
        this.context = context;
    }

    // return the total # of fragments

    @Override
    public int getCount() {
        return 2;
    }


    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        Log.d("position", String.valueOf(position));
        if (position == 0){
            timelineFragment = getTimelineInstance();
            return timelineFragment;
        } else if (position == 1) {
            mentionsFragment = getMentionsInstance();
            return mentionsFragment;
        } else {
            return null;
        }
    }

    public HomeTimelineFragment getTimelineInstance(){
        if (timelineFragment == null) {
            timelineFragment = new HomeTimelineFragment();
        }
        return timelineFragment;

    }

    public MentionsTimelineFragment getMentionsInstance(){
        if (mentionsFragment == null) {
            mentionsFragment = new MentionsTimelineFragment();
        }
        return mentionsFragment;

    }

    // return title

    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
}
