package teamport.aether.entity.animal.phyg;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.animal.MobAetherAnimalRideable;
import teamport.aether.items.AetherItemTags;

import java.util.ArrayList;
import java.util.List;

public class MobPhyg extends MobAetherAnimalRideable {
    public float wingFold;
    public float wingFoldO;
    public float wingAngle;
    public float wingAngleO;
    public float aimingForFold;
    public int ticks;
    public List<WeightedRandomLootObject> burningMobDrops = new ArrayList<>();

    public MobPhyg(World world) {
        super(world);
        maxJumps = 1;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "phyg");
        this.setSize(0.9F, 0.9F);
        this.rideFootSize = 1.0f;

        this.mobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_RAW.getDefaultStack(), 1, 2));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.burningMobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_COOKED.getDefaultStack(), 1, 2));
    }

    public void tick() {
        super.tick();
        if (this.onGround) this.aimingForFold = 0.1F;
        else this.aimingForFold = 1.0F;

        this.wingAngleO = this.wingAngle;
        this.wingFoldO = this.wingFold;

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((float) this.ticks / 31.830988F);
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;

        if (this.yd < -0.2) this.yd = -0.2;
    }

    public void jump() {
        this.yd = 0.6;
    }

    public void defineSynchedData() {
        this.entityData.define(16, (byte) 0, Byte.class);
    }

    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
    }

    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
    }

    public String getLivingSound() {
        return "mob.pig";
    }

    public String getHurtSound() {
        return "mob.pig";
    }

    public String getDeathSound() {
        return "mob.pigdeath";
    }

    public boolean interact(@NonNull Player player) {
        if (super.interact(player)) return true;

        if (!this.getSaddled() || this.world.isClientSide) return false;
        if (this.passenger != null && this.passenger != player) return false;

        player.startRiding(this);
        player.triggerAchievement(AetherAchievements.PHYG);
        return true;
    }

    public void dropDeathItems() {
        if (this.getSaddled()) {
            this.dropItem(Items.SADDLE.id, 1);
        }

        super.dropDeathItems();
    }

    public List<WeightedRandomLootObject> getMobDrops() {
        return this.remainingFireTicks > 0 ? this.burningMobDrops : this.mobDrops;
    }

    public boolean getSaddled() {
        return (this.entityData.getByte(16) & 1) != 0;
    }

    public void setSaddled(boolean flag) {
        if (flag) this.entityData.set(16, (byte) 1);
        else this.entityData.set(16, (byte) 0);
    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.itemID < Blocks.blocksList.length && Blocks.blocksList[itemStack.itemID].hasTag(BlockTags.PIGS_FAVOURITE_BLOCK) || itemStack != null && itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }
}
