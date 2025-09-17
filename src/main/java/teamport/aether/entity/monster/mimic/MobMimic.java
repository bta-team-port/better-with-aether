package teamport.aether.entity.monster.mimic;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.BlockLogicChestPainted;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.Enemy;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.entity.AetherDeathMessage;
import teamport.aether.entity.monster.MobMonsterAether;
import teamport.aether.items.itemtool.ItemToolAxeAether;
import teamport.aether.world.AetherDimension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final Map<Integer, ItemStack> VARIANT_TO_CHEST = new HashMap<>();

    static {
        VARIANT_TO_CHEST.put(VARIANT_OAK,  new ItemStack(Blocks.CHEST_PLANKS_OAK, 1, 0));
        VARIANT_TO_CHEST.put(VARIANT_SKYROOT, new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT, 1, 0));
        VARIANT_TO_CHEST.put(VARIANT_DUNGEON_BRONZE, new ItemStack(AetherBlocks.CHEST_DUNGEON_BRONZE, 1, 0));
        VARIANT_TO_CHEST.put(VARIANT_DUNGEON_SILVER, new ItemStack(AetherBlocks.CHEST_DUNGEON_SILVER, 1, 0));
        VARIANT_TO_CHEST.put(VARIANT_DUNGEON_GOLD, new ItemStack(AetherBlocks.CHEST_DUNGEON_GOLD, 1, 0));

        for (DyeColor color : DyeColor.values()) {
            int meta = BlockLogicChestPainted.getMetaForDyeColor(color.itemMeta);
            VARIANT_TO_CHEST.put(color.blockMeta+2, new ItemStack(Blocks.CHEST_PLANKS_OAK_PAINTED, 1, meta));
        }

    }

    public MobMimic(World world) {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.textureIdentifier = NamespaceID.getPermanent("aether", "mimic");
        this.attackStrength = 5;
        this.scoreValue = 2000;

        if (world.dimension.id == AetherDimension.AetherDimensionID) this.setSkinVariant(VARIANT_SKYROOT);
        else this.setSkinVariant(VARIANT_OAK);
    }

    @Override
    public void dropDeathItems() {
        ItemStack chestStack = VARIANT_TO_CHEST.getOrDefault(
            this.getSkinVariant(),
            new ItemStack(AetherBlocks.CHEST_PLANKS_SKYROOT, 1, 0)
        );

        this.dropItem(chestStack, 0);
        super.dropDeathItems();
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
