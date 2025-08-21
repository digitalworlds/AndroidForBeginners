package com.example.dig4634.developedexample;

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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class GameActivity extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback {

    World world;
    Body circleBody[];
    Body groundBody;

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
        setContentView(R.layout.activity_game);


        // Initialize JBox2D World with gravity
        Vec2 gravity = new Vec2(0, -10);
        world = new World(gravity);

        // Define ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 0); // JBox2D units
        groundBody = world.createBody(groundBodyDef);

        // Define ground shape
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50, 1); // Large enough width

        // Attach shape to the body
        groundBody.createFixture(groundBox, 0.0f);

        circleBody=new Body[100];

        for(int i=0;i<circleBody.length;i++) {
            // Define body
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC; // Dynamic to enable movement
            bodyDef.position.set((float)(0+Math.random()*2-1), (float)(10+Math.random()*2-1)); // JBox2D units
            circleBody[i] = world.createBody(bodyDef);

            // Define shape
            CircleShape circleShape = new CircleShape();
            circleShape.m_radius = 1.0f; // Radius in JBox2D units

            // Define fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = circleShape;
            fixtureDef.density = 1.0f; // Adjust mass
            fixtureDef.friction = 0.3f;
            fixtureDef.restitution = 0.8f; // Bounciness (0 = no bounce, 1 = perfect bounce)

            // Attach fixture to body
            circleBody[i].createFixture(fixtureDef);
        }


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

        planet_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.planet),110,110,false);
        sun_image=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.planet2),110,110,false);

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

        world.step(1.0f / 20.0f, 6, 2); // Physics update


/*
        sun.angle+=5;

        sun.y_position+=5;

        planet.updateWithAccelerometer(width,height,acc_x,acc_y);
        planet2.updateWithAccelerometer(width,height,acc_x,acc_y);


        if(planet.collidesWith(sun)||planet2.collidesWith(sun) ) {
            message = "You won Level "+CurrentLevel;
            GlobalVariables.passed_level[CurrentLevel-1]=true;
        }
*/
    }

    public void draw(){

        if(holder==null)return;




        Canvas c=holder.lockCanvas();

        update(c.getWidth(),c.getHeight());

        c.drawColor(Color.BLACK);

        //c.drawCircle(c.getWidth()/2,c.getHeight()/2,100,white_stroke);

        Vec2 position = groundBody.getPosition();
        float X = position.x * 50 + c.getWidth() / 2; // Scale and center
        float Y = c.getHeight() - position.y * 50;

        // Draw ground (in pixels)
        c.drawRect(0, Y-50, c.getWidth(), c.getHeight(), white_stroke);

        for(int i=0;i<circleBody.length;i++) {

            // Draw circle (convert from JBox2D to screen coordinates)
            position = circleBody[i].getPosition();
            X = position.x * 50 + c.getWidth() / 2; // Scale and center
            Y = c.getHeight() - position.y * 50;    // Invert Y-axis

            c.save();
                c.translate(X,Y);
                c.rotate(-(float)(circleBody[i].getAngle()*180.0/Math.PI));
                c.drawBitmap(planet_image,-55,-55,null);
            c.restore();

            //c.drawBitmap(planet_image,X-55,Y-55,null);
            c.drawCircle(X, Y, 50, white_stroke); // Scale radius
        }

        //c.drawColor(Color.rgb(210,210,255));

        //c.drawRect(0,0, 200,200,red_fill);

        //c.drawCircle(c.getWidth()/2,c.getHeight()/2,100,white_stroke);


        /*sun.draw(c);
        planet.draw(c);
        planet2.draw(c);


        c.drawText(message, 20, c.getHeight()-20, white_text);*/

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
