package com.burak.healven.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.burak.healven.R;
import com.burak.healven.helpful.Profile;

public class AccountFragment extends Fragment {

    Profile user;
    public AccountFragment(Profile user) {
        this.user = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        TextView tw = view.findViewById(R.id.account_tw_);
        tw.setText(user.name + " " + user.surname + " - " +user.mail);
        return view;
    }
}
