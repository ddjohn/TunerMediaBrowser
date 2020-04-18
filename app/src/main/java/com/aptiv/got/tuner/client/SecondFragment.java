package com.aptiv.got.tuner.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.aptiv.got.tuner.R;

import static com.aptiv.got.tuner.utils.Debug.OBJECT;

public class SecondFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SecondFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle instance) {
        super.onViewCreated(view, instance);

        OBJECT(TAG, view, instance);

        /*
        view.findViewById(R.id.pause  ).setOnClickListener(this);
        view.findViewById(R.id.play   ).setOnClickListener(this);
        view.findViewById(R.id.stop   ).setOnClickListener(this);
        view.findViewById(R.id.volup  ).setOnClickListener(this);
        view.findViewById(R.id.voldown).setOnClickListener(this);
*/
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick()");

        OBJECT(TAG, v);
    }
}
