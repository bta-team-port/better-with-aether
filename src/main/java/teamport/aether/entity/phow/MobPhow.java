package teamport.aether.entity.phow;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.tag.ItemTags;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.entity.MobAetherAnimal;
import teamport.aether.items.AetherItems;

public class MobPhow extends MobAetherAnimal {
    public float wingFold;
    public float wingAngle;
    private float aimingForFold;
    public int jumps;
    public int jrem;
    private boolean jpress;
    private int ticks;
    public MobPhow(World world) {
        super(world);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "phow");
        this.setSize(0.9F, 1.3F);
        this.mobDrops.add(new WeightedRandomLootObject(Items.LEATHER.getDefaultStack(), 1, 5));
        this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
    }

    public void tick() {
        super.tick();
        if (this.onGround) {
            this.aimingForFold = 0.1F;
            this.jpress = false;
            this.jrem = this.jumps;
        } else {
            this.aimingForFold = 1.0F;
        }

        ++this.ticks;
        this.wingAngle = this.wingFold * (float) Math.sin((float) this.ticks / 31.830988F);
        this.wingFold += (this.aimingForFold - this.wingFold) / 5.0F;
        this.fallDistance = 0.0F;
        if (this.yd < -0.2) {
            this.yd = -0.2;
        }

    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

    public String getLivingSound() {
        return "mob.cow";
    }

    protected String getHurtSound() {
        return "mob.cowhurt";
    }

    protected String getDeathSound() {
        return "mob.cowhurt";
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean interact(@NotNull Player player) {
        ItemStack itemstack = player.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == Items.BUCKET.id) {
            ItemBucketEmpty.useBucket(player, new ItemStack(Items.BUCKET_MILK));
            return true;
        } else if (itemstack != null && itemstack.itemID == AetherItems.BUCKET_SKYROOT.id) {
            ItemBucketEmpty.useBucket(player, new ItemStack(AetherItems.BUCKET_SKYROOT_MILK));
            return true;
        } else {
            return super.interact(player);
        }
    }

    public boolean isFavouriteItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem().hasTag(ItemTags.COWS_FAVOURITE_ITEM);
    }
}
