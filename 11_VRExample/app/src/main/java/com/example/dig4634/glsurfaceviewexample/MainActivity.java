package com.example.dig4634.glsurfaceviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    MyRenderer my_renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GLSurfaceView surfaceView=findViewById(R.id.surfaceView);
        surfaceView.setEGLContextClientVersion(3);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setEGLConfigChooser(8,8,8,8,16,0);
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);


        my_renderer=new MyRenderer(this);
       // my_renderer.showCamera(findViewById(R.id.textureView));

        surfaceView.setRenderer(my_renderer);
        surfaceView.setOnTouchListener(my_renderer);


    }



    @Override
    public void onPause(){

        my_renderer.pauseCamera();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        my_renderer.resumeCamera();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
