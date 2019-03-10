package cz.cvut.fel.davidzde.engine.canvas;

import cz.cvut.fel.davidzde.engine.AttributeLocations;
import cz.cvut.fel.davidzde.engine.shader.FragmentShader;
import cz.cvut.fel.davidzde.engine.shader.ShaderProgram;
import cz.cvut.fel.davidzde.engine.shader.UniformName;
import cz.cvut.fel.davidzde.engine.shader.VertexShader;
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

    int[] indices = {0, 1, 3, 3, 1, 2};
    float[] positions = {
            -0.25f, 0.25f, 0,
            0.25f, 0.25f, 0,
            0.25f, -0.25f, 0,
            -0.25f, -0.25f, 0,
    };

    private float[] colors = {
            0, 0, 0,
            0, 0, 0,
            1, 1, 1,
            1, 1, 1
    };

    ShaderProgram sp;
    Mesh mesh;
    GameObject object;
    private long window;
    GameObject[][] gameObjects;

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

        mesh = new Mesh(positions, indices, colors, texture, textureClip);

        object = new GameObject(mesh, new Vector2f(0.0f, 0.0f));
        object.getMatrix().translate(-0.75f, 0.75f, 0f); // set to left top corner

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

    private void drawAll() {
        for (int i = 0; i < 16; i++) {

        }
    }

    private void drawWE() {
        sp.setUniform(UniformName.MATRIX, object.getMatrix());

        glEnableVertexAttribArray(AttributeLocations.POSITION);
        glEnableVertexAttribArray(AttributeLocations.COLOR);
        glEnableVertexAttribArray(AttributeLocations.TEXTURE);

        glActiveTexture(mesh.getTexture().getId());

        glBindTexture(GL_TEXTURE_2D, mesh.getTexture().getId());

        glBindVertexArray(mesh.getVaoid());
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }

}
