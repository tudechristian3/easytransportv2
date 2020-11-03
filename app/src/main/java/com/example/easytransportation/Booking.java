package com.example.easytransportation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Booking extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, View.OnClickListener {

    private GoogleMap mMap;
    private Button btnFindPath;
    private Button btnQuickBook;
    private EditText etOrigin;
    private EditText etDestination;
    private EditText etDestinationtext2;
    private EditText etDestinationtext3;
    private TextView kilogram;
    private TextView etDistance;
    private  TextView etTime;
    private TextView etPrice;
    private ImageView img, imageInsert,imageMinus;
    private Uri uriImage;
    private LinearLayout pladestination, destination2, destination3;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    String total;
    SharedPreferences prf;
    //private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        String image_service = getIntent().getStringExtra("service_image");
        String service_price = getIntent().getStringExtra("service_price");
        String service_kilogram = getIntent().getStringExtra("service_kilogram");
        //String loc_deliver = getIntent().getStringExtra("location_deliver");
        String loc_deliver = prf.getString("location_deliver", "");
        String loc_address = prf.getString("location_pickup", "");
//        Toast.makeText(getApplicationContext(), loc_address, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), loc_deliver, Toast.LENGTH_SHORT).show();
        img = (ImageView) findViewById(R.id.logoservice);
        imageInsert = (ImageView) findViewById(R.id.etDestinationInsert);
        imageMinus = (ImageView) findViewById(R.id.etDestinationMinus);
        Bitmap bm  = getBitmapFromURL(image_service);
        img.setImageBitmap(bm);
        //img.setImageBitmap(BitmapFactory.decodeFile(image_service));
        pladestination = (LinearLayout) findViewById(R.id.place_text);
        destination2 = (LinearLayout) findViewById(R.id.etDestinationInsert2);
        destination3 = (LinearLayout) findViewById(R.id.etDestinationInsert3);
        //pladestination.setVisibility(View.GONE);
        btnQuickBook = (Button) findViewById(R.id.input_quickbook);
        btnQuickBook.setOnClickListener(this);
        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etOrigin = (EditText) findViewById(R.id.etOrigin);
        etPrice = (TextView) findViewById(R.id.price);
        kilogram = (TextView) findViewById(R.id.kilogram);
        etDestination = (EditText) findViewById(R.id.etDestination);
        etDestinationtext2 = (EditText) findViewById(R.id.etDestination2);
        etDestinationtext3 = (EditText) findViewById(R.id.etDestination3);
        destination2.setVisibility(View.GONE);
        destination3.setVisibility(View.GONE);
        etDistance = (TextView) findViewById(R.id.tvDistance);
        etTime = (TextView) findViewById(R.id.tvDuration);
        etOrigin.setOnClickListener(this);
        etDestination.setOnClickListener(this);
        imageInsert.setOnClickListener(this);
        imageMinus.setOnClickListener(this);
        etOrigin.setFocusable(false);
        etDestination.setFocusable(false);
        Places.initialize(getApplicationContext(), "AIzaSyB8gc5JTXHOxtm-p8fpBBapv7fdpjgqHWQ");

        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
        etOrigin.setText(loc_address);
        etDestination.setText(loc_deliver);
        etPrice.setText(service_price);
        kilogram.setText(service_kilogram);

    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
        String Distan  = etDistance.getText().toString();
        String EstimatedTime = etTime.getText().toString();
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder((DirectionFinderListener) this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        LatLng humus = new LatLng(10.3157, 123.8854);
//        originMarkers.add(mMap.addMarker(new MarkerOptions()
//                .position(humus)));




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng humus = new LatLng(10.3157, 123.8854);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(humus, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .position(humus)));

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
        mMap.setMyLocationEnabled(true);
    }

    public void onDirectionFinderStart() {
//        progressDialog = ProgressDialog.show(this, "Please wait.",
//                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    public void onDirectionFinderSuccess(List<Route> routes) {
        //progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            etTime.setText(route.duration.text);
            etDistance.setText(route.distance.text);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

//            if(route.distance.equals(2)){
//                Toast.makeText(this, "Price is "+ 20, Toast.LENGTH_SHORT).show();
//            }
        }
    }

    @Override
    public void onClick(View v) {
        String image_service = getIntent().getStringExtra("service_image");
        String service_price = getIntent().getStringExtra("service_price");

        if(v == etOrigin){
//            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Booking.this);
//            startActivityForResult(intent, 100);
            Intent intent = new Intent(this, bookingcustomerdetails.class);
            intent.putExtra("service_image", image_service);
            intent.putExtra("service_price", service_price);
            startActivityForResult(intent, 1);
        }

       if(v == etDestination){
//            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
//            Intent intent2 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(Booking.this);
//            startActivityForResult(intent2,99);
            Intent intent = new Intent(this, bookingcustomerdeliver.class);
            intent.putExtra("service_image", image_service);
           intent.putExtra("service_price", service_price);
            startActivityForResult(intent, 1);
        }

       if(v == btnQuickBook){
          pladestination.setVisibility(View.VISIBLE);
       }

       if(v == imageInsert){
           destination2.setVisibility(View.VISIBLE);
       }else if(v == imageMinus){
           destination2.setVisibility(View.GONE);
       }


    }

//    private void startActivityForResult2(Intent intent2, int i) {
//
//        Place place2 = Autocomplete.getPlaceFromIntent(intent2);
//        etDestination.setText(place2.getAddress());
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 100 && resultCode == RESULT_OK){
//                Place place = Autocomplete.getPlaceFromIntent(data);
//                etOrigin.setText(place.getAddress());
//        }
//        else if(requestCode == 99 && resultCode == RESULT_OK){
//            Place place2;
//            place2 = Autocomplete.getPlaceFromIntent(data);
//            etDestination.setText(place2.getAddress());
//        }
//        else if(resultCode == AutocompleteActivity.RESULT_OK){
//            Status status = Autocomplete.getStatusFromIntent(data);
//            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//    }gfvvvvs

    static Bitmap getBitmapFromURL(String src)
    {
        try{
            URL url = new URL(src);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            InputStream is = con.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(is);
            Log.e("Bitmap", "returned");
            return myBitmap;
        }catch(IOException e){
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}