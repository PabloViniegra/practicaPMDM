package com.example.practicapmdm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicapmdm.R;
import com.example.practicapmdm.controllers.FileControllers;
import com.example.practicapmdm.impl.ViewAdapter;
import com.example.practicapmdm.impl.ViewAdapterSports;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;

import lombok.NonNull;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;

public class ActivityViewAdapter extends AppCompatActivity {
    public final String TAG = getClass().getName();
    private ViewAdapter mViewAdapter;
    private ListView mViewList = null;
    private ListView mViewSports = null;
    private Pool poolclick;
    private FileControllers fileControllers;
    private ViewAdapterSports mViewAdapterSports;
    private ArrayList<Pool> pools = new ArrayList<>();
    private ArrayList<Pool> sports = new ArrayList<>();
    private Button btnLike = null;
    public ArrayList<Pool> myFavoritePools = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adapter);
        Bundle bundle = getIntent().getExtras();
        pools = bundle.getParcelableArrayList("LIST");
        Log.d(TAG, "Piscinas que he recibido del HomeActivity: " + pools.size());
        for (Pool pool : pools) {
            Log.d(TAG, "He entrado en el array de piscinas");
            Log.d(TAG, pool.getName());
            Log.d(TAG, String.valueOf(pool.getLocation().getLatitude()));
            Log.d(TAG, String.valueOf(pool.getLocation().getLongitude()));
        }
        sports = bundle.getParcelableArrayList("LIST2");
        Log.d(TAG, "Polideportivos que he recibido del Home Activity: " + sports.size());
        for (Pool pool : sports) {
            Log.d(TAG, "He entrado en el array de sports en el activityvieweradapter");
            Log.d(TAG, pool.getName());
            Log.d(TAG, String.valueOf(pool.getLocation().getLatitude()));
            Log.d(TAG, String.valueOf(pool.getLocation().getLongitude()));
        }


        mViewList = findViewById(R.id.myListView);
        mViewSports = findViewById(R.id.mySportsList);
        Log.d(TAG, "Antes del evento click en el list view");
        mViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Log.d(TAG, "dentro del evento");
                Toast.makeText(ActivityViewAdapter.this, "Click en el ListView", Toast.LENGTH_SHORT).show();
                Intent locationSchoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
                locationSchoolIntent.putExtra(NAME, pools.get(i).getName());
                locationSchoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
                locationSchoolIntent.putExtra(LATITUDE, pools.get(i).getLocation().getLatitude());
                locationSchoolIntent.putExtra(LONGITUDE, pools.get(i).getLocation().getLongitude());
                poolclick = new Pool(pools.get(i).getName(), new Location(pools.get(i).getLocation().getLatitude(), pools.get(i).getLocation().getLongitude()));
                btnLike = findViewById(R.id.btnFav);
                btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "activado el botón del Like");
                        fileControllers = new FileControllers();
                        for (Pool favourite : InitHomeActivity.favourites) {
                            if (!favourite.getName().equalsIgnoreCase(poolclick.getName())) {
                                fileControllers.fileWriter(poolclick);
                            } else {
                                Toast.makeText(ActivityViewAdapter.this, "Esa Piscina/Polideportivo ya está en favoritos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                startActivity(locationSchoolIntent);

            }
        });


        Log.d(TAG, "Tamaño del array de piscinas antes de entrar en el viewAdapter: " + pools.size());
        Log.d(TAG, "Tamaño del array de polideportivos antes de entrar en el viewAdapter: " + sports.size());
        mViewAdapter = new ViewAdapter(getApplicationContext(), pools);
        mViewList.setAdapter(mViewAdapter);
        mViewAdapter.notifyDataSetChanged();

        if (!sports.get(0).getName().equalsIgnoreCase("undefined")) {
            mViewAdapterSports = new ViewAdapterSports(getApplicationContext(), sports);
            mViewSports.setAdapter(mViewAdapterSports);
            mViewAdapterSports.notifyDataSetChanged();
        } else {
            Log.d(TAG, "El arraylist de sports tiene un polideportivo indefinido");
        }


    }


}