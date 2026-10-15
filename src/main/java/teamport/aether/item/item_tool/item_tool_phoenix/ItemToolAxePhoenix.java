package teamport.aether.item.item_tool.item_tool_phoenix;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicLog;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.gamerule.TreecapitatorHelper;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolAxeAether;
import teamport.aether.mixin.accessors.EntityAccessor;

public class ItemToolAxePhoenix extends ItemToolAxeAether implements AetherHasCustomDamageType, PhoenixAdjustBlockDropped {
    public ItemToolAxePhoenix(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
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
    public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
        return !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) && !block.hasTag(BlockTags.MINEABLE_BY_AXE) ? 1.0F : this.material.getEfficiency(false);
    }

    @Override
    public boolean beforeBlockDestroyed(@NonNull ItemStack itemStack, @NonNull World world, @NonNull Player player, @NonNull Block<?> block, @NonNull TilePosc blockPos, @NonNull Side side) {
        if (!world.isClientSide && world.getGameRuleValue(GameRules.TREECAPITATOR) && !player.isSneaking()) {
            ItemStack held = player.getHeldItem();
            if (Block.hasLogicClass(block, BlockLogicLog.class) && (block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_AXE) || held != null && held.itemID == this.id)) {
                return !(new TreecapitatorHelper(world, blockPos.x(), blockPos.y(), blockPos.z(), player)).chopTree();
            }
        }

        return true;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.FIRE;
    }
}
