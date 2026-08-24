package teamport.aether.entity.animal.phyg;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
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
        this.jumpHeight = 0.6F;
        this.setTextureIdentifier("aether", "phyg");
        this.setSize(0.9F, 0.9F);
        this.stepDownSize = 0.5F;
        this.footSize = 0.5F;
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
        if (super.interact(player) && this.passenger == player) {
            player.triggerAchievement(AetherAchievements.PHYG);
        }

        return super.interact(player);
    }

    @Override
    public List<WeightedRandomLootObject> getMobDrops() {
        return this.remainingFireTicks > 0 ? this.burningMobDrops : this.mobDrops;
    }

    @Override
    public boolean isFeedableItem(ItemStack itemStack) {
        return itemStack != null && (itemStack.itemID == Blocks.MUSHROOM_BROWN.id() || itemStack.itemID == Blocks.MUSHROOM_RED.id());
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
