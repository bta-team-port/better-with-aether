package bta.aether.item;

import bta.aether.world.generate.feature.WorldFeatureAetherDungeonBronzeCursed;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemDevStick extends Item {
    public ItemDevStick(String name, int id) {
        super(name, id);
    }

    @Override
    public int getDamageVsEntity(Entity entity) {
        // I am a man who believes in balance.
        if (entity instanceof EntityLiving) ((EntityLiving) entity).setHealthRaw(Integer.MAX_VALUE * -1);
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        new WorldFeatureAetherDungeonBronzeCursed().generate(world, world.rand, blockX, blockY, blockZ);
        return true;
    }
}
