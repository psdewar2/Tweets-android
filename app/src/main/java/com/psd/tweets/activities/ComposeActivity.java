package com.psd.tweets.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.psd.tweets.R;
import com.psd.tweets.TwitterApplication;
import com.psd.tweets.models.User;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    EditText etNewTweet;
    TextView tvCharCount;
    Button btnSendTweet;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentUser = User.fromJSONObject(response);
            }
        });
        setContentView(R.layout.activity_compose);
        etNewTweet = (EditText) findViewById(R.id.etNewTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        btnSendTweet = (Button) findViewById(R.id.btnSendTweet);

        // Request focus to field
        etNewTweet.requestFocus();
        tvCharCount.setText(String.valueOf(140));
        final int charCountColor = tvCharCount.getCurrentTextColor();
        btnSendTweet.setEnabled(false);
        btnSendTweet.setBackgroundColor(Color.GRAY);
        etNewTweet.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                // this will show characters remaining
                int charsRemaining = 140 - s.toString().length();
                String strCharsRemaining = String.valueOf(charsRemaining);
                tvCharCount.setText(strCharsRemaining);

                if (charsRemaining < 21) {
                    tvCharCount.setTextColor(Color.RED);
                } else {
                    tvCharCount.setTextColor(charCountColor);
                }
                if (charsRemaining < 0 || charsRemaining >= 140) {
                    btnSendTweet.setEnabled(false);
                    btnSendTweet.setBackgroundColor(Color.GRAY);
                } else {
                    btnSendTweet.setEnabled(true);
                    btnSendTweet.setBackgroundResource(R.drawable.button_tweet);
                }
            }
        });
        btnSendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("tweet", etNewTweet.getText().toString());
                intent.putExtra("user", Parcels.wrap(currentUser));
                setResult(1, intent);
                finish();
            }
        });


    }
}
