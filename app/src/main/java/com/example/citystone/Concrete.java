package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

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

    private void register(){

    }

}