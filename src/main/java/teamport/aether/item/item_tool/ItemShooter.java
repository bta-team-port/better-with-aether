package teamport.aether.item.item_tool;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileDart;
import teamport.aether.item.AetherItems;

public class ItemShooter extends Item {
    public ItemShooter(String name, String namespaceId, int id) {
        super(name, namespaceId, id);
        this.setMaxDamage(384);
        this.maxStackSize = 1;
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        if (entityplayer.inventory.consumeInventoryItem(AetherItems.AMMO_DART_ENCHANTED.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 2.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileDart(world, entityplayer, true, 2));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(AetherItems.AMMO_DART_POISON.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 2.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileDart(world, entityplayer, true, 1));
            }
        } else if (entityplayer.inventory.consumeInventoryItem(AetherItems.AMMO_DART_GOLDEN.id)) {
            itemstack.damageItem(1, entityplayer);
            world.playSoundAtEntity(entityplayer, entityplayer, "random.bow", 0.3F, 2.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isClientSide) {
                world.entityJoinedWorld(new ProjectileDart(world, entityplayer, true, 0));
            }
        }
        return itemstack;
    }
}
