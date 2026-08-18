package teamport.aether.item.accessory;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.world.World;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemAccessory<T extends IAccessoryShape> extends Item implements IAccessoryItem<T> {
    private final @NonNull T armorShape;
    public final @Nullable ArmorMaterial material;

    public ItemAccessory(@NonNull String name, @NonNull String namespaceId, int id, @NonNull T armorShape) {
        this(name, namespaceId, id, null, armorShape);
    }

    public ItemAccessory(@NonNull String name, @NonNull String namespaceId, int id, @Nullable ArmorMaterial material, @NonNull T armorShape) {
        super(name, namespaceId, id);
        this.armorShape = armorShape;
        this.material = material;
        this.maxStackSize = 1;
    }

    @Override
    public @Nullable ItemStack onUse(@NonNull ItemStack selfStack, @NonNull World world, @NonNull Player player) {
        if (!(player instanceof IAccessoryWearing<?> wearing)) {
            return selfStack;
        }
        IAccessoryWearing<HumanAccessoryShape> accessoryPlayer = (IAccessoryWearing<HumanAccessoryShape>) wearing;

        List<HumanAccessoryShape> validSlots = new ArrayList<>();
        for (int i = 0; i < accessoryPlayer.getNumAccessorySlots(); i++) {
            HumanAccessoryShape slot = accessoryPlayer.getAccessorySlotByIndex(i);
            if (slot != null && accessoryPlayer.canItemGoInAccessorySlot(slot, selfStack)) {
                validSlots.add(slot);
            }
        }

        if (validSlots.isEmpty()) {
            return selfStack;
        }

        HumanAccessoryShape targetSlot = null;

        for (HumanAccessoryShape slot : validSlots) {
            if (accessoryPlayer.getAccessoryInSlot(slot) == null) {
                targetSlot = slot;
                break;
            }
        }

        if (targetSlot == null) {
            if (player.isSneaking() && validSlots.size() > 1) {
                targetSlot = validSlots.get(1);
            } else {
                targetSlot = validSlots.get(0);
            }
        }
        ItemStack currentStack = accessoryPlayer.getAccessoryInSlot(targetSlot);
        accessoryPlayer.setAccessoryInSlot(targetSlot, selfStack.splitStack(1));

        if (currentStack != null) {
            return currentStack;
        }

        return selfStack;
    }

    @Override
    public @Nullable ArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public @NonNull T getArmorShape() {
        return this.armorShape;
    }

    @Override
    public String name() {
        return null;
    }

    public String getTextureName() {
        if (this.material != null && this.material.identifier != null) {
            return this.material.identifier.value();
        }
        return this.namespaceID.value();
    }
}
