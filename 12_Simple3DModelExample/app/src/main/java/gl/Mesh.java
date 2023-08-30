package gl;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Mesh {

    protected Shader shader;

    protected int triangleLength=0;

    protected int vertexArrayObject;
    private int indexBuffer=0;
    private int[] arrayBuffers;

    public Transform localTransform;

    public Mesh() {
        localTransform=new Transform(this);
        int[] i=new int[1];
        GLES30.glGenVertexArrays( 1,i,0 );
        vertexArrayObject=i[0];
        arrayBuffers=new int[0];
    }

    private int getArrayBuffer(int slot){
        if(slot<arrayBuffers.length){
            if(arrayBuffers[slot]==0){
                int i[] = new int[1];
                GLES30.glGenBuffers(1, i, 0);
                arrayBuffers[slot] = i[0];
            }
            return arrayBuffers[slot];
        }else{
            int[] newarray=new int[slot+1];
            for(int i=0;i<arrayBuffers.length;i++)newarray[i]=arrayBuffers[i];
            arrayBuffers=newarray;

            int i[] = new int[1];
            GLES30.glGenBuffers(1, i, 0);
            arrayBuffers[slot] = i[0];

            return arrayBuffers[slot];
        }
    }

    public void setXYZ(float[] vertices){
        setArrayBuffer3f(vertices,0);
    }

    public void setArrayBuffer1f(float[] array,int slot){
        setArrayBufferf(array,1,slot);
    }
    public void setArrayBuffer2f(float[] array,int slot){
        setArrayBufferf(array,2,slot);
    }
    public void setArrayBuffer3f(float[] array,int slot){
        setArrayBufferf(array,3,slot);
    }
    public void setArrayBuffer4f(float[] array,int slot){
        setArrayBufferf(array,4,slot);
    }

    private void setArrayBufferf(float[] array,int dimensions,int slot){
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                array.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vBuffer = bb.asFloatBuffer();
        vBuffer.put(array);
        vBuffer.position(0);

        int arrayBuffer=getArrayBuffer(slot);
        GLES30.glBindBuffer( GLES30.GL_ARRAY_BUFFER, arrayBuffer ) ;
        GLES30.glBufferData( GLES30.GL_ARRAY_BUFFER, array.length*4, vBuffer, GLES30.GL_STATIC_DRAW );
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER,0);


        GLES30.glBindVertexArray( vertexArrayObject );
        GLES30.glBindBuffer( GLES30.GL_ARRAY_BUFFER, arrayBuffer );

        GLES30.glEnableVertexAttribArray( slot );
        GLES30.glVertexAttribPointer(slot, dimensions,
                GLES30.GL_FLOAT, false,
                0, 0);

        GLES30.glBindVertexArray(0);
    }


    public void setTriangles(short[] triangles){

        triangleLength=triangles.length;

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                triangles.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer triangleBuffer = dlb.asShortBuffer();
        triangleBuffer.put(triangles);
        triangleBuffer.position(0);

        if(indexBuffer==0) {
            int[] i = new int[1];
            GLES30.glGenBuffers(1, i, 0);
            indexBuffer = i[0];
        }
        GLES30.glBindBuffer( GLES30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer) ;
        GLES30.glBufferData( GLES30.GL_ELEMENT_ARRAY_BUFFER, triangles.length*2, triangleBuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer( GLES30.GL_ELEMENT_ARRAY_BUFFER, 0 ) ;

        GLES30.glBindVertexArray( vertexArrayObject );
        GLES30.glBindBuffer( GLES30.GL_ELEMENT_ARRAY_BUFFER, indexBuffer );
        GLES30.glBindVertexArray(0);
    }

    public void setShader(Shader s){
        shader=s;
    }

    public void simulate(double elapsedDisplayTime, double perSec){};

    public void draw() {

        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }


}
