package dosliz.funnytweaks.mixins;

import dosliz.funnytweaks.FunnyTweaksConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {

    @Shadow protected int entityAge;
    @Shadow public abstract boolean isOnLadder();

    @Inject(method = "onEntityUpdate()V", at = @At("HEAD"))
    private void onUpdate(CallbackInfo ci) {
        if (!FunnyTweaksConfig.resetPlayerAge) return;
        if (!((Object) this instanceof EntityPlayer)) return;
        this.entityAge = 0;
    }

    @ModifyConstant(method = "moveEntityWithHeading(FF)V", constant = @Constant(intValue = 0), expect = 1)
    private int redirectPhysicsCondition(int value) {
        if (!FunnyTweaksConfig.customPhysics) return value;
        if (!((Object) this instanceof EntityPlayer)) return value;
        if (this.isOnLadder()) return value;
        return 1;
    }
}