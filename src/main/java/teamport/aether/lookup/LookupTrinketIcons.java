package teamport.aether.lookup;

import net.minecraft.core.item.Item;
import org.jspecify.annotations.Nullable;
import teamport.aether.item.AetherItemTags;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.ItemAccessory;

import java.util.*;

@SuppressWarnings("java:S6548")
public class LookupTrinketIcons {
    public static final LookupTrinketIcons INSTANCE = new LookupTrinketIcons();
    private final Map<Item, String> idOutlineTextures = new HashMap<>();
    private final List<String> listTexture = new ArrayList<>();
    private static final Random random = new Random();

    private LookupTrinketIcons() {
        this.register();
    }

    public void register() {
        addEntry(AetherItems.ARMOR_TALISMAN_LEATHER, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_CHAINMAIL, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_IRON, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GOLD, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_DIAMOND, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_STEEL, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ZANITE, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GRAVITITE, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ICE, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_REGEN, "aether:item/trinket/armor_stone_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD, "aether:item/trinket/armor_feather_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_BUBBLE, "aether:item/trinket/armor_bubble_outline");
        addEntry(AetherItems.ARMOR_SHIELD_REPULSION, "aether:item/trinket/armor_shield_round_outline");
    }

    public void addEntry(Item item, String texturePath) {
        this.idOutlineTextures.put(item, texturePath);
        this.listTexture.add(texturePath);
    }

    public @Nullable String getEntry(Item item) {
        if (item instanceof ItemAccessory<?>) {
            return this.idOutlineTextures.getOrDefault(item, null);
        }

        if (item.hasTag(AetherItemTags.TRINKET)) {
            return this.idOutlineTextures.getOrDefault(item, null);
        }
        return null;
    }

    public String getRandomEntry() {
        return listTexture.get(random.nextInt(listTexture.size()));
    }
}
