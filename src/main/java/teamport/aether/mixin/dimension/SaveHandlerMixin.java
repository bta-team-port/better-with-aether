package teamport.aether.mixin.dimension;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

@Mixin(value = SaveHandlerBase.class, remap = false)
public abstract class SaveHandlerMixin {
    @Shadow
    @Final
    protected ISaveFormat saveFormat;
    @Shadow
    @Final
    protected String worldDirName;
    @Inject(method = "getDimensionData", at = @At("HEAD"))
    private void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        if (dimensionId != AetherDimension.getAether().id) return;

        AetherDimension.setDimensionDataDefaults();

        CompoundTag dimensionData = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if (dimensionData != null) {
            AetherDimension.loadDimensionData(dimensionData);
        }
    }
    @Inject(method = "saveDimensionDataRaw", at = @At("HEAD"))
    private void saveDimensionDataRaw(int dimensionId, CompoundTag dimensionData, CallbackInfo ci) {
        if (dimensionId != AetherDimension.getAether().id) return;
        AetherDimension.saveDimensionData(dimensionData);
    }
}
