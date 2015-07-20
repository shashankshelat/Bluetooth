package com.example.shashank.bluetoothtransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.shashank.bluetoothtransfer.Sender;
import com.example.shashank.bluetoothtransfer.R;
public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton sender = (ImageButton) findViewById(R.id.sender);
        ImageButton receiver = (ImageButton) findViewById(R.id.receiver);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);

        sender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, Sender.class);
                startActivity(myIntent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View arg0) {
                                           Intent myIntent = new Intent(MainActivity.this, rssi.class);
                                           startActivity(myIntent);

                                       }
                                   });


        receiver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        Receiver.class);
                startActivity(myIntent);
            }
        });
   }
}