package teamport.aether.item.item_tool;

import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IDispensable;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import teamport.aether.AetherMod;
import teamport.aether.entity.projectile.ProjectileKnifeLightning;
import teamport.aether.item.AetherHasCustomDamageType;

import java.util.Random;

public class ItemToolKnifeLightning extends Item implements IDispensable, AetherHasCustomDamageType {
    public ItemToolKnifeLightning(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        itemstack.consumeItem(entityplayer);
        entityplayer.swingItem();

        if (!world.isClientSide) {
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            world.entityJoinedWorld(new ProjectileKnifeLightning(world, entityplayer));
        }

        return itemstack;
    }

    @Override
    public void onUseByActivator(ItemStack itemStack, TileEntityActivator activatorBlock, World world, Random random, int blockX, int blockY, int blockZ, double offX, double offY, double offZ, Direction direction) {
        if (!world.isClientSide) {
            ProjectileKnifeLightning projectileKnife = new ProjectileKnifeLightning(world, blockX + offX, blockY + offY, blockZ + offZ);
            projectileKnife.setHeading(direction.getOffsetX() * 0.6, direction.getOffsetY() == 0 ? 0.1 : direction.getOffsetY() * 0.6, direction.getOffsetZ() * 0.6F, 1.1F, 6.0F);
            world.entityJoinedWorld(projectileKnife);
        }
        --itemStack.stackSize;
    }

    @Override
    public void onDispensed(ItemStack itemStack, World world, double x, double y, double z, int xOffset, int yOffset, int zOffset, Random random) {
        if (!world.isClientSide) {
            ProjectileKnifeLightning entityknife = new ProjectileKnifeLightning(world, x, y, z);
            entityknife.setHeading(xOffset, yOffset + 0.1, zOffset, 1.1F, 6.0F);
            world.entityJoinedWorld(entityknife);
        }
    }

    @Override
    public DamageType getDamageType() {
        return AetherMod.LIGHTNING;
    }

}
