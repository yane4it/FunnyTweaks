package dosliz.funnytweaks;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class FunnyTweaksConfig {
    public static Configuration config;

    public static boolean partialTicks = true;
    public static double partialTicksMultiplier = 1.1487D;
    public static boolean resetPlayerAge = true;
    public static boolean customPhysics = true;
    public static boolean fastClick = true;
    public static boolean packetFlush = true;
    public static boolean customAttackLogic = true;
    public static boolean customVelocity = true;

    public static boolean blockhitFix = true;

    public static void init(File configFile) {
        config = new Configuration(configFile);
        load();
    }

    public static void load() {
        config.load();

        partialTicks = config.get(Configuration.CATEGORY_GENERAL, "partialTicks", true, "Enable partial ticks multiplier").getBoolean();
        partialTicksMultiplier = config.get(Configuration.CATEGORY_GENERAL, "partialTicksMultiplier", 1.1487D, "partialTicks multiplier value", 0.0D, 10.0D).getDouble();
        resetPlayerAge = config.get(Configuration.CATEGORY_GENERAL, "resetPlayerAge", true, "Disable Player Aging").getBoolean();
        customPhysics = config.get(Configuration.CATEGORY_GENERAL, "customPhysics", true, "Enable custom movement physics").getBoolean();
        fastClick = config.get(Configuration.CATEGORY_GENERAL, "fastClick", true, "Enable Fast Block Clicking").getBoolean();
        packetFlush = config.get(Configuration.CATEGORY_GENERAL, "packetFlush", true, "Instant Netty channel flush").getBoolean();
        customAttackLogic = config.get(Configuration.CATEGORY_GENERAL, "customAttackLogic", true, "Enable custom attack event handling").getBoolean();
        customVelocity = config.get(Configuration.CATEGORY_GENERAL, "customVelocity", true, "Enable custom velocity handling").getBoolean();

        blockhitFix = config.get(Configuration.CATEGORY_GENERAL, "blockhitFix", true, "Fix 1.8.9 blockhit mechanic to hold damage reduction like 1.7.10").getBoolean();

        if (config.hasChanged()) {
            config.save();
        }
    }
}