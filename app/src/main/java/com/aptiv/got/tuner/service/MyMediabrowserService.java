package com.aptiv.got.tuner.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.android.server.broadcastradio.BroadcastRadioService;
import com.aptiv.got.tuner.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.END;
import static com.aptiv.got.tuner.utils.Debug.METHOD;
import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class MyMediabrowserService extends MediaBrowserService implements AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = MyMediabrowserService.class.getSimpleName();

    private MediaSession mediaSession;
    private AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();

        METHOD(TAG, "onCreate");

        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        //android.hardware.broadcastradio.V2_0.IBroadcastRadio.Proxy
       // RadioManager radio = (RadioManager)getSystemService(Context.RADIO_SERVICE);
      //  OBJECT(TAG, radio);
        //BroadcastRadioService service = (BroadcastRadioService)this.getSystemService(/*Context.RADIO_SERVICE*/ "radio_service");

        OBJECT(TAG, Context.ALARM_SERVICE, Context.WIFI_RTT_RANGING_SERVICE);

        mediaSession = new MediaSession(this, "ddjohn");
        mediaSession.setFlags(
                MediaSession.FLAG_HANDLES_MEDIA_BUTTONS|
                        MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        PlaybackState.Builder stateBuilder = new PlaybackState.Builder().setActions(
                PlaybackState.ACTION_PLAY|
                        PlaybackState.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        MediaController controller = mediaSession.getController();

        final MediaPlayer player = new MediaPlayer();

        final AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build();

        final AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
               .setAudioAttributes(attributes)
                .setOnAudioFocusChangeListener(this)
               .build();

        mediaSession.setCallback(new MediaSession.Callback() {

            @Override
            public void onPause() {
                super.onPause();

                METHOD(TAG, "pause");

                audioManager.abandonAudioFocusRequest(audioFocusRequest);
                player.pause();

                END();
            }

            @Override
            public void onPlay() {
                super.onPlay();

                METHOD(TAG, "onPlay");

                if(audioManager.requestAudioFocus(audioFocusRequest) != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    return;

                player.start();

                MediaController controller = mediaSession.getController();

                MediaMetadata mediaMetadata = controller.getMetadata();
                //MediaDescription description = mediaMetadata.getDescription();


                NotificationChannel channel = new NotificationChannel("ddjohn-id", "ddjohn-name", NotificationManager.IMPORTANCE_HIGH);
                //channel.lightColor = Color.BLUE;
                //channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE;

                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);


                Notification.Builder builder = new Notification.Builder(MyMediabrowserService.this, "ddjohn-id");

                builder
                        // Add the metadata for the currently playing track
                        //.setContentTitle(description.getTitle())
                        //.setContentText(description.getSubtitle())
                        //.setSubText(description.getDescription())
                        //.setLargeIcon(description.getIconBitmap())
                        .setContentTitle("title")
                        .setContentText("subtitle")
                        .setSubText("desc")
                        // .setLargeIcon(description.getIconBitmap())

                        // Enable launching the player by clicking the notification
                        .setContentIntent(controller.getSessionActivity())

                        // Stop the service when the notification is swiped away
                        //.setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackState.ACTION_STOP))

                        // Make the transport controls visible on the lockscreen
                        .setVisibility(Notification.VISIBILITY_PUBLIC)

                        // Add an app icon and set its accent color
                        // Be careful about the color
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        //.setColor(Context.getColor(this, R.color.colorPrimaryDark))

                        // Add a pause button
                        //.addAction(new NotificationCompat.Action(R.drawable.pause, getString(R.string.pause), MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackState.ACTION_PLAY_PAUSE)))

                        // Take advantage of MediaStyle features
                        .setStyle(new Notification.MediaStyle()
                                .setMediaSession(mediaSession.getSessionToken())
                                .setShowActionsInCompactView(0)
                        );

                // Add a cancel button
                // .setShowCancelButton(true)
                // .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackState.ACTION_STOP)));

                startForeground(101, builder.build());

                END();
            }
        });
        this.setSessionToken(mediaSession.getSessionToken());

        //player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setAudioAttributes(attributes);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e(TAG, "onPrepared");
                OBJECT(TAG, mp);
            }
        });
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.e(TAG, "onBufferingUpdate");
                OBJECT(TAG, mp, percent);
            }
        });

        try {
            player.setDataSource("https://dl.espressif.com/dl/audio/ff-16b-2c-44100hz.m4a");
            //player.prepareAsync();
            player.prepare();
            player.start();
        }
        catch(IOException e) {
            Log.e(TAG, e.toString());
        }
        END();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        METHOD(TAG, "onGetRoot");

        END();

        return new BrowserRoot("root", null);
    }

    @Override
    public void onLoadChildren(@NonNull String parentMediaId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {
        METHOD(TAG, "onLoadChildren");

        OBJECT(TAG, parentMediaId, result);

        List<MediaBrowser.MediaItem> items = new ArrayList<>();


        if ("root".equals(items)) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
          //  MediaBrowser.MediaItem menu = new MediaBrowser.MediaItem(
            //        new MediaDescription("test", ""), 0);
          //  items.add(menu);
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }

        END();

        result.sendResult(items);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        METHOD(TAG,"onAudioFocusChange");

        OBJECT(TAG, focusChange);

        END();
    }
}
