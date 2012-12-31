package info.mahmoudhossam.twitter;

import android.content.Context;
import com.commonsware.cwac.endless.EndlessAdapter;

class MyEndlessAdapter extends EndlessAdapter {

    Context ctx;
    TweetAdapter adapter;

    MyEndlessAdapter(Context ctx, TweetAdapter adapter, int ResourceID) {
        super(ctx, adapter, ResourceID);
        this.ctx = ctx;
        this.adapter = adapter;
    }

    @Override
    protected boolean cacheInBackground() throws Exception {
        return false;
    }

    @Override
    protected void appendCachedData() {

    }
}
