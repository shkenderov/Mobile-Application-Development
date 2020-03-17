package com.example.myapplication;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.location.LocationManager;
import android.location.LocationListener;


public class MainActivity extends AppCompatActivity implements LocationListener {
    double x, y, z;
    MapView mv;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        ItemizedIconOverlay<OverlayItem> items;
        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

        LocationManager mgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            public boolean onItemLongPress(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem fernhurst = new OverlayItem("Fernhurst", "Village in West Sussex", new GeoPoint(51.05, -0.72));
        OverlayItem blackdown = new OverlayItem("Blackdown", "highest point in West Sussex", new GeoPoint(51.0581, -0.6897));
        items.addItem(fernhurst);
        items.addItem(blackdown);


        Button btn = (Button) findViewById(R.id.btn1);
        try {
            //  btn.setOnClickListener(this);

        } catch (java.lang.NullPointerException e) {

        }
        mv = findViewById(R.id.map1);

        mv.setMultiTouchControls(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble(prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble(prefs.getString("lon", "-1.4"));
        double zoom = Double.parseDouble(prefs.getString("zoom", "16"));

        //boolean autodownload = prefs.getBoolean("autodownload", true);
        mv.getController().setCenter(new GeoPoint(lat, lon));
        mv.getController().setZoom(zoom);
        mv.getOverlays().add(items);


    }

    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble(prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble(prefs.getString("lon", "-1.4"));
        double zoom = Double.parseDouble(prefs.getString("zoom", "16"));

        boolean autodownload = prefs.getBoolean("autodownload", true);
        mv = findViewById(R.id.map1);
        mv.getController().setCenter(new GeoPoint(lat, lon));
        mv.getController().setZoom(zoom);


    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.hikebikemap");
                if (hikebikemap == true) {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                } else {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.choosemap) {
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        if (item.getItemId() == R.id.preferences) {
            Intent intent = new Intent(this, MyPrefsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        if (item.getItemId() == R.id.choosemaplist) {
            Intent intent = new Intent(this, MyPrefsActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }


    public void onClick(View view) {
        EditText et = (EditText) findViewById(R.id.et1);
        EditText et2 = (EditText) findViewById(R.id.et2);
        EditText et3 = (EditText) findViewById(R.id.et3);

        try {
            x = Double.parseDouble(et.getText().toString());
            y = Double.parseDouble(et2.getText().toString());
            z = Double.parseDouble(et3.getText().toString());

            mv.getController().setZoom(z);

            mv.getController().setCenter(new GeoPoint(x, y));

        } catch (NumberFormatException nfe) {

            popupMessage("Please enter data in all text fields");
        }

    }


    private void popupMessage(String message) {
        new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
    }

    public void onLocationChanged(Location newLoc) {
        System.out.println("********************* debug location changed lon=" + newLoc.getLongitude() + "lat=" + newLoc.getLatitude());
        Toast.makeText
                (this, "Location=" +
                        newLoc.getLatitude() + " " +
                        newLoc.getLongitude(), Toast.LENGTH_LONG).show();
    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Provider " + provider +
                " disabled", Toast.LENGTH_LONG).show();
    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Provider " + provider +
                " enabled", Toast.LENGTH_LONG).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

        Toast.makeText(this, "Status changed: " + status,
                Toast.LENGTH_LONG).show();
    }

}