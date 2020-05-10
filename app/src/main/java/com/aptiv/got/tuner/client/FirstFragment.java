package com.aptiv.got.tuner.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aptiv.got.tuner.R;
import java.util.ArrayList;
import java.util.List;

import static com.aptiv.got.tuner.utils.Debug.END;
import static com.aptiv.got.tuner.utils.Debug.METHOD;
import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class FirstFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = FirstFragment.class.getSimpleName();
    private MediaBrowserProxy proxy = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        METHOD(TAG, "onCreateView");

        proxy = new MediaBrowserProxy(this.getContext());

        END();

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle instance) {
        super.onViewCreated(view, instance);

        METHOD(TAG, "onViewCreated");

        proxy.connect();

        view.findViewById(R.id.pause).setOnClickListener(this);
        view.findViewById(R.id.play).setOnClickListener(this);
        //view.findViewById(R.id.stop).setOnClickListener(this);
        view.findViewById(R.id.volup).setOnClickListener(this);
        view.findViewById(R.id.voldown).setOnClickListener(this);

        ListView listView = (ListView)view.findViewById(R.id.list);
        final List<Info> apps = new ArrayList<Info>();
        apps.add(new Info("a", "b"));
        listView.setAdapter(new Adapter(this.getContext(), apps));

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
                proxy.play();
                break;

            case R.id.pause:
                proxy.pause();
                break;

            case R.id.volup:
                proxy.volup();
                break;

            case R.id.voldown:
                proxy.voldown();
                break;
        }
    }
}