


package com.example.practicapmdm.activities;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicapmdm.R;
import com.example.practicapmdm.constants.Constants;
import com.example.practicapmdm.controllers.FileController;
import com.example.practicapmdm.impl.ViewAdapter;
import com.example.practicapmdm.impl.ViewAdapterSports;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;
import static com.example.practicapmdm.constants.Constants.favourites;

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
        pools = bundle.getParcelableArrayList("LIST");
        sports = bundle.getParcelableArrayList("LIST2");

        mViewList = findViewById(R.id.myListView);
        mViewList.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 1, 0, R.string.goMap);
            menu.add(0, 2, 0, R.string.goFavourites);
            menu.add(0, 3, 0, R.string.quiteFavourites);
        });
        mViewSports = findViewById(R.id.mySportsList);
        mViewSports.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 4, 0, R.string.goMap);
            menu.add(0, 5, 0, R.string.goFavourites);
            menu.add(0, 6, 0, R.string.quiteFavourites);
        });
        mViewList.setOnItemClickListener((parent, view, i, id) -> {
            Intent locationPoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
            locationPoolIntent.putExtra(NAME, pools.get(i).getName());
            locationPoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
            locationPoolIntent.putExtra(LATITUDE, pools.get(i).getLocation().getLatitude());
            locationPoolIntent.putExtra(LONGITUDE, pools.get(i).getLocation().getLongitude());
            poolclick = new Pool(pools.get(i).getName(), new Location(pools.get(i).getLocation().getLatitude(), pools.get(i).getLocation().getLongitude()));
            startActivity(locationPoolIntent);

        });
        mViewSports.setOnItemClickListener((parent, view, position, id) -> {
            Intent locationPoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
            locationPoolIntent.putExtra(NAME, sports.get(position).getName());
            locationPoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
            locationPoolIntent.putExtra(LATITUDE, sports.get(position).getLocation().getLatitude());
            locationPoolIntent.putExtra(LONGITUDE, sports.get(position).getLocation().getLongitude());
            poolclick = new Pool(sports.get(position).getName(), new Location(sports.get(position).getLocation().getLatitude(), sports.get(position).getLocation().getLongitude()));
            startActivity(locationPoolIntent);
        });

        mViewAdapter = new ViewAdapter(getApplicationContext(), pools);
        mViewList.setAdapter(mViewAdapter);
        mViewAdapter.notifyDataSetChanged();

        //Si el polideportivo que llega no es indefinido entonces adaptamos el View de los Polideportivos.
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
        switch (item.getItemId()) {
            case 1: {
                Intent locationPoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
                locationPoolIntent.putExtra(NAME, pools.get(info.position).getName());
                locationPoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
                locationPoolIntent.putExtra(LATITUDE, pools.get(info.position).getLocation().getLatitude());
                locationPoolIntent.putExtra(LONGITUDE, pools.get(info.position).getLocation().getLongitude());
                startActivity(locationPoolIntent);
                break;
            }
            case 2: {
                fileControllers = new FileController();
                int permissionCheckR = ContextCompat.checkSelfPermission(this
                        , Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheckR != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
                } else {
                    Constants.favourites = fileControllers.fileFavReader(getApplicationContext());
                }
                poolclick = new Pool(pools.get(info.position).getName(), new Location(pools.get(info.position).getLocation().getLatitude(), pools.get(info.position).getLocation().getLongitude()));

                //Solo si no está repetido es cuando lo agregamos a favoritos
                if (!Constants.favourites.contains(poolclick)) {
                    favourites.add(poolclick);
                    Toast.makeText(this, "Agregada a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Ya estaba en favoritos", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "Contenido del Array de favoritos: " + Constants.favourites.toString());
                fileControllers.fileFavWriter(favourites, getApplicationContext());
                break;
            }
            case 3:
                fileControllers = new FileController();
                poolclick = new Pool(pools.get(info.position).getName(), new Location(pools.get(info.position).getLocation().getLatitude(), pools.get(info.position).getLocation().getLongitude()));
                for (int i = 0; i < Constants.favourites.size(); i++) {
                    if (Constants.favourites.get(i).equals(poolclick)) {
                        Constants.favourites.remove(i);
                    }
                }
                fileControllers.fileFavWriter(Constants.favourites, getApplicationContext());
                Log.d(TAG, "Contenido del Array de favoritos después de haber borrado: " + Constants.favourites.toString());
                break;
            case 4: {
                Intent locationPoolIntent = new Intent(getApplicationContext(), MapsActivity.class);
                locationPoolIntent.putExtra(NAME, sports.get(info.position).getName());
                locationPoolIntent.putExtra(DESCRIPTION_KEY, DESCRIPTION);
                locationPoolIntent.putExtra(LATITUDE, sports.get(info.position).getLocation().getLatitude());
                locationPoolIntent.putExtra(LONGITUDE, sports.get(info.position).getLocation().getLongitude());
                startActivity(locationPoolIntent);
                break;
            }
            case 5: {
                fileControllers = new FileController();
                int permissionCheckR = ContextCompat.checkSelfPermission(this
                        , Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheckR != PackageManager.PERMISSION_GRANTED) {
                    Log.i("Mensaje", "No se tiene permiso para leer.");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 225);
                } else {
                    Log.i("Mensaje", "Se tiene permiso para leer!");
                    Constants.favourites = fileControllers.fileFavReader(getApplicationContext());
                }
                poolclick = new Pool(sports.get(info.position).getName(), new Location(sports.get(info.position).getLocation().getLatitude(), sports.get(info.position).getLocation().getLongitude()));

                if (!Constants.favourites.contains(poolclick)) {
                    favourites.add(poolclick);
                    Toast.makeText(this, "Agregada a favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Ya estaba en favoritos", Toast.LENGTH_SHORT).show();
                }

                Log.d(TAG, "Contenido del Array de favoritos: " + Constants.favourites.toString());
                fileControllers.fileFavWriter(favourites, getApplicationContext());
                break;
            }
            case 6:
                fileControllers = new FileController();
                poolclick = new Pool(sports.get(info.position).getName(), new Location(sports.get(info.position).getLocation().getLatitude(), sports.get(info.position).getLocation().getLongitude()));
                for (int i = 0; i < Constants.favourites.size(); i++) {
                    if (Constants.favourites.get(i).equals(poolclick)) {
                        Constants.favourites.remove(i);
                    }
                }
                fileControllers.fileFavWriter(Constants.favourites, getApplicationContext());
                Log.d(TAG, "Contenido del Array de favoritos después de haber borrado: " + Constants.favourites.toString());
                break;
        }
        return true;
    }
}