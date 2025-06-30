package teamport.aether.items;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileArrowFlaming;

public class ItemBowPhoenix extends ItemBow {

    public ItemBowPhoenix(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
        this.setMaxDamage(768);
    }

    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        ItemStack quiverSlot = entityplayer.inventory.armorItemInSlot(2);
        if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER.id && quiverSlot.getMetadata() < quiverSlot.getMaxDamage()) {
            entityplayer.inventory.armorItemInSlot(2).damageItem(1, entityplayer);
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
            }
        } else if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW_GOLD.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
            }
        }

        return itemstack;
    }

}
