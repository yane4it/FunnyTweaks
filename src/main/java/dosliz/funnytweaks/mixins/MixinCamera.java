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
        if (!FunnyTweaksConfig.partialTicks) {
            return partialTicks;
        }

        float m = (float) FunnyTweaksConfig.partialTicksMultiplier;

        if (m == 1.0f) {
            return partialTicks;
        }

        float p = partialTicks;
        float p2 = p * p;
        float p3 = p2 * p;

        float modifiedTicks = (m - 1.0f) * p3 + (2.0f - 2.0f * m) * p2 + m * p;

        return Math.max(0.0f, Math.min(1.0f, modifiedTicks));
    }
}