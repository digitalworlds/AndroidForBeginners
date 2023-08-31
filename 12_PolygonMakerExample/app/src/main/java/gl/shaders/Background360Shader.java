package gl.shaders;


import gl.Shader;

public class Background360Shader extends Shader {

    public Background360Shader(){
        super(

                "#version 300 es\n"+
                        "uniform SceneMatrices\n"+
                        "{\n"+
                        "uniform mat4 uMVMatrix;\n" +
                        "uniform mat4 uPMatrix;\n" +
                        "uniform vec3 uLightDir;\n"+
                        "};\n"+

                "uniform mat4 localTransform;\n"+
                "in vec3 aPosition;" +
                "in vec2 aUV;" +
                    "out vec2 vUV;"+
                        "mat4 trans;"+
                "void main() {"  +
                "vUV=aUV;"+
                        "trans=uMVMatrix *localTransform;"+
                        "trans[3][0]=0.0;"+
                        "trans[3][1]=0.0;"+
                        "trans[3][2]=0.0;"+
                        "trans[3][3]=1.0;"+
                "gl_Position = uPMatrix*trans* vec4(aPosition,1.0);" +
                "}",




                        "#version 300 es\n"+
                                "precision mediump float;" +
        "uniform sampler2D uTexture;" +
                    "in vec2 vUV;"+
                                "out vec4 myOutputColor;"+
                "void main() {" +
                "vec4 color=texture(uTexture, vUV);"+
                "  myOutputColor = color;" +
                "}",new String[]{"aPosition","aUV"});
    }

}
