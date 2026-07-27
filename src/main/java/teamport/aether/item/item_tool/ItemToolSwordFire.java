package teamport.aether.item.item_tool;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.world.AetherDimension;

public class ItemToolSwordFire extends ItemToolSword implements AetherHasCustomDamageType {
    public ItemToolSwordFire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target instanceof Mob && target.hurtTime == 10 && hitEntity) {
            if ((target instanceof Player) && ((Player) target).gamemode.hasInvulnerablePlayer()) {
                return false;
            }
            ParticleMaker.spawnFireSwordParticles(target);
            target.maxFireTicks = 600;
            target.remainingFireTicks = 600;
            return true;
        }
        return false;
    }

    @Override
    public boolean onUseOnBlock(ItemStack itemstack, World world, Player player, TilePosc blockPos, Side side, double xPlaced, double yPlaced) {
        int blockX = blockPos.x();
        int blockY = blockPos.y();
        int blockZ = blockPos.z();
        blockX += side.offsetX();
        blockY += side.offsetY();
        blockZ += side.offsetZ();
        int blockID = world.getBlockId(blockX, blockY, blockZ);
        if (blockID != 0) return false;
        if (world.dimension != AetherDimension.getAether() && player != null && !world.setBlockWithNotify(blockX, blockY, blockZ, Blocks.FIRE.id())) return false;
        world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, (double) blockX + (double) 0.5F, (double) blockY + (double) 0.5F, (double) blockZ + (double) 0.5F, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
        itemstack.damageItem(1, player);
        return true;
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FIRE;
    }
}
