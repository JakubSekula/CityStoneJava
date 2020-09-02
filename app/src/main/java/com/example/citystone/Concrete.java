package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class Concrete extends AppCompatActivity {

    static HashMap<String, HashMap<String,String>> Order = new HashMap<String, HashMap<String, String>>();
    static int inHashPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete);

        prepareScreen();
        register();

    }

    private void prepareScreen(){
        TextView header = (TextView) findViewById( R.id.A2Heading );

        int i = 0;

        for ( String key : Order.keySet() ) {
            if( i == inHashPos ) {
                header.setText( "Objednávka: " + Order.get(key).get("produkt"));
            }
            i++;
        }

    }

    private void fullSend( String id ){
        String url = "https://planaxis.space/transferTo.php?id=" + id + "&state=1";

        System.out.println( url );

        StringRequest request = new StringRequest( url , new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                System.out.println( "DONE" );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println( "FAIL" );
                Toast.makeText(getApplicationContext(), "Some error occurred in ending of request!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Concrete.this);
        rQueue.add(request);

    }

    private void register(){

        final Button done = ( Button ) findViewById( R.id.button2 );

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setText( "Ukončeno" );
                int i = 0;
                for ( String key : Order.keySet() ) {
                    if( i == inHashPos ) {
                        fullSend( Order.get( key ).get( "id" ) );
                    }
                    i++;
                }
            }
        });

    }

}