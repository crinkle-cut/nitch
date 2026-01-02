package net.mine_diver.smoothbeta;

import net.fabricmc.api.ModInitializer;
import net.mine_diver.smoothbeta.api.util.Namespace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmoothBeta implements ModInitializer {
    public static final String MOD_ID = "smoothbeta";
    public static final Namespace NAMESPACE = Namespace.of(MOD_ID);

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

    }
}
