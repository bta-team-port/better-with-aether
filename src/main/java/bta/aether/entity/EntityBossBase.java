package bta.aether.entity;

import bta.aether.Aether;
import bta.aether.util.AetherBlockCoord;
import bta.aether.util.NameGenerator;
import bta.aether.world.AetherDimension;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.world.World;

import java.util.Arrays;

public abstract class EntityBossBase extends EntityMonster implements IAetherBoss{

    protected int belongsTo;
    protected ItemStack keyChain;
    private int maxHealth;
    public String personalBossName;
    public String translationKey;

    protected AetherBlockCoord returnPoint;
    protected AetherBlockCoord[] blocksDestroyOnDeath;

    {
        if (this.returnPoint == null)
            returnPoint = new AetherBlockCoord((int) this.x, (int) this.z, (int) this.y);
    }

    public EntityBossBase(World world, int maxHealth, String translationKey) {
        super(world);
        this.setMaxHealth(maxHealth);
        this.setHealthRaw(maxHealth);
        this.translationKey = translationKey;
        this.personalBossName = NameGenerator.getRandomName();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("belongsTo", belongsTo);
        tag.putString("personalBossName", personalBossName);

        if (keyChain != null) {
            CompoundTag inventoryNBT = new CompoundTag();
            keyChain.writeToNBT(inventoryNBT);
            tag.putCompound("keyChain", inventoryNBT);
        }

        super.addAdditionalSaveData(tag);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void tryToDespawn() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        keyChain = ItemStack.readItemStackFromNbt(tag.getCompound("keyChain"));
        belongsTo = tag.getInteger("belongsTo");
        personalBossName = tag.getString("personalBossName");
        super.readAdditionalSaveData(tag);
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 32.0F);
        if (entityplayer != null && (this.canEntityBeSeen(entityplayer) && entityplayer.gamemode.areMobsHostile())) {
            if (!((IPlayerBossList) entityplayer).aether$getBossList().contains(this)) {
                ((IPlayerBossList) entityplayer).aether$getBossList().add(this);
            }

            return entityplayer;
        }
        return null;
    }

    @Override
    public void onEntityDeath() {
        this.world.dropItem((int)x, (int)y, (int)z, keyChain);
        AetherDimension.dugeonMap.remove(belongsTo);
        Aether.LOGGER.info(personalBossName + " of ID " + belongsTo + " has been slain!");

        // try triggering the propagate on dungeon blocks.
        for (int x1 = -3; x1 < 3; x1++) {
            for (int z1 = -3; z1 < 3; z1++) {
                for (int y1 = -3; y1 < 3; y1++) {
                    world.notifyBlockChange((int) x + x1, (int) y + y1, (int) z + z1, world.getBlockId((int) x + x1, (int) y + y1, (int) z + z1));
                }
            }
        }

        if (this.blocksDestroyOnDeath != null) {
            world.playSoundEffect(null, 1003, (int) x, (int) y, (int) z, 0);
            for (AetherBlockCoord coordinate : this.blocksDestroyOnDeath) {
                world.spawnParticle("smoke", coordinate.getX(), coordinate.getY() + 0.8F, coordinate.getZ(), 0.0, 0.0, 0.0);
                world.spawnParticle("largesmoke", coordinate.getX(), coordinate.getY() + 0.8F, coordinate.getZ(), 0.0, 0.0, 0.0);
                world.setBlockAndMetadataWithNotify(coordinate.getX(), coordinate.getY(), coordinate.getZ(), 0, 0);
            }
        }
        super.onEntityDeath();
    }

    public String getBossTitle() {
        return personalBossName + ", The " +  I18n.getInstance().translateKey(translationKey);
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int health) {
        this.maxHealth = health;
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

    public void setReturnPoint(AetherBlockCoord coordinate) {
        this.returnPoint = coordinate;
    }

    public AetherBlockCoord getReturnPoint() {
        return this.returnPoint;
    }

    public void setBlocksDestroyOnDeath(AetherBlockCoord[] CoordinateArray) {
        this.blocksDestroyOnDeath = CoordinateArray;
    }

    public AetherBlockCoord[] getBlocksDestroyOnDeath() {
        return this.blocksDestroyOnDeath;
    }
}
