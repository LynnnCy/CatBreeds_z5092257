package com.example.catbreeds.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catbreeds.Database;
import com.example.catbreeds.R;
import com.example.catbreeds.adapter.BreedAdapter;
import com.example.catbreeds.adapter.FavouriteAdapter;


public class FavouriteRecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    public static FavouriteAdapter favouriteAdapter = new FavouriteAdapter();

    public FavouriteRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_main2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        favouriteAdapter.setData(Database.favouriteBreedList);
        recyclerView.setAdapter(favouriteAdapter);

        return view;
    }
}
