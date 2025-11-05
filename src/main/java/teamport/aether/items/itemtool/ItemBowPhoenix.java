package teamport.aether.items.itemtool;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileArrowFlaming;

import static teamport.aether.items.accessory.SlotAccessory.CAPE_SLOT;

public class ItemBowPhoenix extends ItemBow {

    public ItemBowPhoenix(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
        this.setMaxDamage(768);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        int index = findActiveQuiver(entityplayer, 2);
        ItemStack quiverSlot = entityplayer.inventory.armorItemInSlot(index);
        if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER.id && quiverSlot.getMetadata() < quiverSlot.getMaxDamage()) {
            entityplayer.inventory.armorItemInSlot(index).damageItem(1, entityplayer);
            shootArrow(itemstack, world, entityplayer);
        } else if ((quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) ||
                entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW_GOLD.id)
                || entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW.id)) {
            shootArrow(itemstack, world, entityplayer);
        }
        return itemstack;
    }

    public static void shootArrow(ItemStack itemstack, World world, Player entityplayer) {
        itemstack.damageItem(1, entityplayer);
        playRandomBowSound(world, entityplayer);
        joinArrow(world, entityplayer);
    }

    public static void playRandomBowSound(World world, Player entityplayer) {
        world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
    }

    public static void joinArrow(World world, Player entityplayer) {
        if (!world.isClientSide) {
            world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
        }
    }

    public int findActiveQuiver(Player entityplayer, int index) {
        ItemStack bodyItem = entityplayer.inventory.armorItemInSlot(index);
        if (
                bodyItem == null
                        || (bodyItem.itemID != Items.ARMOR_QUIVER_GOLD.id
                        && 0 >= bodyItem.getMaxDamage() - bodyItem.getMetadata())
        ) {
            return CAPE_SLOT;
        }
        return IArmorItem.PIECE_CHEST;
    }
}
