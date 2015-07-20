package com.example.shashank.bluetoothtransfer;

/**
 * Created by shashank on 7/9/2015.
 */
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import com.opencsv.*;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class rssi extends Activity {
    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    int rssi;
    Button save;
    TextView textView2;
    public ArrayList<String> temp = new ArrayList<String>();
    public ArrayList<String> tempon = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssi);
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        Button boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                BTAdapter.startDiscovery();
            }
        });
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "AnalysisData.csv";
                String filePath = baseDir + File.separator + fileName;
                File f = new File(filePath );
                try
                {
                    CSVWriter writer = new CSVWriter(new FileWriter("/sdcard/myfile.csv"), ',');
                    List<String[]> data = new ArrayList<String[]>();
                    data.add(new String[] {String.valueOf(temp), String.valueOf(tempon)});
                    writer.writeAll(data);
                    writer.writeNext(new String[]{String.valueOf(data)});
                    writer.close();
                    Toast.makeText(getBaseContext(), "Data Saved", Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                }

        });
    }


//==================================================================================================




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }




//==================================================================================================
    private final BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                TextView rssi_msg = (TextView) findViewById(R.id.textView2);
                rssi_msg.setText(rssi_msg.getText() + name + " => " + rssi + "dBm\n");
                temp.add(Integer.toString(rssi));
                tempon.add(name);
            }
        }
    };
}
