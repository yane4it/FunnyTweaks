package dosliz.funnytweaks;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class Pre extends Event {

    private final Entity target;

    public Pre(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }
}