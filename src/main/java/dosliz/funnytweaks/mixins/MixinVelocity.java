package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinVelocity {

    @Inject(method = "handleEntityVelocity", at = @At("HEAD"), cancellable = true)
    private void onVelocity(S12PacketEntityVelocity packet, CallbackInfo ci) {

        if (!FunnyTweaksConfig.customVelocity) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null) {
            ci.cancel();
            return;
        }

        Entity entity = mc.theWorld.getEntityByID(packet.getEntityID());

        if (entity != null) {
            entity.motionX = packet.getMotionX() / 8000.0D;
            entity.motionY = packet.getMotionY() / 8000.0D;
            entity.motionZ = packet.getMotionZ() / 8000.0D;
        }

        ci.cancel();
    }
}