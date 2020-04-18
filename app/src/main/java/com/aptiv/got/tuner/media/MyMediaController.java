package com.aptiv.got.tuner.media;

import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import static com.aptiv.got.tuner.utils.Debug.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyMediaController extends MediaController.Callback {
    private static final String TAG = MyMediaController.class.getSimpleName();
    private MediaController controller;

    public MyMediaController(Context ctx, MediaSession.Token token) {
        Log.e(TAG, "MyMediaController");

        controller = new MediaController(ctx, token);
        controller.registerCallback(this);
    }

    @Override
    public void onSessionDestroyed() {
        super.onSessionDestroyed();

        Log.e(TAG, "onSessionDestroyed");
    }

    @Override
    public void onSessionEvent(@NonNull String event, @Nullable Bundle extras) {
        super.onSessionEvent(event, extras);

        Log.e(TAG, "onSessionEvent");
    }

    @Override
    public void onPlaybackStateChanged(@Nullable PlaybackState state) {
        super.onPlaybackStateChanged(state);

        Log.e(TAG, "onPlaybackStateChanged");

        OBJECT(TAG, state);
    }

    @Override
    public void onMetadataChanged(@Nullable MediaMetadata metadata) {
        super.onMetadataChanged(metadata);

        Log.e(TAG, "onMetadataChanged");
    }

    @Override
    public void onQueueChanged(@Nullable List<MediaSession.QueueItem> queue) {
        super.onQueueChanged(queue);

        Log.e(TAG, "onQueueChanged");
    }

    @Override
    public void onQueueTitleChanged(@Nullable CharSequence title) {
        super.onQueueTitleChanged(title);

        Log.e(TAG, "onQueueTitleChanged");
    }

    @Override
    public void onExtrasChanged(@Nullable Bundle extras) {
        super.onExtrasChanged(extras);

        Log.e(TAG, "onExtrasChanged");
    }

    @Override
    public void onAudioInfoChanged(MediaController.PlaybackInfo info) {
        super.onAudioInfoChanged(info);

        Log.e(TAG, "onAudioInfoChanged");
    }

    public void play() {
        Log.e(TAG, "play");

        controller.getTransportControls().play();
    }

    public void pause() {
        Log.e(TAG, "pause");

        controller.getTransportControls().pause();
    }

    public void skipToPrevious() {
        Log.e(TAG, "skipToPrevious");

        controller.getTransportControls().skipToPrevious();
    }

    public void playFromSearch(String query, Object o) {
        //controller.getTransportControls().playFromSearch(query, o);
    }

    public void skipToNext() {
        Log.e(TAG, "skipToNext");

        controller.getTransportControls().skipToNext();
    }
}
