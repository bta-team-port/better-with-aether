package teamport.aether.lookup;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.Items;
import teamport.aether.items.AetherItemTags;
import teamport.aether.items.accessory.*;

import javax.annotation.Nullable;
import java.util.*;

public class LookupAccessoryIcons {
    public  static final LookupAccessoryIcons instance = new LookupAccessoryIcons();
    public final Map<Class<? extends Item>, String> CLASS_OUTLINE_TEXTURES = new HashMap<>();
    public final Map<Integer, String> ID_OUTLINE_TEXTURES = new HashMap<>();
    public final List<String> LIST_TEXTURE = new ArrayList<>();
    private static final Random random = new Random();

    public LookupAccessoryIcons(){this.register();}

    // TODO needs a better system, it a bit cumbersome right now
    public void register() {
        addEntry(ItemAccessoryPendant.class, "armor_pendant_outline");
        addEntry(ItemRegenStone.class, "armor_stone_outline");
        addEntry(ItemGoldenFeather.class, "armor_feather_outline");
        addEntry(ItemIronBubble.class, "armor_bubble_outline");
        addEntry(ItemShield.class, "armor_shield_outline");

        addEntry(Items.TOOL_CLOCK.id, "armor_clock_outline");
        addEntry(Items.TOOL_COMPASS.id, "armor_compass_outline");
        addEntry(Items.TOOL_CALENDAR.id, "armor_calendar_outline");
        addEntry(Items.MAP.id, "armor_outline_map_filled");
//        addToList("armor_outline_map_filled");
    }

    public void addToList(String texturePath){
        LIST_TEXTURE.add(texturePath);
    }
    public void addEntry(Class<? extends Item> clazz, String texturePath){
        this.CLASS_OUTLINE_TEXTURES.put(clazz,texturePath);
        this.LIST_TEXTURE.add(texturePath);

    }
    public void addEntry(Integer id, String texturePath){
        this.ID_OUTLINE_TEXTURES.put(id, texturePath);
        this.LIST_TEXTURE.add(texturePath);
    }
    public @Nullable String getEntry(Item item ){
        for (Map.Entry<Class<? extends Item>, String> entry : this.CLASS_OUTLINE_TEXTURES.entrySet()) {
            if (entry.getKey().isInstance(item)) {
                return entry.getValue();
            }
        }

        if (item.hasTag(AetherItemTags.ACCESSORY)) {
            return this.ID_OUTLINE_TEXTURES.getOrDefault(item.id, null);
        }
        return null;
    }
    public String getRandomEntry(){
        return LIST_TEXTURE.get(random.nextInt(LIST_TEXTURE.size()));
    }
    public Map<Class<? extends Item>, String> getClassTextureMap(){
        return this.CLASS_OUTLINE_TEXTURES;
    }
    public Map<Integer, String> getIDTextureMap(){
        return this.ID_OUTLINE_TEXTURES;
    }
    public List<String> getTextureList(){
        return this.LIST_TEXTURE;
    }

}
