package gl.shaders;


import gl.Shader;

public class TextureShader extends Shader {

    public TextureShader(){
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
                "void main() {"  +
                "vUV=aUV;"+
                "gl_Position = uPMatrix*uMVMatrix *localTransform* vec4(aPosition,1.0);" +
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
