package com.example.easytransportation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gv;
    SharedPreferences prf;
    ArrayList<vehicleServiceList> list = new ArrayList<>();
    vehicleServiceAdapter adapter;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        this.gv = findViewById(R.id.GridView12);
        this.adapter = new vehicleServiceAdapter(this, list);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);

        try{
            URL url = new URL("https://easytransportation.000webhostapp.com/android/get_vehicle");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("vehicle_service");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                String serv_name = item.getString("vehicle_name");
                String serv_id = item.getString("vehicle_id");
                String ServiceImage = item.getString("vehicle_image");
                list.add(new vehicleServiceList(ServiceImage,serv_id,serv_name));
                adapter.notifyDataSetChanged();

            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, Booking.class);
        startActivityForResult(intent, 1);
    }
}