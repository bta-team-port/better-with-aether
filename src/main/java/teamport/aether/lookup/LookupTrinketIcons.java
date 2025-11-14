package teamport.aether.lookup;

import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import org.jspecify.annotations.Nullable;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.IAccessory;

import java.util.*;

@SuppressWarnings("java:S6548")
public class LookupTrinketIcons {
    public static final LookupTrinketIcons INSTANCE = new LookupTrinketIcons();
    private final Map<NamespaceID, String> idOutlineTextures = new HashMap<>();
    private final List<String> listTexture = new ArrayList<>();
    private static final Random random = new Random();

    private LookupTrinketIcons() {
        this.register();
    }

    public void register() {
        addEntry(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_CHAIN.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_IRON.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GOLD.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_STEEL.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ZANITE.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GRAVITITE.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ICE.namespaceID, "aether:item/trinket/armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_REGEN.namespaceID, "aether:item/trinket/armor_stone_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD.namespaceID, "aether:item/trinket/armor_feather_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_BUBBLE.namespaceID, "aether:item/trinket/armor_bubble_outline");
        addEntry(AetherItems.ARMOR_SHIELD_REPULSION.namespaceID, "aether:item/trinket/armor_shield_round_outline");
    }

    public void addEntry(NamespaceID id, String texturePath) {
        this.idOutlineTextures.put(id, texturePath);
        this.listTexture.add(texturePath);
    }

    public @Nullable String getEntry(Item item) {
        if (item instanceof IAccessory) {
            return this.idOutlineTextures.getOrDefault(item.namespaceID, null);
        }

        if (item.hasTag(AetherItemTags.TRINKET)) {
            return this.idOutlineTextures.getOrDefault(item.namespaceID, null);
        }
        return null;
    }

    public String getRandomEntry() {
        return listTexture.get(random.nextInt(listTexture.size()));
    }
}
