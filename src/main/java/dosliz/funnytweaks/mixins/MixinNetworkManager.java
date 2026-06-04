package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import dosliz.funnytweaks.interfaces.INetworkManager;
import io.netty.channel.Channel;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

    @Shadow private Channel channel;

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"))
    private void onSendPacket(Packet<?> packetIn, CallbackInfo ci) {
        if (!FunnyTweaksConfig.packetFlush) return;
        if (this.channel != null && this.channel.isOpen()) {
            if (packetIn instanceof C02PacketUseEntity || packetIn instanceof C08PacketPlayerBlockPlacement) {
                this.channel.flush();
            }
        }
    }

    @Override
    public void flushChannel() {
        if (this.channel != null && this.channel.isOpen()) {
            this.channel.flush();
        }
    }
}