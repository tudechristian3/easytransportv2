    package com.example.easytransportation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

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
import java.util.Arrays;
import java.util.List;

    public class bookingcustomerdetails extends AppCompatActivity implements View.OnClickListener {

//        GridView gv;
//        SharedPreferences prf;
//        ArrayList<vehicleServiceList> list = new ArrayList<>();
//        vehicleServiceAdapter adapter;
        private EditText txtAddress;
        private Button button_location;
        SharedPreferences pref;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking_customer_details);
            txtAddress = (EditText) findViewById(R.id.input_address);
            button_location = (Button) findViewById(R.id.pickup_location);
            txtAddress.setFocusable(false);
            txtAddress.setOnClickListener(this);
            button_location.setOnClickListener(this);
            pref = getSharedPreferences("user_details", MODE_PRIVATE);
            String image_service = getIntent().getStringExtra("service_image");
            String service_price = getIntent().getStringExtra("service_price");
            //Toast.makeText(this, service_price, Toast.LENGTH_SHORT).show();
            Places.initialize(getApplicationContext(), "AIzaSyB8gc5JTXHOxtm-p8fpBBapv7fdpjgqHWQ");
//            prf = getSharedPreferences("user_details", MODE_PRIVATE);
//            this.gv = findViewById(R.id.GridView12);
//            this.adapter = new vehicleServiceAdapter(this, list);
//            gv.setAdapter(adapter);
//            gv.setOnItemClickListener(this);
//
//            try{
//                URL url = new URL("https://easytransportation.000webhostapp.com/android/get_vehicle");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                InputStream is=conn.getInputStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                String s=br.readLine();
//
//                is.close();
//                conn.disconnect();
//
//                Log.d("json data", s);
//                JSONObject json=new JSONObject(s);
//                JSONArray array = json.getJSONArray("vehicle_service");
//                for(int i=0; i<array.length(); i++){
//                    JSONObject item = array.getJSONObject(i);
//                    String serv_name = item.getString("vehicle_name");
//                    String serv_id = item.getString("vehicle_id");
//                    String ServiceImage = item.getString("vehicle_image");
//                    list.add(new vehicleServiceList(ServiceImage,serv_id,serv_name));
//                    adapter.notifyDataSetChanged();
//
//                }
//            }catch (MalformedURLException e){
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
        }

//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Intent intent = new Intent(this, Booking.class);
//            startActivityForResult(intent, 1);
//        }

        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor=pref.edit();
            String image_service = getIntent().getStringExtra("service_image");
            String service_price = getIntent().getStringExtra("service_price");
            String address = txtAddress.getText().toString();
            editor.putString("location_pickup", address);
            editor.putString("service_image", image_service);
            editor.putString("service_price", service_price);
            editor.commit();


            if(v == txtAddress){
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(bookingcustomerdetails.this);
                startActivityForResult(intent, 100);
            }

            if(v == button_location){
                Intent intent = new Intent(this, Booking.class);
                intent.putExtra("location_pickup", address);
                intent.putExtra("service_image", image_service);
                intent.putExtra("service_price", service_price);
                startActivityForResult(intent, 1);

            }


        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == 100 && resultCode == RESULT_OK){
                Place place = Autocomplete.getPlaceFromIntent(data);
                txtAddress.setText(place.getAddress());
            }
            else if(resultCode == AutocompleteActivity.RESULT_OK){
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }