package dosliz.funnytweaks;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Post extends Event {

    private final Entity target;

    public Post(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }
}