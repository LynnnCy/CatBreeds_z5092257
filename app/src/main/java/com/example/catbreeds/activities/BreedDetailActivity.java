package com.example.catbreeds.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.catbreeds.Database;
import com.example.catbreeds.R;
import com.example.catbreeds.fragment.FavouriteRecyclerFragment;
import com.example.catbreeds.model.BreedList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class BreedDetailActivity extends AppCompatActivity {

    private TextView breedName;
    private TextView desrciption;
    private TextView origin;
    private TextView weight;
    private TextView temperament;
    private TextView lifeSpan;
    private TextView dogFriendliness;
    private TextView wikipedia;
    private ImageView breedImage;
    private String imageUrl;
    private ImageView favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_detail);

        Intent intent = getIntent();

        final String breedId = intent.getStringExtra("id");

        final BreedList breed = Database.getBreedById(breedId);

        breedName = findViewById(R.id.breed_name);
        desrciption = findViewById(R.id.description);
        origin = findViewById(R.id.origin);
        weight = findViewById(R.id.weight);
        temperament = findViewById(R.id.temperament);
        lifeSpan = findViewById(R.id.lifespan);
        dogFriendliness = findViewById(R.id.dog_friendliness);
        wikipedia = findViewById(R.id.link);
        breedImage = findViewById(R.id.breed_image);
        favourite = findViewById(R.id.imageFavourite);

        final String url = "https://api.thecatapi.com/v1/images/search?breed_id=" + breedId;

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    if(jsonObject.has("url")){
                        imageUrl = jsonObject.getString("url");
                        if(!imageUrl.equals("")){
                            Glide.with(getApplicationContext()).load(imageUrl).into(breedImage);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Request error message: " + error);
                Toast.makeText(getApplicationContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);



        breedName.setText(breed.getName());
        desrciption.setText(breed.getDescription());
        origin.setText("Origin:\n" + breed.getOrigin());
        weight.setText("Imperial:\n" + breed.getWeight().imperial + "\nMetric:\n" + breed.getWeight().getMetric());
        temperament.setText("Temperament:\n" + breed.getTemperament());
        lifeSpan.setText("Life Span:\n" + breed.getLifespan());
        dogFriendliness.setText("Dog friendliness:\n" + String.valueOf(breed.getDogfriendly()));
        wikipedia.setText(breed.getLink());

        if(Database.checkDuplicatedFavourite(breedId) > -1){
            favourite.setImageResource(R.drawable.favourite2);
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duplicate = Database.checkDuplicatedFavourite(breedId);
                if(duplicate == -1){
                    favourite.setImageResource(R.drawable.favourite2);
                    Database.favouriteBreedList.add(breed);
                    Toast.makeText(getApplicationContext(),"I love this cat :)", Toast.LENGTH_SHORT).show();
                    FavouriteRecyclerFragment.favouriteAdapter.notifyDataSetChanged();
                } else {
                    favourite.setImageResource(R.drawable.favourite);
                    Database.favouriteBreedList.remove(duplicate);
                    Toast.makeText(getApplicationContext(),"I don't love this cat anymore :(", Toast.LENGTH_SHORT).show();
                    FavouriteRecyclerFragment.favouriteAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
