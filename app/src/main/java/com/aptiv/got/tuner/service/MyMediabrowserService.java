package com.aptiv.got.tuner.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aptiv.got.tuner.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.END;
import static com.aptiv.got.tuner.utils.Debug.METHOD;
import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class MyMediabrowserService extends MediaBrowserService {
    private static final String TAG = MyMediabrowserService.class.getSimpleName();

    private MediaSession mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();

        METHOD(TAG, "onCreate");

        //RadioManager radio = (RadioManager)getSystemService(Context.RADIO_SERVICE);

        mediaSession = new MediaSession(this, "ddjohn");
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS|MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        PlaybackState.Builder stateBuilder = new PlaybackState.Builder().setActions(PlaybackState.ACTION_PLAY|PlaybackState.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(stateBuilder.build());

        MediaController controller = mediaSession.getController();
        controller.registerCallback(new MediaController.Callback() {

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
                                    }
        );

        final MediaPlayer player = new MediaPlayer();

        mediaSession.setCallback(new MediaSession.Callback() {

            @Override
            public void onPause() {
                super.onPause();

                METHOD(TAG, "pause");

                player.pause();

                END();
            }

            @Override
            public void onPlay() {
                super.onPlay();

                METHOD(TAG, "onPlay");

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

        AudioAttributes attributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build();
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

        List<MediaBrowser.MediaItem> mediaItems = new ArrayList<>();

        if ("root".equals(parentMediaId)) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }

        END();

        result.sendResult(mediaItems);
    }
}
