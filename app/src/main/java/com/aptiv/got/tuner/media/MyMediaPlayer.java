package com.aptiv.got.tuner.media;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class MyMediaPlayer implements MediaPlayer.OnPreparedListener,  MediaPlayer.OnBufferingUpdateListener {
    private static final String TAG = MyMediaPlayer.class.getSimpleName();
    private MediaPlayer player;

    public MyMediaPlayer() {
        Log.e(TAG, "MyMediaPlayer");

        player = new MediaPlayer();
        //player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build();
        player.setAudioAttributes(attributes);
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);

        try {
            player.setDataSource("https://dl.espressif.com/dl/audio/ff-16b-2c-44100hz.m4a");
            //player.prepareAsync();
            player.prepare();
            player.start();
        }
        catch(IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        Log.e(TAG, "onPrepared");

        OBJECT(TAG, player);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer player, int percent) {
        Log.e(TAG, "onBufferingUpdate");

        OBJECT(TAG, player, percent);
    }

    public void pause() {
        player.pause();
    }

    public void play() {
        player.start();
    }

    public void stop() {
        player.stop();
    }
}
