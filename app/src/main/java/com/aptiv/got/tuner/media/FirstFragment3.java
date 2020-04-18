package com.aptiv.got.tuner.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aptiv.got.tuner.service.MyMediabrowserService;
import com.aptiv.got.tuner.R;

import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class FirstFragment3 extends Fragment implements View.OnClickListener, ServiceConnection {
    private static final String TAG = FirstFragment3.class.getSimpleName();

    private AudioManager audioManager = null;
    private MyMediaController mediaController = null;
    private MyMediabrowserService service = null;
    private MediaBrowser mediaBrowser = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

 //       this.startService(MyMediabrowserService.class);
 //       this.bindToService(MyMediabrowserService.class);

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle instance) {
        super.onViewCreated(view, instance);

        Log.e(TAG, "onViewCreated");

        OBJECT(TAG, view, instance);

        view.findViewById(R.id.pause  ).setOnClickListener(this);
        view.findViewById(R.id.play   ).setOnClickListener(this);
        //view.findViewById(R.id.stop   ).setOnClickListener(this);
        view.findViewById(R.id.volup  ).setOnClickListener(this);
        view.findViewById(R.id.voldown).setOnClickListener(this);

        Log.i(TAG, "Connect to browser");
        mediaBrowser = new MediaBrowser(this.getContext(),
                new ComponentName(this.getActivity(), MyMediabrowserService.class),
                new MediaBrowser.ConnectionCallback() {

                @Override
                public void onConnected() {
                    Log.e(TAG, "onConnected");

                    MediaSession.Token token = mediaBrowser.getSessionToken();

                    MediaController mediaController = new MediaController(FirstFragment3.this.getContext(), token);

                    // Save the controller
                    //MediaController.setMediaController(MediaPlayerActivity.this, mediaController);

                    // Finish building the UI
                    //buildTransportControls();
                }

                    @Override
                    public void onConnectionSuspended() {
                        Log.e(TAG, "onConnectionSuspended");
                    }

                    @Override
                    public void onConnectionFailed() {
                        Log.e(TAG, "onConnectionFailed");
                    }
                }, null);
        mediaBrowser.connect();

        audioManager = (AudioManager)this.getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.registerAudioPlaybackCallback(new AudioManager.AudioPlaybackCallback() {
            @Override
            public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> configs) {
                super.onPlaybackConfigChanged(configs);
                Log.e(TAG, "onPlaybackConfigChanged");

                OBJECT(TAG, configs);

                for(AudioPlaybackConfiguration config : configs) {
                    OBJECT(TAG, config.describeContents(), config.getAudioAttributes());
                }

                for(AudioPlaybackConfiguration config : configs) {
                    OBJECT(TAG, configs);
                }
            }
        }, null);

       view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick");

                NavHostFragment.findNavController(FirstFragment3.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }


    /*
     * HELPERS
     */
    private void bindToService(Class<?> service) {
        Log.e(TAG, "bindToService");

        Intent intent = new Intent(this.getContext(), service);
        this.getContext().bindService(intent, this, 0);
    }

    private void startService(Class<?> service) {
        Log.e(TAG, "startService");

        Intent intent = new Intent(this.getContext(), service);
        this.getContext().startService(intent);
    }

    private void setVolume(int percent) {
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volume = (int) (current + 0.01 * percent * max);

        if(volume > max) volume = max;
        if(volume <   0) volume = 0;

        OBJECT(TAG, "audio", current, max, volume);

        Log.e(TAG, "Set volume to " + volume);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    /*
     * VIEW ON CLICK LISTENER
     *
     */
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick");

        OBJECT(TAG, v);

        switch(v.getId()){
            case R.id.play:
                if(mediaController != null) mediaController.play();
                break;

            case R.id.pause:
                if(mediaController != null) mediaController.pause();
                break;

            case R.id.volup:
                setVolume(+10);
                break;

            case R.id.voldown:
                setVolume(-10);
                break;

        }
    }

    /*
     * SERVICE CONNECTION
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.e(TAG, "onServiceConnected");

        OBJECT(TAG, name, binder);
//service = ((MyMediabrowserService.MyBinder)binder).getService();
//        mediaController = new MyMediaController(this.getContext(), service.getToken());

        OBJECT(TAG, name, service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "onServiceDisconnected");
    }
}
