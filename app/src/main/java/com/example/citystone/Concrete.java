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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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
                header.setText( "Objednávka: " + Order.get(key).get("cobjednavky"));
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

    private String formateDate( String date ){

        return "01. 08 .2020";

    }

    private String getDate(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        return formateDate( formattedDate );

    }

    private void sendMail( String key ){

        // lukas.perina98@gmail.com
        String email = "lukas.perina98@gmail.com";
        String subject = "Ukonceni objednavky " + Order.get( key ).get( "id" ) + " ze strediska " + Order.get( key ).get( "pracovisko" );
        String message = "Dobry den,\n\nObjednavka s evidencnim cislem: " + Order.get( key ).get( "id" ) + " ze strediska " + Order.get( key ).get( "pracovisko" ) + ", byla dne " + getDate() + " uspesne dokoncena.\n\n" + "Odeslano sluzbou CityStoneAPP";

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
                        fullSend( Order.get( key ).get( "id" ) );
                        sendMail( key );
                    }
                    i++;
                }
            }
        });

    }

}