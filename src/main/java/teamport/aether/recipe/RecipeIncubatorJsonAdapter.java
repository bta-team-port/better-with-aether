package teamport.aether.recipe;

import com.google.gson.*;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;

import java.lang.reflect.Type;

public class RecipeIncubatorJsonAdapter implements RecipeJsonAdapter<RecipeEntryIncubator> {
    @Override
    public RecipeEntryIncubator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        RecipeSymbol input = context.deserialize(obj.get("input").getAsJsonObject(), RecipeSymbol.class);
        RecipeEntity entity = context.deserialize(obj.get("output").getAsJsonObject(), RecipeEntity.class);
        int time = obj.get("time").getAsInt();
        return new RecipeEntryIncubator(input, entity, time);
    }

    @Override
    public JsonElement serialize(RecipeEntryIncubator src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.toString());
        obj.addProperty("type", Registries.RECIPE_TYPES.getKey(src.getClass()));
        obj.add("input", context.serialize(src.getInput(), RecipeSymbol.class));
        obj.add("output", context.serialize(src.getOutput(), RecipeEntity.class));
        obj.add("time", context.serialize(src.getData()));
        return obj;
    }
}
