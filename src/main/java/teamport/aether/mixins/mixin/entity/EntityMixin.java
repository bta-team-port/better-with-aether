package teamport.aether.mixins.mixin.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.joml.primitives.AABBd;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.block.terrain.BlockLogicCloudBase;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public float fallDistance;

    @Shadow
    public World world;

    @Shadow
    @Final
    @NonNull
    public AABBd bb;

    @Inject(method = "checkOnWater", at = @At("TAIL"))
    private void checkOnCloud(boolean addVelocity, CallbackInfo ci) {
        if (this.world == null) return;

        int minX = MathHelper.floor(this.bb.minX + 0.001);
        int minY = MathHelper.floor(this.bb.minY + 0.001);
        int minZ = MathHelper.floor(this.bb.minZ + 0.001);
        int maxX = MathHelper.floor(this.bb.maxX - 0.001);
        int maxY = MathHelper.floor(this.bb.maxY - 0.001);
        int maxZ = MathHelper.floor(this.bb.maxZ - 0.001);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block<?> block = this.world.getBlock(x, y, z);
                    if (Block.hasLogicClass(block, BlockLogicCloudBase.class)) {
                        this.fallDistance = 0.0F;
                        return;
                    }
                }
            }
        }
    }
}
