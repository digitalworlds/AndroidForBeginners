package gl.shaders;

import gl.Shader;

public class ColorShader extends Shader {

    public ColorShader(){
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
                "in vec3 aColor;" +
                        "out vec3 vColor;"+
                "void main() {"  +
                "vColor=aColor;"+
                "gl_Position = uPMatrix*uMVMatrix *localTransform* vec4(aPosition,1.0);" +
                "}",




                        "#version 300 es\n"+
                                "precision mediump float;" +
                    "in vec3 vColor;"+
                                "out vec4 myOutputColor;"+
                "void main() {" +
                "  myOutputColor = vec4(vColor,1.0);" +
                "}",new String[]{"aPosition","aColor"});
    }

}
