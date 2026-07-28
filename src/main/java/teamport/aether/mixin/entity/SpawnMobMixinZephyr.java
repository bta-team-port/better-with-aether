package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.SpawnerMobs;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.monster.zephyr.MobZephyr;

@Mixin(value = SpawnerMobs.class)
public abstract class SpawnMobMixinZephyr {


    @WrapOperation(method = "performSpawning", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/SpawnerMobs;canCreatureTypeSpawnAtLocation(Lnet/minecraft/core/enums/MobCategory;Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;)Z"))
    private static boolean spoofSpawning(
        MobCategory mobCategory,
        World world, TilePosc pos,
        Operation<Boolean> original,
        @Local SpawnListEntry spawnListEntry) {
        if (spawnListEntry.entityClass.equals(MobZephyr.class)) {
            int blockID = world.getBlockId(pos.x(), pos.y() - 1, pos.z());
            return blockID == AetherBlocks.AERCLOUD_WHITE.id() || blockID == AetherBlocks.AERCLOUD_GOLD.id();
        }
        return original.call(mobCategory, world, pos);
    }
}
