package com.example.dig4634.gpsexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LocationListener {

    final int PERMISSION_REQUEST_CODE=0;

    Location NormanGym;
    Location CenturyTower;
    Location Stadium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NormanGym=new Location(LocationManager.GPS_PROVIDER);
        NormanGym.setLongitude(-82.338012);
        NormanGym.setLatitude(29.6462017);

        CenturyTower=new Location(LocationManager.GPS_PROVIDER);
        CenturyTower.setLongitude(-82.343303);
        CenturyTower.setLatitude(29.648796);

        Stadium=new Location(LocationManager.GPS_PROVIDER);
        Stadium.setLongitude(-82.3511307);
        Stadium.setLatitude(29.6494172);


        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted){

            //start Location Services
            Log.d("Example","User granted permissions before. Start GPS now");
            startGPS();
        }
        else{

            //We need to request permissions
            Log.d("Example","User never granted permissions before. Request now");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);

        }
    }

    public void startGPS(){

        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        if(requestCode==PERMISSION_REQUEST_CODE){

            if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                //The user clicked on the DENY button
                Log.d("Example","User denied permissions just now. Exit");
                finish();
            }
            else{
                //The user clicked on the ALLOW button
                Log.d("Example","User granted permissions right now. Start GPS now");
                startGPS();
            }

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Example","New location received. Long:"+location.getLongitude()+" Lat:"+location.getLatitude());

        if(location.distanceTo(NormanGym)<50)//within 50 meters
        {
            Log.d("Example","You are at Digital Worlds");

            Intent my_intent=new Intent(getBaseContext(),DigitalWorldsActivity.class);
            my_intent.putExtra("pictureID",R.drawable.dwlogo);
            my_intent.putExtra("caption","Digital Worlds Institute");
            startActivity(my_intent);

        }
        else if(location.distanceTo(CenturyTower)<50)//within 50 meters
        {
            Log.d("Example","You are at the Century Tower");

            Intent my_intent=new Intent(getBaseContext(),DigitalWorldsActivity.class);
            my_intent.putExtra("pictureID",R.drawable.century_tower);
            my_intent.putExtra("caption","Century Tower");
            startActivity(my_intent);
        }
        else if(location.distanceTo(Stadium)<50)//within 50 meters
        {
            Log.d("Example","You are at the Stadium");

            Intent my_intent=new Intent(getBaseContext(),DigitalWorldsActivity.class);
            my_intent.putExtra("pictureID",R.drawable.oconnell_center);
            my_intent.putExtra("caption","Welcome to the O'Connell Center");
            startActivity(my_intent);
        }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
