package bta.aether.item;

import bta.aether.entity.EntityBossSlider;
import bta.aether.world.generate.feature.WorldFeatureAetherDungeonBronze;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;

import java.util.List;
import java.util.concurrent.Callable;

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
        int distance = 5;

        AABB bb = entityplayer.bb.expand(32, 32, 32);

        EntityLiving entityLiving = null;
        List<Entity> entities = world.getEntitiesWithinAABB(EntityBossSlider.class, bb);

        for (Entity entity : entities) {
            double d3 = entityplayer.x - entity.x; double d4 = entityplayer.y - entity.y; double d5 = entityplayer.z - entity.z;
            if ((d3 * d3 + d4 * d4 + d5 * d5) >= (distance * distance)) {
                entityLiving = (EntityLiving) entity;
                System.out.println(entity);
                break;
            }
        }

        if (entityLiving == null) return false;

        double value = 180 - Math.atan2(entityplayer.x - entityLiving.x, entityplayer.z - entityLiving.z) * 180 / Math.PI;
        entityplayer.yRot = (float) value;
        entityplayer.addChatMessage(String.valueOf(value));

        return true;
    }
}
