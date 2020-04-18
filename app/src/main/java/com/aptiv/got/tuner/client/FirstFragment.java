package com.aptiv.got.tuner.client;

import android.content.ComponentName;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aptiv.got.tuner.R;
import com.aptiv.got.tuner.service.MyMediabrowserService;

import java.util.ArrayList;
import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.END;
import static com.aptiv.got.tuner.utils.Debug.METHOD;
import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class FirstFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = FirstFragment.class.getSimpleName();

    private MediaController mediaController = null;
    private MediaBrowser       mediaBrowser = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        METHOD(TAG, "onCreateView");

        END();

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle instance) {
        super.onViewCreated(view, instance);

        METHOD(TAG, "onViewCreated");

        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.play).setOnClickListener(this);
        //view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.volup).setOnClickListener(this);
        view.findViewById(R.id.voldown).setOnClickListener(this);

        ListView listView = (ListView)view.findViewById(R.id.list);
        final List<Info> apps = new ArrayList<Info>();
        apps.add(new Info("a", "b"));
        listView.setAdapter(new Adapter(this.getContext(), apps));

        connectToService();

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                METHOD(TAG, "onClick");

                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);

                END();
            }
        });

        END();
    }

    private void connectToService() {
        METHOD(TAG, "connectToService");

        mediaBrowser = new MediaBrowser(this.getContext(), new ComponentName(this.getActivity(), MyMediabrowserService.class), new MediaBrowser.ConnectionCallback() {

            @Override
            public void onConnected() {
                METHOD(TAG, "onConnected");

                mediaController = new MediaController(FirstFragment.this.getContext(), mediaBrowser.getSessionToken());
                mediaController.registerCallback(new MediaController.Callback() {

                    @Override
                    public void onPlaybackStateChanged(@Nullable PlaybackState state) {
                        super.onPlaybackStateChanged(state);

                        METHOD(TAG, "onPlaybackStateChanged");

                        END();
                    }

                    @Override
                    public void onMetadataChanged(@Nullable MediaMetadata metadata) {
                        super.onMetadataChanged(metadata);

                        METHOD(TAG, "onMetadataChanged");

                        END();
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

            @Override
            public void onConnectionSuspended() {
                METHOD(TAG, "onConnectionSuspended");

                END();
            }

            @Override
            public void onConnectionFailed() {
                METHOD(TAG, "onConnectionFailed");

                END();
            }
        }, null);
        mediaBrowser.connect();

        END();
    }

    /*
     * VIEW ON CLICK LISTENER
     *
     */
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick");

        OBJECT(TAG, v);

        switch (v.getId()) {
            case R.id.play:
                if (mediaController != null) mediaController.getTransportControls().play();
                break;

            case R.id.pause:
                if (mediaController != null) mediaController.getTransportControls().pause();
                break;
/*
            case R.id.stop:
                OBJECT(TAG, mediaBrowser.getRoot());
                if (mediaController != null) mediaController.getTransportControls().rewind();
                break;
*/
            case R.id.volup:
                if (mediaController != null) mediaController.adjustVolume(AudioManager.ADJUST_RAISE, 0);
                break;

            case R.id.voldown:
                if (mediaController != null) mediaController.adjustVolume(AudioManager.ADJUST_LOWER, 0);
                break;
        }
    }
}