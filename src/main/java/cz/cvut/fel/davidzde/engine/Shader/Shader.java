package cz.cvut.fel.davidzde.engine.Shader;

import cz.cvut.fel.davidzde.util.FileUtil;

import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glCompileShader;

public abstract class Shader {

    private int shaderId = createShaderId();

    protected Shader(String fileName) {
        String fileContent = null;
        try {
            fileContent = FileUtil.readText(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        glShaderSource(shaderId, fileContent);
        glCompileShader(shaderId);
        System.out.println(glGetShaderInfoLog(shaderId));
    }

    public int getShaderId() {
        return shaderId;
    }

    protected abstract int createShaderId();
}
