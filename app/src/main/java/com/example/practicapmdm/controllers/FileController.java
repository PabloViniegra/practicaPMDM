
package com.example.practicapmdm.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicapmdm.R;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.favourites;
import static com.example.practicapmdm.constants.Constants.TAG;

public class FileController {

    public void fileFavWriter(ArrayList<Pool> pools, Context ctx) {

        ArrayList<Pool> arrayfileFavReader = new ArrayList<>();
        arrayfileFavReader = fileFavReader();
        Pool poolAux;
        Log.d(TAG,"TAMAÑO DEL ARRAY AUXILIAR"+arrayfileFavReader.size());
        for (int i = 0; i < arrayfileFavReader.size(); i++) {
            if (arrayfileFavReader.get(i).getName().equalsIgnoreCase(pools.get(0).getName())) {
                poolAux = new Pool(pools.get(0).getName(), pools.get(0).getLocation());
                arrayfileFavReader.add(poolAux);

            }

        }

        String FILENAME = "favourites.txt";
        FileOutputStream fos;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < pools.size(); i++) {
                fos.write(pools.get(i).getName().getBytes());
                fos.write(":".getBytes());
                fos.write((pools.get(i).getLocation().getLatitude() + "").getBytes());
                fos.write(":".getBytes());
                fos.write((pools.get(i).getLocation().getLongitude() + "").getBytes());
                fos.write("\n".getBytes());
            }
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < pools.size(); i++) {
            Log.d(TAG, "NOMBREF: " + pools.get(i).getName());
            Log.d(TAG, "LATITUDF: " + pools.get(i).getLocation().getLatitude());
            Log.d(TAG, "LONGITUDF: " + pools.get(i).getLocation().getLongitude());
        }
    }

    public ArrayList fileFavReader() {
        ArrayList<Pool> arrayFav = new ArrayList();
        Pool pool;
        String name;
        Location location;
        File file = new File("favourites.txt");
        Log.d(TAG, "entrando en la lectura del fichero");
      /*  try {

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
        }*/
        FileInputStream fis;
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        Log.d(TAG, "entrando en la lectura del fichero1");
        try {
            Log.d(TAG, "entrando en la lectura del fichero2");
            fis = new FileInputStream(file);

            Log.d(TAG, "entrando en la lectura del fichero3");
            isr = new InputStreamReader(fis);
            Log.d(TAG, "entrando en la lectura del fichero4");
            br = new BufferedReader(isr);
            Log.d(TAG, "entrando en la lectura del fichero5");
            String linea;
            while((linea = br.readLine()) != null) {
                String[] tokens = linea.split(":");
                location = new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                pool = new Pool(tokens[0], location);
                Log.d(TAG, "entrando en la lectura del fichero" + tokens[0] + " " + tokens[1] + " " + tokens[2]);
                arrayFav.add(pool);
            }
            fis.close();
        }
        catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ file + ": " + e);
        }
        Log.d(TAG, "saliendo de la lectura del documento");
        return arrayFav;
    }
}