package teamport.aether.mixin.dimension;

import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.Mixin;
import teamport.aether.world.AetherDimension;
import teamport.aether.world.type.AetherWorldTypes;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = WorldTypeGroups.class)
public abstract class WorldTypeGroupsMixin {
    static {
        Map<WorldType, WorldType> overworldToAetherWorldTypeMap = new HashMap<>();
        for (WorldType t : new WorldType[]{
            WorldTypes.OVERWORLD_EXTENDED,
            WorldTypes.OVERWORLD_AMPLIFIED,
            WorldTypes.OVERWORLD_INLAND,
            WorldTypes.OVERWORLD_PARADISE,
            WorldTypes.OVERWORLD_WOODS,
            WorldTypes.OVERWORLD_HELL,
            WorldTypes.OVERWORLD_WINTER,
            WorldTypes.OVERWORLD_ISLANDS,
            WorldTypes.OVERWORLD_FLOATING,
            WorldTypes.FLAT,
            WorldTypes.EMPTY,
            WorldTypes.DEBUG,
        })
            overworldToAetherWorldTypeMap.put(t, AetherWorldTypes.AETHER_EXTENDED);

        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_DEFAULT, AetherWorldTypes.AETHER_DEFAULT);

        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_SKYBLOCK, AetherWorldTypes.AETHER_SKYBLOCK);

        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_RETRO, AetherWorldTypes.AETHER_RETRO);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_CLASSIC, AetherWorldTypes.AETHER_RETRO);
        overworldToAetherWorldTypeMap.put(WorldTypes.OVERWORLD_INDEV, AetherWorldTypes.AETHER_RETRO);
        for (WorldTypeGroups.Group group : WorldTypeGroups.GROUPS) {

            WorldType overworldType = group.get(Dimension.OVERWORLD);
            group.with(AetherDimension.getAether(), overworldToAetherWorldTypeMap.computeIfAbsent(overworldType, w -> AetherWorldTypes.AETHER_DEFAULT));
        }
    }
}
