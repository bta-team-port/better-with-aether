package teamport.aether.mixins.mixin.fix;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemMap;
import net.minecraft.core.world.World;
import net.minecraft.core.world.saveddata.maps.ItemMapSavedData;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.world.AetherDimension;

@Mixin(ItemMap.class)
public abstract class MapFixMixin {
    @Definition(id = "getBlockId", method = "Lnet/minecraft/core/world/chunk/Chunk;getBlockId(Lnet/minecraft/core/world/pos/ChunkTilePosc;)I")
    @Expression("? = ?.getBlockId(?)")
    @Inject(method = "update", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    void fixMap(@NonNull World world, Entity entity, ItemMapSavedData data, CallbackInfo ci, @Local(name = "id") LocalIntRef id, @Local(name = "height") int height) {
        if (world.dimension.id != AetherDimension.getAether().id) return;
        if (height < 0) {
            id.set(Blocks.TORCH_COAL.id());
        }
    }
}
