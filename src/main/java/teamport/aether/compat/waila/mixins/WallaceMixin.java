package teamport.aether.compat.waila.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.blocks.dungeon.BlockLogicChestMimic;
import toufoumaster.btwaila.gui.components.BaseInfoComponent;

@Mixin(value = BaseInfoComponent.class, remap = false)
public abstract class WallaceMixin {
    @Definition(id = "translateNameKey", method = "Lnet/minecraft/core/lang/I18n;translateNameKey(Ljava/lang/String;)Ljava/lang/String;")
    @Expression("? = ?.translateNameKey(?)")
    @Inject(method = "baseBlockInfo", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    public void injectMimicName(
        Block<?> block, int blockMetadata,
        ItemStack[] blockDrops, CallbackInfo ci,
        @Local(name = "renderItem") ItemStack renderItem,
        @Local(name = "blockName") LocalRef<String> blockName
    ) {
        if (renderItem.getItem() instanceof ItemBlock) {
            ItemBlock<?> itemBlock = (ItemBlock<?>) renderItem.getItem();
            if (itemBlock.getBlock().getLogic() instanceof BlockLogicChestMimic) {
                String name = renderItem.getData().getString("name");
                if (!name.isEmpty()) blockName.set(name);
            }
        }
    }
}
