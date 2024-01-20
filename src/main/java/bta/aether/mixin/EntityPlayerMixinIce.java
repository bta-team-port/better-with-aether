package bta.aether.mixin;

import bta.aether.item.AetherItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
abstract public class EntityPlayerMixinIce extends EntityLiving {
    @Shadow public InventoryPlayer inventory;

    public EntityPlayerMixinIce(World world) {
        super(world);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void freezeWaterAndLava(CallbackInfo ci) {
        ItemStack pendantSlot = inventory.armorItemInSlot(4);
        ItemStack ringSlot1 = inventory.armorItemInSlot(8);
        ItemStack ringSlot2 = inventory.armorItemInSlot(9);

        boolean isPendantValid = pendantSlot != null && pendantSlot.itemID == AetherItems.armorPendantIce.id;
        boolean isRing1Valid = ringSlot1 != null && ringSlot1.itemID == AetherItems.armorRingIce.id;
        boolean isRing2Valid = ringSlot2 != null && ringSlot2.itemID == AetherItems.armorRingIce.id;

        if (!isPendantValid && !isRing1Valid && !isRing2Valid) {
            return;
        }

        int radius = 3;
        int offset = 1;
        for (int x = 0; x < radius; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < radius; z++) {
                    int x1 = x - offset + MathHelper.floor_double(this.x);
                    int y1 = y - offset - 1 + MathHelper.floor_double(this.y);
                    int z1 = z - offset + MathHelper.floor_double(this.z);

                    Block block = world.getBlock(x1, y1, z1);
                    if (block == null) {
                        continue;
                    }

                    if (block.id == Block.fluidWaterStill.id) {
                        world.setBlockWithNotify(x1, y1, z1, Block.ice.id);
                        damageAccessories(pendantSlot, ringSlot1, ringSlot2, isPendantValid, isRing1Valid, isRing2Valid);
                    }

                    if (block.id == Block.fluidLavaStill.id) {
                        world.setBlockWithNotify(x1, y1, z1, Block.stone.id);
                        if (pendantSlot != null) pendantSlot.damageItem(1, this);
                        else if (ringSlot1 != null) ringSlot1.damageItem(1, this);
                        else if (ringSlot2 != null) ringSlot2.damageItem(1, this);
                    }
                }
            }
        }
    }

    @Unique
    private void damageAccessories(ItemStack pendantSlot, ItemStack ringSlot1, ItemStack ringSlot2, boolean isPendantValid, boolean isRing1Valid, boolean isRing2Valid) {
        if (isPendantValid && isRing1Valid && isRing2Valid) {
            int rand = random.nextInt(3);

            switch (rand) {
                case 0:
                    damageAcessory(pendantSlot, 4);
                    break;
                case 1:
                    damageAcessory(ringSlot1, 8);
                    break;
                case 2:
                    damageAcessory(ringSlot2, 9);
                    break;
            }
        } else if (isPendantValid) {
            if (isRing1Valid) {
                if (random.nextBoolean()) {
                    damageAcessory(pendantSlot, 4);
                } else {
                    damageAcessory(ringSlot1, 8);
                }
            } else if (isRing2Valid) {
                if (random.nextBoolean()) {
                    damageAcessory(pendantSlot, 4);
                } else {
                    damageAcessory(ringSlot2, 9);
                }
            } else {
                damageAcessory(pendantSlot, 4);
            }
        } else if (isRing1Valid) {
            if (isRing2Valid) {
                if (random.nextBoolean()) {
                    damageAcessory(ringSlot1, 8);
                } else {
                    damageAcessory(ringSlot2, 9);
                }
            } else {
                damageAcessory(ringSlot1, 8);
            }
        } else if (isRing2Valid) {
            damageAcessory(ringSlot2, 9);
        }
    }

    @Unique
    private void damageAcessory(ItemStack itemStack, int slot) {
        itemStack.damageItem(1, this);
        if (itemStack.stackSize <= 0) {
            inventory.armorInventory[slot] = null;
        }
    }
}
