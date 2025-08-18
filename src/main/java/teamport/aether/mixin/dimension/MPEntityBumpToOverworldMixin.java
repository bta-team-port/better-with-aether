package teamport.aether.mixin.dimension;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.AetherMod;
import teamport.aether.world.AetherDimension;

import static teamport.aether.world.AetherDimension.OVERWORLD_RETURN_HEIGHT;

@Mixin(value = Entity.class, remap = false)
public abstract class MPEntityBumpToOverworldMixin {

    @Shadow @Nullable public World world;
    @Shadow public double y;


    @Shadow public abstract boolean save(@NotNull CompoundTag tag);

    @Shadow public abstract void remove();

    @Shadow public abstract Entity ejectRider();

    @Shadow public abstract boolean isPassenger();

    @Shadow public abstract @Nullable Entity getPassenger();

    @Unique
    public int teleportDelay = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    public void fallToOverWorld(CallbackInfo ci) {
        teleportDelay--;

        Dimension dimension = world.dimension;

        if (teleportDelay < 0 && dimension.id == AetherDimension.AetherDimensionID && y < world.worldType.getMinY() - 10) {
            teleportDelay = 20;

            AetherMod.LOGGER.info(String.format("Sending %s to overworld", Entity.getNameFromEntity(Entity.class.cast(this), true)));

            if (getPassenger() != null) {
                ejectRider();
            }

            CompoundTag data = new CompoundTag();
            save(data);
            float x = (float) Entity.class.cast(this).x;
            float z = (float) Entity.class.cast(this).z;
            remove();

            MinecraftServer server = MinecraftServer.getInstance();
            WorldServer overworld = server.getDimensionWorld(Dimension.OVERWORLD.id);

            Entity copy = EntityDispatcher.createEntityFromNBT(data, overworld);
            copy.load(data);

            float scale = Dimension.getCoordScale(AetherDimension.AETHER, Dimension.OVERWORLD);
            copy.moveTo(x * scale, OVERWORLD_RETURN_HEIGHT, z * scale, copy.yRot, copy.xRot);

            overworld.entityJoinedWorld(copy);
        }
    }

}
