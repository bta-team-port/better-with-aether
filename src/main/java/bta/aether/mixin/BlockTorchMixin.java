package bta.aether.mixin;

import bta.aether.world.AetherDimension;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTorch;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockTorch.class, remap = false)
public class BlockTorchMixin extends Block {
    public BlockTorchMixin(String key, int id, Material material) {
        super(key, id, material);
    }

    @Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
    public void callCanPlaceBlockAt(World world, int x, int y, int z, CallbackInfoReturnable<Boolean> info) {
        if (world.dimension == AetherDimension.dimensionAether) {
            world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);

            for (int l = 0; l < 8; ++l) {
                double angle = Math.toRadians(l * 45);
                world.spawnParticle("smoke", (double) x + 0.5, (double) y + 0.6, (double) z + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0);
            }
            info.setReturnValue(false);
        }

    }

        /**    >:[
        @Inject(method = "onBlockPlaced", at = @At("HEAD"))
        public void callOnBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight, CallbackInfo info) {
            if (world.dimension.languageKey.equals("aether")){
                int meta = world.getBlockMetadata(x, y, z);

                if (side.getId() == 1 && world.isBlockNormalCube(x, y, z)) {
                    meta = 5;
                }

                if (side.getId() == 2 && world.isBlockNormalCube(x, y, z + 1)) {
                    meta = 4;
                }

                if (side.getId() == 3 && world.isBlockNormalCube(x, y, z - 1)) {
                    meta = 3;
                }

                if (side.getId() == 4 && world.isBlockNormalCube(x + 1, y, z)) {
                    meta = 2;
                }

                if (side.getId() == 5 && world.isBlockNormalCube(x - 1, y, z)) {
                    meta = 1;
                }

                world.setBlockAndMetadataWithNotify(x,y,z, AetherBlocks.torchUnlit.id, meta);
                world.playSoundEffect(SoundType.WORLD_SOUNDS, (double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "fire.ignite", 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);

                for (int l = 0; l < 8; ++l) {
                    double angle = Math.toRadians(l * 45);
                    world.spawnParticle("largesmoke", (double) x + 0.5, (double) y + 0.6, (double) z + 0.5, -Math.cos(angle) / 20.0,  0.03, -Math.sin(angle) / 20.0);
                }
            }
         **/
}
