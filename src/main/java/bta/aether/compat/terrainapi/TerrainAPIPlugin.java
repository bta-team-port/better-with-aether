package bta.aether.compat.terrainapi;

import useless.terrainapi.api.TerrainAPI;

import static bta.aether.Aether.MOD_ID;

public class TerrainAPIPlugin implements TerrainAPI {

    @Override
    public String getModID() {
        return MOD_ID;
    }

    @Override
    public void onInitialize() {
        new AetherInitialization().init();
    }
}
