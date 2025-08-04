package teamport.aether.models;

import net.minecraft.client.render.block.model.BlockModelJar;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;

public class BlockModelQuickglassJar extends BlockModelJar {
    public BlockModelQuickglassJar(Block block) {
        super(block);

        jarEmpty = TextureRegistry.getTexture("aether:block/glass_quicksoil_jar");
        jarFull = TextureRegistry.getTexture("aether:block/glass_quicksoil_jar_dirt");
    }

}
