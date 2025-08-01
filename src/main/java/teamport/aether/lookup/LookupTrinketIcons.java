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

    // TODO needs a better system, it a bit cumbersome right now
    public void register() {
        addEntry(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_CHAIN.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_IRON.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GOLD.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_DIAMOND.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_STEEL.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ZANITE.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_GRAVITITE.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_ICE.namespaceID, "armor_pendant_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_REGEN.namespaceID, "armor_stone_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_FEATHER_GOLD.namespaceID, "armor_feather_outline");
        addEntry(AetherItems.ARMOR_TALISMAN_BUBBLE.namespaceID, "armor_bubble_outline");
        addEntry(AetherItems.ARMOR_SHIELD_REPULSION.namespaceID, "armor_shield_outline");
        addEntry(Items.TOOL_CLOCK.namespaceID, "armor_clock_outline");
        addEntry(Items.TOOL_COMPASS.namespaceID, "armor_compass_outline");
        addEntry(Items.TOOL_CALENDAR.namespaceID, "armor_calendar_outline");
        addEntry(Items.MAP.namespaceID, "armor_outline_map_filled");
    }

    public void addEntry(NamespaceID id, String texturePath){
        this.ID_OUTLINE_TEXTURES.put(id, texturePath);
        this.LIST_TEXTURE.add(texturePath);
    }
    public @Nullable String getEntry(Item item ){
        if(item instanceof Accessory){
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
