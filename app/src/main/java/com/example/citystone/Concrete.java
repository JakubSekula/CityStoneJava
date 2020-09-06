package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Concrete extends AppCompatActivity {

    static HashMap<String, HashMap<String,String>> Order = new HashMap<String, HashMap<String, String>>();
    static HashMap<String, HashMap<String,String>> HashIt = new HashMap<>();
    static int inHashPos = 0;
    boolean isClickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete);

        prepareScreen();
        register();

    }

    private int getProducts( final String key ){

        String url ="http://planaxis.space/getProducts.php?cobjednavky=" + Order.get( key ).get( "cobjednavky" )+"&pracovisko=" + Order.get( key ).get( "pracovisko" );

        System.out.println( url );

        StringRequest request = new StringRequest( url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                HashMap<String, HashMap<String,String>> Hash;
                Parser.pkey = false;
                Hash = Parser.parseJsonData( string );
                Concrete.HashIt = Hash;
                createTable( key );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Concrete.this);
        rQueue.add(request);
        return 1;
    }

    private void createTable( String key ){
        TableLayout tl = ( TableLayout ) findViewById( R.id.table );

        tl.setStretchAllColumns( true );
        tl.bringToFront();

        TableRow tr1 = new TableRow( this );
        TextView c11 = new TextView( this );
        c11.setText( "Produkt:" );
        c11.setTextSize( 22 );
        c11.setGravity( Gravity.CENTER );
        c11.setTextColor(Color.parseColor( "#000000" ) );
        TextView c22 = new TextView( this );
        c22.setText( "Pocet:" );
        c22.setTextSize( 22 );
        c22.setGravity( Gravity.CENTER );
        c22.setTextColor(Color.parseColor( "#000000" ) );
        tr1.addView( c11 );
        tr1.addView( c22 );
        tl.addView( tr1 );

        for( String ikey : HashIt.keySet() ) {
            TableRow tr = new TableRow( this );
            TextView c1 = new TextView( this );
            c1.setText( HashIt.get( ikey ).get( "produkt" ) );
            c1.setTextSize( 18 );
            c1.setGravity( Gravity.CENTER );
            TextView c2 = new TextView( this );
            c2.setText( HashIt.get( ikey ).get( "pocet" ) );
            c2.setGravity( Gravity.CENTER );
            c2.setTextSize( 18 );
            tr.addView( c1 );
            tr.addView( c2 );
            tl.addView( tr );
        }

    }

    private void prepareScreen(){
        final Button done = ( Button ) findViewById( R.id.button2 );
        TextView header = (TextView) findViewById( R.id.A2Heading );
        TextView note = (TextView) findViewById( R.id.Fieldpoznamka );

        int i = 0;

        for ( String key : Order.keySet() ) {
            if( i == inHashPos ) {
                if( Integer.parseInt( Order.get( key ).get( "stav" ) ) == 1 ){
                    done.setText( "Ukončeno" );
                    isClickable = false;
                }
                getProducts( key );
                if( !Order.get( key ).get( "poznamka" ).equals( "null" ) ) {
                    note.setText(Order.get(key).get("poznamka"));
                }
                header.setText( "Objednávka č.: " + Order.get(key).get("cobjednavky"));
            }
            i++;
        }

    }

    private String getPoznamkaField(){
        TextView note = (TextView) findViewById( R.id.Fieldpoznamka );

        return note.getText().toString();
    }

    private void fullSend( String cobjednavky, String pracovisko ){

        String note = getPoznamkaField();

        String url = "https://planaxis.space/transferTo.php?cobjednavky=" + cobjednavky + "&state=1&pracovisko=" + pracovisko + "&note=" + note;

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

    private String formateDate( String date ){

        String[] split = date.split( "-" );

        return split[ 0 ] + ". " + split[ 1 ] + ". " + split[ 2 ];

    }

    private String getDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        return formateDate( formattedDate );

    }

    private void sendMail( String key ){

        // lukas.perina98@gmail.com
        String email = "kubasekula@seznam.cz";
        String subject = "Ukonceni objednavky " + Order.get( key ).get( "id" ) + " ze strediska '" + Order.get( key ).get( "nazov" ) +"'";
        String message = "Dobry den,\n\nObjednavka s evidencnim cislem: " + Order.get( key ).get( "id" ) + " ze strediska '" + Order.get( key ).get( "nazov" ) + "', byla dne " + getDate() + " uspesne dokoncena.\n\n" +
                "Poznámka k dokončené objednávce: " + Order.get( key ).get( "poznamka" ) + ". \n\n Odeslano sluzbou CityStoneAPP";

        SendMail sm = new SendMail(this, email, subject, message);

        sm.execute();

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
                        fullSend( Order.get( key ).get( "cobjednavky" ), Order.get( key ).get( "pracovisko" ) );
                        if( isClickable ) {
                            sendMail(key);
                        }
                    }
                    i++;
                }
            }
        });

    }

}