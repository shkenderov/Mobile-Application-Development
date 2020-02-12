package com.example.myapplication;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    double x, y,z;
    MapView mv;
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.hikebikemap");
                if(hikebikemap==true)
                {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            Intent intent = new Intent(this,MapChooseActivity.class);
            startActivityForResult(intent,0);
            return true;
        }
        if(item.getItemId()==R.id.preferences){
            Intent intent = new Intent(this,MyPrefsActivity.class);
            startActivityForResult(intent,0);
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

        }
        catch (NumberFormatException nfe){

            popupMessage("Please enter data in all text fields");
        }

        }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);




        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        mv = findViewById(R.id.map1);

        mv.setMultiTouchControls(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble ( prefs.getString("lat", "50.9") );
        double lon = Double.parseDouble ( prefs.getString("lon", "-1.4") );
        double zoom = Double.parseDouble ( prefs.getString("zoom", "16") );

        //boolean autodownload = prefs.getBoolean("autodownload", true);
        mv.getController().setCenter(new GeoPoint(lat, lon));
        mv.getController().setZoom(zoom);
    }
    public void onResume()
    {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble ( prefs.getString("lat", "50.9") );
        double lon = Double.parseDouble ( prefs.getString("lon", "-1.4") );
        double zoom = Double.parseDouble ( prefs.getString("zoom", "16") );

        boolean autodownload = prefs.getBoolean("autodownload", true);
        mv.getController().setCenter(new GeoPoint(lat, lon));
        mv.getController().setZoom(zoom);


    }


    private void popupMessage(String message) {
        new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
    }

}