package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.btn1);
        btn.setOnClickListener(this);
    }
    public void onClick(View view) {

        MyTask mt = new MyTask();
        mt.execute();
    }



    class MyTask extends AsyncTask<Void,Void,String> {

        public String doInBackground(Void... unused) {
            HttpURLConnection conn = null;
            try {
                EditText et1 = (EditText)findViewById(R.id.et);

                String input = et1.getText().toString();
                URL url = new URL("http://www.free-map.org.uk/course/ws/hits.php?artist=" + input);
                System.out.println("*************URL CALLED**********");
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while ((line = br.readLine()) != null) {
                        result += line + "\n";
                    }
                    return result;
                } else {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }
            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

        }
        public void onPostExecute(String result)
        {
            TextView tv = (TextView) findViewById(R.id.txtview);

            tv.setText(result);
        }
    }
}
