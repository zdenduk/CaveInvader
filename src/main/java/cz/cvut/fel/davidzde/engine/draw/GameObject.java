package cz.cvut.fel.davidzde.engine.draw;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class GameObject {

    private Mesh mesh;
    private Matrix4f matrix;

    public GameObject(Mesh mesh, Vector2f pos) {
        this.mesh = mesh;
        matrix = new Matrix4f().identity().translate(pos.x, pos.y, 0f);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }
}
