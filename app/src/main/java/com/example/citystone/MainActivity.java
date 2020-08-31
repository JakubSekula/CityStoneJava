package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register();

    }

    private void register(){

        Button red = (Button) findViewById(R.id.redbtn);
        Button green = (Button) findViewById(R.id.greenbtn);
        Button blue = (Button) findViewById(R.id.bluebtn);
        Button active = (Button) findViewById( R.id.button12 );
        Button finished = (Button) findViewById( R.id.button13 );

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                EditText text = (EditText) findViewById( R.id.textDivision );
                text.setText( "Red Project" );
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                EditText text = (EditText) findViewById( R.id.textDivision );
                text.setText( "Blue Project" );
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                EditText text = (EditText) findViewById( R.id.textDivision );
                text.setText( "Green Project" );
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData( "http://planaxis.space/selectActive.php" );
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData( "http://planaxis.space/selectFinished.php" );
            }
        });

    }

    private void parseJsonData(String jsonString) {

        example example = Gson.fromJson(jsonString, example.class);


    /*
        System.out.println( jsonString1 );

        String str = "{\"id\":\"1\",\"pracovisko\":\"Red\",\"produkt\":\"Granada\",\"farba\":\"Biela\",\"pocet\":\"2 palety\",\"stav\":\"0\"}";
        try {
            JSONObject obj = new JSONObject(str);
            String n = obj.getString("pracovisko");
            int a = obj.getInt("id");
            System.out.println(n + " " + a);  // prints "Alice 20"
        } catch( Exception e ) {

        }
        */
    }

    private void getData( String url ){
        StringRequest request = new StringRequest( url , new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData( string );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);

    }
}