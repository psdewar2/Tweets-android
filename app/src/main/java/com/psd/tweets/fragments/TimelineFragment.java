package com.psd.tweets.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psd.tweets.R;
import com.psd.tweets.activities.TweetsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    TweetsFragmentPagerAdapter adapterViewPager;

    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        adapterViewPager = new TweetsFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // listeners
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String tabTitles[] = {"Home", "Mentions"};
                ((TweetsActivity) getActivity()).getSupportActionBar().setTitle(tabTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // PAGERADAPTER CLASS
    public static class TweetsFragmentPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private String tabTitles[] = new String[] { "Home", "Mentions"};

        public TweetsFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // HomeFragment
                    return HomeFragment.newInstance(0, "Home");
                case 1: // MentionsFragment
                    return MentionsFragment.newInstance(1, "Mentions");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
