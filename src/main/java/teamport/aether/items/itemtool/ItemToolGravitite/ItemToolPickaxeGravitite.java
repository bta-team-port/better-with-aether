package teamport.aether.items.itemtool.ItemToolGravitite;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import teamport.aether.blocks.AetherBlockTags;
import teamport.aether.entity.floatingBlock.EntityFloatingBlock;
import teamport.aether.helper.MobUtil;
import teamport.aether.items.AetherHasCustomDamageType;
import teamport.aether.items.itemtool.ItemToolPickaxeAether;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemToolPickaxeGravitite extends ItemToolPickaxeAether implements AetherHasCustomDamageType {
    public final float knockbackStrength;
    public final float lift;

    public ItemToolPickaxeGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
        this.knockbackStrength = this.lift = 3.0f/4.0f;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10) {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker,knockbackStrength, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, lift);
            }
        }
        return super.hitEntity(itemstack, target, attacker);
    }

    @Override
    public boolean onUseItemOnBlock(
            ItemStack itemstack,
            Player player,
            World world,
            int blockX, int blockY, int blockZ,
            Side side,
            double xPlaced, double yPlaced
    ) {
        Block<?> block = world.getBlock(blockX, blockY, blockZ);
        Block<?> nextBlock = world.getBlock(blockX, blockY + 1, blockZ);
        if (block == null
                || !block.hasTag(AetherBlockTags.MINEABLE_BY_AETHER_PICKAXE)
                || !player.isSneaking()
                || block.getHardness() < 0
                || nextBlock != null
                && nextBlock.id() != Blocks.COBWEB.id()
                && !nextBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
            return false;
        }

        if (EnvironmentHelper.isClientWorld()) {
            return true;
        }

        @Nullable TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);
        int metadata = world.getBlockMetadata(blockX, blockY, blockZ);
        world.removeBlockTileEntity(blockX, blockY, blockZ);
        world.setBlockWithNotify(blockX, blockY, blockZ, 0);
        EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(
                world,
                (double) blockX + 0.5F, (double) blockY + 0.5F, (double) blockZ + 0.5F,
                block.id(), metadata, tileEntity);
        entityFloatingBlock.hasRemovedBlock = true;
        world.entityJoinedWorld(entityFloatingBlock);
        itemstack.damageItem(1, player);
        return true;
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FALL;
    }
}
