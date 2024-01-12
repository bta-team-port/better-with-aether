package bta.aether.mixin;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemDye;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(value = ItemDye.class, remap = false)
public class ItemDyeMixin extends Item {

    public ItemDyeMixin(int id) {
        super(id);
    }

    @Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
    public void callOnItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced, CallbackInfoReturnable<Boolean> info) {
        if (itemstack.getMetadata() == 15 ) {
            if (world.getBlockId(blockX, blockY, blockZ) == AetherBlocks.grassAether.id) {
                Random random = new Random();

                for(int l = 0; l < 64; ++l) {
                    Block plantBlock = new Block[]{AetherBlocks.flowerPurple, AetherBlocks.flowerWhite}[random.nextInt(2)];

                    int x = blockX + random.nextInt(8) - random.nextInt(8);
                    int y = blockY + random.nextInt(4) - random.nextInt(4);
                    int z = blockZ + random.nextInt(8) - random.nextInt(8);

                    if (world.isAirBlock(x, y, z) && (plantBlock.canBlockStay(world, x, y, z))) {
                        world.setBlockWithNotify(x, y, z, plantBlock.id);
                    }
                }

                if (entityplayer.getGamemode().consumeBlocks()) {
                    --itemstack.stackSize;
                }
                entityplayer.swingItem();
                info.setReturnValue(true);
            }
        }
    }
}
