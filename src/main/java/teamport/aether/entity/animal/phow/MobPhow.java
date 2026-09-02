package teamport.aether.entity.animal.phow;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import teamport.aether.entity.animal.MobAetherAnimalRideable;
import teamport.aether.item.AetherItems;
import teamport.aether.item.ItemBucketSkyrootEmpty;

public class MobPhow extends MobAetherAnimalRideable {
    private float wingFold;
    private float wingFoldO;
    private float wingAngle;
    private float wingAngleO;
    private int ticks;

    public MobPhow(World world) {
        super(world);
        this.maxJumps = 1;
        this.jumpHeight = 0.8F;
        this.setTextureIdentifier("aether", "phow");
        this.setSize(0.9F, 1.3F);
        this.stepDownSize = 1.0F;
        this.footSize = 1.0F;
        this.accelerationRate = 2.0F;
        this.mobDrops.add(new WeightedRandomLootObject(Items.LEATHER.getDefaultStack(), 1, 5));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
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
    public double getRideHeight() {
        return this.bbHeight;
    }

    @Override
    public String getLivingSound() {
        return "mob.cow";
    }

    @Override
    public String getHurtSound() {
        return "mob.cowhurt";
    }

    @Override
    public String getDeathSound() {
        return "mob.cowhurt";
    }

    @Override
    public float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean isFeedableItem(ItemStack itemStack) {
        return itemStack != null && itemStack.itemID == Items.WHEAT.id;
    }

    @Override
    public boolean interact(@NonNull Player player) {
        ItemStack itemstack = player.getHeldItem();

        if (itemstack != null) {
            if (itemstack.itemID == Items.BUCKET_IRON.id && ItemBucket.STATE_EMPTY.equals(ItemBucket.getState(itemstack))) {
                ItemBucket.useBucket(itemstack, player, this.world, ItemBucket.STATE_MILK);
                return true;
            } else if (itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
                ItemBucketSkyrootEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
                return true;
            }
        }

        return super.interact(player);
    }

    @Override
    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(ItemTags.COWS_FAVOURITE_ITEM);
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
