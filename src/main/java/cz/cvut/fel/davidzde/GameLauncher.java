package cz.cvut.fel.davidzde;

import cz.cvut.fel.davidzde.window.Window;
import org.lwjgl.system.Configuration;

public class GameLauncher {

    public static void main(String[] args) {
        new Window(800, 600, "Cave Invader");
    }

}
