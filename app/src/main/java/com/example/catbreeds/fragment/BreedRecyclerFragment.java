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
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.catbreeds.Database;
import com.example.catbreeds.R;
import com.example.catbreeds.adapter.BreedAdapter;
import com.example.catbreeds.model.BreedList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BreedRecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private BreedAdapter breedAdapter =new BreedAdapter();
    private SearchView searchView;

    public BreedRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breed_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        getCatList();

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                breedAdapter.setData(Database.filterList(newText));
                recyclerView.setAdapter(breedAdapter);

                return false;
            }
        });

        return view;
    }

    public void getCatList(){
        final String url = "https://api.thecatapi.com/v1/breeds";

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                BreedList[] objectArray = gson.fromJson(response, BreedList[].class);
                List breedList = Arrays.asList(objectArray);
                Database.saveBreedList(breedList);
                breedAdapter.setData(breedList);
                recyclerView.setAdapter(breedAdapter);
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }
}
