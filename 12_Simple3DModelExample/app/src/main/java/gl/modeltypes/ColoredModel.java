package gl.modeltypes;

import android.opengl.GLES30;

import gl.Mesh;
import gl.Shader;
import gl.shaders.ColorShader;


public class ColoredModel extends Mesh {

    public ColoredModel(){
        super();

        Shader s=new ColorShader();
        setShader(s);
        localTransform.updateShader();
    }

    public void setColors(float[] colors){
        setArrayBuffer3f(colors,1);
    }

    public void draw(){
        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }
}

