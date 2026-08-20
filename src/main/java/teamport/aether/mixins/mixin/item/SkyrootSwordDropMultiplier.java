package teamport.aether.mixins.mixin.item;

import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import org.jspecify.annotations.NonNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.entity.animal.moa.MobMoa;
import teamport.aether.entity.animal.phow.MobPhow;
import teamport.aether.entity.animal.phyg.MobPhyg;
import teamport.aether.item.item_tool.AetherToolMaterial;
import teamport.aether.mixins.mixin.accessors.ItemToolSwordAccessor;

import java.util.List;

@Mixin(Mob.class)
public abstract class SkyrootSwordDropMultiplier {
    @Shadow
    protected abstract void dropDeathItems();

    @Shadow
    @Final
    @NonNull
    public List<WeightedRandomLootObject> mobDrops;

    @Shadow
    protected abstract List<WeightedRandomLootObject> getMobDrops();

    @Inject(method = "onDeath(Lnet/minecraft/core/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;dropDeathItems()V"))
    private void multiplyDrop(Entity entityKilledBy, CallbackInfo ci) {
        if (!(entityKilledBy instanceof Player)) {
            return;
        }
        ItemStack heldStack = ((Player) entityKilledBy).getHeldItem();
        if (heldStack == null || !(heldStack.getItem() instanceof ItemToolSword)) {
            return;
        }
        ToolMaterial material = ((ItemToolSwordAccessor) heldStack.getItem()).getMaterial();
        if (material != null && material == AetherToolMaterial.skyroot) {
            Mob self = (Mob) (Object) this;
            if (self instanceof MobPhyg) {
                if (self.remainingFireTicks > 0) {
                    this.getMobDrops().add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_COOKED.getDefaultStack(), 1, 2));
                } else {
                    this.mobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_RAW.getDefaultStack(), 1, 2));
                    this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
                }
            } else if (self instanceof MobPig) {
                if (self.remainingFireTicks > 0) {
                    this.getMobDrops().add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_COOKED.getDefaultStack(), 1, 2));
                } else {
                    this.mobDrops.add(new WeightedRandomLootObject(Items.FOOD_PORKCHOP_RAW.getDefaultStack(), 1, 2));
                }

            } else if (self instanceof MobPhow) {
                this.mobDrops.add(new WeightedRandomLootObject(Items.LEATHER.getDefaultStack(), 1, 5));
                this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));

            } else if (self instanceof MobMoa && ((MobMoa) self).getSaddled()) {
                this.mobDrops.add(new WeightedRandomLootObject(Items.FEATHER_CHICKEN.getDefaultStack(), 0, 2));
            } else {
                dropDeathItems();
            }
        }
    }
}
