package com.example.practicapmdm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.practicapmdm.R;
import com.example.practicapmdm.impl.FavouritesAdapter;
import com.example.practicapmdm.models.Pool;

import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.activities.InitHomeActivity.favourites;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;

public class FavouritesActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private ListView mFavouritesList = null;
    private FavouritesAdapter mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Log.d(TAG, "Contenido del array de favoritos en FavouritesActivity.java:");
        for (Pool favourite : InitHomeActivity.favourites) {
            Log.d(TAG, "Nombre: " + favourite.getName());
            Log.d(TAG, "Latitud: " + favourite.getLocation().getLatitude());
            Log.d(TAG, "Longitud: " + favourite.getLocation().getLongitude());
            Log.d(TAG, "-----------------------------------------------");

        }
        mFavouritesList = findViewById(R.id.myFavouritesList);
        mFavouritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent locationIntent = new Intent(getApplicationContext(), MapsActivity.class);
                locationIntent.putExtra(NAME, favourites.get(position).getName());
                locationIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
                locationIntent.putExtra(LATITUDE, favourites.get(position).getLocation().getLatitude());
                locationIntent.putExtra(LONGITUDE, favourites.get(position).getLocation().getLongitude());
                startActivity(locationIntent);
            }
        });
        mAdapter = new FavouritesAdapter(getApplicationContext(), favourites);
        mFavouritesList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}