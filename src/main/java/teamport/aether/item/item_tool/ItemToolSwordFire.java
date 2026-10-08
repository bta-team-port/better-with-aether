package teamport.aether.item.item_tool;

import net.minecraft.core.block.Block;
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
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.NonNull;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.world.AetherDimension;

public class ItemToolSwordFire extends ItemToolSword implements AetherHasCustomDamageType {
    public ItemToolSwordFire(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(@NonNull ItemStack itemstack, @NonNull Mob target, @NonNull Mob attacker) {
        if (!(target instanceof EntityAccessor accessor)
            || accessor.isFireImmune()
            || target.hurtTime != 10
            || (target instanceof Player player && player.gamemode.hasInvulnerablePlayer())
            || !attacker.isSneaking()
        ) {
            return false;
        }
        ParticleMaker.spawnFireSwordParticles(target);
        target.maxFireTicks = 600;
        target.remainingFireTicks = 600;
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public boolean onUseOnBlock(@NonNull ItemStack itemstack, @NonNull World world, Player player, @NonNull TilePosc blockPos, @NonNull Side side, double xPlaced, double yPlaced) {
        TilePos tilePos = new TilePos(blockPos).add(side);
        Block<?> block = world.getBlockType(tilePos);
        if (block != Blocks.AIR) return false;
        if (world.dimension != AetherDimension.getAether() && player != null && !world.setBlockTypeNotify(tilePos, Blocks.FIRE)) {
            return false;
        }
        world.playSoundEffect(player, SoundCategory.WORLD_SOUNDS, (double) tilePos.x() + (double) 0.5F, (double) tilePos.y() + (double) 0.5F, (double) tilePos.z() + (double) 0.5F, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
        itemstack.damageItem(1, player);
        return true;
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FIRE;
    }
}
