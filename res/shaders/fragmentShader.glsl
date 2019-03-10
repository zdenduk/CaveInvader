#version 400 core

in vec3 outColor;
in vec2 outTexture;

out vec4 FragColor;

uniform sampler2D textureSampler;

void main() {

    if(outTexture != vec2(0, 0)) {
            vec4 textureWithAlpha = texture(textureSampler, outTexture);
                if(textureWithAlpha.a < 0.1)
                    discard;
                FragColor = textureWithAlpha;
        } else {
            FragColor = vec4(outColor, 1);
        }
}