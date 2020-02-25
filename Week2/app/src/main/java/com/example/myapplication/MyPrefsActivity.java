package com.example.myapplication;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyPrefsActivity extends PreferenceActivity implements View.OnClickListener
{
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Button savebtn = (Button)findViewById(R.id.savebtn);
//        savebtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {


        finish();
    }
}