package com.example.practicapmdm.apiRest;

import com.example.practicapmdm.models.Pool;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.http.HttpMethod;

public class HttpGetPetition {
    private final String HEADER_URL = "https://datos.madrid.es/egob/";
    private final String END_POINT = "catalogo/210227-0-piscinas-publicas.json?latitude=40.47876758357458&longitude=-3.7086993281311904";

    public List<Pool> getHttpLocationsPetition() {
        List<Pool> httpLocations = new ArrayList<>();
        StringBuilder stringBuilder = null;
        URL url = null;
        try {
            url = new URL(HEADER_URL + END_POINT);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");
            request.connect();
            switch (request.getResponseCode()) {
                case 200:
                case 201:
                    try (BufferedReader bf = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                        stringBuilder = new StringBuilder();
                        String line = "";
                        while ((line = bf.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                    }
                    break;

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("@graph");
            String title = "";
            double latitude;
            double longitude;
            for (int i = 0; i < jsonArray.length(); i++) {
                title = jsonArray.getJSONObject(i).getString("title");
                latitude = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude"));
                longitude = Double.parseDouble(jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude"));
                httpLocations.add(new Pool(title, latitude, longitude));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return httpLocations;
    }
}
