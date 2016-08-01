package com.psd.tweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.psd.tweets.R;
import com.psd.tweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PSD on 8/1/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Tweet> tweetList;

    // Store the context for easy access
    private Context mContext;

    //listener member variables
    private static OnItemClickListener mClickListener;

    //constructor
    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        tweetList = tweets;
    }

    //item_article.xml
    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profilePictureImageView) ImageView profilePicture;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvTweetBody) TextView tvTweetBody;
        public TweetViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }

    // Define the listener interface for normal/long clicks
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        return tweetList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    //creates different RecyclerView.ViewHolder objects based on the item view type
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                viewHolder = new TweetViewHolder(inflater.inflate(R.layout.item_tweet, parent, false));
                break;
        }
        return viewHolder;
    }

    //updates the RecyclerView.ViewHolder contents with the item at the given position
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = tweetList.get(position);
        switch (holder.getItemViewType()) {
            default:
                TweetViewHolder tVH = (TweetViewHolder) holder;
                tVH.profilePicture.setImageResource(0);
                tVH.tvUserName.setText(tweet.getUser().getName());
                tVH.tvTweetBody.setText(tweet.getBody());

                //populate thumbnail by remotely downloading image
                String profileImageUrl = tweet.getUser().getProfileImageUrl();
                Log.d("profile image url", profileImageUrl);
                if (!TextUtils.isEmpty(profileImageUrl)) {
                    Picasso.with(getContext()).load(profileImageUrl).into(tVH.profilePicture);
                }
                break;
        }
    }
}
