package gl.modeltypes;

import android.opengl.GLES30;

import gl.Mesh;
import gl.Shader;
import gl.Texture;
import gl.shaders.MatCapShader;

public class MatCapModel extends Mesh {

    public MatCapModel(){
        this(new MatCapShader());
    }

    public MatCapModel(Shader s){
        super();


        setShader(s);
        localTransform.updateShader();
    }

    protected Texture matcap=null;

    public void setMatCap(Texture matcap){
        this.matcap=matcap;
        int mTextureHandle = GLES30.glGetUniformLocation(shader.shaderProgram, "uMatCap");
        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES30.glUniform1i(mTextureHandle, matcap.slot);
    }

    public void setNormals(float[] normals){
        setArrayBuffer2f(normals,1);
    }

    public void draw(){
        GLES30.glUseProgram(shader.shaderProgram);
        GLES30.glBindVertexArray( vertexArrayObject ) ;


        if(matcap!=null) {
            //shader.setUniformInteger("uTexture", texture.slot);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + matcap.slot);
            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, matcap.data);
        }

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, triangleLength, GLES30.GL_UNSIGNED_SHORT,0);
        GLES30.glBindVertexArray( 0 );
        GLES30.glUseProgram( 0 );

    }
}

