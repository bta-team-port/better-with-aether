package teamport.aether.mixin.player;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.item.AetherItems;
import teamport.aether.item.DartInterface;

@Mixin(value = Player.class)
public abstract class PlayerGetNextDartMixin extends Mob implements DartInterface {
    protected PlayerGetNextDartMixin(@Nullable World world) {
        super(world);
    }
    @Shadow
    public abstract boolean hasItem(Item item);
    @Override
    public Item better_with_aether$getNextDart() {
        Item nextDart = null;
        if (hasItem(AetherItems.AMMO_DART_ENCHANTED)) {
            nextDart = AetherItems.AMMO_DART_ENCHANTED;
        } else if (hasItem(AetherItems.AMMO_DART_POISON)) {
            nextDart = AetherItems.AMMO_DART_POISON;
        } else if (hasItem(AetherItems.AMMO_DART_GOLDEN)) {
            nextDart = AetherItems.AMMO_DART_GOLDEN;
        }

        return nextDart;
    }
    @Override
    public int better_with_aether$getDartId() {
        return entityData.getInt(19);
    }
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void addDartData(CallbackInfo ci) {
        entityData.define(19, -1, Integer.class);
    }
}
