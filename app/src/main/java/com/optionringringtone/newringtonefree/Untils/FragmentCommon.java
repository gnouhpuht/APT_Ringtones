package com.optionringringtone.newringtonefree.Untils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class FragmentCommon extends Fragment {
    public Activity activity;

    public abstract int getLayout();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
        View view = LayoutInflater.from(activity).inflate(getLayout(), container, false);
        init(view);
        return view;
    }

    public abstract void init(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
