package teamport.aether.mixin.dimension;

import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.Tag;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.LevelStorage;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;
import teamport.aether.world.generate.feature.components.WorldFeaturePoint;
import teamport.aether.world.AetherDimension;

@Mixin(value =  SaveHandlerBase.class, remap = false)
public abstract class SaveHandlerMixin implements LevelStorage {

    @Shadow
    @Final
    ISaveFormat saveFormat;

    @Shadow @Final
    String worldDirName;

    @Inject(method = "getDimensionData", at = @At("HEAD"))
    public void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        if (dimensionId != AetherDimension.AetherDimensionID) {
            return;
        }
        AetherMod.LOGGER.info("Loading additional level data.");
        CompoundTag data = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if (data != null) {
            AetherDimension.dungeonMap.clear();

            for (Tag<?> tag: data.getCompound("aether.dungeon").getValues()) {
                if(tag instanceof CompoundTag) {
                    AetherDimension.dungeonMap.put(
                        Integer.parseInt(tag.getTagName()),
                        WorldFeaturePoint.fromCompoundTag(((CompoundTag) tag))
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

        AetherMod.LOGGER.debug("Saving additional level data.");
        CompoundTag dungeonMapNBT = new CompoundTag();
        AetherDimension.dungeonMap.forEach( (id, coords) -> dungeonMapNBT.put(String.valueOf(id), coords.toCompoundTag()));
        dimensionDataTag.putCompound("aether.dungeon", dungeonMapNBT);
    }

}
