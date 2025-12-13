package teamport.aether.entity.animal.phyg;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.achievements.AetherAchievements;
import teamport.aether.entity.animal.MobAetherAnimalRideable;
import teamport.aether.item.AetherItemTags;

import java.util.ArrayList;
import java.util.List;

public class MobPhyg extends MobAetherAnimalRideable {
    private float wingFold;
    private float wingFoldO;
    private float wingAngle;
    private float wingAngleO;

    private int ticks;
    private final List<WeightedRandomLootObject> burningMobDrops = new ArrayList<>();

    public MobPhyg(World world) {
        super(world);
        this.maxJumps = 1;
        this.textureIdentifier = NamespaceID.getPermanent("aether", "phyg");
        this.setSize(0.9F, 0.9F);
        this.rideFootSize = 1.0f;

        this.mobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_RAW.getDefaultStack(), 1, 2));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
        this.burningMobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_COOKED.getDefaultStack(), 1, 2));
    }

    @Override
    public void tick() {
        super.tick();
        float aimingForFold = this.onGround ? 0.1F : 1.0F;

        this.wingAngleO = this.wingAngle;
        this.wingFoldO = this.wingFold;

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.830988F);
        this.wingFold += (aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;

        if (this.yd < -0.2) this.yd = -0.2;
    }

    @Override
    public void jump() {
        this.yd = 0.6;
    }

    @Override
    public void defineSynchedData() {
        this.entityData.define(16, (byte) 0, Byte.class);
    }

    @Override
    public void addAdditionalSaveData(@NonNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddle", this.getSaddled());
    }

    @Override
    public void readAdditionalSaveData(@NonNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSaddled(tag.getBoolean("Saddle"));
    }

    @Override
    public String getLivingSound() {
        return "mob.pig";
    }

    @Override
    public String getHurtSound() {
        return "mob.pig";
    }

    @Override
    public String getDeathSound() {
        return "mob.pigdeath";
    }

    @Override
    public boolean interact(@NonNull Player player) {
        if (super.interact(player)) return true;

        if (!this.getSaddled() || this.world == null || this.world.isClientSide) return false;
        if (this.passenger != null && this.passenger != player) return false;

        player.startRiding(this);
        player.triggerAchievement(AetherAchievements.PHYG);
        return true;
    }

    @Override
    public void dropDeathItems() {
        if (this.getSaddled()) {
            this.dropItem(Items.SADDLE.id, 1);
        }

        super.dropDeathItems();
    }

    @Override
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

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        if (itemStack == null) return false;
        if (itemStack.itemID < Blocks.blocksList.length) {
            Block<?> block = Blocks.blocksList[itemStack.itemID];
            if (block != null && block.hasTag(BlockTags.PIGS_FAVOURITE_BLOCK)) return true;
        }
        return itemStack.getItem().hasTag(AetherItemTags.NATURE_STAFF_FOLLOW);
    }

    public float getWingFold() {
        return wingFold;
    }
    public float getWingFoldO() {
        return wingFoldO;
    }
    public float getWingAngle() {
        return wingAngle;
    }
    public float getWingAngleO() {
        return wingAngleO;
    }
}
