package gl.models;


import gl.ObjectMaker;
import gl.modeltypes.ShadedColoredModel;

public class Tree extends ShadedColoredModel {

    public Tree(){

        ObjectMaker om=new ObjectMaker();


        om.color(101/255f,67/255f,33/255f);//brown
        om.cylinderY(0.25f,1,0.25f,16);
        om.translate(0,0.5f,0);
        om.color(0,0.5f,0);//green
        om.pyramid(1f,1f,1f);
        om.rotateY(45);
        om.pyramid(1f,1f,1f);
        om.translate(0,0.5f,0);
        om.pyramid(0.75f,1f,0.75f);
        om.rotateY(45);
        om.pyramid(0.75f,1f,0.75f);
        om.translate(0,0.5f,0);
        om.pyramid(0.5f,1f,0.5f);
        om.rotateY(45);
        om.pyramid(0.5f,1f,0.5f);

        om.flush(this);

    }

}
