package cz.cvut.fel.davidzde.engine.Shader;

public enum UniformName {
    TEXTURE("textureSampler"),
    MATRIX("objectMatrix");

    public String uniformName;

    UniformName(String uniformName) {
        this.uniformName = uniformName;
    }
}
