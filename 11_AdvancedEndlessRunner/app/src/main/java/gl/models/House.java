package gl.models;


import gl.ObjectMaker;
import gl.modeltypes.ShadedColoredModel;

public class House extends ShadedColoredModel {

    public House(){

        ObjectMaker om=new ObjectMaker();


        om.color(1,1,1);//pick white color
        om.box(1,1,1);
        om.translate(0,0.5f,0);
        om.color(1,0,0);//pick red color
        om.pyramid(1,1,1);



        om.flush(this);
    }

}
