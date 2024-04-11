package bta.aether.entity;

import bta.aether.block.AetherBlocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityMimic extends EntityMonster {
    public EntityMimic(World world) {
        super(world);
        setSize(0.9F, 0.9F);
    }
    private int tickCounter = 0;

    @Override
    protected void dropFewItems() {
        this.spawnAtLocation(AetherBlocks.chestSkyroot.id, 1);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void tryToDespawn() {
    }

    @Override
    public boolean hurt(Entity attacker, int i, DamageType type) {
        if (attacker instanceof EntityPlayer) {
            ItemStack stack = ((EntityPlayer) attacker).inventory.getCurrentItem();
            if (stack != null && stack.getItem() instanceof ItemToolAxe)
                return super.hurt(attacker,i*3, type);
        }
        return super.hurt(attacker, i, type);
    }

    @Override
    public void tick() {
        super.tick();
        tickCounter = (tickCounter+1)%100;
        if (tickCounter == 0 && world.players.stream().noneMatch(entityPlayer -> this.distanceToSqr(entityPlayer) < 10000) && world.getBlockId((int) this.x, (int) this.y, (int) this.z) == 0) {
            world.setBlockWithNotify((int) this.x, (int) this.y, (int) this.z, AetherBlocks.chestMimic.id);
            this.remove();
        }
    }

    // TODO: get a better sound for this (*˘▽˘)b
    @Override
    protected String getDeathSound() {
        return "step.wood";
    }

    @Override
    protected String getHurtSound() {
        return "step.wood";
    }

    public String getEntityTexture() {
            return "/assets/aether/mobs/Mimic.png";
        }

    public String getDefaultEntityTexture() {
        return "/assets/aether/mobs/Mimic.png";
    }
}
