package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    // informace o objednavce
    HashMap<String, HashMap<String,String>> Order = new HashMap<String, HashMap<String, String>>();

    // ktere tlacitko je stisknute Dlazby = 1, Ploty = 2, Antiko = 3
    public static int projectPressed = 1;

    // ktere tlacitko stiskunte AKTIVNI = 0, PASIVNI = 1
    public static int actPas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

        register();

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }


    private void openActivity2(){
        Intent intent = new Intent( this, Concrete.class );
        startActivity( intent );
        Concrete.Order = Order;
    }

    private void register(){

        final Button active = (Button) findViewById( R.id.button12 );
        final Button finished = (Button) findViewById( R.id.button13 );

        final ListView fruitsList = (ListView) findViewById( R.id.fruitsList );

        final int LIGHTBLUE = Color.parseColor( "#1E90FF" );
        final int DEEPBLUE = Color.parseColor( "#2F3947" );

        fruitsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Concrete.inHashPos = (int) fruitsList.getItemIdAtPosition( i );

                if( fruitsList.getItemAtPosition( i ) != "Nenalezeny žádné záznamy" ) {
                    openActivity2();
                }
            }
        });

        active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setBackgroundColor( LIGHTBLUE );
                finished.setBackgroundColor( DEEPBLUE );
                actPas = 0;
                getData();
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                active.setBackgroundColor( DEEPBLUE );
                finished.setBackgroundColor( LIGHTBLUE );
                actPas = 1;
                getData();
            }
        });

    }

    private void createTable( HashMap<String, HashMap<String,String>> Hash ){
        ListView lv = ( ListView ) findViewById( R.id.fruitsList );

        List<String> fruits_list = new ArrayList<String>();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, fruits_list);

        lv.setAdapter(arrayAdapter);

        for( String key : Hash.keySet() ){
            fruits_list.add( Hash.get( key ).get( "cobjednavky" ) );
        }

        arrayAdapter.notifyDataSetChanged();

    }

    private String getApiUrl(){

        if( projectPressed == 1 && actPas == 0 ) return( "https://planaxis.space/selectData.php?proj=1&actPas=0" );
        if( projectPressed == 2 && actPas == 0 ) return( "https://planaxis.space/selectData.php?proj=2&actPas=0" );
        if( projectPressed == 3 && actPas == 0 ) return( "https://planaxis.space/selectData.php?proj=3&actPas=0" );

        if( projectPressed == 1 && actPas == 1 ) return( "https://planaxis.space/selectData.php?proj=1&actPas=1" );
        if( projectPressed == 2 && actPas == 1 ) return( "https://planaxis.space/selectData.php?proj=2&actPas=1" );
        if( projectPressed == 3 && actPas == 1 ) return( "https://planaxis.space/selectData.php?proj=3&actPas=1" );

        return null;

    }

    private void getData(){

        String url = getApiUrl();

        if( url == null ){
            System.out.println( "Chyba v url" );
            System.exit( 10 );
        }

        System.out.println( url );

        StringRequest request = new StringRequest( url , new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                HashMap<String, HashMap<String,String>> Hash;
                Parser.pkey = true;
                Hash = Parser.parseJsonData( string );
                createTable( Hash );
                Order = Hash;
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