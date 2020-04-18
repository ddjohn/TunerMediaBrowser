package com.aptiv.got.tuner.media;

import android.media.session.PlaybackState;
import android.util.Log;

public class MyPlaybackState {
    private static final String TAG = MyPlaybackState.class.getSimpleName();
    private PlaybackState state;

    public MyPlaybackState() {
        Log.e(TAG, "MyPlaybackState");
        state = new PlaybackState.Builder().setState(PlaybackState.STATE_NONE, 0, 1.0f).build();
    }

    public PlaybackState getPlaybackState() {
        return state;
    }
}
