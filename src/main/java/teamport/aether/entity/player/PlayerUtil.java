package teamport.aether.entity.player;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobSlime;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.enums.HumanArmorShape;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import sunsetsatellite.catalyst.effects.api.effect.EffectContainer;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import teamport.aether.ducks.IContainerInventoryAether;
import teamport.aether.effect.AetherEffects;
import teamport.aether.effect.DeathCauseEffects;
import teamport.aether.entity.PreVehicle;
import teamport.aether.entity.boss.DeathCauseBoss;
import teamport.aether.entity.boss.EnemyBoss;
import teamport.aether.entity.monster.mimic.DeathCauseMimic;
import teamport.aether.entity.monster.mimic.MobMimic;
import teamport.aether.entity.monster.swet.DeathCauseKilledSecondary;
import teamport.aether.entity.monster.swet.MobSwet;
import teamport.aether.entity.monster.swet.MobSwetGold;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.gloves.ItemGloves;
import teamport.aether.item.accessory.pendant.ItemIcePendant;
import turniplabs.halplibe.helper.EnvironmentHelper;
import turniplabs.halplibe.util.deathcause.DeathCause;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseGeneric;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseProjectile;

import static teamport.aether.item.accessory.SlotAccessory.*;

public class PlayerUtil {
    private PlayerUtil() {/* no need to initiate*/}

    public enum InventoryType {
        HOLD, MAIN, ARMOR
    }

    ///  Count the armor pieces of a specific material.
    @SuppressWarnings("java:S135")
    public static int countArmorPiecesOfMaterial(@NonNull ContainerInventory inventory, ArmorMaterial material) {
        int count = 0;
        for (int i = 0; i < inventory.armorInventory.length; ++i) {
            ItemStack itemStack = inventory.armorInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof IArmorItem<?> armor && armor.getArmorShape().getSlotIndex() == i && hasArmorMaterial(armor, material)) {
                count++;
            }

        }

        if (inventory instanceof IContainerInventoryAether aetherInv) {
            ItemStack[] accessories = aetherInv.aether$getAccessoryInventory();
            if (accessories != null && 0 < accessories.length) {
                ItemStack glovesStack = accessories[0];
                if (glovesStack != null && glovesStack.getItem() instanceof ItemGloves gloves && gloves.getArmorMaterial() != null && gloves.getArmorMaterial().equals(material)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean hasArmorMaterial(@NonNull IArmorItem<?> armor, ArmorMaterial material) {
        ArmorMaterial armorMaterial = armor.getArmorMaterial();
        return armorMaterial != null && armorMaterial.equals(material);
    }

    /// Checks if player is wearing gold pendants
    public static boolean isSilkTouchPendant(Player player) {
        ItemStack trinketOne = getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
        ItemStack trinketTwo = getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
        return trinketOne != null && trinketOne.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id
            || trinketTwo != null && trinketTwo.getItem().id == AetherItems.ARMOR_TALISMAN_GOLD.id;
    }

    /// Y pos on the server counted from the player's foot height but on the client it is counted from the player's head height
    /// We want count the player pos from his feet
    public static double getY(Player player) {
        if (EnvironmentHelper.isSingleplayerClient()) {
            return player.y - player.bbHeight;
        }
        return player.y;
    }

    public static double getHeadY(Player player) {
        if (EnvironmentHelper.isSingleplayerClient()) {
            return player.y;
        }
        return player.y - player.bbHeight;
    }

    public static void damageItem(Player player, ItemStack stack, InventoryType type, int index) {
        PlayerUtil.damageItem(player, 1, stack, type, index);
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero.
    public static void damageItem(Player player, int itemDamage, @NonNull ItemStack stack, InventoryType type, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            switch (type) {
                case HOLD:
                    player.destroyCurrentEquippedItem();
                    return;
                case ARMOR:
                    PlayerUtil.damageItemArmor(player, itemDamage, stack, index);
                    return;
                case MAIN:
                default:
                    PlayerUtil.damageItemMain(player, itemDamage, stack, index);
            }
        }
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero. This target an item to destroy
    /// in the player main inventory at an index.
    public static void damageItemMain(Player player, int itemDamage, @NonNull ItemStack stack, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            player.inventory.mainInventory[index] = null;
        }
    }

    ///  Tool taking only 1 damage is quite common.
    public static void damageItemMain(Player player, ItemStack stack, int index) {
        PlayerUtil.damageItemMain(player, 1, stack, index);
    }

    /// The normal damageItem does not destroy the item if the item durability hits zero. This target an item to destroy
    /// in the player armor inventory at an index.
    public static void damageItemArmor(Player player, int itemDamage, @NonNull ItemStack stack, int index) {
        stack.damageItem(itemDamage, player);
        if (stack.stackSize <= 0) {
            if (index < player.inventory.armorInventory.length) {
                player.inventory.armorInventory[index] = null;
            } else {
                ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory()[index - GLOVES_SLOT] = null;
            }
        }
    }

    public static @Nullable ItemStack getArmorOrAccessoryItem(@NonNull Player player, int armorSlot) {
        if (armorSlot < player.inventory.armorInventory.length) {
            return player.inventory.armorInventory[armorSlot];
        }
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        int accessorySlot = armorSlot - GLOVES_SLOT;
        return accessorySlot >= 0 && accessorySlot < accessories.length ? accessories[accessorySlot] : null;
    }

    public static void clearArmorOrAccessoryItem(@NonNull Player player, int armorSlot) {
        if (armorSlot < player.inventory.armorInventory.length) {
            player.inventory.armorInventory[armorSlot] = null;
            return;
        }
        ItemStack[] accessories = ((IContainerInventoryAether) player.inventory).aether$getAccessoryInventory();
        int accessorySlot = armorSlot - GLOVES_SLOT;
        if (accessorySlot >= 0 && accessorySlot < accessories.length) {
            accessories[accessorySlot] = null;
        }
    }

    public static @Nullable ItemStack getActiveQuiver(Player player) {
        int slot = getActiveQuiverSlot(player);
        return slot >= 0 ? getArmorOrAccessoryItem(player, slot) : null;
    }

    public static int getActiveQuiverSlot(Player player) {
        int chestSlot = HumanArmorShape.CHEST.getSlotIndex();
        if (isUsableQuiver(getArmorOrAccessoryItem(player, chestSlot))) return chestSlot;
        if (isUsableQuiver(getArmorOrAccessoryItem(player, CAPE_SLOT))) return CAPE_SLOT;
        return -1;
    }

    public static boolean isUsableQuiver(ItemStack stack) {
        if (stack == null) return false;
        if (stack.itemID == Items.ARMOR_QUIVER_GOLD.id) return true;
        return stack.itemID == Items.ARMOR_QUIVER.id && stack.getMetadata() < stack.getMaxDamage();
    }

    ///  Tool taking only 1 damage is quite common.
    public static void damageItemArmor(Player player, ItemStack stack, int index) {
        PlayerUtil.damageItemArmor(player, 1, stack, index);
    }

    @FunctionalInterface
    public interface PlayerStatus {
        boolean test(Player player, Double distance);
    }


    /// The default way of finding player does not account for invisible player. The default is used by other function
    /// aside mobs and as such cannot be changed. Please use these function to search for closest player in mobs.
    public static Player getClosestNonInvisPlayerToEntity(World world, Entity entity, double radius) {
        return getClosestPlayerToEntity(world, entity, radius, PlayerUtil::isInvisible);
    }

    /// The default way of finding player does not account for invisible player. The default is used by other function
    /// aside mobs and as such cannot be changed. Please use these function to search for closest player in mobs.
    public static Player getClosestPlayerToEntity(World world, Entity entity, double radius, PlayerStatus... playerStatus) {
        if (radius < 0.0F || playerStatus.length == 0) {
            return world.getClosestPlayerToEntity(entity, radius);
        }
        return PlayerUtil.getClosestPlayerToEntity(world, entity.x, entity.y, entity.z, radius, playerStatus);
    }

    public static @Nullable Player getClosestPlayerToEntity(@NonNull World world, double x, double y, double z, double radius, PlayerStatus[] playerStatus) {
        double closestDistance = Double.POSITIVE_INFINITY;
        Player returnPlayer = null;
        for (Player currentPlayer : world.players) {
            double currentDistance = currentPlayer.distanceToSqr(x, y, z);
            if (currentDistance > radius * radius
                || PlayerUtil.test(playerStatus, currentPlayer, currentDistance)
                || currentDistance >= closestDistance
            ) {
                continue;
            }
            closestDistance = currentDistance;
            returnPlayer = currentPlayer;
        }
        return returnPlayer;
    }

    private static boolean test(PlayerStatus @NonNull [] playerStatus, Player player, double distance) {
        boolean acc = false;
        for (PlayerStatus status : playerStatus) {
            acc |= status.test(player, distance);
        }
        return acc;
    }

    ///  To check if the player can be attacked by Swets
    @SuppressWarnings("java:S1172")
    public static boolean isSwetty(Entity entity, double distance) {
        return PlayerUtil.isSwetty(entity);
    }

    ///  The interface requires the distance but either call is fine, as long it not used to figure out targeting
    public static boolean isSwetty(Entity entity) {
        return entity instanceof IHasEffects<?> iHasEffects
            && iHasEffects.getContainer().hasEffect(AetherEffects.swetty);
    }

    ///  To check if the player is Invisible for targeting
    public static boolean isInvisible(Entity entity, double distance) {
        return PlayerUtil.isInvisible(entity) && distance > 2.0f;
    }

    ///  To check if the player is Invisible
    public static boolean isInvisible(Entity entity) {
        return entity instanceof IHasEffects<?> iHasEffects
            && iHasEffects.getContainer().hasEffect(AetherEffects.invisibility);
    }

    public static DeathCause deathCause(Player victim, Entity entityKilledBy) {
        IVehicle prevVehicle = ((PreVehicle) victim).better_with_aether$preVehicle();
        DeathCause deathCause = PlayerUtil.deathCause(victim, entityKilledBy, prevVehicle);
        ((PreVehicle) victim).better_with_aether$resestVehicle();
        return deathCause;
    }

    public static DeathCause deathCause(Player victim, Entity entityKilledBy, IVehicle prevVehicle) {
        if (entityKilledBy instanceof Mob mob) {
            return PlayerUtil.killedByAetherMob(victim, mob, prevVehicle);
        }
        if (entityKilledBy instanceof Projectile projectile) {
            return new DeathCauseProjectile(victim, projectile);
        }
        EffectContainer<?> victimsEffects = ((IHasEffects<?>) victim).getContainer();
        if (victimsEffects.hasEffect(AetherEffects.poisonEffect)) {
            DeathCauseEffects deathCausePoison = new DeathCauseEffects(victim, AetherEffects.poisonEffect);
            if (prevVehicle != null) {
                return deathCausePoison.setSecondary("driving");
            }
            return deathCausePoison;
        }
        if (victim.fallDistance <= 0) {
            return null;
        }
        ItemStack[] accessories = ((IContainerInventoryAether) victim.inventory).aether$getAccessoryInventory();
        ItemStack pendant1 = accessories[TRINKET_1_SLOT - GLOVES_SLOT];
        ItemStack pendant2 = accessories[TRINKET_2_SLOT - GLOVES_SLOT];
        if ((pendant1 != null && pendant1.getItem() instanceof ItemIcePendant)
            || (pendant2 != null && pendant2.getItem() instanceof ItemIcePendant)
        ) {
            return null;
        }
        TilePos tilePos = new TilePos(victim);
        Block<?> block = victim.world.getBlockType(tilePos);
        if (block == Blocks.OBSIDIAN || block == Blocks.ICE) {
            return new DeathCauseGeneric(victim, "ice_pendant");
        }
        return null;
    }

    private static DeathCause killedByAetherMob(Player victim, Mob mob, IVehicle prevVehicle) {
        EffectContainer<?> victimsEffects = ((IHasEffects<?>) victim).getContainer();
        if (victimsEffects.hasEffect(AetherEffects.poisonEffect) && prevVehicle != null) {
            return new DeathCauseEffects(victim, AetherEffects.poisonEffect).setSecondary("driving");
        }
        if (mob instanceof EnemyBoss enemyBoss) {
            return new DeathCauseBoss(victim, mob, enemyBoss);
        }
        if (mob instanceof MobSlime || mob instanceof MobSwet || mob instanceof MobSwetGold) {
            DeathCauseKilledSecondary deathCauseSwet = new DeathCauseKilledSecondary(victim, mob);
            if (victimsEffects.hasEffect(AetherEffects.swetty)) {
                return deathCauseSwet.setSecondary("friendly");
            }
            return deathCauseSwet;
        }
        if (mob instanceof MobMimic) {
            return new DeathCauseMimic(victim, mob);
        }
        return new DeathCauseKilledBy(victim, mob);
    }

}
