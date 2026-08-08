package teamport.aether.achievements;

import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.item.AetherItems;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherAchievements {

    public static @NonNull NamespaceID key(String string) {
        return NamespaceID.fromPool(MOD_ID, string);
    }

    public static String lang(String key) {
        return String.format("%s.%s", MOD_ID, key);
    }

    public static final Achievement HOSTILE_PARADISE = new Achievement
        (key("aether"), lang("hostile_paradise"), Blocks.GLOWSTONE, null)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();

    public static final Achievement SHOOTER = new Achievement
        (key("shooter"), lang("shooter"), AetherItems.TOOL_SHOOTER, HOSTILE_PARADISE)
        .registerAchievement();
    public static final Achievement HIT_ZEPHYR = new Achievement
        (key("zephyr"), lang("zephyr"), AetherBlocks.AERCLOUD_WHITE, SHOOTER)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();

    public static final Achievement POISON = new Achievement
        (key("poison"), lang("poison"), AetherItems.BUCKET_SKYROOT_POISON, HOSTILE_PARADISE)
        .registerAchievement();
    public static final Achievement REMEDY = new Achievement
        (key("remedy"), lang("remedy"), AetherItems.BUCKET_SKYROOT_REMEDY, POISON)
        .registerAchievement();


    public static final Achievement BOUNCE = new Achievement
        (key("bounce"), lang("bounce"), AetherBlocks.AERCLOUD_BLUE, HOSTILE_PARADISE)
        .registerAchievement();
    public static final Achievement GOLD_CLOUD = new Achievement
        (key("gold_cloud"), lang("gold_cloud"), AetherBlocks.AERCLOUD_GOLD, BOUNCE)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();
    public static final Achievement PARACHUTE = new Achievement
        (key("parachute"), lang("parachute"), AetherItems.PARACHUTE_CLOUD_GOLD, BOUNCE)
        .registerAchievement();

    public static final Achievement PHYG = new Achievement
        (key("phyg"), lang("phyg"), Items.SADDLE, HOSTILE_PARADISE)
        .registerAchievement();
    public static final Achievement MOA = new Achievement
        (key("moa"), lang("moa"), AetherBlocks.INCUBATOR_IDLE, PHYG)
        .registerAchievement();

    public static final Achievement WEVE_GOT_HOSTILES = new Achievement
        (key("weve_got_hostiles"), lang("weve_got_hostiles"), AetherItems.TOOL_SWORD_HOLYSTONE, HOSTILE_PARADISE)
        .setType(Achievement.TYPE_NORMAL)
        .registerAchievement();

    public static final Achievement SENTRY_DEPLOYED = new Achievement
        (key("sentry_deployed"), lang("sentry_deployed"), AetherBlocks.CARVED_STONE_LIGHT, WEVE_GOT_HOSTILES)
        .setType(Achievement.TYPE_NORMAL)
        .registerAchievement();
    public static final Achievement BRONZE = new Achievement
        (key("bronze"), lang("bronze"), AetherItems.KEY_BRONZE, SENTRY_DEPLOYED)
        .setType(Achievement.TYPE_SECRET)
        .registerAchievement();

    public static final Achievement ITS_A_TRAP = new Achievement
        (key("its_a_trap"), lang("its_a_trap"), AetherBlocks.CHEST_MIMIC_SKYROOT, WEVE_GOT_HOSTILES)
        .setType(Achievement.TYPE_NORMAL)
        .registerAchievement();
    public static final Achievement SILVER = new Achievement
        (key("silver"), lang("silver"), AetherItems.KEY_SILVER, ITS_A_TRAP)
        .setType(Achievement.TYPE_SECRET)
        .registerAchievement();

    public static final Achievement ICE_DEFLECT = new Achievement
        (key("ice_deflect"), lang("ice_deflect"), AetherItems.PROJECTILE_ICE, WEVE_GOT_HOSTILES)
        .setType(Achievement.TYPE_NORMAL)
        .registerAchievement();
    public static final Achievement GOLD = new Achievement
        (key("gold"), lang("gold"), AetherItems.KEY_GOLD, ICE_DEFLECT)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();

    public static final Achievement SKYROOT = new Achievement
        (key("skyroot"), lang("skyroot"), AetherItems.TOOL_PICKAXE_SKYROOT, HOSTILE_PARADISE)
        .registerAchievement();

    public static final Achievement ENCHANTER = new Achievement
        (key("enchanter"), lang("enchanter"), AetherBlocks.ENCHANTER_IDLE, SKYROOT)
        .registerAchievement();

    public static final Achievement AMBROSIUM = new Achievement
        (key("ambrosium"), lang("ambrosium"), AetherItems.AMBROSIUM, SKYROOT)
        .registerAchievement();
    public static final Achievement HEALING_STONE = new Achievement
        (key("healing_stone"), lang("healing_stone"), AetherItems.FOOD_HEALING_STONE, AMBROSIUM)
        .registerAchievement();

    public static final Achievement GRAVITITE = new Achievement
        (key("gravitite"), lang("gravitite"), AetherItems.TOOL_PICKAXE_GRAVITITE, SKYROOT)
        .registerAchievement();

    public static final Achievement ALL_MUSIC_DISCS = new Achievement
        (key("all_music_discs"), lang("all_music_discs"), AetherItems.RECORD_AETHER, null)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();
    public static final Achievement MAX_LIFE = new Achievement
        (key("max_life"), lang("max_life"), AetherItems.LIFESHARD, null)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();
    public static final Achievement ALL_ACCESSORY_TYPES = new Achievement
        (key("all_accessory_types"), lang("all_accessory_types"), AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, null)
        .setType(Achievement.TYPE_SPECIAL)
        .registerAchievement();


}
