package cz.cvut.fel.davidzde.engine.Shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.glCreateShader;

public class FragmentShader extends Shader {

    public FragmentShader(String fileName) {
        super(fileName);
    }

    @Override
    protected int createShaderId() {
        return glCreateShader(GL_FRAGMENT_SHADER);
    }

}
