package teamport.aether.item.accessory.pendant;

import net.minecraft.core.item.ItemStack;
import org.jspecify.annotations.NonNull;
import sunsetsatellite.catalyst.effects.api.attribute.Attributes;
import sunsetsatellite.catalyst.effects.api.effect.IHasEffects;
import sunsetsatellite.catalyst.effects.api.modifier.IItemWithModifiers;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import sunsetsatellite.catalyst.effects.api.modifier.ModifierType;
import sunsetsatellite.catalyst.effects.api.modifier.type.IntModifier;

import java.util.HashMap;
import java.util.Map;

public class ItemPendantHealth extends ItemPendant implements IItemWithModifiers {

    public ItemPendantHealth(@NonNull String translationKey, @NonNull String namespaceId, int id, String name) {
        super(translationKey, namespaceId, id, name);
    }

    @Override
    public Map<Modifier<?>, Boolean> getModifiers(IHasEffects<?> iHasEffects, ItemStack itemStack, int slot) {
        if (slot < 106 || slot > 108) {
            return new HashMap<>();
        }
        HashMap<Modifier<?>, Boolean> map = new HashMap<>();
        map.put(new IntModifier(Attributes.EXTRA_HEALTH, ModifierType.ADD, 2), true);
        return map;
    }
}
