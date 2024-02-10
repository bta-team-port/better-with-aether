package bta.aether.world;

import bta.aether.AetherAchievements;
import bta.aether.AetherClient;
import bta.aether.AetherServer;
import bta.aether.block.AetherBlocks;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherDimension implements PreLaunchEntrypoint {

    // coordinates and if the boss has been defeated.
    public static Map<Integer, ChunkCoordinates> dugeonMap = new HashMap<>();
    public static int AetherDimensionID = 3;
    private static final HashMap<Dimension, List<Integer>> dimensionPlacementBlacklist = new HashMap<>();
    public static List<Integer> getDimensionBlacklist(Dimension dimension){
        if (!dimensionPlacementBlacklist.containsKey(dimension)){
            dimensionPlacementBlacklist.put(dimension, new ArrayList<>());
        }
        return dimensionPlacementBlacklist.get(dimension);
    }

    public static int registerDungeonToMap(int x, int y, int z){
        int id = dugeonMap.size();
        while (dugeonMap.get(id) != null) {
            id++;
        }

        dugeonMap.put(id, new ChunkCoordinates(x, y, z));
        return id;
    }

    // Biomes
    public static final Biome biomeAether = Biomes.register("aether:aether.aether", new BiomeAether().setBlockedWeathers(Weather.overworldRain, Weather.overworldSnow, Weather.overworldStorm, Weather.overworldFog))
            .setTopBlock(AetherBlocks.grassAether.id)
            .setFillerBlock(AetherBlocks.dirtAether.id);
    // World types
    public static final WorldType worldTypeAether = WorldTypes.register("aether:aether.default", new WorldTypeAetherDefault("worldType.aether.default"));
    // Dimensions
    public static final Dimension dimensionAether = new Dimension("aether", Dimension.overworld, 3f, AetherBlocks.portalAether.id).setDefaultWorldType(worldTypeAether);
    @Override
    public void onPreLaunch() {
        // This is here so that the dimension is created and added to the dimension list before the Server even launches, it'll crash otherwise
        Dimension.registerDimension(AetherDimensionID, dimensionAether);
    }
    public static void dimensionShift(EntityPlayer player, int targetDimension){
        World world = player.world;
        if (player instanceof EntityPlayerMP){
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
            AetherServer.teleportNotToPortalMP(entityPlayerMP, targetDimension);
        } else if (player instanceof EntityPlayerSP) {
            if (world.isClientSide) return;
            Dimension lastDim = Dimension.getDimensionList().get(player.dimension);
            Dimension newDim = Dimension.getDimensionList().get(targetDimension);
            System.out.println("Switching to dimension \"" + newDim.getTranslatedName() + "\"!!");
            player.dimension = targetDimension;
            world.setEntityDead(player);
            player.removed = false;

            player.moveTo(player.x *= Dimension.getCoordScale(lastDim, newDim), player.y, player.z *= Dimension.getCoordScale(lastDim, newDim), player.yRot, player.xRot);
            if (player.isAlive()) {
                world.updateEntityWithOptionalForce(player, false);
            }

            if (player.isAlive()) {
                world.updateEntityWithOptionalForce(player, false);
            }

            world = new World(world, newDim);
            if (newDim == lastDim.homeDim) {
                AetherClient.getMinecraft().changeWorld(world, "Leaving " + lastDim.getTranslatedName(), player);
            } else {
                AetherClient.getMinecraft().changeWorld(world, "Entering " + newDim.getTranslatedName(), player);
            }
            player.world = world;
            if (player.isAlive()) {
                player.moveTo(player.x, world.worldType.getMaxY()+1, player.z, player.yRot, player.xRot);
                world.updateEntityWithOptionalForce(player, false);
                player.addStat(AetherAchievements.HOSTILE_PARADISE, 1);
            }
        }
    }
}
