package bta.aether.accessory.API;
import java.util.HashMap;
import java.util.Map;

public class AccessoryTypeRegistry {

	// not sure if we should be using a map, since typically few types will be registered
	public static Map<String,Integer> accessoryTypes = new HashMap<>();

	/**
	 * Get texture index for an accessory slot of a type.
	 * @param key String identifier of the type
	 * @return Texture index into item atlas
	 */
	public static int getSlotIconTextureIndex(String key) {
		return accessoryTypes.getOrDefault(key, -1);
	}

	/**
	 * Register a new accessory type
	 * @param key String identifier of the type e.g. 'ring', 'quiver', 'misc'
	 * @param slot_icon_index texture index of the icon for the slot, in the ITEM atlas. For a blank icon, use -1.
	 */
	public static void add(String key, int slot_icon_index) {
		accessoryTypes.put(key, slot_icon_index);
	}
}

