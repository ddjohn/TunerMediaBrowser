package com.aptiv.got.tuner.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.os.IBinder;
import android.util.Log;

import com.aptiv.got.tuner.service.MyMediabrowserService;

import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.*;

public class MyProxy implements ServiceConnection {
    private static final String TAG = MyProxy.class.getSimpleName();
    private MyMediabrowserService service = null;
    private MyMediaController controller = null;
    private Context ctx;
    private AudioManager audio;

    public MyProxy(Context ctx) {
        this.ctx = ctx;

        this.startService(ctx, MyMediabrowserService.class);
        this.bindToService(ctx, MyMediabrowserService.class);

        audio = (AudioManager)ctx.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audio.registerAudioPlaybackCallback(new AudioManager.AudioPlaybackCallback() {
            @Override
            public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs) {
                super.onPlaybackConfigChanged(configs);
                Log.e(TAG, "onPlaybackConfigChanged");

                OBJECT(TAG, configs);
            }
        }, null);
    }

    private void bindToService(Context ctx, Class<?> service) {
        Log.e(TAG, "bindToService");

        Intent intent = new Intent(ctx, service);
        ctx.bindService(intent, this, 0);
    }

    private void startService(Context ctx, Class<?> service) {
        Log.e(TAG, "startService");

        Intent intent = new Intent(ctx, service);
        ctx.startService(intent);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.e(TAG, "onServiceConnected");

        OBJECT(TAG, name, binder);

    //    service = ((MyMediabrowserService.MyBinder)binder).getService();
    //    controller = new MyMediaController(ctx, service.getToken());

        OBJECT(TAG, name, service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "onServiceDisconnected");
    }

    public void play() {
        if(controller != null)
            controller.play();
    }

    public void pause() {
        if(controller != null)
            controller.pause();
    }

    public void skipToPrevious() {
        if(controller != null)
            controller.skipToPrevious();
    }

    public void playFromSearch(String s, Object o) {
        if(controller != null)
            controller.playFromSearch(s, o);
    }

    public void skipToNext() {
        if(controller != null)
            controller.skipToNext();
    }

    private void setVolume(int percent) {
        int current = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        int volume = (int) (current + 0.01 * percent / max);

        if(volume > max) volume = max;
        if(volume <   0) volume = 0;

        OBJECT(TAG, "audio", current, max, volume);

        Log.e(TAG, "Set volume to " + volume);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

    }

    public void volUp() {
        setVolume(+10);
    }

    public void volDown() {
        setVolume(-10);
    }
}
