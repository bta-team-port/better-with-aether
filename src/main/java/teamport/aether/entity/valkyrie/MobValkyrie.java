package teamport.aether.entity.valkyrie;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.items.AetherItems;

public class MobValkyrie extends MobMonster implements Enemy {
    public static final ItemStack defaultHeldItem;
    public MobValkyrie(@Nullable World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "valkyrie");
        this.setSize(0.8F, 2.0F);
        this.mobDrops.add(new WeightedRandomLootObject(AetherItems.MEDAL_VICTORY.getDefaultStack(), 1));
    }

    public void spawnInit() {
        super.spawnInit();
        this.setHoldingSword(true);
    }

    public void defineSynchedData() {
        this.entityData.define(6, 0, Integer.class);
        this.entityData.define(7, (short) 0, Short.class);
        this.entityData.define(8, (byte)0, Byte.class);
    }

    public int getMaxHealth() {
        return 40;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public void setHoldingSword(boolean flag) {
        if (flag) {
            this.entityData.set(8, (byte)(this.entityData.getByte(8) | 1));
            this.attackStrength = 7;
        } else {
            this.entityData.set(8, (byte)(this.entityData.getByte(8) & -2));
            this.attackStrength = 5;
        }

    }

    public boolean isHoldingSword() {
        byte data = this.entityData.getByte(8);
        return (data & 1) != 0;
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("hasSword", this.isHoldingSword());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.containsKey("hasSword")) {
            this.setHoldingSword(tag.getBoolean("hasSword"));
        }

    }

    public ItemStack getHeldItem() {
        return this.isHoldingSword() ? defaultHeldItem : null;
    }

    static {
        defaultHeldItem = new ItemStack(Items.TOOL_SWORD_GOLD, 1);
    }
}
