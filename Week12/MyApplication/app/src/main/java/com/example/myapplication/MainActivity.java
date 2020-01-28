package com.example.myapplication;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity ma = new MainActivity();
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(this);   
    }

    public void onClick(View view) {
        TextView tv = (TextView) findViewById(R.id.tv1);
        EditText et = (EditText) findViewById(R.id.et1);
        String numStr = "";
        Double feet = 0.0;
        try {
            numStr = et.getText().toString();
            feet = Double.parseDouble(numStr);
        } catch (NumberFormatException ex) {
            popupMessage("cannot parse " + numStr + " to double ");
        }
        double meters = feet * 0.305;
        tv.setText("That is " + meters + " meters");

    }

    private void popupMessage(String message) {
        new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
    }
}
