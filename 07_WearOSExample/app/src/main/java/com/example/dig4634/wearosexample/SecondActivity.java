package com.example.dig4634.wearosexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

public class SecondActivity extends WearableActivity  implements SensorEventListener, SurfaceHolder.Callback {

    Paint red_fill;
    Paint white_stroke;
    Paint white_text;
    Bitmap planet_image;
    Bitmap sun_image;

    SurfaceHolder holder=null;

    Animator my_animator;

    int CurrentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle extras=this.getIntent().getExtras();
        if(extras!=null){
            CurrentLevel=extras.getInt("Level");
        }

        red_fill=new Paint();
        red_fill.setColor(Color.RED);
        red_fill.setStyle(Paint.Style.FILL);

        white_stroke=new Paint();
        white_stroke.setColor(Color.WHITE);
        white_stroke.setStyle(Paint.Style.STROKE);
        white_stroke.setStrokeWidth(10);

        white_text=new Paint();
        white_text.setColor(Color.WHITE);
        white_text.setTextSize(100);

        planet_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.planet),200,200,false);
        sun_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.planet2),200,200,false);

        planet=new MovingObject(planet_image,0,0);
        planet2=new MovingObject(planet_image,600,700);
        sun=new MovingObject(sun_image,400,0);

        SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer!=null){
            manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }

        SurfaceView my_surface=findViewById(R.id.surfaceView);
        my_surface.getHolder().addCallback(this);


        my_animator=new Animator(this);
        my_animator.start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        acc_x=sensorEvent.values[0];
        acc_y=sensorEvent.values[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    String message="Go to the sun.";
    float acc_x=0;
    float acc_y=0;
    MovingObject sun;
    MovingObject planet;
    MovingObject planet2;

    public void update(int width, int height){


        sun.angle+=5;

        sun.y_position+=5;

        planet.updateWithAccelerometer(width,height,acc_x,acc_y);
        planet2.updateWithAccelerometer(width,height,acc_x,acc_y);


        if(planet.collidesWith(sun)||planet2.collidesWith(sun) ) {
            message = "You won Level "+CurrentLevel;
            //GlobalVariables.passed_level[CurrentLevel-1]=true;
        }

    }

    public void draw(){

        if(holder==null)return;




        Canvas c=holder.lockCanvas();

        update(c.getWidth(),c.getHeight());

        c.drawColor(Color.rgb(210,210,255));

        //c.drawRect(0,0, 200,200,red_fill);

        //c.drawCircle(c.getWidth()/2,c.getHeight()/2,100,white_stroke);


        sun.draw(c);
        planet.draw(c);
        planet2.draw(c);


        c.drawText(message, 20, c.getHeight()-20, white_text);

        holder.unlockCanvasAndPost(c);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d("Example","Surface is created");
        holder=surfaceHolder;

        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d("Example","Surface changed");
        holder=surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        holder=null;
    }

    @Override
    public void onDestroy(){

        my_animator.finish();
        SensorManager manager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));

        super.onDestroy();
    }
}