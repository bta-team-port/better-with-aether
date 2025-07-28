package teamport.aether.mixin.armor.player.phoenix.obsidian;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import teamport.aether.helper.ContainerHelper;
import teamport.aether.items.AetherArmorMaterial;

@Mixin(value = Player.class, remap = false)
public abstract class PlayerMixinNoKnockback extends Mob{

    @Shadow
    public ContainerInventory inventory;

    public PlayerMixinNoKnockback(@Nullable World world) {
        super(world);
    }

    // prevent any type of knockback
    @Override
    public void fling(double xd, double yd, double zd, float pushTime) {
        if (ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.OBSIDIAN) >= 5) {
            return;
        }
        super.fling(xd, yd, zd, pushTime);
    }

    @Override
    public void knockBack(Entity entity, int damage, double xd, double yd) {
        if (ContainerHelper.countArmorPiecesOfMaterial(this.inventory, AetherArmorMaterial.OBSIDIAN) >= 5) {
            return;
        }
        super.knockBack(entity, damage, xd, yd);
    }

}
