package cz.cvut.fel.davidzde.window;

import cz.cvut.fel.davidzde.util.Timer;
import cz.cvut.fel.davidzde.engine.canvas.Canvas;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width;
    private int height;

    private String title;

    private long window;

    private Canvas canvas;

    public static final int TARGET_FPS = 75;

    public static final int TARGET_UPS = 30;

    private Timer timer;


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
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

        glfwSwapInterval(1);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glEnable(GL_DEPTH);

        Callback debugProc = GLUtil.setupDebugMessageCallback(); // may return null if the debug mode is not available

        timer = new Timer();
        timer.init();

// cleanup
        if (debugProc != null)
            debugProc.free();

        System.out.println("OpenGL version: " + glGetString(GL_VERSION));

        Canvas canvas = new Canvas(window);

        // game loop TODO fix this with game loop
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        while (!glfwWindowShouldClose(window)) {

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            while (accumulator >= interval) {

                canvas.update();
                accumulator -= interval;
            }

            canvas.render();
            update();
            sync();
        }
    }

    public long getWindow() {
        return window;
    }

    public void update() {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    private void destroy() { // TODO do interface for this
        glfwDestroyWindow(window);
        glfwTerminate();
    }


}
