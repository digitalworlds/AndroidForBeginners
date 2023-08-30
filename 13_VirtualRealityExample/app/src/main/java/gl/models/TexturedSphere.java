package gl.models;

import gl.ObjectMaker;
import gl.modeltypes.ShadedTexturedModel;

public class TexturedSphere extends ShadedTexturedModel {

    public TexturedSphere(float width, float height, float depth)
    {
        this(width,height,depth,16);
    }

    public TexturedSphere(float width, float height, float depth, int resolution){
        ObjectMaker om=new ObjectMaker();
        om.sphere(width,height,depth,resolution);
        om.flush(this);
    }

}
