package cz.cvut.fel.davidzde.engine.Shader;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCreateShader;

public final class VertexShader extends Shader {

    public VertexShader(String fileName) {
        super(fileName);
    }

    @Override
    protected int createShaderId() {
        return glCreateShader(GL_VERTEX_SHADER);
    }
}
