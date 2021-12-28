package com.example.guetshareimagedemo.view.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guetshareimagedemo.R;
import com.example.guetshareimagedemo.utils.StatusBarUtil;

public class AttentionFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView rvAttentionHor;
    private RecyclerView rvAttentionVer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StatusBarUtil.transparencyBar(getActivity());
        StatusBarUtil.StatusBarLightMode(getActivity());
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.attention_toolbar);
        rvAttentionHor = view.findViewById(R.id.rv_attention_hor);
        rvAttentionVer = view.findViewById(R.id.rv_attention_ver);
    }
}