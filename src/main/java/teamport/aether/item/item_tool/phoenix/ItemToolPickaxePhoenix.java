package teamport.aether.item.item_tool.phoenix;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import teamport.aether.mixin.accessors.EntityAccessor;

public class ItemToolPickaxePhoenix extends ItemToolPickaxeAether implements AetherHasCustomDamageType, PhoenixAdjustBlockDropped {
	public ItemToolPickaxePhoenix(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
		super(name, namespaceId, id, enumtoolmaterial);
	}

	@Override
	public boolean hitEntity(@NotNull ItemStack itemstack, @NotNull Mob target, @NotNull Mob attacker) {
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
    public boolean canHarvestBlock(@NonNull ItemStack selfStack, @NonNull Mob mob, @NonNull Block<?> block) {
        Integer miningLevel = aetherMiningLevels.get(block);
        if (miningLevel != null) {
            return this.material.getMiningLevel() >= miningLevel;
        } else {
            return block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) || block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
        }
    }

    @Override
	public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
		return !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE) && !block.hasTag(BlockTags.MINEABLE_BY_PICKAXE) ? 1.0F : this.material.getEfficiency(false);
	}

	@Override
	public DamageType getDamageType() {
		return DamageType.FIRE;
	}
}
