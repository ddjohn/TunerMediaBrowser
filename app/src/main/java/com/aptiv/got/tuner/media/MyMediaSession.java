package com.aptiv.got.tuner.media;

import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyMediaSession extends MediaSession.Callback {
    private static final String TAG = MyMediaSession.class.getSimpleName();
    private MediaSession session;
    private MyMediaPlayer player = new MyMediaPlayer();

    public MyMediaSession(Context ctx, String tag) {
        session = new MediaSession(ctx, tag);
        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        session.setCallback(this);

        session.setActive(true);

        PlaybackState state = new PlaybackState.Builder().setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PLAY_PAUSE).build();
        session.setPlaybackState(state);
    }

    public MediaSession.Token getSessionToken() {
        return session.getSessionToken();
    }

    @Override
    public void onCommand(@NonNull String command, @Nullable Bundle args, @Nullable ResultReceiver cb) {
        super.onCommand(command, args, cb);

        Log.e(TAG, "onCommand");

    }

    @Override
    public boolean onMediaButtonEvent(@NonNull Intent mediaButtonIntent) {
        Log.e(TAG, "onMediaButtonEvent");

        return super.onMediaButtonEvent(mediaButtonIntent);
    }

    @Override
    public void onPrepare() {
        super.onPrepare();

        Log.e(TAG, "onPrepare");
    }

    @Override
    public void onPrepareFromMediaId(String mediaId, Bundle extras) {
        super.onPrepareFromMediaId(mediaId, extras);

        Log.e(TAG, "onPrepareFromMediaId");
    }

    @Override
    public void onPrepareFromSearch(String query, Bundle extras) {
        super.onPrepareFromSearch(query, extras);

        Log.e(TAG, "onPrepareFromSearch");
    }

    @Override
    public void onPrepareFromUri(Uri uri, Bundle extras) {
        super.onPrepareFromUri(uri, extras);

        Log.e(TAG, "onPrepareFromUri");
    }

    @Override
    public void onPlay() {
        super.onPlay();

        Log.e(TAG, "onPlay");
        player.play();
    }

    @Override
    public void onPlayFromSearch(String query, Bundle extras) {
        super.onPlayFromSearch(query, extras);

        Log.e(TAG, "onPlayFromSearch");
    }

    @Override
    public void onPlayFromMediaId(String mediaId, Bundle extras) {
        super.onPlayFromMediaId(mediaId, extras);

        Log.e(TAG, "onPlayFromMediaId");
    }

    @Override
    public void onPlayFromUri(Uri uri, Bundle extras) {
        super.onPlayFromUri(uri, extras);

        Log.e(TAG, "onPlayFromUri");
    }

    @Override
    public void onSkipToQueueItem(long id) {
        super.onSkipToQueueItem(id);

        Log.e(TAG, "onSkipToQueueItem");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e(TAG, "onPause");
        player.pause();
    }

    @Override
    public void onSkipToNext() {
        super.onSkipToNext();

        Log.e(TAG, "onSkipToNext");
    }

    @Override
    public void onSkipToPrevious() {
        super.onSkipToPrevious();

        Log.e(TAG, "onSkipToPrevious");
    }

    @Override
    public void onFastForward() {
        super.onFastForward();

        Log.e(TAG, "onFastForward");
    }

    @Override
    public void onRewind() {
        super.onRewind();

        Log.e(TAG, "onRewind");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e(TAG, "onStop");
        player.stop();
    }

    @Override
    public void onSeekTo(long pos) {
        super.onSeekTo(pos);

        Log.e(TAG, "onSeekTo");
    }

    @Override
    public void onSetRating(@NonNull Rating rating) {
        super.onSetRating(rating);

        Log.e(TAG, "onSetRating");
    }

    @Override
    public void onSetPlaybackSpeed(float speed) {
        super.onSetPlaybackSpeed(speed);

        Log.e(TAG, "onSetPlaybackSpeed");
    }

    @Override
    public void onCustomAction(@NonNull String action, @Nullable Bundle extras) {
        super.onCustomAction(action, extras);

        Log.e(TAG, "onCustomAction");
    }

    public void setPlaybackState(PlaybackState state) {
        session.setPlaybackState(state);

        Log.e(TAG, "setPlaybackState");
    }
}
