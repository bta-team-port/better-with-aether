package teamport.aether.item.item_tool.item_tool_holystone;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import teamport.aether.item.item_tool.ItemToolAxeAether;
import turniplabs.halplibe.helper.EnvironmentHelper;

import static teamport.aether.item.AetherItems.AMBROSIUM;

public class ItemToolAxeHolystone extends ItemToolAxeAether {
    public ItemToolAxeHolystone(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int x, int y, int z, Side side, Mob mob) {
        Block<?> block = Blocks.blocksList[i];
        if (block != null) {
            if (block.getHardness() > 0.0F || this.isSilkTouch()) {
                itemstack.damageItem(1, mob);
            }
            if (!EnvironmentHelper.isClientWorld() && itemRand.nextInt(16) == 0 && block.getHardness() > 0.0F) {
                world.dropItem(x, y, z, new ItemStack(AMBROSIUM, 1));
            }
        }
        return true;
    }
}

