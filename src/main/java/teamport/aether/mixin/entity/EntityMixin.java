package teamport.aether.mixin.entity;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.block.terrain.BlockLogicCloudBase;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin {
    @Shadow
    public float fallDistance;
    @Shadow
    protected boolean wasInWater;
    @Shadow
    public World world;

    @Shadow
    @Final
    @NotNull
    public AABB bb;

    @Inject(method = "checkOnWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Entity;checkAndHandleWater(Z)Z"))
    private void checkOnCloud(boolean addVelocity, CallbackInfo ci) {
        if (world == null) return;

        int minX = MathHelper.floor(bb.minX);
        int minY = MathHelper.floor(bb.minY);
        int minZ = MathHelper.floor(bb.minZ);
        int maxX = MathHelper.floor(bb.maxX);
        int maxY = MathHelper.floor(bb.maxY);
        int maxZ = MathHelper.floor(bb.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block<?> block = world.getBlock(x, y, z);
                    if (block != null && Block.hasLogicClass(block, BlockLogicCloudBase.class)) {
                        this.fallDistance = 0.0F;
                        this.wasInWater = false;
                        return;
                    }
                }
            }
        }
    }
}
