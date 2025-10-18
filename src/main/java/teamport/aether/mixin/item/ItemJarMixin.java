package teamport.aether.mixin.item;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobFireflyCluster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemJar;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamport.aether.AetherMod;
import teamport.aether.items.AetherItems;

import java.util.List;

@Mixin(value = ItemJar.class, remap = false)
public class ItemJarMixin {
    @Inject(
            method = "onUseItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/animal/MobFireflyCluster;getColor()Lnet/minecraft/core/entity/animal/MobFireflyCluster$FireflyColor;"),
            cancellable = true
    )
    private void onGetFireflyColor(ItemStack itemstack, World world, Player entityplayer, CallbackInfoReturnable<ItemStack> cir) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.bb.grow(2.0, 2.0, 2.0));

        for (Entity entity : list) {
            if (entity instanceof MobFireflyCluster) {
                MobFireflyCluster fireflyCluster = (MobFireflyCluster) entity;
                MobFireflyCluster.FireflyColor color = fireflyCluster.getColor();
                boolean didFillJar = false;

                if (color == MobFireflyCluster.FireflyColor.BLUE) {
                    didFillJar = ItemJar.fillJar(entityplayer, new ItemStack(Items.LANTERN_FIREFLY_BLUE, 1));
                } else if (color == MobFireflyCluster.FireflyColor.ORANGE) {
                    didFillJar = ItemJar.fillJar(entityplayer, new ItemStack(Items.LANTERN_FIREFLY_ORANGE, 1));
                } else if (color == MobFireflyCluster.FireflyColor.RED) {
                    didFillJar = ItemJar.fillJar(entityplayer, new ItemStack(Items.LANTERN_FIREFLY_RED, 1));
                } else if (color == MobFireflyCluster.FireflyColor.GREEN) {
                    didFillJar = ItemJar.fillJar(entityplayer, new ItemStack(Items.LANTERN_FIREFLY_GREEN, 1));
                } else if (color == AetherMod.SILVER) {
                    didFillJar = ItemJar.fillJar(entityplayer, new ItemStack(AetherItems.LANTERN_FIREFLY_SILVER, 1));
                }
                if (!world.isClientSide && didFillJar) {
                    fireflyCluster.setFireflyCount(fireflyCluster.getFireflyCount() - 1);
                    if (fireflyCluster.getFireflyCount() <= 0) {
                        fireflyCluster.remove();
                    }
                }

                cir.setReturnValue(itemstack);
                return;
            }
        }
    }
}
