package teamport.aether.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.SpawnerMobs;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.monster.zephyr.MobZephyr;

@Mixin(value = SpawnerMobs.class, remap = false)
public abstract class SpawnMobMixinZephyr {


    @WrapOperation(method = "performSpawning", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/SpawnerMobs;canCreatureTypeSpawnAtLocation(Lnet/minecraft/core/enums/MobCategory;Lnet/minecraft/core/world/World;III)Z"))
    private static boolean spoofSpawning(
        MobCategory mobCategory,
        World world, int x, int y, int z,
        Operation<Boolean> original,
        @Local SpawnListEntry spawnListEntry) {
        if (spawnListEntry.entityClass.equals(MobZephyr.class)) {
            int blockID = world.getBlockId(x, y - 1, z);
            return blockID == AetherBlocks.AERCLOUD_WHITE.id() || blockID == AetherBlocks.AERCLOUD_GOLD.id();
        }
        return original.call(mobCategory, world, x, y, z);
    }
}
