package com.example.citystone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.HashMap;

public class MainActivityActuall extends AppCompatActivity {

    HashMap<String, HashMap<String,String>> Names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actuall);

        changeName();

    }

    private void openActivity(){
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );
    }

    private void changeName(){
        Button button1 = (Button) findViewById( R.id.redbtn );
        Button button2 = (Button) findViewById( R.id.greenbtn );
        Button button3 = (Button) findViewById( R.id.bluebtn );

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                MainActivity.projectPressed = 1;
                openActivity();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                MainActivity.projectPressed = 2;
                openActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                MainActivity.projectPressed = 3;
                openActivity();
            }
        });

    }

}