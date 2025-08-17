package teamport.aether;

import net.minecraft.core.achievement.Achievement;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.blocks.AetherBlocks;
import teamport.aether.items.AetherItems;

import static teamport.aether.AetherMod.MOD_ID;

public class AetherAchievements {

    public static NamespaceID key(String string) {
        return NamespaceID.getPermanent(MOD_ID, string);
    }

    public static final Achievement HOSTILE_PARADISE = new Achievement(key("aether"), "aether.hostile.paradise", Blocks.GLOWSTONE, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement SHOOTER = new Achievement(key("shooter"), "shooter", AetherItems.TOOL_SHOOTER, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement HIT_ZEPHYR = new Achievement(key("zephyr"), "zephyr", AetherBlocks.AERCLOUD_WHITE, SHOOTER).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement POISON = new Achievement(key("poison"), "poison", AetherItems.BUCKET_SKYROOT_POISON, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement REMEDY = new Achievement(key("remedy"), "remedy", AetherItems.BUCKET_SKYROOT_REMEDY, POISON).registerAchievement();


    public static final Achievement BOUNCE = new Achievement(key("bounce"), "aether.bounce", AetherBlocks.AERCLOUD_BLUE, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement GOLD_CLOUD = new Achievement(key("gold_cloud"), "gold.cloud", AetherBlocks.AERCLOUD_GOLD, BOUNCE).setType(Achievement.TYPE_SPECIAL).registerAchievement();
    public static final Achievement PARACHUTE = new Achievement(key("parachute"), "parachute", AetherItems.PARACHUTE_CLOUD_GOLD, BOUNCE).registerAchievement();

    public static final Achievement PHYG = new Achievement(key("phyg"), "aether.phyg", Items.SADDLE, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement MOA = new Achievement(key("moa"), "aether.moa", AetherBlocks.INCUBATOR_IDLE, PHYG).registerAchievement();

    public static final Achievement SENTRY_DEPLOYED = new Achievement(key("sentry_deployed"), "sentry.deployed", AetherBlocks.CARVED_STONE_LIGHT, HOSTILE_PARADISE).setType(Achievement.TYPE_NORMAL).registerAchievement();
    public static final Achievement BRONZE = new Achievement(key("bronze"), "aether.bronze", AetherItems.KEY_BRONZE, HOSTILE_PARADISE).setType(Achievement.TYPE_SECRET).registerAchievement();

    public static final Achievement ITS_A_TRAP = new Achievement(key("its_a_trap"), "its.a.trap", AetherBlocks.CHEST_MIMIC, BRONZE).setType(Achievement.TYPE_NORMAL).registerAchievement();
    public static final Achievement SILVER = new Achievement(key("silver"), "aether.silver", AetherItems.KEY_SILVER, ITS_A_TRAP).setType(Achievement.TYPE_SECRET).registerAchievement();

    public static final Achievement ICE_DEFLECT = new Achievement(key("ice_deflect"), "ice.deflect", AetherItems.PROJECTILE_ICE, SILVER).setType(Achievement.TYPE_NORMAL).registerAchievement();
    public static final Achievement GOLD = new Achievement(key("gold"), "aether.gold", AetherItems.KEY_GOLD, ICE_DEFLECT).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement SKYROOT = new Achievement(key("skyroot"), "aether.skyroot", AetherItems.TOOL_PICKAXE_SKYROOT, HOSTILE_PARADISE).registerAchievement();

    public static final Achievement ENCHANTER = new Achievement(key("enchanter"), "aether.enchanter", AetherBlocks.ENCHANTER_IDLE, SKYROOT).registerAchievement();

    public static final Achievement AMBROSIUM = new Achievement(key("ambrosium"), "aether.ambrosium", AetherItems.AMBROSIUM, SKYROOT).registerAchievement();
    public static final Achievement HEALING_STONE = new Achievement(key("healing_stone"), "healing.stone", AetherItems.FOOD_HEALING_STONE, AMBROSIUM).registerAchievement();

    public static final Achievement GRAVITITE = new Achievement(key("gravitite"), "aether.gravitite", AetherItems.TOOL_PICKAXE_GRAVITITE, SKYROOT).registerAchievement();

    public static final Achievement ALL_MUSIC_DISCS = new Achievement(key("all_music_discs"), "all.musics.discs", AetherItems.RECORD_AETHER, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement MAX_LIFE = new Achievement(key("max_life"), "max.life", AetherItems.LIFESHARD, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement ALL_ACCESSORY_TYPES  = new Achievement(key("all_accessory_types"), "all.accessory.types", AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();


}
