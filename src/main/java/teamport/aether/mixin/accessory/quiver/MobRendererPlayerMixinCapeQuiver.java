package teamport.aether.mixin.accessory.quiver;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPlayer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemQuiver;
import net.minecraft.core.item.ItemQuiverEndless;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(value = MobRendererPlayer.class, remap = false)
public abstract class MobRendererPlayerMixinCapeQuiver extends MobRenderer<Player> {

    @Shadow
    public abstract void render(Tessellator tessellator, Player entity, double x, double y, double z, float yaw, float partialTick);

    @Unique
    public final ModelBiped quiver = new ModelBiped(1.05F);

    public MobRendererPlayerMixinCapeQuiver(ModelBase model, float shadowSize) {
        super(model, shadowSize);
    }

    @Inject(method = "prepareArmor*", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/item/ItemStack;getItem()Lnet/minecraft/core/item/Item;", shift = At.Shift.AFTER), cancellable = true)
    public void setArmorModel(@NotNull Player player, int renderPass, float partialTick, CallbackInfoReturnable<Boolean> info) {
        ItemStack armorStack = player.inventory.armorInventory[renderPass];
        if (armorStack == null) return;
        Item item = armorStack.getItem();
        ItemStack chestplate = player.inventory.armorInventory[2];
        if (item instanceof ItemQuiver) {
            String path = "/assets/minecraft/textures/armor/quiver.png";
            if (
                    renderPass == 7
                            && chestplate != null
                            && (chestplate.getItem() instanceof ItemQuiver || chestplate.getItem() instanceof ItemQuiverEndless)
            ) {
                path = "/assets/aether/textures/armor/quiver_flipped.png";
            }
            this.bindTexture(path);
            ModelBiped modelBiped = this.quiver;
            modelBiped.body.visible = renderPass == 1 || renderPass == 2 || renderPass == 5;
            this.setArmorModel(modelBiped);
            info.setReturnValue(true);
            return;
        }
        if (item instanceof ItemQuiverEndless) {
            String path = "/assets/minecraft/textures/armor/quiver_golden.png";
            if (
                    renderPass == 7
                            && chestplate != null
                            && (chestplate.getItem() instanceof ItemQuiver || chestplate.getItem() instanceof ItemQuiverEndless)
            ) {
                path = "/assets/aether/textures/armor/quiver_golden_flipped.png";
            }
            this.bindTexture(path);
            ModelBiped modelBiped = this.quiver;
            modelBiped.body.visible = renderPass == 1 || renderPass == 2 || renderPass == 5;
            this.setArmorModel(modelBiped);
            info.setReturnValue(true);
        }
    }
}
