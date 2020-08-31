package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register();

    }

    private void register(){

        final Button red = (Button) findViewById(R.id.redbtn);
        final Button green = (Button) findViewById(R.id.greenbtn);
        final Button blue = (Button) findViewById(R.id.bluebtn);
        final Button active = (Button) findViewById( R.id.button12 );
        final Button finished = (Button) findViewById( R.id.button13 );

        final int LIGHTBLUE = Color.parseColor( "#1E90FF" );
        final int DEEPBLUE = Color.parseColor( "#2F3947" );

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                red.setBackgroundColor( LIGHTBLUE );
                green.setBackgroundColor( DEEPBLUE );
                blue.setBackgroundColor( DEEPBLUE );

            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                red.setBackgroundColor( DEEPBLUE );
                green.setBackgroundColor( DEEPBLUE );
                blue.setBackgroundColor( LIGHTBLUE );
            }
        });

        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                red.setBackgroundColor( DEEPBLUE );
                green.setBackgroundColor( LIGHTBLUE );
                blue.setBackgroundColor( DEEPBLUE );
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setBackgroundColor( LIGHTBLUE );
                finished.setBackgroundColor( DEEPBLUE );
                getData( "http://planaxis.space/selectActive.php" );
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setBackgroundColor( DEEPBLUE );
                finished.setBackgroundColor( LIGHTBLUE );
                getData( "http://planaxis.space/selectFinished.php" );
            }
        });

    }

    private HashMap<String, HashMap<String,String>> parseJsonData(String jsonStrings) {
        HashMap<String, HashMap<String,String>> Hash = new HashMap<String, HashMap<String,String>>();

        if( jsonStrings != null ) {
            Hash = Parser.FromJSON( jsonStrings );
        } else {
            System.out.println( "ERROR" );
        }

        return Hash;

    }

    private void createTable( HashMap<String, HashMap<String,String>> Hash ){
        ListView lv = ( ListView ) findViewById( R.id.fruitsList );

        List<String> fruits_list = new ArrayList<String>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        lv.setAdapter(arrayAdapter);

        for( String key : Hash.keySet() ){
            fruits_list.add( Hash.get( key ).get( "produkt" ) );
        }

        arrayAdapter.notifyDataSetChanged();

    }

    private void getData( String url ){
        StringRequest request = new StringRequest( url , new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                HashMap<String, HashMap<String,String>> Hash;
                Hash = parseJsonData( string );
                createTable( Hash );
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