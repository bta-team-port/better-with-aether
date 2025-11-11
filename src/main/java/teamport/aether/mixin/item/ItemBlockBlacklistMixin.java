package teamport.aether.mixin.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

import java.util.List;

@Mixin(value = ItemBlock.class, remap = false)
public abstract class ItemBlockBlacklistMixin {

    @Shadow
    @NotNull
    protected Block<?> block;

    @Shadow
    public abstract int getPlacedBlockMetadata(@Nullable Player player, ItemStack stack, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced);

    @Unique
    private static final int[] BLOCK_TO_PLACE = {
            Blocks.PUMPKIN_CARVED_ACTIVE.id(),
            Blocks.BRAZIER_ACTIVE.id(),
            Blocks.PUMICE_WET.id(),
            Blocks.COBBLE_NETHERRACK_IGNEOUS.id()
    };

    @Unique
    private static final int[] BLOCK_TO_BECOME = {
            Blocks.PUMPKIN_CARVED_IDLE.id(),
            Blocks.BRAZIER_INACTIVE.id(),
            Blocks.PUMICE_DRY.id(),
            Blocks.COBBLE_NETHERRACK.id()
    };

    @Inject(method = "onUseItemOnBlock", at = @At(value = "HEAD"), cancellable = true)
    public void banBlocksFromDimensions(ItemStack stack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir) {
        Dimension dim = world.dimension;
        List<Integer> BLACKLIST = AetherDimension.getDimensionBlacklist(dim);

        if (BLACKLIST.contains(block.id())) {
            blockX += side.getOffsetX();
            blockY += side.getOffsetY();
            blockZ += side.getOffsetZ();

            if (dim == AetherDimension.AETHER) {
                for (int i = 0; i < BLOCK_TO_PLACE.length; i++) {
                    if (block.id() == BLOCK_TO_PLACE[i]) {
                        int replacementId = BLOCK_TO_BECOME[i];
                        if (world.canBlockBePlacedAt(replacementId, blockX, blockY, blockZ, false, side) && stack.consumeItem(player)) {
                            int meta = this.getPlacedBlockMetadata(player, stack, world, blockX, blockY, blockZ, side, xPlaced, yPlaced);

                            if (world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, replacementId, meta)) {
                                ParticleMaker.spawnReplacementEffects(world, blockX, blockY, blockZ);
                                if (player != null) {
                                    this.block.onBlockPlacedByMob(world, blockX, blockY, blockZ, side, player, xPlaced, yPlaced);
                                } else {
                                    this.block.onBlockPlacedByWorld(world, blockX, blockY, blockZ);
                                }
                                world.playBlockSoundEffect(player, (float) blockX + 0.5F, (float) blockY + 0.5F, (float) blockZ + 0.5F, this.block, EnumBlockSoundEffectType.PLACE);
                                cir.setReturnValue(true);
                                return;
                            } else {
                                if (player != null && player.getGamemode().consumeBlocks()) {
                                    ++stack.stackSize;
                                }
                            }
                        }
                        break;
                    }
                }
            } else {
                cir.setReturnValue(false);
                return;
            }
            cir.setReturnValue(false);
        }
    }
}
