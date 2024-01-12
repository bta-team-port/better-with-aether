package bta.aether.entity;

import bta.aether.world.AetherDimension;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkCoordinates;

public abstract class EntityAetherBossBase extends EntityMonster {

    public ChunkCoordinates belongsTo;

    public EntityAetherBossBase(World world) {
        super(world);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        CompoundTag belongsTo = new CompoundTag();
        belongsTo.putInt("x", this.belongsTo.x);
        belongsTo.putInt("y", this.belongsTo.y);
        belongsTo.putInt("z", this.belongsTo.z);

        tag.putCompound("belongsTo", belongsTo);
        super.addAdditionalSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        CompoundTag value = tag.getCompound("belongsTo");
        belongsTo = new ChunkCoordinates(value.getInteger("x"), value.getInteger("y"), value.getInteger("z"));
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void onEntityDeath() {
        AetherDimension.dugeonMap.put(belongsTo, true);
        super.onEntityDeath();
    }

    // boss bar to be displayed at the top of the screen.
    public String getBarTexture(){
        return "insert the default one here please.";
    }
}
