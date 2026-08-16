package teamport.aether.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.SpawnerMobs;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.monster.zephyr.MobZephyr;

@Mixin(SpawnerMobs.class)
public abstract class SpawnMobMixinZephyr {

    // TODO this currently just stops the zephyr from spawning so ive disabled it
    @WrapOperation(method = "performSpawning", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/SpawnerMobs;canCreatureTypeSpawnAtLocation(Lnet/minecraft/core/enums/MobCategory;Lnet/minecraft/core/world/World;Lnet/minecraft/core/world/pos/TilePosc;)Z"))
    private static boolean spoofSpawning(MobCategory mobCategory, World world, TilePosc tilePos, Operation<Boolean> original, @Local @NonNull SpawnListEntry spawnListEntry) {
        TilePos pos = new TilePos();
        if (spawnListEntry.entityClass.equals(MobZephyr.class)) {
            int blockID = world.getBlockData(pos.down());
            return blockID == AetherBlocks.AERCLOUD_WHITE.id() || blockID == AetherBlocks.AERCLOUD_GOLD.id();
        }
        return original.call(mobCategory, world, tilePos);
    }
}
