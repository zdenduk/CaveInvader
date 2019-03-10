package cz.cvut.fel.davidzde.engine.draw;

import cz.cvut.fel.davidzde.engine.AttributeLocations;
import cz.cvut.fel.davidzde.engine.texture.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {

    private int[] indicies;
    private float[] verticies;

    private int vaoid;
    private int vboid;

    private Texture texture;

    public Mesh(float[] verticies, int[] indicies, float[] colors, Texture texture, float[] textureClip) {
        vaoid = glGenVertexArrays();
        vboid = glGenBuffers();

        glBindVertexArray(vaoid);

        setVerticies(verticies);
        setIndicies(indicies);
        setColor(colors);
        setTexture(texture, textureClip);
    }

    private void setIndicies(int[] indicies) {
        this.indicies = indicies;
        int indicesID = glGenBuffers();

        IntBuffer indiciesBuffer = BufferUtils.createIntBuffer(indicies.length);
        indiciesBuffer.put(indicies).flip();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiciesBuffer, GL_STATIC_DRAW);
    }

    private void setVerticies(float[] verticies) {
        this.verticies = verticies;

        FloatBuffer verticiesBuffer = BufferUtils.createFloatBuffer(verticies.length);
        verticiesBuffer.put(verticies).flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboid);
        glBufferData(GL_ARRAY_BUFFER, verticiesBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(AttributeLocations.POSITION, 3, GL_FLOAT, false, 0, 0);
    }

    private void setColor(float[] colors) {
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
        colorBuffer.put(colors).flip();

        int colorsId = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, colorsId);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(AttributeLocations.COLOR, 3, GL_FLOAT, false, 0, 0);

    }

    private void setTexture(Texture texture, float[] textureClip) {
        this.texture = texture;

        FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(textureClip.length);
        textureBuffer.put(textureClip).flip();

        int texturePosId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texturePosId);
        glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(AttributeLocations.TEXTURE, 2, GL_FLOAT, false, 0, 0);
    }

    public Texture getTexture() {
        return texture;
    }

    public int getVaoid() {
        return vaoid;
    }
}
