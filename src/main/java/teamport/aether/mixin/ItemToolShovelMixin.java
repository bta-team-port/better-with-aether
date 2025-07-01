package teamport.aether.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumBlockSoundEffectType;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolShovel;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.blocks.BlockLogicPathDirtAether;

@Mixin(value = ItemToolShovel.class, remap = false)
public class ItemToolShovelMixin {
    @Inject(method = "onUseItemOnBlock", at = @At(value = "HEAD"), cancellable = true)
    public void addNewPathBlock(ItemStack itemstack, Player entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> cir){
        int blockId = world.getBlockId(blockX,blockY,blockZ);
        int blockAbove = world.getBlockId(blockX,blockY + 1,blockZ);
        if (side != Side.BOTTOM && blockAbove == 0 && (blockId == AetherBlocks.GRASS_AETHER.id() || blockId == AetherBlocks.DIRT_AETHER.id())) {
            Block<BlockLogicPathDirtAether> pathBlock = AetherBlocks.PATH_DIRT_AETHER;
            world.playBlockSoundEffect(null, (float)blockX + 0.5f, (float)blockY + 0.5f, (float)blockZ + 0.5f, pathBlock, EnumBlockSoundEffectType.PLACE);
            if (!world.isClientSide) {
                world.setBlockWithNotify(blockX, blockY, blockZ, pathBlock.id());
                itemstack.damageItem(1, entityplayer);
            }
            cir.setReturnValue(true);
        }
    }
}
