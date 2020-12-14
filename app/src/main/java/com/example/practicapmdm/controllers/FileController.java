
package com.example.practicapmdm.controllers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.practicapmdm.activities.ActivityViewAdapter;
import com.example.practicapmdm.constants.Constants;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


import static com.example.practicapmdm.constants.Constants.TAG;

public class FileController {


    public void fileFavWriter(ArrayList<Pool> pools, Context ctx) {

        File file = new File(ctx.getFilesDir() + Constants.FILENAME);
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(pools);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public ArrayList<Pool> fileFavReader(Context ctx) {
        ArrayList<Pool> arrayFav = new ArrayList<>();
        File file = new File(ctx.getFilesDir() + Constants.FILENAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Log.d(TAG, "antes del try");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            arrayFav = (ArrayList<Pool>) objectInputStream.readObject();
            Log.d(TAG,"fichero leido: " + arrayFav.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayFav;

    }
}