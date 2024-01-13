package bta.aether.entity;

import bta.aether.Aether;
import bta.aether.world.AetherDimension;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;

public abstract class EntityAetherBossBase extends EntityMonster implements IAetherBoss{

    protected int belongsTo;
    protected ItemStack keyChain;
    protected int maxHealth;

    public EntityAetherBossBase(World world, int maxHealth) {
        super(world);
        this.maxHealth = maxHealth;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("belongsTo", belongsTo);

        if (keyChain != null) {
            CompoundTag inventoryNBT = new CompoundTag();
            keyChain.writeToNBT(inventoryNBT);
            tag.putCompound("inventory", inventoryNBT);
        }

        super.addAdditionalSaveData(tag);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        keyChain = ItemStack.readItemStackFromNbt(tag.getCompound("inventory"));
        belongsTo = tag.getInteger("belongsTo");
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void onEntityDeath() {
        this.world.dropItem((int)x, (int)y, (int)z, keyChain);
        AetherDimension.dugeonMap.remove(belongsTo);
        Aether.LOGGER.info("A boss of ID " + belongsTo + " has been slain!");
        super.onEntityDeath();
    }

    public String getBossTitle() {
        return I18n.getInstance().translateKey("boss."+Aether.MOD_ID+".father_sentry.title");
    }

    public void setMaxHealth(int health) {
        this.maxHealth = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setToDungeon(int ID) {
        this.belongsTo = ID;
    }

    public int getDungeon() {
        return this.belongsTo;
    }

    public void setKeychain(ItemStack key) {
        this.keyChain = key;
    }

    public ItemStack getKeyChain() {
        return this.keyChain;
    }
}
