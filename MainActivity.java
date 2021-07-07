package com.codeanu.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    EditText ct;
    TextView tv , tv1, tv2;
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ct = findViewById(R.id.ct);
        tv = findViewById(R.id.tv);
        tv2 = findViewById(R.id.tv2);
        tv1 = findViewById(R.id.tv1);


    }

    public void get(View view){
        String apikey = "9c47ac6d42b6c9e31cdbc4bab3c9379f";
        String city = ct.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=9c47ac6d42b6c9e31cdbc4bab3c9379f";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            //get response
            public void onResponse(JSONObject response) {
               //if it is unable to get response and gets error
                try {
                    JSONObject object = response.getJSONObject("main");
                    String temperature = object.getString("temp");
                    double temp= Double.parseDouble(temperature)-273.15;
                    temperature = Double.toString(temp).substring(0,5);
                    tv.setText(temperature+" °C");

                    JSONObject object1 = response.getJSONObject("main");
                    String humidity = object1.getString("humidity");
                    tv1.setText(humidity+" %");

                    JSONObject object2 = response.getJSONObject("main");
                    String  pressure = object2.getString("pressure");
                    tv2.setText(pressure+" hPa");



                } catch (JSONException e) {
                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Enter valid city name", Toast.LENGTH_SHORT).show();
                tv.setText("");
                tv1.setText("");
                tv2.setText("");


            }
        });

        queue.add(request);
    }
}