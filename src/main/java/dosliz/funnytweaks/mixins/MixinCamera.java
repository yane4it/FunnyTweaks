package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class MixinCamera {

    @ModifyVariable(method = "updateCameraAndRender", at = @At("HEAD"), argsOnly = true)
    private float modifyPartialTicks(float partialTicks) {
        if (!FunnyTweaksConfig.cameraTweaks) {
            return partialTicks;
        }
        return partialTicks * (float) FunnyTweaksConfig.cameraTweaksMultiplier;
    }
}