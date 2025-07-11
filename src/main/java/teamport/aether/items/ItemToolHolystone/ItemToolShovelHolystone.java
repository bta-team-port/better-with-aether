package teamport.aether.items.ItemToolHolystone;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.items.ItemToolShovelAether;

import static teamport.aether.items.AetherItems.AMBROSIUM;

public class ItemToolShovelHolystone extends ItemToolShovelAether {

    public ItemToolShovelHolystone(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int x, int y, int z, Side side, Mob
            mob) {
        Block<?> block = Blocks.blocksList[i];
        if (block != null && (block.getHardness() > 0.0F || this.isSilkTouch())) {
            itemstack.damageItem(1, mob);
        }
        if (itemRand.nextInt(32) == 0) {
            world.dropItem(x, y, z, new ItemStack(AMBROSIUM, 1));
        }
        return true;
    }
}
