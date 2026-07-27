package teamport.aether.gui.machine;

import net.minecraft.client.gui.container.ScreenContainerAbstract;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.data.registry.recipe.entry.RecipeEntryFurnace;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.menu.*;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.player.inventory.slot.SlotCreative;
import net.minecraft.core.player.inventory.slot.SlotResult;
import org.lwjgl.glfw.GLFW;
import teamport.aether.mixin.accessors.ScreenContainerAbstractAccessor;

public abstract class ScreenAetherMachine extends ScreenContainerAbstract {

    protected ScreenAetherMachine(MenuAbstract container) {
        super(container);
    }

    public abstract int getTargetSlot(ItemStack stackInSlot, int clickedItemId);

    /**
     * @implNote This function is a direct copy of its parent. This allows aether machines an entry to check what slot
     * a given item targets. As such only change the function to keep up to date with its parent.
     * Additional machines can be checked in getTargetSlot. All aether machine screens need to inherit from
     * this class for it work.
     */
    @Override
    public void clickInventory(int x, int y, int mouseButton) {
        int slotId = ((ScreenContainerAbstractAccessor) this).invokeGetSlotId(x, y);
        if (slotId != -1) {
            if (slotId == -999) {
                InventoryAction action = InventoryAction.DROP_HELD_STACK;
                if (mouseButton == 1) {
                    action = InventoryAction.DROP_HELD_SINGLE;
                }

                this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, action, null, this.mc.thePlayer);
            } else if (!this.mc.thePlayer.getGamemode().hasBlockConsumption() && mouseButton == 2) {
                Slot slot = this.inventorySlots.getSlot(slotId);
                if (slot.getItemStack() == null) {
                    this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.SORT, new int[]{slotId, 64}, this.mc.thePlayer);
                } else {
                    this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_GRAB, new int[]{slotId, 64}, this.mc.thePlayer);
                }

            } else {
                InventoryAction action = InventoryAction.CLICK_LEFT;
                long window = this.mc.gameWindow.getHandle();
                boolean shiftPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
                boolean ctrlPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
                boolean altPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS
                    || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
                boolean spacePressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
                if (mouseButton == 10) {
                    shiftPressed = true;
                    mouseButton = 0;
                }

                if (GameSettings.KEY_SORT_INVENTORY.isMouseButton(mouseButton)) {
                    action = InventoryAction.SORT;
                }

                int target;
                Slot slot = this.inventorySlots.getSlot(slotId);
                ItemStack stackInSlot = slot != null ? slot.getItemStack() : null;
                int clickedItemId = stackInSlot != null ? stackInSlot.getItem().id : 0;
                ItemStack grabbedItem = this.mc.thePlayer.inventory.getHeldItemStack();
                if (mouseButton == 1) {
                    action = InventoryAction.CLICK_RIGHT;
                }

                if (slot instanceof SlotResult) {
                    if (Boolean.TRUE.equals(GameSettings.SWAP_CRAFTING_BUTTONS.value)) {
                        if (shiftPressed && ctrlPressed) {
                            action = InventoryAction.MOVE_SIMILAR;
                        } else if (shiftPressed) {
                            action = InventoryAction.MOVE_SINGLE_ITEM;
                        } else if (ctrlPressed) {
                            action = InventoryAction.MOVE_STACK;
                        }
                    } else if (shiftPressed && ctrlPressed) {
                        action = InventoryAction.MOVE_SIMILAR;
                    } else if (shiftPressed) {
                        action = InventoryAction.MOVE_STACK;
                    } else if (ctrlPressed) {
                        action = InventoryAction.MOVE_SINGLE_ITEM;
                    }
                } else if (spacePressed) {
                    action = InventoryAction.MOVE_ALL;
                } else if (shiftPressed && ctrlPressed) {
                    action = InventoryAction.MOVE_SIMILAR;
                } else if (!shiftPressed && !altPressed) {
                    if (ctrlPressed) {
                        action = InventoryAction.MOVE_SINGLE_ITEM;
                    }
                } else {
                    action = InventoryAction.MOVE_STACK;
                }

                if (slot instanceof SlotCreative) {
                    ItemStack heldItem = this.mc.thePlayer.inventory.getHeldItemStack();
                    if (heldItem != null) {
                        if (heldItem.canStackWith(stackInSlot)) {
                            if (!shiftPressed && !ctrlPressed) {
                                this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_GRAB, new int[]{slot.index, heldItem.stackSize + 1}, this.mc.thePlayer);
                            } else {
                                this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_GRAB, new int[]{slot.index, heldItem.getMaxStackSize()}, this.mc.thePlayer);
                            }
                        } else {
                            this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_GRAB, new int[]{slot.index, 0}, this.mc.thePlayer);
                        }
                    } else if (!shiftPressed && !ctrlPressed) {
                        this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_GRAB, new int[]{slot.index, 1}, this.mc.thePlayer);
                    } else {
                        int amount = 1;
                        if (shiftPressed && stackInSlot != null) {
                            amount = stackInSlot.getMaxStackSize();
                        }

                        this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_MOVE, new int[]{slot.index, amount}, this.mc.thePlayer);
                    }

                } else {
                    /// ------------------------------------------------------------------------------------------------
                    /// call the child to get the target slot
                    /// ------------------------------------------------------------------------------------------------
                    target = this.getTargetSlot(stackInSlot, clickedItemId);

                    if (this.inventorySlots instanceof MenuFurnace) {
                        boolean isIngredient = false;
                        for (RecipeEntryFurnace recipe : Registries.RECIPES.getAllFurnaceRecipes()) {
                            isIngredient = recipe.matches(stackInSlot);
                            if (isIngredient) {
                                break;
                            }
                        }

                        if (isIngredient) {
                            target = 1;
                        } else if (LookupFuelFurnace.instance.getFuelYield(clickedItemId) > 0) {
                            target = 2;
                        }
                    }

                    if (this.inventorySlots instanceof MenuTrommel) {
                        if (LookupFuelFurnace.instance.getFuelYield(clickedItemId) > 0) {
                            target = 2;
                        } else {
                            target = 1;
                        }
                    }

                    if ((this.inventorySlots instanceof MenuCrafting || this.inventorySlots instanceof MenuInventory) && altPressed) {
                        target = 1;
                    }

                    if (this.inventorySlots instanceof MenuInventoryCreative && altPressed) {
                        if (slot != null) this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.CREATIVE_DELETE, new int[]{slot.index}, this.mc.thePlayer);
                    } else if (slot != null && slot.allowItemInteraction() && grabbedItem != null && grabbedItem.getItem().hasInventoryInteraction() && mouseButton == 1) {
                        this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.INTERACT_GRABBED, new int[]{slot.index}, this.mc.thePlayer);
                    } else if (slot != null && stackInSlot != null && slot.allowItemInteraction() && stackInSlot.getItem().hasInventoryInteraction() && mouseButton == 1) {
                        this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, InventoryAction.INTERACT_SLOT, new int[]{slot.index}, this.mc.thePlayer);
                    } else {
                        int[] args = new int[]{slotId, target};
                        this.mc.playerController.handleInventoryMouseClick(this.inventorySlots.containerId, action, args, this.mc.thePlayer);
                    }
                }
            }
        }
    }
}
