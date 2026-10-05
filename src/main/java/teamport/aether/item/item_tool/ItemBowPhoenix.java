package teamport.aether.item.item_tool;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.entity.projectile.ProjectileArrowFlaming;

public class ItemBowPhoenix extends ItemBow {
    public ItemBowPhoenix(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.maxStackSize = 1;
        this.setMaxDamage(768);
    }

    @Override
    public ItemStack onUse(@NonNull ItemStack itemstack, @NonNull World world, @NonNull Player entityplayer) {
        int quiverIndex = PlayerUtil.getActiveQuiverSlot(entityplayer);
        ItemStack quiverSlot = PlayerUtil.getActiveQuiver(entityplayer);
        if (quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER.id) {
            PlayerUtil.damageItemArmor(entityplayer, quiverSlot, quiverIndex);
            shootArrow(itemstack, world, entityplayer);
        } else if ((quiverSlot != null && quiverSlot.itemID == Items.ARMOR_QUIVER_GOLD.id) ||
            entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW_GOLD.id)
            || entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW.id)
            || entityplayer.inventory.consumeInventoryItem(Items.AMMO_ARROW_FLAMING.id)) {
            shootArrow(itemstack, world, entityplayer);
        }
        return itemstack;
    }

    public static void shootArrow(@NonNull ItemStack itemstack, World world, Player entityplayer) {
        itemstack.damageItem(1, entityplayer);
        playRandomBowSound(world, entityplayer);
        joinArrow(world, entityplayer);
    }

    public static void playRandomBowSound(@NonNull World world, Player entityplayer) {
        world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
    }

    public static void joinArrow(@NonNull World world, Player entityplayer) {
        if (!world.isClientSide) {
            world.entityJoinedWorld(new ProjectileArrowFlaming(world, entityplayer, true, 0));
        }
    }

}
