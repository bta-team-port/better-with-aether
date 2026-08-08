package teamport.aether.mixin.accessory.trinket;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicIce;
import net.minecraft.core.block.BlockLogicPermaIce;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.item.AetherItems;
import teamport.aether.entity.player.PlayerUtil;

import static teamport.aether.item.accessory.SlotAccessory.TRINKET_1_SLOT;
import static teamport.aether.item.accessory.SlotAccessory.TRINKET_2_SLOT;

@Mixin(Mob.class)
public abstract class BlockLogicSlipperyNegationMixin {
    @WrapOperation(method = "moveEntityWithHeading(FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/core/block/Block;friction:F", opcode = Opcodes.GETFIELD))
    private float adjustFriction(Block<?> instance, Operation<Float> original) {
        Mob mob = (Mob) (Object) this;
        if (!(mob instanceof Player player)) return original.call(instance);

        World world = player.world;
        if (world == null) return original.call(instance);
        int x = (int) Math.floor(player.x);
        int y = (int) Math.floor(player.bb.minY) - 1;
        int z = (int) Math.floor(player.z);
        Block<?> blockBelow = world.getBlock(x, y, z);

        if (blockBelow != null) {
            BlockLogic logic = blockBelow.getLogic();
            if (logic instanceof BlockLogicIce || logic instanceof BlockLogicPermaIce) {
                ItemStack trinketOne = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_1_SLOT);
                ItemStack trinketTwo = PlayerUtil.getArmorOrAccessoryItem(player, TRINKET_2_SLOT);
                if (trinketOne != null && trinketOne.getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID)
                    && trinketTwo != null && trinketTwo.getItem().namespaceID.equals(AetherItems.ARMOR_TALISMAN_LEATHER.namespaceID)) {
                    return 0.6F;
                }
            }
        }
        return original.call(instance);
    }
}
