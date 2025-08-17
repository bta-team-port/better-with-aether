package teamport.aether.helper;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;

public class StringHelper {
    public static String formatTranslationKey(Class<? extends Entity> clazz) {
        String[] stringArray = EntityDispatcher.nameKeyForClass(clazz).split("\\.");
        StringBuilder identifier = new StringBuilder();
        for (int i = 0; i < stringArray.length; i++) {
            identifier.append(stringArray[i]);
            if (stringArray[i].contains("aether")) {
                identifier.append(".death.message");
            }
            if (i + 1 < stringArray.length) {
                identifier.append(".");
            }
        }
        return identifier.toString();
    }

}
