package com.example.practicapmdm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.practicapmdm.R;
import com.example.practicapmdm.activities.MapsActivity;
import com.example.practicapmdm.apiRest.HttpGetPetition;
import com.example.practicapmdm.impl.ViewAdapter;
import com.example.practicapmdm.models.Pool;

import java.util.List;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION;
import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.constants.Constants.*;

public class ActivityViewAdapter extends AppCompatActivity {
    public final String TAG = getClass().getName();
    private ViewAdapter mViewAdapter;
    private ListView mViewList = null;
    private List<Pool> pools;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_adapter);

        mViewList = findViewById(R.id.myListView);

        mViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent locationSchoolIntent = new Intent(getApplicationContext(),MapsActivity.class);
                locationSchoolIntent.putExtra(NAME,pools.get(i).getName());
                locationSchoolIntent.putExtra(DESCRIPTION_KEY,DESCRIPTION);
                locationSchoolIntent.putExtra(LATITUDE,pools.get(i).getLatitude());
                locationSchoolIntent.putExtra(LONGITUDE,pools.get(i).getLongitude());
                startActivity(locationSchoolIntent);
            }
        });

        AsyncTask asyncTask = new AsyncTask();
        asyncTask.execute();
    }

    public class AsyncTask extends android.os.AsyncTask<Void, Integer, String> {


        @Override
        protected String doInBackground(Void... voids) {
            HttpGetPetition httpGetPetition = new HttpGetPetition();
            pools = httpGetPetition.getHttpLocationsPetition();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mViewAdapter = new ViewAdapter(ActivityViewAdapter.this, R.layout.activity_view_adapter, pools);
            mViewList.setAdapter(mViewAdapter);
            mViewAdapter.notifyDataSetChanged();
        }
    }
}