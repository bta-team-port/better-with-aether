package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

@Mixin(value = ItemFireStriker.class)
public abstract class ItemFireStrikerMixin extends Item {
    protected ItemFireStrikerMixin(NamespaceID namespaceId, int id) {
        super(namespaceId, id);
    }
    @WrapOperation(method = "onUseItemOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockWithNotify(IIII)Z"))
    private boolean callOnItemUseOne(World instance, int x, int y, int z, int id, Operation<Boolean> original, ItemStack itemstack, Player entityplayer, World world, int blockXIgnore, int blockYIgnore, int blockZIgnore, Side side, double xPlaced, double yPlaced, @Local(name = "blockX") LocalIntRef blockX, @Local(name = "blockY") LocalIntRef blockY, @Local(name = "blockZ") LocalIntRef blockZ) {
        boolean isAether = instance.dimension == AetherDimension.getAether();
        if (isAether) {
            blockX.set(blockX.get() - side.getOffsetX());
            blockY.set(blockY.get() - side.getOffsetY());
            blockZ.set(blockZ.get() - side.getOffsetZ());
        }
        return isAether || original.call(instance, x, y, z, id);
    }
    @WrapOperation(method = "onUseItemOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V"))
    private void callOnItemUseTwo(World instance, @Nullable Entity player, SoundCategory category, double x, double y, double z, String soundPath, float volume, float pitch, Operation<Void> original) {
        if (instance.dimension == AetherDimension.getAether() && player != null) {
            for (int l = 0; l < 8; ++l) {
                double angle = Math.toRadians(l * 45.0);
                ParticleMaker.spawnParticle(instance, "smoke", x, y + 0.5, z, -Math.cos(angle) / 20.0, 0.03, -Math.sin(angle) / 20.0, 0);
            }
        }
        original.call(instance, player, category, x, y, z, soundPath, volume, pitch);
    }
}
