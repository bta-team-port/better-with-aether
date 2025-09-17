package teamport.aether.entity.monster.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.items.itemtool.ItemToolAxeAether;

import java.util.List;

public class MobMimic extends MobMonsterAether implements Enemy, AetherDeathMessage {

    public static final int VARIANT_SKYROOT = 0;

    public static final int VARIANT_OAK = 1;
    public static final int VARIANT_OAK_WHITE = 2;
    public static final int VARIANT_OAK_ORANGE = 3;
    public static final int VARIANT_OAK_MAGENTA = 4;
    public static final int VARIANT_OAK_LIGHTBLUE = 5;
    public static final int VARIANT_OAK_YELLOW = 6;
    public static final int VARIANT_OAK_LIME = 7;
    public static final int VARIANT_OAK_PINK = 8;
    public static final int VARIANT_OAK_GRAY = 9;
    public static final int VARIANT_OAK_SILVER = 10;
    public static final int VARIANT_OAK_CYAN = 11;
    public static final int VARIANT_OAK_PURPLE = 12;
    public static final int VARIANT_OAK_BLUE = 13;
    public static final int VARIANT_OAK_BROWN = 14;
    public static final int VARIANT_OAK_GREEN = 15;
    public static final int VARIANT_OAK_RED = 16;
    public static final int VARIANT_OAK_BLACK = 17;

    public static final int VARIANT_DUNGEON_BRONZE = 18;
    public static final int VARIANT_DUNGEON_SILVER = 19;
    public static final int VARIANT_DUNGEON_GOLD = 20;

    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.scoreValue = 2000;

        this.setSkinVariant(VARIANT_SKYROOT);

        this.setSkinVariant(VARIANT_OAK);
        this.setSkinVariant(VARIANT_OAK_WHITE);
        this.setSkinVariant(VARIANT_OAK_ORANGE);
        this.setSkinVariant(VARIANT_OAK_MAGENTA);
        this.setSkinVariant(VARIANT_OAK_LIGHTBLUE);
        this.setSkinVariant(VARIANT_OAK_YELLOW);
        this.setSkinVariant(VARIANT_OAK_LIME);
        this.setSkinVariant(VARIANT_OAK_PINK);
        this.setSkinVariant(VARIANT_OAK_GRAY);
        this.setSkinVariant(VARIANT_OAK_SILVER);
        this.setSkinVariant(VARIANT_OAK_CYAN);
        this.setSkinVariant(VARIANT_OAK_PURPLE);
        this.setSkinVariant(VARIANT_OAK_BLUE);
        this.setSkinVariant(VARIANT_OAK_BROWN);
        this.setSkinVariant(VARIANT_OAK_GREEN);
        this.setSkinVariant(VARIANT_OAK_RED);
        this.setSkinVariant(VARIANT_OAK_BLACK);

        this.setSkinVariant(VARIANT_DUNGEON_BRONZE);
        this.setSkinVariant(VARIANT_DUNGEON_SILVER);
        this.setSkinVariant(VARIANT_DUNGEON_GOLD);
    }

    @Override
    public void dropDeathItems() {
        ItemStack chestStack;
        switch (this.getSkinVariant()) {
            case VARIANT_OAK:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK, 1, 0);
                break;
            case VARIANT_OAK_WHITE:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 0);
                break;
            case VARIANT_OAK_ORANGE:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 16);
                break;
            case VARIANT_OAK_MAGENTA:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 32);
                break;
            case VARIANT_OAK_LIGHTBLUE:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 48);
                break;
            case VARIANT_OAK_YELLOW:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 64);
                break;
            case VARIANT_OAK_LIME:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 80);
                break;
            case VARIANT_OAK_PINK:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 96);
                break;
            case VARIANT_OAK_GRAY:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 112);
                break;
            case VARIANT_OAK_SILVER:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 128);
                break;
            case VARIANT_OAK_CYAN:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 144);
                break;
            case VARIANT_OAK_PURPLE:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 160);
                break;
            case VARIANT_OAK_BLUE:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 176);
                break;
            case VARIANT_OAK_BROWN:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 192);
                break;
            case VARIANT_OAK_GREEN:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 208);
                break;
            case VARIANT_OAK_RED:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 224);
                break;
            case VARIANT_OAK_BLACK:
                chestStack = new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, 240);
                break;
            case VARIANT_DUNGEON_BRONZE:
                chestStack = new ItemStack(AetherBlocks.CHEST_DUNGEON_BRONZE, 1, 0);
                break;
            case VARIANT_DUNGEON_SILVER:
                chestStack = new ItemStack(AetherBlocks.CHEST_DUNGEON_SILVER, 1, 0);
                break;
            case VARIANT_DUNGEON_GOLD:
                chestStack = new ItemStack(AetherBlocks.CHEST_DUNGEON_GOLD, 1, 0);
                break;
            default:
                chestStack = new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT, 1, 0);
        }

        this.dropItem(chestStack, 0);
    }


    public Entity findPlayerToAttack() {
        Player entityplayer = this.world.getClosestPlayerToEntity(this, 64.0);
        return entityplayer != null && this.canEntityBeSeen(entityplayer) && entityplayer.getGamemode().areMobsHostile() ? entityplayer : null;
    }

    public void attackEntity(@NotNull Entity entity, float distance) {
        if (this.attackTime <= 0 && distance < 2.5F && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
            this.attackTime = 20;
            entity.hurt(this, this.attackStrength, DamageType.COMBAT);
        }

    }

    public boolean hurt(Entity attacker, int damage, DamageType type) {

        if (attacker instanceof Player) {
            ItemStack item = ((Player) attacker).inventory.getCurrentItem();

            if (item != null && (item.getItem() instanceof ItemToolAxe || item.getItem() instanceof ItemToolAxeAether)) {
                return super.hurt(attacker, damage * 2, type);
            }
        }
        return super.hurt(attacker, damage, type);
    }

    public String getHurtSound() {
        return "step.wood";
    }

    public String getDeathSound() {
        return "random.door_open";
    }

    public float getSoundVolume() {
        return 0.6F;
    }

    public int getMaxHealth() {
        return 80;
    }

    public void setLoot(List<ItemStack> loot) {
        if (loot == null || loot.isEmpty()) return;

        int amount = random.nextInt(4) + 1;

        int end = Math.min(random.nextInt(loot.size()) + amount, loot.size());
        int start = Math.max(0, end - amount);

        for (int i = start; i < end; i++) {
            mobDrops.add(new WeightedRandomLootObject(loot.get(i)));
        }

    }
}
