package teamport.aether.compat.commandly;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.world.World;
import redart15.commandly.CommandlyMod;

public class AetherCommandlyRules {
    private AetherCommandlyRules() {}
    private static final boolean IS_LOADED = FabricLoader.getInstance().isModLoaded("commandly");

    public static boolean canVeinMine(World world) {
        if(IS_LOADED) return world.getGameRuleValue(CommandlyMod.VEINMINING);
        return false;
    }

    public static boolean canGrassSpread(World world) {
        if(IS_LOADED) return world.getGameRuleValue(CommandlyMod.GRASS_SPREADING);
        return true;
    }
}
