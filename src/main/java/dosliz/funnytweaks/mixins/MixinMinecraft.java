package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow public EntityPlayerSP thePlayer;
    @Shadow public GameSettings gameSettings;
    @Shadow private int leftClickCounter;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    private void onSendClickBlock(boolean leftClick, CallbackInfo ci) {
        if (!FunnyTweaksConfig.fastClick) return;
        if (leftClick && this.gameSettings.keyBindUseItem.isKeyDown()) {
            if (this.thePlayer != null && this.thePlayer.getCurrentEquippedItem() != null) {
                this.leftClickCounter = 0;
            }
        }
    }
}