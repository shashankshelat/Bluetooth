package com.example.shashank.bluetoothtransfer;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVWriter;

/**
 * Created by shashank on 7/8/2015.
 */
public class Sender extends Activity
{
    public static String message;
    private TextView shashank;
    private static final int DISCOVER_DURATION = 300;
    private static final int REQUEST_BLU = 1;
    TextView textlan;
    TextView textlog;
    public HashMap<String, String> temp = new HashMap<>();
    public HashMap<String, String> tempon = new HashMap<>();
    public ArrayList<Double> abcd = new ArrayList<>();
    public long startTime, endTime;
    String Time, disst;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender);
        textlan = (TextView) findViewById(R.id.textlan);
        textlog = (TextView) findViewById(R.id.textlog);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationListner();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        Button button3 = (Button) findViewById(R.id.button3);
        final EditText timeo = (EditText) findViewById(R.id.editText);
        final EditText dist = (EditText) findViewById(R.id.editText);
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Time = timeo.getText().toString();
                disst = dist.getText().toString();
                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "AnalysisData.csv";
                String filePath = baseDir + File.separator + fileName;
                File f = new File(filePath);
                try {
                    CSVWriter writer = new CSVWriter(new FileWriter("/sdcard/myfileone.csv"), ',');
                    List<String[]> data = new ArrayList<String[]>();
                    data.add(new String[]{String.valueOf(temp), String.valueOf(tempon), disst, String.valueOf(abcd)});
                    writer.writeAll(data);
                    writer.writeNext(new String[]{String.valueOf(data)});
                    writer.close();
                    Toast.makeText(getBaseContext(), "Data Saved", Toast.LENGTH_LONG).show();
                    }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    public void SendCapture(View v)
    {
        endTime = System.currentTimeMillis();
        Toast.makeText(this, "Time taken for file transfer is : " + (endTime - startTime) + " millisecs", Toast.LENGTH_LONG).show();
        double abc = endTime - startTime;
        abcd.add(abc);
    }
        class mylocationListner implements LocationListener{
            @Override
            public void onLocationChanged(Location location) {
                if(location != null)
                {
                    double plong = location.getLongitude();
                    double plat = location.getLatitude();
                    textlog.setText(Double.toString(plong));
                    textlan.setText(Double.toString(plat));
                    String kce = String.format("%.4g%n", plong);
                    String kde = String.format("%.4g%n", plat);
                    if (temp.get(kce) == null)
                        temp.put(kce, kce);
                    if (tempon.get(kde) == null)
                        tempon.put(kde, kde);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
            }
            @Override
            public void onProviderEnabled(String provider)
            {
            }
            @Override
            public void onProviderDisabled(String provider)
            {
            }
        }
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    public void SendViaBluetooth(View v) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        if (btAdapter == null)
        {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
        }
        else
        {
            enableBluetooth();
        }
    }
    public void enableBluetooth()
    {
        Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(discoveryIntent, REQUEST_BLU);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ( requestCode == 8793)
        {
            startTime=System.currentTimeMillis();
            Toast.makeText(this, "BlueTooth came back", Toast.LENGTH_SHORT).show();
        }
        if (resultCode == DISCOVER_DURATION && requestCode == REQUEST_BLU) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            File message = new File("/storage/emulated/0/filetransfer/1mb");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(message));
            PackageManager pm = getPackageManager();
            List<ResolveInfo> appsList = pm.queryIntentActivities(intent, 0);
            if (appsList.size() > 0) {
                String packageName = null;
                String className = null;
                boolean found = false;
                for (ResolveInfo info : appsList)
                {
                    packageName = info.activityInfo.packageName;
                    if (packageName.equals("com.android.bluetooth"))
                    {
                        className = info.activityInfo.name;
                        found = true;
                        break;
                    }
                }
                if (!found)
                {
                    Toast.makeText(this, "BlueTooth havent been Found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    intent.setClassName(packageName, className);
                    startActivity(intent);
                }
            }
        }
        else
        {
            Toast.makeText(this, "BlueTooth is cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
