package teamport.aether.helper;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;

public class StringHelper {
    public static String formatTranslationKey(Class<? extends Entity> clazz) {
       return EntityDispatcher.nameKeyForClass(clazz) + ".death_message";
    }

}
