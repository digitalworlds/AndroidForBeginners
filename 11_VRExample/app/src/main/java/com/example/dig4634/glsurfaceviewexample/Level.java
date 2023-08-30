package com.example.dig4634.glsurfaceviewexample;

import gl.Texture;

public class Level {

    int segments=0;
    LevelSegment[] level_segments;

    public Level(int s, Texture texture, Texture texture_c){
        segments=s;

        level_segments=new LevelSegment[s];
        for(int i=0;i<segments;i++){
            level_segments[i]=new LevelSegment();
            level_segments[i].setTexture(texture);
            level_segments[i].positionZ=-5-i*4;
            level_segments[i].positionY=-2;
            level_segments[i].speedZ=1;
            level_segments[i].segments=s;

            if(Math.random()<0.5) {
                level_segments[i].collectable = new Collectable();
                level_segments[i].collectable.setTexture(texture_c);
                level_segments[i].collectable.positionZ = -5 - i * 4;
                level_segments[i].collectable.positionY = 1;
                level_segments[i].collectable.speedZ = 1;
                level_segments[i].collectable.segments = s;
            }
        }
    }

    public void simulate(float perSec){
        for(int i=0;i<segments;i++) {
            level_segments[i].simulate(perSec);
            if(level_segments[i].collectable!=null)
                    level_segments[i].collectable.simulate(perSec);
        }
    }

    public void draw(){
        for(int i=0;i<segments;i++) {
            level_segments[i].draw();
            if(level_segments[i].collectable!=null && level_segments[i].collectable.shown)
                level_segments[i].collectable.draw();
        }
    }

}
