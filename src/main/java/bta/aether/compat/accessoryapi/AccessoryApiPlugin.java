package bta.aether.compat.accessoryapi;

import csweetla.accessoryapi.API.AccessoryApiEntrypoint;
import csweetla.accessoryapi.API.AccessoryTypeRegistry;

import java.util.List;

public class AccessoryApiPlugin implements AccessoryApiEntrypoint {
    @Override
    public void onInitialize(List<String> slotKeys) {
        // register our slot types
        AccessoryTypeRegistry.add("pendant",-1);
        AccessoryTypeRegistry.add("ring",-1);
        AccessoryTypeRegistry.add("gloves",-1);
        AccessoryTypeRegistry.add("cape",-1);
        AccessoryTypeRegistry.add("shield",-1);
        AccessoryTypeRegistry.add("misc",-1);

        // add slots to appear in game
        slotKeys.add("pendant");
        slotKeys.add("cape");
        slotKeys.add("shield");
        slotKeys.add("misc");
        slotKeys.add("ring");
        slotKeys.add("ring");
        slotKeys.add("gloves");
        slotKeys.add("misc");
    }
}
