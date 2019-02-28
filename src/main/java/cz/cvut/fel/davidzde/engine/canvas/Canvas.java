package cz.cvut.fel.davidzde.engine.canvas;

import static org.lwjgl.opengl.GL11.*;

public class Canvas {
    // TODO generate inteface update raender
    public Canvas() {

    }

    public void update() {

    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(1f, 0f, 0f, 1f);
    }

}
