package teamport.aether.item.item_tool;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.world.World;
import teamport.aether.entity.projectile.ProjectileHammerHead;

public class ItemToolHammerNotch extends ItemToolSword {
    public ItemToolHammerNotch(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
        itemstack.damageItem(1, entityplayer);
        world.playSoundAtEntity(null, entityplayer, "mob.ghast.fireball", 0.5F, 0.5F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            world.entityJoinedWorld(new ProjectileHammerHead(world, entityplayer));
        }
        return itemstack;
    }
}
