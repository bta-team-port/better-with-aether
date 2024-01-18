package bta.aether.util;

import com.b100.utils.StringUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import turniplabs.halplibe.HalpLibe;

import java.util.HashMap;

public class StructureLoader {
    public HashMap<String, ItemStack> symbols = new HashMap<>();
    public JsonObject structureRoot;


    public void loadStructure(String path){
        String jsonString = StringUtils.readInputString(StructureLoader.class.getResourceAsStream(path));
        this.structureRoot = JsonParser.parseString(jsonString).getAsJsonObject();

        JsonArray ingredients = structureRoot.getAsJsonArray("ingredients");
        for (JsonElement element : ingredients) {
            JsonObject ingredient = element.getAsJsonObject();

            int blockId = HalpLibe.getTrueItemOrBlockId(ingredient.get("key").getAsString());
            symbols.put(ingredient.get("symbol").getAsString(), new ItemStack(blockId, 1, ingredient.get("meta").getAsInt()));
        }

    }

    public void generate(World world, int x, int y, int z) {
        JsonArray structure = structureRoot.getAsJsonArray("structure");

        for (JsonElement layer : structure) {

            int toPlaceZ = z;
            for(JsonElement row : layer.getAsJsonArray()){

                int toPlaceX = x;
                for(JsonElement block : row.getAsJsonArray()) {
                    if (block.getAsString().equals("null")) {
                        toPlaceX++;
                        continue;
                    }

                    ItemStack toPlace = symbols.get(block.getAsString());
                    world.setBlockAndMetadataWithNotify(toPlaceX, y, toPlaceZ, toPlace.itemID, toPlace.getMetadata());
                    toPlaceX++;
                }
                toPlaceZ++;
            }
            y++;
        }
    }

}
