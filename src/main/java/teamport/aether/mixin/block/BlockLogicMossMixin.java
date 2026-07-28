package teamport.aether.mixin.block;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.block.BlockLogicMoss;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;

@Mixin(value = BlockLogicMoss.class, remap = false)
public abstract class BlockLogicMossMixin {
    @ModifyReturnValue(method = "onBonemealUsed", at = @At(value = "TAIL"))
    private boolean addOnBonemealUsed(boolean original, ItemStack itemstack, @Nullable Player player, World world, TilePosc pos, Side side, double xPlaced, double yPlaced) {
        if (!world.isClientSide) {
            if (player == null || player.getGamemode().hasBlockConsumption()) {
                --itemstack.stackSize;
            }

            for (int j1 = 0; j1 < 32; ++j1) {
                int k1 = pos.x();
                int l1 = pos.y();
                int i2 = pos.z();

                int blockId;
                for (blockId = 0; blockId < j1 / 16; ++blockId) {
                    k1 += world.rand.nextInt(3) - 1;
                    l1 += (world.rand.nextInt(3) - 1) * world.rand.nextInt(3) / 2;
                    i2 += world.rand.nextInt(3) - 1;
                }

                if (((BlockLogicMoss) (Object) this).airExposed(world, new TilePos(k1, l1, i2)) && world.getBlockLightValue(k1, l1 + 1, i2) <= 5 && world.getBlockLightValue(k1, l1 - 1, i2) <= 5 && world.getBlockLightValue(k1 + 1, l1, i2) <= 5 && world.getBlockLightValue(k1 - 1, l1, i2) <= 5 && world.getBlockLightValue(k1, l1, i2 - 1) <= 5 && world.getBlockLightValue(k1, l1, i2 + 1) <= 5) {
                    blockId = world.getBlockId(k1, l1, i2);
                    if (blockId == AetherBlocks.HOLYSTONE.id()) {
                        world.setBlockWithNotify(k1, l1, i2, AetherBlocks.HOLYSTONE_MOSSY.id());
                    } else if (blockId == AetherBlocks.COBBLE_HOLYSTONE.id()) {
                        world.setBlockWithNotify(k1, l1, i2, AetherBlocks.COBBLE_HOLYSTONE_MOSSY.id());
                    }
                }
            }
        }
        return true;
    }
}
