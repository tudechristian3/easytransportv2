package com.example.easytransportation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Locale;

public class bookingcustomerdetails extends AppCompatActivity implements View.OnClickListener {

    //        GridView gv;
//        SharedPreferences prf;
//        ArrayList<vehicleServiceList> list = new ArrayList<>();
//        vehicleServiceAdapter adapter;
    private EditText txtAddress;
    private Button button_location;
    private Button button_get_location;
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_customer_details);
        txtAddress = (EditText) findViewById(R.id.input_address);
        button_location = (Button) findViewById(R.id.pickup_location);
        button_get_location = (Button) findViewById(R.id.get_location);
        txtAddress.setFocusable(false);
        txtAddress.setOnClickListener(this);
        button_location.setOnClickListener(this);
        button_get_location.setOnClickListener(this);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        String image_service = getIntent().getStringExtra("service_image");
        String service_price = getIntent().getStringExtra("service_price");
        //Toast.makeText(this, service_price, Toast.LENGTH_SHORT).show();
        Places.initialize(getApplicationContext(), "AIzaSyB8gc5JTXHOxtm-p8fpBBapv7fdpjgqHWQ");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        SharedPreferences.Editor editor = pref.edit();
        String image_service = getIntent().getStringExtra("service_image");
        String service_price = getIntent().getStringExtra("service_price");
        String address = txtAddress.getText().toString();
        editor.putString("location_pickup", address);
        editor.putString("service_image", image_service);
        editor.putString("service_price", service_price);
        editor.commit();


        if (v == txtAddress) {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(bookingcustomerdetails.this);
            startActivityForResult(intent, 100);
        }

        if (v == button_location) {
            Intent intent = new Intent(this, Booking.class);
            intent.putExtra("location_pickup", address);
            intent.putExtra("service_image", image_service);
            intent.putExtra("service_price", service_price);
            startActivityForResult(intent, 1);

        }

        if (v == button_get_location) {
            if (ActivityCompat.checkSelfPermission(bookingcustomerdetails.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                ActivityCompat.requestPermissions(bookingcustomerdetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if(location != null){
                        Geocoder geocoder = new Geocoder(bookingcustomerdetails.this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(),location.getLongitude(), 1
                            );

                            txtAddress.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
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