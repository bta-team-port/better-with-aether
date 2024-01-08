package bta.aether.api.impl.guidebookpp;

import bta.aether.Aether;
import bta.aether.api.impl.guidebookpp.handler.RecipeHandlerEnchanter;
import bta.aether.api.impl.guidebookpp.handler.RecipeHandlerFreezer;
import org.slf4j.Logger;
import sunsetsatellite.guidebookpp.GuidebookCustomRecipePlugin;

public class AetherGuidebookPlugin implements GuidebookCustomRecipePlugin {
    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading recipe plugin: "+this.getClass().getSimpleName()+" from "+ Aether.MOD_ID);
        (new RecipeHandlerEnchanter()).addRecipes();
        (new RecipeHandlerFreezer()).addRecipes();
    }
}
