package teamport.aether.mixin.entity;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.item.AetherItemTags;

@Mixin(value = EntityItem.class, remap = false)
public abstract class EntityItemFallUpwardsMixin extends Entity {
    @Shadow
    public ItemStack item;

    protected EntityItemFallUpwardsMixin(@Nullable World world) {
        super(world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void makeGravititeFloat(CallbackInfo ci) {
        if (item != null && item.getItem().hasTag(AetherItemTags.FALLS_UPWARDS)) {
            this.yd += 0.08;
        }
    }

    @ModifyVariable(method = "tick", at = @At(value = "STORE"), ordinal = 0)
    private float flipFriction(float friction) {
        if (!item.getItem().hasTag(AetherItemTags.FALLS_UPWARDS)) {
            return friction;
        }

        int blockAbove = this.world.getBlockId(MathHelper.floor(this.x), MathHelper.floor(this.bb.maxY) + 1, MathHelper.floor(this.z));
        if (blockAbove > 0) {
            return Blocks.blocksList[blockAbove].friction * 0.98F;
        }

        if (this.y > (double) 320.0F) {
            this.outOfWorld();
        }

        return friction;
    }
}
