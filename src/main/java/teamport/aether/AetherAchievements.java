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

    public static final Achievement BOUNCE = new Achievement(key("bounce"), "aether.bounce", AetherBlocks.AERCLOUD_BLUE, HOSTILE_PARADISE).registerAchievement();

    public static final Achievement PHYG = new Achievement(key("phyg"), "aether.phyg", Items.SADDLE, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement MOA = new Achievement(key("moa"), "aether.moa", AetherBlocks.INCUBATOR_IDLE, PHYG).registerAchievement();

    public static final Achievement ENCHANTER = new Achievement(key("enchanter"), "aether.enchanter", AetherBlocks.ENCHANTER_IDLE, HOSTILE_PARADISE).registerAchievement();

    public static final Achievement BRONZE = new Achievement(key("bronze"), "aether.bronze", AetherItems.KEY_BRONZE, HOSTILE_PARADISE).setType(Achievement.TYPE_SECRET).registerAchievement();
    public static final Achievement SILVER = new Achievement(key("silver"), "aether.silver", AetherItems.KEY_SILVER, BRONZE).setType(Achievement.TYPE_SECRET).registerAchievement();
    public static final Achievement GOLD = new Achievement(key("gold"), "aether.gold", AetherItems.KEY_GOLD, SILVER).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement SKYROOT = new Achievement(key("skyroot"), "aether.skyroot", AetherItems.TOOL_PICKAXE_SKYROOT, HOSTILE_PARADISE).registerAchievement();
    public static final Achievement AMBROSIUM = new Achievement(key("ambrosium"), "aether.ambrosium", AetherItems.AMBROSIUM, SKYROOT).registerAchievement();
    public static final Achievement GRAVITITE = new Achievement(key("gravitite"), "aether.gravitite", AetherItems.TOOL_PICKAXE_GRAVITITE, SKYROOT).registerAchievement();

    public static final Achievement ALL_MUSIC_DISCS = new Achievement(key("all_music_discs"), "all.musics.discs", AetherItems.RECORD_AETHER, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement MAX_LIFE = new Achievement(key("max_life"), "max.life", AetherItems.LIFESHARD, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();

    public static final Achievement ALL_ACCESSORY_TYPES  = new Achievement(key("all_accessory_types"), "all.accessory.types", AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, null).setType(Achievement.TYPE_SPECIAL).registerAchievement();


}
