package dosliz.funnytweaks.mixins;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class MixinLoader implements IFMLLoadingPlugin {

    static {
        System.out.println("[FunnyTweaks] MixinLoader static init");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.funnytweaks.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    public MixinLoader() {
        System.out.println("[FunnyTweaks] MixinLoader constructor loaded");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        System.out.println("[FunnyTweaks] injectData called");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}