package com.aptiv.got.tuner.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.aptiv.got.tuner.R;

import java.util.ArrayList;
import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class MyMediabrowserService2 extends MediaBrowserService {
    private static final String TAG = MyMediabrowserService2.class.getSimpleName();
    private static final String TOKEN = "ddjohn";

    private MyMediaSession session;

    public MediaSession.Token getToken() {
        Log.e(TAG, "getToken");

        return session.getSessionToken();
    }

    public class MyBinder extends Binder {
        public MyMediabrowserService2 getService() {
            Log.e(TAG, "getService");

            return MyMediabrowserService2.this;
        }
    }

    public MyMediabrowserService2() {
        Log.e(TAG, "MyService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate");

        AudioManager manager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        session = new MyMediaSession(this, MyMediabrowserService2.TOKEN);
        MyMediaController controller = new MyMediaController(this, session.getSessionToken());
        MyPlaybackState state = new MyPlaybackState();

        session.setPlaybackState(state.getPlaybackState());

        notify2();
    }

    private void notify2() {
        int importance = NotificationManager.IMPORTANCE_HIGH;

        /*
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext(), "");
        builder.setContentTitle("title").setContentText("content").setSubText("desc")

                    //icon
                // Enable launching the player by clicking the notification
               // .setContentIntent(controller.getSessionActivity())

                // Stop the service when the notification is swiped away
               // .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                //        PlaybackStateCompat.ACTION_STOP))

                // Make the transport controls visible on the lockscreen
                .setVisibility(Notification.VISIBILITY_PUBLIC)

                // Add an app icon and set its accent color
                // Be careful about the color
                //.setSmallIcon(R.drawable.notification_icon)
                //.setColor(this.getApplicationContext().getColor(this.getApplicationContext(), R.color.colorPrimaryDark))

                // Add a pause button
                //.addAction(new NotificationCompat.Action(
                //        R.drawable.pause, getString(R.string.pause),
                //        MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                //                PlaybackStateCompat.ACTION_PLAY_PAUSE)))

                // Take advantage of MediaStyle features
                //.setStyle(new MediaStyle()
                //        .setMediaSession(mediaSession.getSessionToken())
                //        .setShowActionsInCompactView(0)

                        // Add a cancel button
                //        .setShowCancelButton(true)
                //        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                //                PlaybackStateCompat.ACTION_STOP)));
;

         */

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("ddjohn-id", "ddjohn-name", NotificationManager.IMPORTANCE_HIGH);
        //channel.lightColor = Color.BLUE;
        //channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE;

        manager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ddjohn-id")
                .setContentTitle("title")
                .setContentText("ctext")
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                //.setLights(
                //        SessionAlarmService.NOTIFICATION_ARGB_COLOR,
                //        SessionAlarmService.NOTIFICATION_LED_ON_MS,
                //        SessionAlarmService.NOTIFICATION_LED_OFF_MS)
                .setSmallIcon(R.drawable.ic_launcher_background)
                //.setContentIntent(resultPendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);
        // Display the notification and place the service in the foreground
        this.startForeground(1, builder.build());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int id) {
        Log.e(TAG, "onStartCommand");

        OBJECT(TAG, intent, flags, id);

        return super.onStartCommand(intent, flags, id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");

        OBJECT(TAG, intent);

        return new MyBinder();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String packageName, int clientUid, @Nullable Bundle rootHints) {
        Log.e(TAG, "onGetRoot");

        OBJECT(TAG, packageName, clientUid, rootHints);

        if (packageName.contains("tuner")) {
            return new BrowserRoot("root", null);
        } else {
            return new BrowserRoot("null", null);
        }
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {
        Log.e(TAG, "onLoadChildren");


        if (TextUtils.equals("empty", parentId)) {
            result.sendResult(null);
            return;
        }

        List<MediaBrowser.MediaItem> mediaItems = new ArrayList<>();

        if ("root".equals(parentId)) {
            //MediaBrowser.MediaItem item = new MediaBrowser.MediaItem(MediaDescription.BT_FOLDER_TYPE_ARTISTS, 0);


            // mediaItems.add(new MediaBrowser.MediaItem("hello"));
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems);
    }
}
