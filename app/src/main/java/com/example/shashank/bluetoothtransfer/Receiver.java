package com.example.shashank.bluetoothtransfer;

import android.app.Activity;

import android.os.Bundle;

import android.widget.Toast;




import android.view.View;
import android.widget.Toast;

public class
        Receiver extends Activity {

    public long startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver);
    }
    public void ReceiveFile(View v) {
        endTime = System.currentTimeMillis();
        Toast.makeText(this, "Time taken for file transfer is : " + (endTime - startTime) + " millisecs", Toast.LENGTH_LONG).show();

    }

    public void ClickTime(View v) {
        startTime = System.currentTimeMillis();


    }


    public void DisplayFile(View v) {
        Toast.makeText(this, "File has been Received:  Received/storage/emulated/0/bluetooth/1mb ", Toast.LENGTH_LONG).show();



    }

}

