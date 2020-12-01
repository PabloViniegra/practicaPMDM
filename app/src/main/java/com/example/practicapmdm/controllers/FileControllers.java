package com.example.practicapmdm.controllers;

import com.example.practicapmdm.activities.InitHomeActivity;
import com.example.practicapmdm.models.Location;
import com.example.practicapmdm.models.Pool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileControllers {

    public ArrayList fileReader() {
        ArrayList<Pool> arrayFavoritos = new ArrayList();
        Location location;
        Pool pool;
        try {
            BufferedReader br = new BufferedReader(new FileReader("favorites.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] tokens = linea.split(":");
                location = new Location(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                pool = new Pool(tokens[1], location);
                arrayFavoritos.add(pool);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return arrayFavoritos;
    }

    public void fileWriter(Pool pool) {
        File file = new File("favorites.txt");
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(pool.getName());
            fw.append(":");
            fw.write(pool.getLocation().getLatitude()+"");
            fw.append(":");
            fw.write(pool.getLocation().getLongitude()+"\n");
            fw.close();
            InitHomeActivity.favourites.add(pool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
