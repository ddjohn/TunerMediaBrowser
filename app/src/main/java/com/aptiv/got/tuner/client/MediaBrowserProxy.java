package com.aptiv.got.tuner.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aptiv.got.tuner.R;
import com.aptiv.got.tuner.service.MyMediabrowserService;

import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.END;
import static com.aptiv.got.tuner.utils.Debug.METHOD;
import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class MediaBrowserProxy  {
    private static final String TAG = MediaBrowserProxy.class.getSimpleName();

    private MediaBrowser mediaBrowser;
    private MediaController mediaController;

    public MediaBrowserProxy(final Context ctx) {
        METHOD(TAG, "MediaBrowserProxy");

        MediaBrowser.ConnectionCallback callback = new MediaBrowser.ConnectionCallback() {

            @Override
            public void onConnected() {
                super.onConnected();

                METHOD(TAG, "onConnected");

                mediaController = new MediaController(ctx, mediaBrowser.getSessionToken());

                mediaController.registerCallback(new MediaController.Callback() {

                    @Override
                    public void onPlaybackStateChanged(@Nullable PlaybackState state) {
                        super.onPlaybackStateChanged(state);

                        Log.e(TAG, "state=" + state.getActions());

                    }

                    @Override
                    public void onAudioInfoChanged(MediaController.PlaybackInfo info) {
                        super.onAudioInfoChanged(info);

                        Log.e(TAG, "volume=" + info.getCurrentVolume());
                    }


                });


                String root = mediaBrowser.getRoot();
                OBJECT(TAG, root);

                mediaBrowser.subscribe(root, new MediaBrowser.SubscriptionCallback() {

                    @Override
                    public void onChildrenLoaded(@NonNull String parentId, List<MediaBrowser.MediaItem> children) {
                        METHOD(TAG, "onChildrenLoaded");

                        if (children == null || children.isEmpty()) {
                            return;
                        }
                        MediaBrowser.MediaItem firstItem = children.get(0);
                        // Play the first item?
                        // Probably should check firstItem.isPlayable()
                        mediaController
                                .getTransportControls()
                                .playFromMediaId(firstItem.getMediaId(), null);

                        END();
                    }
                });

                END();
            }
        };

        mediaBrowser = new MediaBrowser(ctx, new ComponentName(ctx, MyMediabrowserService.class), callback, null);

        END();
    }

    public void connect() {
        METHOD(TAG, "connect");

        mediaBrowser.connect();

        END();
    }

    public void play() {
        METHOD(TAG, "play");

        if (mediaController != null) mediaController.getTransportControls().play();

        END();
    }

    public void pause() {
        METHOD(TAG, "pause");

        if (mediaController != null) mediaController.getTransportControls().pause();

        END();
    }

    public void volup() {
        METHOD(TAG, "volup");

        if (mediaController != null) mediaController.adjustVolume(AudioManager.ADJUST_RAISE, 0);

        END();
    }

    public void voldown() {
        METHOD(TAG, "voldown");

        if (mediaController != null) mediaController.adjustVolume(AudioManager.ADJUST_LOWER, 0);

        END();
    }
}
