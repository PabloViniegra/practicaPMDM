package com.example.practicapmdm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.practicapmdm.R;
import com.example.practicapmdm.impl.ViewAdapter;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.constants.Constants.*;

public class ActivityViewAdapter extends AppCompatActivity {
    public final String TAG = getClass().getName();
    private ViewAdapter mViewAdapter;
    private ListView mViewList = null;
    private ArrayList<Pool> pools;
    //private Button btnLike = null;
    public ArrayList<Pool> myFavoritePools = new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adapter);
        /*btnLike = findViewById(R.id.btnFav);*/
        Intent getIntent = getIntent();
        Bundle bundle = getIntent().getExtras();
        pools = bundle.getParcelableArrayList("LIST");
        Log.d(TAG, "Antes de recibir el intent de la lista");
        for (Pool pool : pools) {
            Log.d(TAG,"He entrado en el array de piscinas");
            Log.d(TAG, pool.getName());
            Log.d(TAG, String.valueOf(pool.getLocation().getLatitude()));
            Log.d(TAG, String.valueOf(pool.getLocation().getLongitude()));
        }

        mViewList = findViewById(R.id.myListView);
        Log.d(TAG, "Antes del evento click en el list view");
        mViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Log.d(TAG, "dentro del evento");
                Toast.makeText(ActivityViewAdapter.this, "Click en el ListView", Toast.LENGTH_SHORT).show();
                Intent locationSchoolIntent = new Intent(getApplicationContext(),MapsActivity.class);
                locationSchoolIntent.putExtra(NAME,pools.get(i).getName());
                locationSchoolIntent.putExtra(DESCRIPTION_KEY,DESCRIPTION);
                locationSchoolIntent.putExtra(LATITUDE,pools.get(i).getLocation().getLatitude());
                locationSchoolIntent.putExtra(LONGITUDE,pools.get(i).getLocation().getLongitude());
                startActivity(locationSchoolIntent);
            }
        });

        mViewAdapter = new ViewAdapter(getApplicationContext(),pools);
        mViewList.setAdapter(mViewAdapter);
        mViewAdapter.notifyDataSetChanged();

        /*btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "activado el botón del Like");
            }
        });*/


    }
    

    private void sharedMyPreferences(Pool pool) {
        SharedPreferences  mysharedpreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mysharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(pool);
        prefsEditor.putString("POOL", json);
        prefsEditor.commit();
    }

    private void showMyPreferences() {
        SharedPreferences  mysharedpreferences = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mysharedpreferences.getString("POOL","");
        Pool pool = gson.fromJson(json, Pool.class);
        myFavoritePools.add(pool);
    }

}