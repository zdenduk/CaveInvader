package cz.cvut.fel.davidzde.engine.canvas;

import cz.cvut.fel.davidzde.engine.AttributeLocations;
import cz.cvut.fel.davidzde.engine.Shader.FragmentShader;
import cz.cvut.fel.davidzde.engine.Shader.ShaderProgram;
import cz.cvut.fel.davidzde.engine.Shader.UniformName;
import cz.cvut.fel.davidzde.engine.Shader.VertexShader;
import cz.cvut.fel.davidzde.engine.draw.GameObject;
import cz.cvut.fel.davidzde.engine.draw.Mesh;
import cz.cvut.fel.davidzde.engine.texture.Texture;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Canvas {

    int[] indicies = {0, 1, 3, 3, 1, 2};
    float[] verticies = {
            -0.25f, 0.25f, 0,
            0.25f, 0.25f, 0,
            0.25f, -0.25f, 0,
            -0.25f, -0.25f, 0,
    };

    float[] verticies2 = {
            0.75f, 0.75f, 0,
            1f, 0.75f, 0,
            1f, 1f, 0,
            0.75f, 1f, 0,
    };

    private float[] colors = {
            0, 0, 0,
            0, 0, 0,
            1, 1, 1,
            1, 1, 1
    };

    ShaderProgram sp;
    Mesh mesh;
    Mesh mesh2;
    GameObject object;
    private long window;

    // TODO generate inteface update render
    public Canvas(long window) {
        this.window = window;
        VertexShader vs = new VertexShader("res/shaders/vertexShader.glsl");
        FragmentShader fs = new FragmentShader("res/shaders/FragmentShader.glsl");

        Texture texture = new Texture("res/sprites/generic-rpg-bridge.png");

        float[] textureClip = {
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        mesh = new Mesh(verticies, indicies, colors, texture, textureClip);

        mesh2 = new Mesh(verticies2, indicies, colors, texture, textureClip);

        object = new GameObject(mesh, new Vector2f(0.1f, 0.1f));

        sp = new ShaderProgram(vs.getShaderId(), fs.getShaderId());
        sp.link();

        sp.createUniform(UniformName.TEXTURE);
        sp.setUniform(UniformName.TEXTURE, 0);

        sp.createUniform(UniformName.MATRIX);


    }

    public void update() {
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            object.getMatrix().translate(0.0f, 0.001f, 0f);
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glClearColor(0f, 0f, 0f, 1f);

        sp.use();
        drawWE();
    }


    private void drawWE() {
        sp.setUniform(UniformName.MATRIX, object.getMatrix());
        glEnableVertexAttribArray(AttributeLocations.POSITION);
        glEnableVertexAttribArray(AttributeLocations.COLOR);
        glEnableVertexAttribArray(AttributeLocations.TEXTURE);

        glActiveTexture(mesh.getTexture().getId());

        glBindTexture(GL_TEXTURE_2D, mesh.getTexture().getId());

        glBindVertexArray(mesh.getVaoid());
        glDrawElements(GL_TRIANGLES, indicies.length, GL_UNSIGNED_INT, 0);

        glEnableVertexAttribArray(AttributeLocations.POSITION);
        glEnableVertexAttribArray(AttributeLocations.COLOR);
        glEnableVertexAttribArray(AttributeLocations.TEXTURE);

        glActiveTexture(mesh2.getTexture().getId());

        glBindTexture(GL_TEXTURE_2D, mesh2.getTexture().getId());

        glBindVertexArray(mesh2.getVaoid());
        glDrawElements(GL_TRIANGLES, indicies.length, GL_UNSIGNED_INT, 0);
    }

}
