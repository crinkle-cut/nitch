package net.mine_diver.smoothbeta.client.render;

import net.mine_diver.smoothbeta.client.render.gl.Program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.mine_diver.smoothbeta.SmoothBeta.LOGGER;

public class Shaders {

    private static Shader terrainShader;
    private static boolean initialized = false;

    public static void init() {
        // Don't load shaders here - OpenGL context not available yet
        // Shaders will be loaded lazily on first access
    }

    private static void loadShaders() {
        if (initialized)
            return;
        initialized = true;

        // Clear old program cache
        List<Program> list = new ArrayList<>();
        list.addAll(Program.Type.FRAGMENT.getProgramCache().values());
        list.addAll(Program.Type.VERTEX.getProgramCache().values());
        list.forEach(Program::release);

        if (terrainShader != null) {
            terrainShader.close();
        }

        try {
            terrainShader = new Shader("terrain", VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
        } catch (IOException e) {
            LOGGER.error("Could not load terrain shader", e);
            throw new RuntimeException("Could not load terrain shader", e);
        }
    }

    public static Shader getTerrainShader() {
        if (!initialized) {
            loadShaders();
        }
        return terrainShader;
    }
}
