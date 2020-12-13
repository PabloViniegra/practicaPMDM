
package com.example.practicapmdm.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicapmdm.activities.ActivityViewAdapter;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.favourites;
import static com.example.practicapmdm.constants.Constants.TAG;

public class FileController {


    public void fileFavWriter(ArrayList<Pool> pools, Context ctx) {

        for (int i = 0; i < pools.size(); i++) {
            Log.d(TAG, "NOMBREFILE: " + pools.get(i).getName());
            Log.d(TAG, "LATITUDFILE: " + pools.get(i).getLocation().getLatitude());
            Log.d(TAG, "LONGITUDFILE: " + pools.get(i).getLocation().getLongitude());
        }
        File file = new File("favourites.txt");
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = ctx.openFileOutput("favourites.txt", Context.MODE_PRIVATE);
            for (Pool pool : pools) {
                fileOutputStream.write(pool.getName().getBytes());
                fileOutputStream.write(':');
                fileOutputStream.write(String.valueOf(pool.getLocation().getLatitude()).getBytes());
                fileOutputStream.write(':');
                fileOutputStream.write(String.valueOf(pool.getLocation().getLongitude()).getBytes());
                fileOutputStream.write('\n');
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(file);
            for (int i = 0; i < pools.size(); i++) {
                fileWriter.write(pools.get(i).getName());
                fileWriter.append(":");
                fileWriter.write(pools.get(i).getLocation().getLatitude() + "");
                fileWriter.append(":");
                fileWriter.write(pools.get(i).getLocation().getLongitude() + "");
                fileWriter.append("/n");
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public ArrayList fileFavReader() {
        ArrayList<Pool> arrayFav = new ArrayList();
        Pool pool;
        Location location;
        File file = new File("favourites.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader bred;

        try {
            BufferedReader br = new BufferedReader(new FileReader("favourites.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(":");
                location = new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                pool = new Pool(tokens[0], location);
                Log.d(TAG, "--------------------------------------------------" + tokens[0] + " " + tokens[1] + " " + tokens[2]);
                arrayFav.add(pool);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return arrayFav;
        }

    }
}

