package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.block.ItemBlockSlab;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.MixinHelper;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

@Mixin(value = ItemBlockSlab.class, remap = false)
public abstract class ItemBlockSlabBlacklistMixin<T extends BlockLogic> extends ItemBlock<T> {

    protected ItemBlockSlabBlacklistMixin(@NotNull Block<T> block) {
        super(block);
    }

    @Definition(id = "stackSize", field = "Lnet/minecraft/core/item/ItemStack;stackSize:I")
    @Expression("?.stackSize <= 0")
    @ModifyExpressionValue(method = "onUseItemOnBlock", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean banBlocksFromDimensionsOne(boolean original, ItemStack stack, @Nullable Player player, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced) {
        return original || world.dimension != AetherDimension.getAether() && AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id());
    }

    @Expression("? != 0")
    @ModifyExpressionValue(method = "onUseItemOnBlock", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean banBlocksFromDimensionsTwo(boolean original, ItemStack stack, @Nullable Player player, World world, int x, int y, int z, Side side, double xPlaced, double yPlaced, @Share("replacementId") LocalIntRef replacementId) {
        replacementId.set(AetherDimension.getDimensionBlacklist(world.dimension).contains(block.id()) ? MixinHelper.BLOCK_TO_BECOME.getOrDefault(block.id(), -2) : -1);
        return original;
    }

    @WrapOperation(method = "onUseItemOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;canBlockBePlacedAt(IIIIZLnet/minecraft/core/util/helper/Side;)Z"))
    private boolean banBlocksFromDimensionsThree(World instance, int blockId, int x, int y, int z, boolean flag, Side side, Operation<Boolean> original, @Share("replacementId") LocalIntRef replacementId) {
        int id = replacementId.get();
        if (id == -2) {
            ParticleMaker.spawnBlockBreakParticles(instance, x, y, z, blockId);
            return false;
        }
        return original.call(instance, id == -1 ? blockId : id, x, y, z, flag, side);
    }

    @WrapOperation(method = "onUseItemOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockAndMetadataWithNotify(IIIII)Z"))
    private boolean banBlocksFromDimensionsFour(World instance, int x, int y, int z, int id, int meta, Operation<Boolean> original, @Share("replacementId") LocalIntRef replacementId) {
        int theReplacementId = replacementId.get();
        boolean condition = original.call(instance, x, y, z, theReplacementId == -1 ? id : theReplacementId, meta);
        if (theReplacementId != -1 && condition) ParticleMaker.spawnReplacementEffects(instance, x, y, z);
        return condition;
    }

}
