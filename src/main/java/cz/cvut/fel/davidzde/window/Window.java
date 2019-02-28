package cz.cvut.fel.davidzde.window;

import cz.cvut.fel.davidzde.engine.canvas.Canvas;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width;
    private int height;

    private String title;

    private long window;

    private Canvas canvas;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;

        init();
    }

    private void init() {
        if (!glfwInit()) {
            System.out.println("eror"); // TODO throw excp
            System.exit(-1);
        }
        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (window == NULL) {
            System.out.println("eror"); // TODO throw exp
            destroy();
            System.exit(-1);
        }

        // add opengl  to window
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        System.out.println("OpenGL version: " + glGetString(GL_VERSION));

        Canvas canvas = new Canvas();

        // game loop TODO fix this with game loop
        while (!glfwWindowShouldClose(window)) {
            canvas.update();
            canvas.render();

            glfwPollEvents();
            glfwSwapBuffers(window);
        }
    }

    private void destroy() { // TODO do interface for this
        glfwDestroyWindow(window);
        glfwTerminate();
    }

}
