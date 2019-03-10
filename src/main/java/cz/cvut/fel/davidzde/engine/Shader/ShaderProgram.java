package cz.cvut.fel.davidzde.engine.Shader;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int programId = glCreateProgram();

    private int vertexShaderId;
    private int fragmentShaderId;

    private HashMap<UniformName, Integer> uniforms = new HashMap<>(); // TODO create uniform a set uniform

    public ShaderProgram(int vertexShaderId, int fragmentShaderId) {
        this.vertexShaderId = vertexShaderId;
        this.fragmentShaderId = fragmentShaderId;
    }

    public void link() {
        glAttachShader(programId, vertexShaderId);
        glAttachShader(programId, fragmentShaderId);

        glLinkProgram(programId);

        // cleanup
        glDetachShader(programId, vertexShaderId);
        glDetachShader(programId, fragmentShaderId);
        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        // debug
        glValidateProgram(programId);
    }

    public void use() {
        glUseProgram(programId);
    }

    public void createUniform(UniformName name) {
        int uniformLocation = glGetUniformLocation(programId, name.uniformName); // todo getter

        if (uniformLocation < 0) try {
            throw new Exception("No uniform linked");
        } catch (Exception e) {
            e.printStackTrace();
        }

        uniforms.put(name, uniformLocation);
    }

    public void setUniform(UniformName uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(UniformName uniformName, Matrix4f matrix4f) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix4f.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    // TODO interface destroyable
    public void destroy() {
        glDeleteProgram(programId);
    }
}
