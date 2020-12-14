package com.example.practicapmdm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.practicapmdm.R;
import com.example.practicapmdm.constants.Constants;
import com.example.practicapmdm.controllers.FileController;
import com.example.practicapmdm.impl.FavouritesAdapter;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;
import static com.example.practicapmdm.constants.Constants.favourites;

public class FavouritesActivity extends AppCompatActivity {
    private final String TAG = getClass().getName();
    private ListView mFavouritesList = null;
    private FavouritesAdapter mAdapter = null;
    private FileController fileControllers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Log.d(TAG, "Contenido del array de favoritos en FavouritesActivity.java:");
        for (Pool favourite : Constants.favourites) {
            Log.d(TAG, "Nombre: " + favourite.getName());
            Log.d(TAG, "Latitud: " + favourite.getLocation().getLatitude());
            Log.d(TAG, "Longitud: " + favourite.getLocation().getLongitude());
            Log.d(TAG, "-----------------------------------------------");

        }
        mFavouritesList = findViewById(R.id.myFavouritesList);
        mFavouritesList.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 1, 0, "Quitar de favoritos");
        });
        mFavouritesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent locationIntent = new Intent(getApplicationContext(), MapsActivity.class);
                locationIntent.putExtra(NAME, Constants.favourites.get(position).getName());
                locationIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
                locationIntent.putExtra(LATITUDE, Constants.favourites.get(position).getLocation().getLatitude());
                locationIntent.putExtra(LONGITUDE, Constants.favourites.get(position).getLocation().getLongitude());
                startActivity(locationIntent);
            }
        });
        mAdapter = new FavouritesAdapter(getApplicationContext(), Constants.favourites);
        mFavouritesList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 1) {
            fileControllers = new FileController();
            Pool poolclick = new Pool(favourites.get(info.position).getName(), new Location(favourites.get(info.position).getLocation().getLatitude(), favourites.get(info.position).getLocation().getLongitude()));
            for (int i = 0; i < Constants.favourites.size(); i++) {
                if (Constants.favourites.get(i).equals(poolclick)) {
                    Constants.favourites.remove(i);
                }
            }
            Log.d(TAG, "Contenido del Array de favoritos para borrar: " + Constants.favourites.toString());
            fileControllers.fileFavWriter(Constants.favourites, getApplicationContext());
            Intent refresh = new Intent(this, FavouritesActivity.class);
            startActivity(refresh);
            finish();
        }
        return true;
    }
}