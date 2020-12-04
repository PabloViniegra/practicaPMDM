


package com.example.practicapmdm.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicapmdm.R;
import com.example.practicapmdm.controllers.FileController;
import com.example.practicapmdm.impl.ViewAdapter;
import com.example.practicapmdm.impl.ViewAdapterSports;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.activities.InitHomeActivity.favourites;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;

public class ActivityViewAdapter extends AppCompatActivity {
    public final String TAG = getClass().getName();
    private ViewAdapter mViewAdapter;
    private ListView mViewList = null;
    private ListView mViewSports = null;
    private Pool poolclick;
    private FileController fileControllers;
    private ViewAdapterSports mViewAdapterSports;
    private ArrayList<Pool> pools = new ArrayList<>();
    private ArrayList<Pool> sports = new ArrayList<>();
    private Button btnLike = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adapter);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        pools = bundle.getParcelableArrayList("LIST");
        Log.d(TAG, "Piscinas que he recibido del HomeActivity: " + pools.size());
        for (Pool pool : pools) {
            Log.d(TAG, "He entrado en el array de piscinas");
            Log.d(TAG, pool.getName());
            Log.d(TAG, String.valueOf(pool.getLocation().getLatitude()));
            Log.d(TAG, String.valueOf(pool.getLocation().getLongitude()));
        }
        sports = bundle.getParcelableArrayList("LIST2");

        for (Pool pool : sports) {
            Log.d(TAG, "He entrado en el array de sports en el activityvieweradapter");
            Log.d(TAG, pool.getName());
            Log.d(TAG, String.valueOf(pool.getLocation().getLatitude()));
            Log.d(TAG, String.valueOf(pool.getLocation().getLongitude()));
        }


        mViewList = findViewById(R.id.myListView);
        mViewList.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 1, 0, "Ir al mapa");
            menu.add(0, 2, 0, "Agregar a favoritos");
            menu.add(0, 3, 0, "Quitar de favoritos");
        });
        mViewSports = findViewById(R.id.mySportsList);
        mViewSports.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 4, 0, "Ir al mapa");
            menu.add(0, 5, 0, "Agregar a favoritos");
            menu.add(0, 6, 0, "Quitar de favoritos");
        });
        Log.d(TAG, "Antes del evento click en el list view");
        mViewList.setOnItemClickListener((parent, view, i, id) -> {
            Log.d(TAG, "dentro del evento");
            Toast.makeText(ActivityViewAdapter.this, "Click en el ListView", Toast.LENGTH_SHORT).show();
            Intent locationSchoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
            locationSchoolIntent.putExtra(NAME, pools.get(i).getName());
            locationSchoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
            locationSchoolIntent.putExtra(LATITUDE, pools.get(i).getLocation().getLatitude());
            locationSchoolIntent.putExtra(LONGITUDE, pools.get(i).getLocation().getLongitude());
            poolclick = new Pool(pools.get(i).getName(), new Location(pools.get(i).getLocation().getLatitude(), pools.get(i).getLocation().getLongitude()));
            startActivity(locationSchoolIntent);

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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getItemId() == 1 || item.getItemId() == 4) {
            Log.d(TAG, "dentro del evento");
            Intent locationSchoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
            locationSchoolIntent.putExtra(NAME, pools.get(info.position).getName());
            locationSchoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
            locationSchoolIntent.putExtra(LATITUDE, pools.get(info.position).getLocation().getLatitude());
            locationSchoolIntent.putExtra(LONGITUDE, pools.get(info.position).getLocation().getLongitude());
            startActivity(locationSchoolIntent);
        } else if (item.getItemId() == 2 || item.getItemId() == 5) {
            Log.d(TAG, "activado el botón del Like");
            fileControllers = new FileController();
            Log.d(TAG, "Contenido del Array de favoritos: " + favourites.toString());
            favourites = fileControllers.fileFavReader();
            if (!InitHomeActivity.favourites.contains(poolclick)) {
                InitHomeActivity.favourites.add(poolclick);
                fileControllers.fileFavWriter(favourites, getApplicationContext());
            } else {
                Toast.makeText(this, "Esta piscina/polideportivo ya existe", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == 3 || item.getItemId() == 6) {
            Log.d(TAG, "Activado quitar de favoritos");
            for (int i = 0; i < favourites.size(); i++) {
                if (favourites.get(i).getName().equalsIgnoreCase(poolclick.getName())) {
                    favourites.remove(i);
                }
            }
            fileControllers.fileFavWriter(favourites, getApplicationContext());
            Log.d(TAG, "Contenido del Array de favoritos para borrar: " + favourites.toString());


        }
        return true;
    }
}