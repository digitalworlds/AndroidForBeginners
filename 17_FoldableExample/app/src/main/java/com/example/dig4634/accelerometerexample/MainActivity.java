package com.example.dig4634.accelerometerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback, View.OnTouchListener {

    Paint red_fill;
    Paint white_text;

    SurfaceHolder holder=null;

    Animator my_animator;

    float hinge_angle=0;
    Display display;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        red_fill=new Paint();
        red_fill.setColor(Color.RED);
        red_fill.setStyle(Paint.Style.FILL);


        white_text=new Paint();
        white_text.setColor(Color.WHITE);
        white_text.setTextSize(100);



        SensorManager manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor hinge=manager.getDefaultSensor(Sensor.TYPE_HINGE_ANGLE);
        if(hinge!=null){
            manager.registerListener(this,hinge,SensorManager.SENSOR_DELAY_NORMAL,SensorManager.SENSOR_DELAY_UI);
        }

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

        SurfaceView my_surface=findViewById(R.id.surfaceView);
        my_surface.getHolder().addCallback(this);

        my_surface.setOnTouchListener(this);

        my_animator=new Animator(this);
        my_animator.start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_HINGE_ANGLE) {
            hinge_angle = sensorEvent.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



    public void draw(){

        if(holder==null)return;




        Canvas c=holder.lockCanvas();




        c.drawColor(Color.rgb(0,0,0));


        //Split content view
        if(hinge_angle>30 && hinge_angle<150 && (display.getRotation()==Surface.ROTATION_90 || display.getRotation()==Surface.ROTATION_270) ){

            c.drawRect(0,0, c.getWidth(),c.getHeight()/2,red_fill);

        }
        else if (hinge_angle>=150){//full tablet view

        }
        else if (hinge_angle<=30){ //small screen view

        }

        String s="";
        for(int i=0;i<fingers_down;i++){
            s+="("+touch_x[i]+","+touch_y[i]+")";
        }

        c.drawText("angle="+hinge_angle+" "+s, 20, c.getHeight()-20, white_text);

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

    int fingers_down=0;
    float[] touch_x=new float[5];
    float[] touch_y=new float[5];

    private void processTouchEvent(){
            for(int i=0;i<fingers_down;i++){
                int x=(int)touch_x[i];
                int y=(int)touch_y[i];

                



            }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                fingers_down=1;
                for(int i=0;i<touch_x.length;i++){
                    touch_x[i]=0;
                    touch_y[i]=0;
                }
                touch_x[0]=event.getX();
                touch_y[0]=event.getY();
                processTouchEvent();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                fingers_down=event.getPointerCount();
                for(int i=0;i<touch_x.length;i++){
                    touch_x[i]=0;
                    touch_y[i]=0;
                }
                for(int i=0;i<event.getPointerCount()&& i<touch_x.length;i++){
                    touch_x[i] = event.getX(i);
                    touch_y[i] = event.getY(i);
                }
                processTouchEvent();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                fingers_down=event.getPointerCount()-1;
                int pointerIndex = event.getActionIndex();
                for(int i=0;i<touch_x.length;i++){
                    touch_x[i]=0;
                    touch_y[i]=0;
                }
                for(int i=0;i<event.getPointerCount()&& i<touch_x.length;i++){
                    if(i<pointerIndex) {
                        touch_x[i] = event.getX(i);
                        touch_y[i] = event.getY(i);
                    }else if(i>pointerIndex){
                        touch_x[i-1] = event.getX(i);
                        touch_y[i-1] = event.getY(i);
                    }
                }
                processTouchEvent();
                break;
            case MotionEvent.ACTION_UP:
                fingers_down=0;
                for(int i=0;i<touch_x.length;i++){
                    touch_x[i]=0;
                    touch_y[i]=0;
                }
                processTouchEvent();
                break;
            case MotionEvent.ACTION_CANCEL:
                fingers_down=0;
                for(int i=0;i<touch_x.length;i++){
                    touch_x[i]=0;
                    touch_y[i]=0;
                }
                processTouchEvent();
                break;
        }

        return true;
    }
}
