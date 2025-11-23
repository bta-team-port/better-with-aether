package teamport.aether.compat.commandly;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.world.World;
import redart15.commandly.CommandlyMod;

public class AetherCommandlyRules {
    private AetherCommandlyRules(){}
    protected static boolean isLoaded = FabricLoader.getInstance().isModLoaded("commandly");

    public static boolean isLoaded(){return isLoaded;}

    public static boolean canVeinMine(World world){
        if(AetherCommandlyRules.isLoaded){
            return world.getGameRuleValue(CommandlyMod.VEINMINING);
        }
        return false;
    }

    public static boolean canGrassSpread(World world){
        if(AetherCommandlyRules.isLoaded){
            return world.getGameRuleValue(CommandlyMod.GRASS_SPREADING);
        }
        return true;
    }

    public static boolean canMossSpread(World world){
        if(AetherCommandlyRules.isLoaded){
            return world.getGameRuleValue(CommandlyMod.MOSS_SPREADING);
        }
        return true;
    }
}
