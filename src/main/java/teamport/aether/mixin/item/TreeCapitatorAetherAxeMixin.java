package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkPosition;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.blocks.terrain.BlockLogicLogAether;
import teamport.aether.items.item_tool.ItemToolAxeAether;

@Mixin(value = TreecapitatorHelper.class, remap = false)
public abstract class TreeCapitatorAetherAxeMixin {
    @Unique
    @Nullable
    private Item tool = null;
    @Unique
    int metadata = 0;

    @WrapMethod(method = "chopTree")
    public boolean preventNormalAxeMiningAetherTrees(Operation<Boolean> original) {
        TreecapitatorHelper helper = (TreecapitatorHelper) (Object) this;
        Player player = helper.player;
        if (player.getHeldItem() != null) tool = player.getHeldItem().getItem();
        int id = helper.world.getBlockId(helper.basePosition.x, helper.basePosition.y, helper.basePosition.z);
        if (id == 0) return false;
        Block<?> block = Blocks.getBlock(id);
        if (tool == null || block == null || (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) && !(tool instanceof ItemToolAxeAether))) {
            return false;
        }
        return original.call();
    }

    @Inject(method = "chopTree", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/data/gamerule/TreecapitatorHelper;breakBlock(Lnet/minecraft/core/world/chunk/ChunkPosition;ZLnet/minecraft/core/data/gamerule/TreecapitatorHelper$ItemList;)Z"))
    public void captureMetadata(CallbackInfoReturnable<Boolean> cir, @Local ChunkPosition pos) {
        TreecapitatorHelper helper = (TreecapitatorHelper) (Object) this;
        this.metadata = helper.world.getBlockMetadata(pos.x, pos.y, pos.z);
    }

    @WrapOperation(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/block/Block;getBreakResult(Lnet/minecraft/core/world/World;Lnet/minecraft/core/enums/EnumDropCause;IIIILnet/minecraft/core/block/entity/TileEntity;)[Lnet/minecraft/core/item/ItemStack;"))
    private ItemStack[] modifyBlockResults(Block<?> instance, World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity, Operation<ItemStack[]> original) {
        ItemStack[] results = original.call(instance, world, dropCause, x, y, z, meta, tileEntity);
        if (this.tool instanceof ItemToolAxeAether && instance.getLogic() instanceof BlockLogicLogAether) {
            BlockLogicLogAether logic = (BlockLogicLogAether) instance.getLogic();
            return logic.getAdditionalBreakResult(world, tool, results, this.metadata);
        }
        return results;
    }
}
