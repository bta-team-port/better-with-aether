package bta.aether.item;

import net.minecraft.core.item.ItemRecord;

import java.util.HashMap;

public class ItemRecordAccessor extends ItemRecord {

    public static HashMap<String, String> titleToAuthor = new HashMap<>();

    protected ItemRecordAccessor(String name, int id, String title, String author) {
        super(name, id, title);
        titleToAuthor.put(title, author);
    }

    public static String getAuthor(String title) {
        if (title == null || !titleToAuthor.containsKey(title)) return "C418";
        return titleToAuthor.get(title);
    }
}