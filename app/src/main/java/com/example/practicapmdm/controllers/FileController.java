
package com.example.practicapmdm.controllers;

import android.content.Context;

import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class FileController {

    public void fileFavWriter(Pool pool,Context ctx) {

        String FILENAME = "favourites";
        FileOutputStream fos;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(pool.getName().getBytes());
            fos.write(":".getBytes());
            fos.write((pool.getLocation().getLatitude()+"").getBytes());
            fos.write(":".getBytes());
            fos.write((pool.getLocation().getLongitude()+"").getBytes());
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

     /*   File file = new File("favorites.txt");
        FileWriter fileWriter;
        try {
            fileWriter=new FileWriter(file,true);
            fileWriter.write(pool.getName());
            fileWriter.append(":");
            fileWriter.write(pool.getLocation().getLatitude()+"");
            fileWriter.append(":");
            fileWriter.write(pool.getLocation().getLongitude()+"\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public ArrayList fileFavReader() {
        ArrayList<Pool> arrayFav = new ArrayList();
        Pool pool;
        String name;
        Location location;
        try {
            BufferedReader br = new BufferedReader(new FileReader("favorites.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(":");
                location=new Location(Double.parseDouble(tokens[1]),Double.parseDouble(tokens[2]));
                pool=new Pool(tokens[0],location);
                arrayFav.add(pool);
            }
            br.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return arrayFav;
    }
}

