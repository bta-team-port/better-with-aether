package teamport.aether.recipe;

import com.google.gson.*;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.RecipeSymbol;
import net.minecraft.core.data.registry.recipe.adapter.RecipeJsonAdapter;
import net.minecraft.core.entity.Mob;
import java.lang.reflect.Type;

public class RecipeIncubatorJsonAdapter implements RecipeJsonAdapter<RecipeEntryAetherIncubator> {
    @Override
    public RecipeEntryAetherIncubator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        RecipeSymbol input = (RecipeSymbol)context.deserialize(obj.get("input").getAsJsonObject(), RecipeSymbol.class);
        Mob output = (Mob)context.deserialize(obj.get("output").getAsJsonObject(), Mob.class);
        int time = obj.get("time").getAsInt();
        return new RecipeEntryAetherIncubator(input, output, time);
    }

    @Override
    public JsonElement serialize(RecipeEntryAetherIncubator src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.toString());
        obj.addProperty("type", Registries.RECIPE_TYPES.getKey(src.getClass()));
        obj.add("input", context.serialize(src.getInput(), RecipeSymbol.class));
        obj.add("output", context.serialize(src.getOutput(), Mob.class));
        obj.add("time", context.serialize(src.getData()));
        return obj;
    }
}
