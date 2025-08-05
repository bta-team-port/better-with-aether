package teamport.aether.effect;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import sunsetsatellite.catalyst.effects.api.effect.*;
import sunsetsatellite.catalyst.effects.api.modifier.Modifier;
import teamport.aether.AetherMod;
import teamport.aether.effect.render.EffectRenderer;
import teamport.aether.effect.render.PoisonEffectRenderer;
import teamport.aether.gui.IHudVisibility;
import teamport.aether.mixin.accessors.EntityAccessor;
import teamport.aether.particle.ParticalHelper;

import java.util.List;
import java.util.Random;

public class PoisonEffect extends Effect implements IHudVisibility {
    public EffectRenderer renderer;
    public final Random random = new Random();
    public String PATH_HEART;
    public double rotD;
    public double motD;

    public PoisonEffect(AetherEffectBuilder builder) {
        this(
                builder.getNameKey(), builder.getId(),
                builder.getImagePath(), builder.getHeartPath(), builder.getVignette(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getColor(), builder.getTint(),
                builder.getDefaultDuration(), builder.getMaxStack()
        );
    }

    public PoisonEffect(
            String nameKey, String id,
            String imagePath, String PATH_HEART, String vignette,
            List<Modifier<?>> modifiers,
            EffectTimeType effectTimeType,
            int color, int tint,
            int defaultDuration, int maxStack
    ) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
        this.PATH_HEART = PATH_HEART;
        renderer = new PoisonEffectRenderer(vignette, tint);
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (effectStack.getAmount() == 1) ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    @Override
    public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
        effectContainer.remove(AetherEffects.poisonEffect);
        EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), AetherEffects.poisonEffect, effectStack.getAmount() - 1);
        newStack.start(effectContainer);
        effectContainer.add(newStack);
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    // TODO change poison particles for player  or maybe make them apply exclusively on the back
    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob)) return;
        Mob mob = (Mob) effectContainer.getParent();
        if (mob.world == null) {AetherMod.LOGGER.warn("PoisonEffect is not applied cause the world is null");return;}
        if(mob instanceof Player){
            ParticalHelper.spawnPoisonParticles(mob.world, mob.x, mob.y - 2, mob.z, mob.bbHeight, mob.bbWidth);
        }else {
            ParticalHelper.spawnPoisonParticles(mob.world, mob.x, mob.y, mob.z, mob.bbHeight, mob.bbWidth);
        }
        slideEntity(mob);
    }

    private void slideEntity(Mob mob) {
        double gauss = ((EntityAccessor)mob).getRandom().nextGaussian();
        double newMotD = 0.1 * gauss;
        motD = 0.2 * newMotD + (1.0 - 0.2) * motD;
        mob.xd += motD;
        mob.zd += motD;
        double newRotD = 0.7853981633974483 * gauss;
        rotD = 0.125 * newRotD + (1.0 - 0.125) * rotD;
        mob.yRot = (float) ((double) mob.yRot + rotD);
        mob.xRot = (float) ((double) mob.xRot + rotD);
    }

    @Override
    public <T> void stackAdded(EffectStack effectStack, EffectContainer<T> effectContainer) {
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
        super.stackAdded(effectStack, effectContainer);
    }

    @Override
    public String getPath() {
        return PATH_HEART;
    }

    @Override
    public EffectRenderer getRenderer() {
        return this.renderer;
    }
}
