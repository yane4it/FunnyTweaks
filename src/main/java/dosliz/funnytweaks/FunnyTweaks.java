package dosliz.funnytweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FunnyTweaks.MODID, name = FunnyTweaks.NAME, version = FunnyTweaks.VERSION, guiFactory = "dosliz.funnytweaks.FunnyTweaksGuiFactory")

public class FunnyTweaks {
    public static final String MODID = "funnytweaks";
    public static final String NAME = "FunnyTweaks";
    public static final String VERSION = "1.1";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FunnyTweaksConfig.init(event.getSuggestedConfigurationFile());
    }
}