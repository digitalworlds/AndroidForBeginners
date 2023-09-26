package com.example.angelos.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends AppCompatActivity implements LocationListener, SensorEventListener, SurfaceHolder.Callback {

    private final int REQUEST_PERMISSION_ACCESS_FINE_LOCATION=1;

    private SurfaceHolder holder=null;
    private Bitmap map=null;
    private double mapWidth=4504;
    private double subpixelRatio=1.0;
    private float zoom=0.5f;
    private int lastX=0;
    private int lastY=0;

    private SensorManager mSensorManager;

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView s=(SurfaceView)findViewById(R.id.surfaceView);
        SurfaceHolder h=s.getHolder();
        h.addCallback(this);
        s.setWillNotDraw(false);

        map = BitmapFactory.decodeResource(getResources(),R.drawable.ufmap);
        subpixelRatio=map.getWidth()/mapWidth;

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            mSensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        openGPS();

    }


    private void openGPS(){
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    REQUEST_PERMISSION_ACCESS_FINE_LOCATION );
            return;
        }
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == REQUEST_PERMISSION_ACCESS_FINE_LOCATION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();
            }
            else {
                openGPS();
            }
        }
    }


    private double distance(double lon1, double lat1, double lon2, double lat2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515* 1000.0;
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    boolean got_key=false;
    boolean got_diamond=false;

    @Override
    public void onLocationChanged(Location location) {

        double long00=-82.3650253;
        double lat00=29.6539845;


        double dx=distance(long00,lat00,location.getLongitude(),lat00);
        if(location.getLongitude()<long00)dx=-dx;

        double dy=distance(long00,lat00,long00, location.getLatitude());
        if(location.getLatitude()>lat00)dy=-dy;

        //266px is 100 meters on my map
        lastX=(int)(dx*265.0/100.0);
        lastY=(int)(dy*265.0/100.0);

        draw();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void my_draw(Canvas c,int x,int y){
        c.drawColor(Color.BLACK);

        if(lastX==0 && lastY==0)return;

        Matrix matrix = new Matrix();
        matrix.postRotate((float)(-orientationAngles[0]*180.0/Math.PI), x, y);
        matrix.postScale(zoom, zoom, x,y);
        matrix.postTranslate(-x+(int)(c.getWidth()/2.0), -y+(int)(c.getHeight()/2.0));
        c.drawBitmap(map,matrix,null);

        Paint p=new Paint();
        p.setColor(Color.RED);
        c.drawCircle(c.getWidth()/2,c.getHeight()/2,20,p);

    }

    public void draw(){
        if(holder==null)return;
        Canvas c=holder.lockCanvas();
        if(c!=null){
            my_draw(c,(int)(lastX*subpixelRatio),(int)(lastY*subpixelRatio));
            holder.unlockCanvasAndPost(c);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder h) {
        holder=h;
    }

    @Override
    public void surfaceChanged(SurfaceHolder h, int format, int width, int height) {
        holder=h;
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }

        // Rotation matrix based on current readings from accelerometer and magnetometer.
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        // Express the updated rotation matrix as three orientation angles.
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
