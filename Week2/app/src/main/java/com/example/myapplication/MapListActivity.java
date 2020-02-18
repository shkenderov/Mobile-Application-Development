package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.ArrayAdapter;


public class MapListActivity extends ListActivity {
    String [] data;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        data = new String[] { "Normal Map", "Bike Map"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView lv, View view, int index, long id)
    {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();

        boolean mapcode=true;
        if (index == 1){
            mapcode=false;
        }

        bundle.putBoolean("com.example.hikebikemap",mapcode);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}
