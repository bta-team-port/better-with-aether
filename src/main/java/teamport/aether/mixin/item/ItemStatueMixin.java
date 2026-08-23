package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemPlaceablePair;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.ItemStatue;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.SunSpiritDeath;

@Mixin(ItemStatue.class)
public abstract class ItemStatueMixin extends ItemPlaceablePair {

    protected ItemStatueMixin(String name, String namespaceId, int id, Block<?> blockA, Block<?> blockB) {
        super(name, namespaceId, id, blockA, blockB);
    }

    @ModifyReturnValue(method = "canPlaceDirectlyAtPosition", at = @At("RETURN"))
    private boolean banStatuesInAether(boolean original, @NonNull ItemStack selfStack, @NonNull World world, @Nullable Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xHit, double yHit) {
        if (world.dimension == AetherDimension.getAether()
            && (AetherDimension.getDimensionBlacklist(world.dimension).contains(selfStack.itemID)
                || AetherDimension.getDimensionBlacklist(world.dimension).contains(this.blockA.id())
                || AetherDimension.getDimensionBlacklist(world.dimension).contains(this.blockB.id()))
                && !SunSpiritDeath.isDead()) {
                if (player != null) {
                    player.swingItem();
                }

                ParticleMaker.spawnBlockBreakParticles(world, blockPos.x(), blockPos.y(), blockPos.z(), this.blockA.id());

                return false;
            }


        return original;
    }
}
