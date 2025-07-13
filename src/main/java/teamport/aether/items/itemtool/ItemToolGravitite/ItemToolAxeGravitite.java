package teamport.aether.items.itemtool.ItemToolGravitite;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.entity.EntityFallingGravitite;
import teamport.aether.items.itemtool.ItemToolAxeAether;

public class ItemToolAxeGravitite extends ItemToolAxeAether {

    public ItemToolAxeGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean onUseItemOnBlock(
            ItemStack itemstack,
            Player player,
            World world,
            int blockX, int blockY, int blockZ,
            Side side,
            double xPlaced, double yPlaced
    ) {
        Block<?> block = world.getBlock(blockX, blockY, blockZ);
        if(block == null || !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) || player.isSneaking()) return false;
        EntityFallingGravitite entityFallingGravitite = new EntityFallingGravitite(
                world,
                (double) blockX + 0.5F, (double) blockY + 0.5F, (double) blockZ + 0.5F,
                block.id(), world.getBlockMetadata(blockX, blockY, blockZ), null);
        world.entityJoinedWorld(entityFallingGravitite);
        world.setBlockWithNotify(blockX, blockY, blockZ, 0);
        itemstack.damageItem(1, player);
        return true;
    }
//        float f1 = player.aT;
//        float f2 = player.aS;
//        double d = player.aM;
//        double d1 = player.aN + 1.62D - player.bf;
//        double d2 = player.aO;
//        bt vec3d = bt.b(d, d1, d2);
//        float f3 = in.b(-f2 * 0.01745329F - 3.141593F);
//        float f4 = in.a(-f2 * 0.01745329F - 3.141593F);
//        float f5 = -in.b(-f1 * 0.01745329F);
//        float f6 = in.a(-f1 * 0.01745329F);
//        float f7 = f4 * f5;
//        float f8 = f6;
//        float f9 = f3 * f5;
//        double d3 = 5.0D;
//        bt vec3d1 = vec3d.c(f7 * d3, f8 * d3, f9 * d3);
//        vf movingobjectposition = world.a(vec3d, vec3d1, false);
//        if (movingobjectposition == null)
//            return itemstack;
//        if (movingobjectposition.a == jg.a) {
//            int i = movingobjectposition.b;
//            int j = movingobjectposition.c;
//            int k = movingobjectposition.d;
//            if (!world.B) {
//                int blockID = world.a(i, j, k);
//                int metadata = world.e(i, j, k);
//                for (int n = 0; n < blocksEffectiveAgainst.length; n++) {
//                    if (blockID == (blocksEffectiveAgainst[n]).bn) {
//                        EntityFloatingBlock floating = new EntityFloatingBlock(world, (i + 0.5F), (j + 0.5F), (k + 0.5F), blockID, metadata);
//                        world.b(floating);
//                    }
//                }
//            }
//            itemstack.a(4, (sn) entityplayer);
//        }
//        return itemstack;
//    }
}