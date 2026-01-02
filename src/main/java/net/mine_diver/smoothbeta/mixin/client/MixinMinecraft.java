package net.mine_diver.smoothbeta.mixin.client;

import net.mine_diver.smoothbeta.client.render.Shaders;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "init()V", at = @At("RETURN"))
    private void smoothbeta_init(CallbackInfo ci) {
        Shaders.init();
    }
}
