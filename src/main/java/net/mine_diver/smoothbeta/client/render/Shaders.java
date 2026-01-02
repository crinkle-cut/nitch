package net.mine_diver.smoothbeta.client.render;

import net.mine_diver.smoothbeta.api.resource.ResourceManager;
import net.mine_diver.smoothbeta.api.util.Identifier;
import net.mine_diver.smoothbeta.client.render.gl.Program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.mine_diver.smoothbeta.SmoothBeta.NAMESPACE;

public class Shaders {
    public static final Identifier ID = NAMESPACE.id("shaders");

    private static Shader terrainShader;

    public static void init() {
        List<Program> list = new ArrayList<>();
        list.addAll(Program.Type.FRAGMENT.getProgramCache().values());
        list.addAll(Program.Type.VERTEX.getProgramCache().values());
        list.forEach(Program::release);

        if (terrainShader != null) {
            terrainShader.close();
        }

        try {
            terrainShader = new Shader(ResourceManager.INSTANCE, "terrain",
                    VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
        } catch (IOException e) {
            throw new RuntimeException("Could not load terrain shader", e);
        }
    }

    public static Shader getTerrainShader() {
        return terrainShader;
    }

    public static Identifier getId() {
        return ID;
    }
}
