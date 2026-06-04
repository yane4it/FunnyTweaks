package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    private long lastBlockPacket = 0L;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void onPlayerUpdate(CallbackInfo ci) {
        if (!FunnyTweaksConfig.blockhitFix) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = (EntityPlayerSP) (Object) this;

        if (!mc.gameSettings.keyBindUseItem.isKeyDown()) return;

        ItemStack heldItem = player.inventory.getCurrentItem();
        if (heldItem == null || !(heldItem.getItem() instanceof ItemSword)) return;

        long now = System.currentTimeMillis();

        if (now - lastBlockPacket >= 140L) {
            mc.getNetHandler().addToSendQueue(
                    new C08PacketPlayerBlockPlacement(heldItem)
            );
            lastBlockPacket = now;
        }
    }
}