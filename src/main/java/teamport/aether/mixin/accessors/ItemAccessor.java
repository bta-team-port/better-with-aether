package teamport.aether.mixin.accessors;

import net.minecraft.core.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(Item.class)
public interface ItemAccessor {
    @Accessor("itemRand")
    static Random getItemRand() {
        throw new AssertionError();
    }

    @Invoker
    Item callSetMaxDamage(int i);
}
