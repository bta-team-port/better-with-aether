package bta.aether.mixin;

import bta.aether.Aether;
import bta.aether.AetherBlockTags;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockJar;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import turniplabs.halplibe.helper.TextureHelper;

@Mixin(value = BlockJar.class, remap = false)
public abstract class BlockJarMixin extends Block {
    public BlockJarMixin(String key, int id, Material material) {
        super(key, id, material);
    }
    @Unique
    @Override
    public int getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
        TileEntityFlowerJar jar = (TileEntityFlowerJar) blockAccess.getBlockTileEntity(x, y, z);
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta == 1 && jar != null && blocksList[jar.flowerInPot].hasTag(AetherBlockTags.AETHER_JAR_RENDERING)){
            return TextureHelper.getOrCreateBlockTextureIndex(Aether.MOD_ID, "jarAether.png");
        }
        return super.getBlockTexture(blockAccess,x, y, z, side);
    }
}
