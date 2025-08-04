package teamport.aether.effect;

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
    public EffectRenderer renderer = new PoisonEffectRenderer();
    public final Random random = new Random();
    public String PATH_HEART;
    public final int tint;
    public double rotD;
    public double motD;

    public PoisonEffect(AetherEffectBuilder builder) {
        this(
                builder.getNameKey(), builder.getId(),
                builder.getImagePath(), builder.getHeartPath(),
                builder.getModifiers(),
                builder.getEffectTimeType(),
                builder.getColor(), builder.getTint(),
                builder.getDefaultDuration(), builder.getMaxStack()
        );
    }

    public PoisonEffect(
            String nameKey, String id,
            String imagePath, String PATH_HEART,
            List<Modifier<?>> modifiers,
            EffectTimeType effectTimeType,
            int color, int tint,
            int defaultDuration, int maxStack
    ) {
        super(nameKey, id, imagePath, color, modifiers, effectTimeType, defaultDuration, maxStack);
        this.tint = tint;
        this.PATH_HEART = PATH_HEART;
    }

    @Override
    public <T> void activated(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (effectStack.getAmount() == 1) ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
//        this.slideX = random.nextGaussian() * 0.013;
//        this.slideZ = random.nextGaussian() * 0.013;
    }

    @Override
    public <T> void expired(EffectStack effectStack, EffectContainer<T> effectContainer) {
        effectContainer.remove(AetherEffects.poisonEffect);
        EffectStack newStack = new EffectStack((IHasEffects) effectContainer.getParent(), AetherEffects.poisonEffect, effectStack.getAmount() - 1);
        newStack.start(effectContainer);
        effectContainer.add(newStack);
        ((Mob) effectContainer.getParent()).hurt(null, 1, DamageType.GENERIC);
    }

    // TODO change poison particles for player or maybe make them apply exclusively on the back
    @Override
    public <T> void tick(EffectStack effectStack, EffectContainer<T> effectContainer) {
        if (!(effectContainer.getParent() instanceof Mob)) return;
//        ((Mob) effectContainer.getParent()).fling(slideX, 0, slideZ, 1);
        Mob mob = (Mob) effectContainer.getParent();
        if (mob.world == null) {
            AetherMod.LOGGER.warn("PoisonEffect is not applied cause the world is null");
            return;
        }
        double mobY = mob.y + (mob instanceof Player ? -1.0F : 0.0F);
        if (random.nextDouble() < 0.1) {
            ParticalHelper.spawnPoisonParticles(mob.world, mob.x, mobY, mob.z, mob.bbHeight / 2, mob.bbWidth);
        }
        if (!(effectContainer.getParent() instanceof Mob)) return;
        double gauss = ((EntityAccessor) effectContainer.getParent()).getRandom().nextGaussian();
        double newMotD = 0.1 * gauss;
        motD = 0.2 * newMotD + (1.0 - 0.2) * motD;
        ((Mob) effectContainer.getParent()).xd += motD;
        ((Mob) effectContainer.getParent()).zd += motD;
        double newRotD = 0.7853981633974483 * gauss;
        rotD = 0.125 * newRotD + (1.0 - 0.125) * rotD;
        ((Mob) effectContainer.getParent()).yRot = (float) ((double) ((Mob) effectContainer.getParent()).yRot + rotD);
        ((Mob) effectContainer.getParent()).xRot = (float) ((double) ((Mob) effectContainer.getParent()).xRot + rotD);
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
    public int getTint() {
        return tint;
    }

    @Override
    public EffectRenderer getRenderer() {
        return this.renderer;
    }
}
