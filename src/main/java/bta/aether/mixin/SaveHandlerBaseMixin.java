package bta.aether.mixin;

import bta.aether.world.AetherDimension;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.Tag;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.core.world.save.DimensionData;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;


@Mixin(value = SaveHandlerBase.class,remap = false)
public abstract class SaveHandlerBaseMixin implements LevelStorage {

    @Shadow @Final private ISaveFormat saveFormat;

    @Shadow @Final private String worldDirName;

    private int index = 0;
    private void writeDugeonNBT(CompoundTag tag, int id , int x, int y, int z){
        CompoundTag subTag = new CompoundTag();
        subTag.putInt("x", x);
        subTag.putInt("y", y);
        subTag.putInt("z", z);
        subTag.putInt("id", id);

        tag.putCompound(String.valueOf(id), subTag);
    }

    @Inject(method = "getDimensionData", at = @At("HEAD"))
    public void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        if (dimensionId != AetherDimension.AetherDimensionID) {
            return;
        }

        CompoundTag data = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if (data != null) {
            CompoundTag dungeonMapNBT = data.getCompound("aether.dungeon");
            AetherDimension.dugeonMap.clear();
            for (Tag<?> value: dungeonMapNBT.getValues()) {
                if(value instanceof CompoundTag) {
                    AetherDimension.dugeonMap.put(
                            ((CompoundTag) value).getInteger("id"),
                            new ChunkCoordinates(
                                    ((CompoundTag) value).getInteger("x"),
                                    ((CompoundTag) value).getInteger("y"),
                                    ((CompoundTag) value).getInteger("z")
                            )
                    );
                }
            }
        }
    }

    @Inject(method = "saveDimensionDataRaw", at = @At("HEAD"))
    public void saveDimensionDataRaw(int dimensionId, CompoundTag dimensionDataTag, CallbackInfo ci) {
        if (dimensionId != AetherDimension.AetherDimensionID) {
            return;
        }

        CompoundTag dungeonMapNBT = new CompoundTag();
        AetherDimension.dugeonMap.forEach( (id, cords) -> writeDugeonNBT(dungeonMapNBT, id, cords.x, cords.y, cords.z));
        dimensionDataTag.putCompound("aether.dungeon", dungeonMapNBT);
    }

}

