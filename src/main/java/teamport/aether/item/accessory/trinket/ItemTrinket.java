package teamport.aether.item.accessory.trinket;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import org.jspecify.annotations.NonNull;
import teamport.aether.item.AetherItems;
import teamport.aether.item.accessory.HumanAccessoryShape;
import teamport.aether.item.accessory.IAccessoryItem;
import teamport.aether.item.accessory.ItemAccessory;
import teamport.aether.item.accessory.SlotAccessory;
import teamport.aether.lookup.LookupTrinketOutlines;

import java.util.HashMap;
import java.util.Map;

public class ItemTrinket extends ItemAccessory<HumanAccessoryShape> implements IAccessoryItem<HumanAccessoryShape> {
    private static final Map<Item, String> TRINKET_TEXTURES = new HashMap<>();
    private final String name;

    public ItemTrinket(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, HumanAccessoryShape.TRINKET);
        this.name = name;
        this.maxStackSize = 1;
    }

    public static void setOutline(@NonNull Item item, String path) {
        LookupTrinketOutlines.INSTANCE.addEntry(item, path);
    }

    public ItemTrinket setArmorTexture(String textureID){
        ItemTrinket.TRINKET_TEXTURES.putIfAbsent(this, textureID);
        return this;
    }

    public static void setArmorTexture(Item item, String textureID){
        ItemTrinket.TRINKET_TEXTURES.putIfAbsent(item, textureID);
    }

    public static String getTextureKey(Item key){
        return ItemTrinket.TRINKET_TEXTURES.getOrDefault(key, null);
    }

    @Override
    public boolean fitsInShape(@NonNull HumanAccessoryShape shape) {
        return shape == HumanAccessoryShape.TRINKET;
    }

    @Override
    public boolean isEquipped(int relativeSlot) {
        return relativeSlot == SlotAccessory.TRINKET_1_SLOT || relativeSlot == SlotAccessory.TRINKET_2_SLOT;
    }

    @Override
    public String name() {
        return name;
    }


    static {
        TRINKET_TEXTURES.put(Items.TOOL_COMPASS, "compass_trinket");
        TRINKET_TEXTURES.put(Items.TOOL_CLOCK, "clock_trinket");
        TRINKET_TEXTURES.put(Items.TOOL_CALENDAR, "calendar_trinket");
        TRINKET_TEXTURES.put(AetherItems.TOOL_DUNGEON_COMPASS, "compass_dungeon_trinket");
    }
}
