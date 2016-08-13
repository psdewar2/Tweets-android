package com.psd.tweets.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by PSD on 8/12/16.
 */
public class MentionsFragment extends Fragment {

    public static MentionsFragment newInstance(int tab, String title) {
        MentionsFragment mentionsFragment = new MentionsFragment();
        Bundle args = new Bundle();
        args.putInt("tab", tab);
        mentionsFragment.setArguments(args);
        return mentionsFragment;
    }
}
