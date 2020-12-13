
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

    public void fileFavWriter(ArrayList<Pool> pools,Context ctx) {

        String FILENAME = "favourites.txt";
        FileOutputStream fos;
       /* try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            for (int i = 0; i < pools.size(); i++) {
                fos.write(pools.get(i).getName().getBytes());
                fos.write(":".getBytes());
                fos.write((pools.get(i).getLocation().getLatitude()+"").getBytes());
                fos.write(":".getBytes());
                fos.write((pools.get(i).getLocation().getLongitude()+"").getBytes());
            }
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
        for (int i = 0; i <pools.size() ; i++) {
            Log.d(TAG, "NOMBREF: " +pools.get(i).getName() );
            Log.d(TAG, "LATITUDF: " +pools.get(i).getLocation().getLatitude() );
            Log.d(TAG, "LONGITUDF: " +pools.get(i).getLocation().getLongitude());
        }
       File file = new File("favourites.txt");
        FileWriter fileWriter;
      //  OutputStreamWriter osw=null;
        try {

            //osw= new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            fileWriter=new FileWriter(file,true);
            for (int i = 0; i <pools.size() ; i++) {

                fileWriter.write(pools.get(i).getName());
                fileWriter.append(":");
                fileWriter.write(pools.get(i).getLocation().getLatitude()+"");
                fileWriter.append(":");
                fileWriter.write(pools.get(i).getLocation().getLongitude()+"");
                fileWriter.append("/n");

/*
                osw.write(pools.get(i).getName());
                osw.write(":");
                osw.write(pools.get(i).getLocation().getLatitude()+"");
                osw.write(":");
                osw.write(pools.get(i).getLocation().getLongitude()+"");
                osw.write("/n");
*/
            }
            fileWriter.close();
           // osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList fileFavReader() {
        ArrayList<Pool> arrayFav = new ArrayList();
        Pool pool;
        String name;
        Location location;
        File file = new File("favourites.txt");
        BufferedReader bred;
        try {

            BufferedReader br = new BufferedReader(new FileReader("favourites.txt"));

           /* BufferedReader br = new BufferedReader(new FileReader("favourites.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(":");
                location=new Location(Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));
                pool=new Pool(tokens[0],location);
                Log.d(TAG,"--------------------------------------------------"+tokens[0]+" "+tokens[1]+" "+tokens[2]);
                arrayFav.add(pool);*/
            String linea;
            bred = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((linea = bred.readLine()) != null) {
                String[] tokens = linea.split(":");
                location = new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                pool = new Pool(tokens[0], location);
                Log.d(TAG, "--------------------------------------------------" + tokens[0] + " " + tokens[1] + " " + tokens[2]);
                arrayFav.add(pool);

                bred.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayFav;
    }
}

