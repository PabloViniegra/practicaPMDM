package com.example.practicapmdm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicapmdm.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import static com.example.practicapmdm.activities.InitHomeActivity.DESCRIPTION_KEY;
import static com.example.practicapmdm.activities.InitHomeActivity.TITLE_KEY;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;
import static com.example.practicapmdm.constants.Constants.NAME;

public class MapsActivity extends AppCompatActivity {
    private MapView mMap;
    private MapController mapController;
    GeoPoint geoMyPosition;
    private String name;
    private ArrayList<OverlayItem> overlayItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        Intent getDataIntent = getIntent();
        geoMyPosition = new GeoPoint(getDataIntent.getDoubleExtra(LATITUDE, 0), getDataIntent.getDoubleExtra(LONGITUDE, 0));
        name = getDataIntent.getStringExtra("NAME");
        generateOpenMaps();
        addMarker(geoMyPosition);

        boolean add = overlayItems.add(new OverlayItem(getDataIntent.getStringExtra(TITLE_KEY), getDataIntent.getStringExtra(DESCRIPTION_KEY), geoMyPosition));
        ItemizedOverlayWithFocus<OverlayItem> mOverLay = new ItemizedOverlayWithFocus<OverlayItem>(overlayItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        }, getApplicationContext());

        mOverLay.setFocusItemsOnTap(true);
        mMap.getOverlays().add(mOverLay);
    }

    public void generateOpenMaps() {
        mMap = findViewById(R.id.openStreetMap);
        mMap.setBuiltInZoomControls(true);
        mMap.setMultiTouchControls(true);
        mapController = (MapController) mMap.getController();
        mapController.setZoom(18);
        mapController.setCenter(geoMyPosition);
    }

    public void addMarker(GeoPoint center) {
        Marker marker = new Marker(mMap);
        marker.setPosition(center);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.moreinfo_arrow));
        mMap.getOverlays().clear();
        mMap.getOverlays().add(marker);
        mMap.invalidate();
        marker.setTitle(name);
    }
}