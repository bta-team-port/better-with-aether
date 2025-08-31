package teamport.aether.helper;

import turniplabs.halplibe.util.toml.Toml;

import java.util.HashSet;
import java.util.Set;

public class AetherToml extends turniplabs.halplibe.util.toml.Toml {
    public AetherToml(String s) {
        super(s);
    }

    public void addMissing(turniplabs.halplibe.util.toml.Toml other) {
        for (String orderedKey : other.getOrderedKeys()) {
            
            if (orderedKey.startsWith(".")) {
                if (!this.categories.containsKey(orderedKey.substring(1))) {
                    turniplabs.halplibe.util.toml.Toml toml = other.get(orderedKey, turniplabs.halplibe.util.toml.Toml.class);
                    
                    if (toml.getComment().isPresent()) addCategory(toml.getComment().get(), orderedKey.substring(1));
                    else addCategory(orderedKey.substring(1));
                    
                    categories.get(orderedKey.substring(1)).addMissing(toml);
                }
            } 
            
            else {
                if (!this.entries.containsKey(orderedKey)) {
                    addEntry(orderedKey, other.getEntry(orderedKey));
                }
            }
        }
    }

    @Override
    public String toString(String rootKey, int indents) {
        return TomlToString(this, rootKey, indents);
    }

    private static String repeatS(String txt, int count) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < count; i++) out.append(txt);
        return out.toString();
    }
    
    public static String TomlToString(Toml toml, String rootKey, int indent) {
        StringBuilder out = new StringBuilder();

        if (toml.getComment().isPresent()) {
            String comment = toml.getComment().get();

            for (String line : comment.split("\n")) {
                out.append(repeatS("\t", indent)).append("# ").append(line).append("\n");
            }

            out.append("\n");
        }

        Set<String> realKeys = new HashSet<>(toml.getOrderedKeys());

        for (String orderedKey : realKeys) {
            String[] res;
            int offset = 0;
            int sep = 0;

            if (orderedKey.startsWith(".")) {
                if (orderedKey.substring(1).contains(".")) continue;

                Toml cat = toml.get(orderedKey, Toml.class);
                String full = rootKey + (rootKey.isEmpty() ? "" : ".") + orderedKey.substring(1);

                if (cat.getComment().isPresent()) {
                    String comment = cat.getComment().get();

                    for (String re : comment.split("\n"))
                        out.append(repeatS("\t", indent)).append("# ").append(re).append("\n");
                }

                out.append(repeatS("\t", indent)).append("[").append(full).append("]").append("\n");


                res = TomlToString(cat, full, 0).split("\n");
                sep = offset = 1;
            } else {
                res = toml.getEntry(orderedKey).toString(orderedKey).split("\n");
            }

            for (String re : res) out.append(repeatS("\t", indent + offset)).append(re).append("\n");
            out.append(repeatS("\n", sep));
        }

        return out.toString();
    }
}
