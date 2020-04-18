package com.aptiv.got.tuner.media;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aptiv.got.tuner.R;

public class FirstFragment2 extends Fragment {
    private static final String TAG = FirstFragment2.class.getSimpleName();
    private MyProxy proxy = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*
       Log.e(TAG, "onViewCreated");

        view.findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "pause");
                proxy.pause();
            }
        });

        view.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "play");
                proxy.play();
            }
        });

        view.findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "stop");
                // proxy.stop();
            }
        });

        view.findViewById(R.id.voldown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "voldown");
                proxy.volDown();
            }
        });

        view.findViewById(R.id.volup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "volup");
                proxy.volUp();
            }
        });
*/
       view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick");

                NavHostFragment.findNavController(FirstFragment2.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

  //      proxy = new MyProxy(this.getContext());
    }
}
