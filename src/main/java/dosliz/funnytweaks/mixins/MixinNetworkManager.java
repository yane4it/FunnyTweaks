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

import java.text.SimpleDateFormat;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager implements INetworkManager {

    @Shadow private Channel channel;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
    private void onSendPacketHead(Packet<?> packetIn, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        boolean debugMode = true;

        if (debugMode) {
            long now = System.currentTimeMillis();
            String timeStr = timeFormat.format(new java.util.Date(now));

            int currentTick = -1;
            try {
                if (net.minecraft.client.Minecraft.getMinecraft().thePlayer != null) {
                    currentTick = net.minecraft.client.Minecraft.getMinecraft().thePlayer.ticksExisted;
                }
            } catch (Exception ignored) {}

            String tickPrefix = (currentTick != -1) ? "[Tick: " + currentTick + "]" : "[Tick: N/A]";

            if (packetIn instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
                net.minecraft.network.play.client.C02PacketUseEntity c02 = (net.minecraft.network.play.client.C02PacketUseEntity) packetIn;
                System.out.println(String.format("[FT-DEBUG] %s [%s] C02_ATTACK | Action: %s | MS: %d",
                        tickPrefix, timeStr, c02.getAction().toString(), now));
            }
            else if (packetIn instanceof net.minecraft.network.play.client.C08PacketPlayerBlockPlacement) {
                System.out.println(String.format("[FT-DEBUG] %s [%s] C08_BLOCK | MS: %d",
                        tickPrefix, timeStr, now));
            }
            else if (packetIn instanceof net.minecraft.network.play.client.C07PacketPlayerDigging) {
                net.minecraft.network.play.client.C07PacketPlayerDigging c07 = (net.minecraft.network.play.client.C07PacketPlayerDigging) packetIn;
                if (c07.getStatus() == net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM) {
                    System.out.println(String.format("[FT-DEBUG] %s [%s] C07_UNBLOCK | Action: %s | MS: %d",
                            tickPrefix, timeStr, c07.getStatus().toString(), now));
                }
            }
        }

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