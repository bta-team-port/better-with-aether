package bta.aether.item;

import bta.aether.block.AetherBlocks;
import bta.aether.entity.EntityBossSlider;
import bta.aether.entity.projectiles.EntityHammerHead;
import bta.aether.entity.projectiles.EntityZephyrSnowball;
import bta.aether.util.StructureLoader;
import bta.aether.world.AetherDimension;
import bta.aether.util.LootTable;
import bta.aether.world.generate.WorldFeatureAetherDungeonBase;
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
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.entityJoinedWorld(new EntityZephyrSnowball(world, entityplayer, true));
        return itemstack;
    }
}
