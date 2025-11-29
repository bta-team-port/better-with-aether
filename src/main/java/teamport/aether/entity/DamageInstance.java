package teamport.aether.entity;

import net.minecraft.core.util.helper.DamageType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DamageInstance implements Comparable<DamageInstance> {

	int damage;
	DamageType type;
    /**
     * @implNote When hurt is triggered to close to a previous hurt instance it will result in damage cut offs.
     * To prevent some specific damage instance from being cut off you can define the weight to allow some instances to
     * have priority.
     * */
	double weight;

	public static DamageInstance inst(DamageType type, int damage) {
		return new DamageInstance(type, damage, 0);
	}

	public static DamageInstance inst(DamageType type, int damage, double weight) {
		return new DamageInstance(type, damage, weight);
	}

	public DamageInstance(DamageType type, int damage, double weight) {
		this.damage = damage;
		this.type = type;
		this.weight = weight;
	}

	public int getDamage() {
		return damage;
	}

	public DamageType getType() {
		return type;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DamageInstance)) return false;
		DamageInstance damageInstance1 = (DamageInstance) object;
		return damage == damageInstance1.damage && Objects.equals(type, damageInstance1.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(damage, type);
	}

	@Override
	public int compareTo(@NotNull DamageInstance that) {
		if(this.weight - that.weight < 1.0E-5){
			if(this.damage == that.damage){
				return 0;
			}
			return this.damage - that.damage;
		}
		return (int)Math.round(this.weight - that.weight);
	}
}
