package teamport.aether.mixin.dimension;

import com.mojang.nbt.NbtIo;
import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.world.save.DimensionData;
import net.minecraft.core.world.save.ISaveFormat;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.save.SaveHandlerBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.world.AetherDimension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Mixin(value = SaveHandlerBase.class)
public abstract class SaveHandlerMixin {
    @Shadow
    @Final
    protected ISaveFormat saveFormat;
    @Shadow
    @Final
    protected String worldDirName;
    @Shadow
    @Final
    protected File saveDirectory;

    @Inject(method = "getDimensionData", at = @At("HEAD"))
    private void getDimensionData(int dimensionId, CallbackInfoReturnable<DimensionData> cir) {
        if (dimensionId != AetherDimension.getAether().id) return;

        AetherDimension.setDimensionDataDefaults();

        CompoundTag dimensionData = saveFormat.getDimensionDataRaw(worldDirName, dimensionId);
        if (dimensionData != null) {
            AetherDimension.loadDimensionData(dimensionData);
        }
    }

    @Inject(method = "getLevelData", at = @At("HEAD"))
    private void getWorldData(CallbackInfoReturnable<LevelData> cir) throws IOException  {
        File AETHER_CUSTOM_DATA_FILE = new File(saveDirectory, "data/aether_custom_data.dat");

        if (AETHER_CUSTOM_DATA_FILE.exists()) {
            InputStream dis = Files.newInputStream(AETHER_CUSTOM_DATA_FILE.toPath());
            CompoundTag aetherCustomTag = NbtIo.readCompressed(dis);
            AetherDimension.loadWorldData(aetherCustomTag);
            dis.close();
        }
    }

    @Inject(method = "saveDimensionDataRaw", at = @At("HEAD"))
    private void saveDimensionDataRaw(int dimensionId, CompoundTag dimensionData, CallbackInfo ci) throws IOException {

        File AETHER_CUSTOM_DATA_FILE = new File(saveDirectory, "data/aether_custom_data.dat");
        CompoundTag aetherData = new CompoundTag();
        AetherDimension.saveWorldData(aetherData);

        AETHER_CUSTOM_DATA_FILE.getParentFile().mkdirs();
        OutputStream dos = Files.newOutputStream(AETHER_CUSTOM_DATA_FILE.toPath());
        NbtIo.writeCompressed(aetherData, dos);
        dos.close();

        if (dimensionId != AetherDimension.getAether().id) return;
        AetherDimension.saveDimensionData(dimensionData);
    }
}
