package teamport.aether.entity;

import net.minecraft.core.util.helper.DamageType;

import java.util.Objects;

public class DamageInstance {

    int damage;
    DamageType type;

    public static DamageInstance inst(int damage, DamageType type) {
        return new DamageInstance(type, damage);
    }

    public DamageInstance(DamageType type, int damage) {
        this.damage = damage;
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public DamageType getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DamageInstance damageInstance)) return false;
        return damage == damageInstance.damage && Objects.equals(type, damageInstance.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(damage, type);
    }
}
