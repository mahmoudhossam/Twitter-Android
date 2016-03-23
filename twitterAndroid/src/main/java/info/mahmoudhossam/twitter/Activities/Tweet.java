package info.mahmoudhossam.twitter.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import info.mahmoudhossam.twitter.R;

public class Tweet extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tweet);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("username"));
        TextView tweet = (TextView) findViewById(R.id.tweet);
        tweet.setText(getIntent().getStringExtra("text"));
    }
}