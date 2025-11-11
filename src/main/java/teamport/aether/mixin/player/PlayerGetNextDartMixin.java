package teamport.aether.mixin.player;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.world.World;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.aether.items.AetherItems;
import teamport.aether.items.DartInterface;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerGetNextDartMixin extends Mob implements DartInterface {

    @Shadow
    public abstract boolean hasItem(Item item);

    @Unique
    private static final int DATA_HELD_DART = 19;

    public PlayerGetNextDartMixin(@Nullable World world) {
        super(world);
    }

    @Unique
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

    @Unique
    public int better_with_aether$getDartId() {
        return entityData.getInt(19);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void addDartData(CallbackInfo ci) {
        entityData.define(19, -1, Integer.class);
    }
}
