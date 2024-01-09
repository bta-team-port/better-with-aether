package bta.aether.mixin;

import bta.aether.item.ToolMaterialAether;
import net.minecraft.core.achievement.stat.StatList;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, remap = false)
public abstract class BlockMixin {
    @Shadow public abstract ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity);

    @Shadow @Final public static Block rail;

    @Inject(method = "harvestBlock(Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/EntityPlayer;IIIILnet/minecraft/core/block/entity/TileEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;dropBlockWithCause(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)V", ordinal = 2),
            cancellable = true)
    private void dropsMultiplier(World world, EntityPlayer entityplayer, int x, int y, int z, int meta, TileEntity tileEntity, CallbackInfo ci){
        ItemStack heldItemStack = entityplayer.inventory.getCurrentItem();
        Item heldItem = heldItemStack != null ? Item.itemsList[heldItemStack.itemID] : null;
        if (heldItem instanceof ItemTool){
            ItemTool tool = (ItemTool) heldItem;
            if (tool.getMaterial() instanceof ToolMaterialAether){
                dropBlockWithCauseAndMultipier(world, EnumDropCause.PROPER_TOOL, (ToolMaterialAether) tool.getMaterial(), x, y, z, meta, tileEntity);
                ci.cancel();
            }
        }
    }
    private void dropBlockWithCauseAndMultipier(World world, EnumDropCause cause, ToolMaterialAether toolMaterialAether, int x, int y, int z, int meta, TileEntity tileEntity) {
        if (world.isClientSide) {
            return;
        }
        ItemStack[] baseDrops = this.getBreakResult(world, cause, x, y, z, meta, tileEntity);
        if (baseDrops == null) return;
        ItemStack[] drops = new ItemStack[baseDrops.length * toolMaterialAether.getDropMultipier()];
        for (int i = 0; i < baseDrops.length; i++) {
            for (int j = 0; j < toolMaterialAether.getDropMultipier(); j++) {
                drops[i + baseDrops.length * j] = baseDrops[i];
            }
        }
        for (ItemStack drop : drops) {
            if (drop == null) continue;
            if (EntityItem.enableItemClumping) {
                world.dropItem(x, y, z, drop.copy());
                continue;
            }
            for (int i = 0; i < drop.stackSize; ++i) {
                ItemStack drop1 = drop.copy();
                drop1.stackSize = 1;
                world.dropItem(x, y, z, drop1);
            }
        }
    }
}
