// TODO: restore btwaila compat once upstream mod lands on BTA 8.0

package teamport.aether.mixin.compat;

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
import teamport.aether.block.dungeon.BlockLogicChestMimic;
import toufoumaster.btwaila.gui.components.BaseInfoComponent;

@Mixin(BaseInfoComponent.class)
public abstract class WallaceMixin {
    @Definition(id = "translateKey", method = "Lnet/minecraft/core/lang/I18n;translateKey(Ljava/lang/String;)Ljava/lang/String;")
    @Expression("? = ?.translateKey(?)")
    @Inject(method = "baseBlockInfo", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 0, shift = At.Shift.AFTER))
    public void injectMimicName(
        Block<?> block, int blockMetadata,
        ItemStack[] blockDrops, CallbackInfo ci,
        @Local(name = "renderItem") ItemStack renderItem,
        @Local(name = "blockName") LocalRef<String> blockName
    ) {
        if (renderItem.getItem() instanceof ItemBlock<?> itemBlock
            && itemBlock.getBlock().getLogic() instanceof BlockLogicChestMimic
        ) {
            String name = renderItem.getData().getString("name");
            if (!name.isEmpty()) blockName.set(name);
        }
    }
}

