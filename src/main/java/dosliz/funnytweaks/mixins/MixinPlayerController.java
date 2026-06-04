package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import dosliz.funnytweaks.Post;
import dosliz.funnytweaks.Pre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerController {

    @Shadow private Minecraft mc;
    @Shadow private WorldSettings.GameType currentGameType;
    @Shadow public abstract void syncCurrentPlayItem();

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttack(EntityPlayer player, Entity target, CallbackInfo ci) {
        if (!FunnyTweaksConfig.customAttackLogic) return;
        if (target == null) return;

        Pre pre = new Pre(target);
        if (MinecraftForge.EVENT_BUS.post(pre)) {
            ci.cancel();
            return;
        }

        syncCurrentPlayItem();

        if (currentGameType != WorldSettings.GameType.SPECTATOR) {
            mc.getNetHandler().addToSendQueue(
                    new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK)
            );

            player.attackTargetEntityWithCurrentItem(target);
        }

        MinecraftForge.EVENT_BUS.post(new Post(target));
        ci.cancel();
    }
}