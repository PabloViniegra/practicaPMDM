
package com.example.practicapmdm.controllers;

import android.content.Context;

import com.example.practicapmdm.constants.Constants;
import com.example.practicapmdm.models.Pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            arrayFav = (ArrayList<Pool>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayFav;
    }
}