package net.mine_diver.smoothbeta.client;

import net.fabricmc.api.ClientModInitializer;
import net.mine_diver.smoothbeta.client.render.Shaders;

public class SmoothBetaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Shaders.init();
    }
}
