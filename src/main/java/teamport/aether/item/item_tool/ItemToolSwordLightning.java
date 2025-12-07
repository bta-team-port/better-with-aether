package teamport.aether.item.item_tool;

import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import teamport.aether.AetherMod;
import teamport.aether.helper.ParticleMaker;
import teamport.aether.item.AetherHasCustomDamageType;

public class ItemToolSwordLightning extends ItemToolSword implements AetherHasCustomDamageType {
    public ItemToolSwordLightning(String name, String namespaceId, int id, ToolMaterial enumtoolmaterial) {
        super(name, namespaceId, id, enumtoolmaterial);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
        boolean hitEntity = super.hitEntity(itemstack, target, attacker);
        if (target instanceof Mob && target.hurtTime == 10 && hitEntity) {
            if ((target instanceof Player) && ((Player) target).gamemode.isPlayerInvulnerable()) {
                return false;
            }
            ParticleMaker.spawnLightningSwordParticles(target);
            return true;
        }
        return false;
    }

    @Override
    public DamageType getDamageType(){
        return AetherMod.LIGHTNING;
    }
}
