package teamport.aether.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemFireStriker;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.world.AetherDimension;

@Mixin(value = ItemFireStriker.class)
public abstract class ItemFireStrikerMixin extends Item {
    protected ItemFireStrikerMixin(NamespaceID namespaceId, String key, int id) {
        super(namespaceId, key, id);
    }
    @WrapOperation(method = "onUseOnBlock(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;DD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;setBlockTypeNotify(Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/block/Block;)Z"))
    private boolean callOnItemUseOne(World instance, TilePosc firePos, Block<?> fireToPlace, Operation<Boolean> original, ItemStack itemstack, World world, Player player, TilePosc blockPos, Side side, double xPlaced, double yPlaced) {
        boolean isAether = instance.dimension == AetherDimension.getAether();
        if (isAether && firePos instanceof TilePos mutableFirePos) {
            mutableFirePos.set(blockPos);
        }
        return isAether || original.call(instance, firePos, fireToPlace);
    }
    @WrapOperation(method = "onUseOnBlock(Lnet/minecraft/core/item/ItemStack;Lnet/minecraft/core/world/World;Lnet/minecraft/core/entity/player/Player;Lnet/minecraft/core/world/pos/TilePosc;Lnet/minecraft/core/util/helper/Side;DD)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/World;playSoundEffect(Lnet/minecraft/core/entity/Entity;Lnet/minecraft/core/sound/SoundCategory;DDDLjava/lang/String;FF)V"))
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
