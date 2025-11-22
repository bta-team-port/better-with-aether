package teamport.aether.items;

import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import teamport.aether.entity.animal.phow.MobPhow;

import java.util.Objects;
import java.util.Random;

public class ItemBucketSkyrootEmpty extends Item {
    public ItemBucketSkyrootEmpty(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        double reachDistance = entityplayer.getGamemode().getBlockReachDistance();
        HitResult hitResult = entityplayer.rayTrace(reachDistance, 1.0F, true, false);
        if (hitResult != null && hitResult.hitType == HitResult.HitType.TILE) {
            int i = hitResult.x;
            int j = hitResult.y;
            int k = hitResult.z;
            if (!world.canMineBlock(entityplayer, i, j, k)) {
                return itemstack;
            }

            if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0 && useBucket(entityplayer, new ItemStack(AetherItems.BUCKET_SKYROOT_WATER))) {
                world.setBlockWithNotify(i, j, k, 0);
                entityplayer.swingItem();
            }
        }

        return itemstack;
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
        if (itemStack.stackSize <= 1) {
            int x = blockX + direction.getOffsetX();
            int y = blockY + direction.getOffsetY();
            int z = blockZ + direction.getOffsetZ();
            if (world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0) {
                world.setBlockWithNotify(x, y, z, 0);
                itemStack.itemID = AetherItems.BUCKET_SKYROOT_WATER.id;
            }

            AABB box = AABB.getTemporaryBB(x, y, z, x + 0.5, y + 1.0, z + 0.5);

            boolean hasCow = !world.getEntitiesWithinAABB(MobCow.class, box).isEmpty();
            boolean hasPhow = !world.getEntitiesWithinAABB(MobPhow.class, box).isEmpty();

            if (hasCow || hasPhow) {
                itemStack.itemID = AetherItems.BUCKET_SKYROOT_MILK.id;
            }
        }
    }

    private static boolean useBucket(Player player, ItemStack itemToGive) {
        if (Objects.requireNonNull(player.inventory.getCurrentItem()).stackSize <= 1) {
            player.inventory.setItem(player.inventory.getCurrentItemIndex(), itemToGive);
            return true;
        } else {
            player.inventory.insertItem(itemToGive, true);
            if (itemToGive.stackSize < 1) {
                player.inventory.getCurrentItem().consumeItem(player);
                return true;
            } else {
                return false;
            }
        }
    }
}
