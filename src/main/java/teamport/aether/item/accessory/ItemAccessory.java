package teamport.aether.item.accessory;

import net.minecraft.core.entity.Entity;
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

    public ItemAccessory(@NonNull String translationKey, @NonNull String namespaceId, int id, @NonNull T armorShape) {
        this(translationKey, namespaceId, id, null, armorShape);
    }

    public ItemAccessory(@NonNull String name, @NonNull String namespaceId, int id, @Nullable ArmorMaterial material, @NonNull T armorShape) {
        super(name, namespaceId, id);
        this.armorShape = armorShape;
        this.material = material;
        this.maxStackSize = 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable ItemStack onUse(@NonNull ItemStack selfStack, @NonNull World world, @NonNull Player player) {
        if (!(player instanceof IAccessoryWearing<?> wearing)) {
            return selfStack;
        }
        IAccessoryWearing<HumanAccessoryShape> accessoryPlayer = (IAccessoryWearing<HumanAccessoryShape>) wearing;

        List<Integer> validSlots = new ArrayList<>();
        for (int i = 0; i < accessoryPlayer.getNumAccessorySlots(); i++) {
            if (accessoryPlayer.canItemGoInAccessorySlot(i, selfStack)) {
                validSlots.add(i);
            }
        }

        if (validSlots.isEmpty()) {
            return selfStack;
        }

        int targetSlot = -1;

        for (int slotIndex : validSlots) {
            if (accessoryPlayer.getAccessoryInSlot(slotIndex) == null) {
                targetSlot = slotIndex;
                break;
            }
        }

        if (targetSlot == -1) {
            if (player.isSneaking() && validSlots.size() > 1) {
                targetSlot = validSlots.get(1);
            } else {
                targetSlot = validSlots.get(0);
            }
        }
        ItemStack currentStack = accessoryPlayer.getAccessoryInSlot(targetSlot);
        accessoryPlayer.setAccessoryInSlot(targetSlot, selfStack.splitStack(1));
        player.world.playSoundAtEntity(player, player, "random.equip", 1.0F, 1.0F);

        if (currentStack != null) {
            return currentStack;
        }
        return selfStack;
    }

    @Override
    public void inventoryTick(@NonNull ItemStack stack, @NonNull World world, @NonNull Entity entity, int slotId, boolean flag) {
        if (!(entity instanceof Player player)) return;

        int relativeSlot = slotId - player.inventory.mainInventory.length;
        if (!isEquipped(relativeSlot)) {
            return;
        }

        tickAccessory(stack, world, player, slotId, flag);
    }

    public boolean isEquipped(int relativeSlot) {
        int accessoryIndex = relativeSlot - 4;
        if (accessoryIndex < 0 || accessoryIndex >= 4) {
            return false;
        }

        if (this.armorShape.getSlotIndex() == 2) {
            return accessoryIndex == 2 || accessoryIndex == 3;
        }

        return accessoryIndex == this.armorShape.getSlotIndex();
    }

    protected void tickAccessory(@NonNull ItemStack stack, @NonNull World world, @NonNull Player player, int slotId, boolean flag) {
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
        } else return this.namespaceID.value();
    }
}
