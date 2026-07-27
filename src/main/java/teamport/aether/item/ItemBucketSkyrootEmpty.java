package teamport.aether.item;

import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.material.Materials;
import net.minecraft.core.entity.animal.MobCow;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBdc;
import teamport.aether.entity.animal.phow.MobPhow;

import java.util.Objects;
import java.util.Random;

public class ItemBucketSkyrootEmpty extends Item {
    public ItemBucketSkyrootEmpty(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Override
    public ItemStack onUse(ItemStack itemstack, World world, Player entityplayer) {
        double reachDistance = entityplayer.getGamemode().getBlockReachDistance();
        HitResult hitResult = entityplayer.rayCast(reachDistance, 1.0F, true, false, false);
        if (hitResult instanceof HitResult.Tile) {
            HitResult.Tile tileHit = (HitResult.Tile) hitResult;
            int i = tileHit.tilePos.x();
            int j = tileHit.tilePos.y();
            int k = tileHit.tilePos.z();
            if (!world.canMineBlock(entityplayer, i, j, k)) {
                return itemstack;
            }

            if (world.getBlockMaterial(i, j, k) == Materials.WATER && world.getBlockMetadata(i, j, k) == 0 && useBucket(entityplayer, new ItemStack(AetherItems.BUCKET_SKYROOT_WATER))) {
                world.setBlockWithNotify(i, j, k, 0);
                entityplayer.swingItem();
            }
        }

        return itemstack;
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, World world, TileEntityActivator activatorBlock, Random random, TilePosc blockPos, Direction direction, double offX, double offY, double offZ) {
        int blockX = blockPos.x();
        int blockY = blockPos.y();
        int blockZ = blockPos.z();
        if (itemStack.stackSize <= 1) {
            int x = blockX + direction.offsetX();
            int y = blockY + direction.offsetY();
            int z = blockZ + direction.offsetZ();
            if (world.getBlockMaterial(x, y, z) == Materials.WATER && world.getBlockMetadata(x, y, z) == 0) {
                world.setBlockWithNotify(x, y, z, 0);
                itemStack.itemID = AetherItems.BUCKET_SKYROOT_WATER.id;
            }

            AABBdc box = new AABBd(x, y, z, x + 0.5, y + 1.0, z + 0.5);

            boolean hasCow = !world.getEntitiesWithinAABB(MobCow.class, box).isEmpty();
            boolean hasPhow = !world.getEntitiesWithinAABB(MobPhow.class, box).isEmpty();

            if (hasCow || hasPhow) {
                itemStack.itemID = AetherItems.BUCKET_SKYROOT_MILK.id;
            }
        }
    }

    public static boolean useBucket(Player player, ItemStack itemToGive) {
        if (Objects.requireNonNull(player.inventory.getCurrentItem()).stackSize <= 1) {
            player.inventory.setItem(player.inventory.getCurrentSlot(), itemToGive);
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
