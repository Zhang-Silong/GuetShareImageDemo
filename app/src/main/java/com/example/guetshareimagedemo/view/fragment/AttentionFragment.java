package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.utils.StatusBarUtil;

public class AttentionFragment extends Fragment {

    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        toolbar = view.findViewById(R.id.attention_toolbar);
        StatusBarUtil.transparencyBar(getActivity());
        StatusBarUtil.StatusBarLightMode(getActivity());
        return view;
    }
}