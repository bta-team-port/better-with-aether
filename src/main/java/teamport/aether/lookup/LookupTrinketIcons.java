package teamport.aether.lookup;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.collection.NamespaceID;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.AetherItems;
import teamport.aether.items.accessory.*;

import javax.annotation.Nullable;
import java.util.*;

public class LookupTrinketIcons {
    public  static final LookupTrinketIcons instance = new LookupTrinketIcons();
    public final Map<NamespaceID, String> ID_OUTLINE_TEXTURES = new HashMap<>();
    public final List<String> LIST_TEXTURE = new ArrayList<>();
    public static final Random random = new Random();

    public LookupTrinketIcons(){this.register();}

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
        addEntry(Items.TOOL_CLOCK.namespaceID, "aether:item/trinket/armor_clock_outline_alt1");
        addEntry(Items.TOOL_COMPASS.namespaceID, "aether:item/trinket/armor_compass_outline_alt2");
        addEntry(Items.TOOL_CALENDAR.namespaceID, "aether:item/trinket/armor_calendar_outline");
        addEntry(Items.MAP.namespaceID, "aether:item/trinket/armor_outline_map_filled");
    }

    public void addEntry(NamespaceID id, String texturePath){
        this.ID_OUTLINE_TEXTURES.put(id, texturePath);
        this.LIST_TEXTURE.add(texturePath);
    }
    public @Nullable String getEntry(Item item ){
        if(item instanceof IAccessory){
            return this.ID_OUTLINE_TEXTURES.getOrDefault(item.namespaceID, null);
        }

        if (item.hasTag(AetherItemTags.TRINKET)) {
            return this.ID_OUTLINE_TEXTURES.getOrDefault(item.namespaceID, null);
        }
        return null;
    }
    public String getRandomEntry(){
        return LIST_TEXTURE.get(random.nextInt(LIST_TEXTURE.size()));
    }
    public Map<NamespaceID, String> getIDTextureMap(){
        return this.ID_OUTLINE_TEXTURES;
    }
    public List<String> getTextureList(){
        return this.LIST_TEXTURE;
    }

}
