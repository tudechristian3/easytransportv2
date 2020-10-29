package com.example.easytransportation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    EditText user,pass;
    Button btnLogin;
    TextView txtregister;
    //AlertDialog.Builder builder;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        user = findViewById(R.id.editText3);
        pass = findViewById(R.id.editText4);
        btnLogin = findViewById(R.id.button);
        txtregister =  findViewById(R.id.register);
        //builder = new AlertDialog.Builder(this);
        btnLogin.setOnClickListener(this);
        txtregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String username = user.getText().toString();
        String password = pass.getText().toString();
        //192.168.43.19
        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwashseeker");
            URL url = new URL("https://easytransportation.000webhostapp.com/android/get_user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("customer_ride");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                final String user_name = item.getString("customer_username");
                final String user_pass = item.getString("customer_password");
                final String client_id = item.getString("customer_id");
//                final String customer_name = item.getString("seeker_name");
//                final String picture = item.getString("seeker_image");
//                final String seeker_wallets = item.getString("seeker_wallet");
                //final String customer_lname = item.getString("cust_lastname");
                //String client_name = item.getString("cust_name");
                if(username.equals(user_name) && password.equals(user_pass))
                {
                    //Toast.makeText(this,"True",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("user", username);
                    editor.putString("pass", password);
                    //editor.putString("id", client_id);
                    //editor.putString("cust_name", customer_name);
                    editor.putString("cid", client_id);
                    //editor.putString("seeker_name", customer_name);
                    //editor.putString("seeker_image", picture);
                    //editor.putString("seeker_wallet", seeker_wallets);
                    //editor.putString("cust_lastname", customer_lname);
                    //editor.putString("pestcontrol_id", client_id);
                    //editor.putString("cust_name", client_name);
                    editor.commit();
                    //Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Booking.class);
                    startActivity(intent);
                    user.setText("");
                    pass.setText("");
                }else{
                    user.setText("");
                    pass.setText("");
                    //Toast.makeText(this, "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                }

            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
//            URL url = new URL("http://192.168.43.118/washmycar/index.php/androidcontroller/get_carwashseeker");
            URL url = new URL("https://easytransportation.000webhostapp.com/android/get_user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s=br.readLine();

            is.close();
            conn.disconnect();

            Log.d("json data", s);
            JSONObject json=new JSONObject(s);
            JSONArray array = json.getJSONArray("customer_ride");
            for(int i=0; i<array.length(); i++){
                JSONObject item = array.getJSONObject(i);
                final String user_name = item.getString("customer_username");
                final String user_pass = item.getString("customer_password");
                final String client_id = item.getString("customer_id");
//                final String customer_name = item.getString("seeker_name");
//                final String picture = item.getString("seeker_image");
//                final String seeker_wallets = item.getString("seeker_wallet");
                //final String customer_lname = item.getString("cust_lastname");
                //String client_name = item.getString("cust_name");
                if(username.equals(user_name) != password.equals(user_pass))
                {
                    //Toast.makeText(this,"True",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("user", username);
                    editor.putString("pass", password);
                    //editor.putString("id", client_id);
                    //editor.putString("cust_name", customer_name);
                    editor.putString("cid", client_id);
                    //editor.putString("seeker_name", customer_name);
                    //editor.putString("seeker_image", picture);
                    //editor.putString("seeker_wallet", seeker_wallets);
                    //editor.putString("cust_lastname", customer_lname);
                    //editor.putString("pestcontrol_id", client_id);
                    //editor.putString("cust_name", client_name);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(this, Booking.class);
//                    startActivity(intent);
//                    user.setText("");
//                    pass.setText("");
                }

            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}