package dosliz.funnytweaks;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class FunnyTweaksConfigGui extends GuiConfig {

    public FunnyTweaksConfigGui(GuiScreen parent) {
        super(
                parent,
                new ConfigElement(FunnyTweaksConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                FunnyTweaks.MODID,
                false,
                false,
                "FunnyTweaks Configuration"
        );
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 2000) {
            try {
                if (this.entryList != null) {
                    this.entryList.saveConfigElements();
                }

                FunnyTweaksConfig.config.save();
                FunnyTweaksConfig.load();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.actionPerformed(button);
    }
}