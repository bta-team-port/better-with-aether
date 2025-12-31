package teamport.aether.item.item_tool.item_tool_gravitite;

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
import teamport.aether.block.AetherBlockTags;
import teamport.aether.entity.MobUtil;
import teamport.aether.entity.floating_block.EntityFloatingBlock;
import teamport.aether.item.AetherHasCustomDamageType;
import teamport.aether.item.item_tool.ItemToolPickaxeAether;
import turniplabs.halplibe.helper.EnvironmentHelper;

public class ItemToolPickaxeGravitite extends ItemToolPickaxeAether implements AetherHasCustomDamageType {
    private static final float KNOCKBACK_STRENGTH = 3.0F/4.0F;
    private static final float LIFT = KNOCKBACK_STRENGTH;

    public ItemToolPickaxeGravitite(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        if (target instanceof Mob && target.hurtTime == 10) {
            if(attacker.isSneaking() && attacker instanceof Player){
                MobUtil.knockback(target, attacker,KNOCKBACK_STRENGTH, 0.4f);
            }else{
                MobUtil.knockback(target, attacker, 0.4f, LIFT);
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
            || !player.isSneaking() // because otherwise it not possible to open chests
            || block.getHardness() < 0
            || nextBlock != null
            && nextBlock.id() != Blocks.COBWEB.id()
            && !nextBlock.hasTag(BlockTags.PLACE_OVERWRITES)) {
            return false;
        }

        if (EnvironmentHelper.isClientWorld()) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockX, blockY, blockZ);
        int metadata = world.getBlockMetadata(blockX, blockY, blockZ);
        world.removeBlockTileEntity(blockX, blockY, blockZ);
        world.setBlockWithNotify(blockX, blockY, blockZ, 0);
        EntityFloatingBlock entityFloatingBlock = new EntityFloatingBlock(
            world,
            (double) blockX + 0.5F, (double) blockY + 0.5F, (double) blockZ + 0.5F,
            block.id(), metadata, tileEntity);
        entityFloatingBlock.setHasRemovedBlock(true);
        world.entityJoinedWorld(entityFloatingBlock);
        itemstack.damageItem(1, player);
        return true;
    }

    @Override
    public DamageType getDamageType(){
        return DamageType.FALL;
    }
}
