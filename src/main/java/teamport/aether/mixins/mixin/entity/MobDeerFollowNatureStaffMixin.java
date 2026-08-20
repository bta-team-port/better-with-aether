package teamport.aether.mixins.mixin.entity;

import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobDeer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.item.AetherItemTags;

@Mixin(MobDeer.class)
public abstract class MobDeerFollowNatureStaffMixin extends MobAnimal {

    protected MobDeerFollowNatureStaffMixin(World world) {
        super(world);
    }

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }
}
