package cz.cvut.fel.davidzde.engine.texture;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    public int getId() {
        return id;
    }

    private int id;

    private int width;
    private int height;

    String path;

    public Texture(String path) {
        this.id = glGenTextures();
        this.path = path;

        MemoryStack stack = MemoryStack.create();
        IntBuffer widthBuffer = stack.mallocInt(1);
        IntBuffer heightBuffer = stack.mallocInt(1);
        IntBuffer componentBuffer = stack.mallocInt(1);

        ByteBuffer image = STBImage.stbi_load(path, widthBuffer, heightBuffer, componentBuffer, 4);

        this.width = widthBuffer.get();
        this.height = heightBuffer.get();

        image.flip();

        glBindTexture(GL_TEXTURE_2D, id);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

}

