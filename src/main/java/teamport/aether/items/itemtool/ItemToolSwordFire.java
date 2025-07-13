package teamport.aether.items.itemtool;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemToolSwordFire extends ItemToolSword {

    public ItemToolSwordFire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.isAlive()) {
            target.maxFireTicks = 30 * 20;
            target.remainingFireTicks = 30 * 20;
        }

        itemstack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onUseItemOnBlock(ItemStack itemstack, Player player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
        blockX += side.getOffsetX();
        blockY += side.getOffsetY();
        blockZ += side.getOffsetZ();
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        if (blockID != 0) return false;
        if (!world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.FIRE.id())) return false;
        world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, (double)blockX + (double)0.5F, (double)blockY + (double)0.5F, (double)blockZ + (double)0.5F, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
        itemstack.damageItem(1, player);
        return true;
    }
}
