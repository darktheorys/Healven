package com.burak.healven.main_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.burak.healven.R;
import com.burak.healven.helpful.RecyclerViewAdapterForMain;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private ArrayList<Integer> imagePaths= new ArrayList<>();
    private ArrayList<String> itemNames1 = new ArrayList<>();
    private ArrayList<String> itemNames2 = new ArrayList<>();
    private ArrayList<String> descriptions1 = new ArrayList<>();
    private ArrayList<String> descriptions2 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        fillLists(v);
        return v;
    }


    private void fillLists(View v){
        imagePaths.add(R.drawable.ic_for_sale_24dp);
        itemNames1.add("For Sale");
        itemNames2.add(">");
        descriptions1.add("Locals transact here");
        descriptions2.add("");

        imagePaths.add(R.drawable.ic_social_media_24dp);
        itemNames1.add("Social Media");
        itemNames2.add(">");
        descriptions1.add("Learn your environment here");
        descriptions2.add("");

        imagePaths.add(R.drawable.ic_others_24dp);
        itemNames1.add("Others");
        itemNames2.add(">");
        descriptions1.add("Other useful things here");
        descriptions2.add("");

        initRecyclerView(v);

    }

    private void initRecyclerView(View v){
        RecyclerView recyclerView = v.findViewById(R.id.recyclerViewMenu);
        RecyclerViewAdapterForMain recyclerViewAdapter = new RecyclerViewAdapterForMain(descriptions1,descriptions2, itemNames1, itemNames2, imagePaths, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
