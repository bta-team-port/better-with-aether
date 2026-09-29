package teamport.aether.entity.pathing.base;

import net.minecraft.core.world.pos.TilePosc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Objects;

public class Node {
    private final int hash;
    private Vector3dc pos;

    public Node(double x, double y, double z) {
        this(Objects.hash(x, y, z), x, y, z);
    }

    public Node(int hash, double x, double y, double z) {
        this.hash = hash;
        this.pos = new Vector3d(x, y, z);
    }

    public Vector3dc pos() {
        return this.pos;
    }

    public double x() {
        return this.pos.x();
    }
    public double y() {
        return this.pos.y();
    }
    public double z() {
        return this.pos.z();
    }

    public double distanceTo(Node other) {
        return this.pos.distance(other.pos);
    }

    public double distanceTo(Vector3dc other) {
        return this.pos.distance(other);
    }

    public double distanceTo(TilePosc tilePosc) {
        return this.pos.distance(tilePosc.x(), tilePosc.y(), tilePosc.z());
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Node nThat)) {
            return false;
        } else {
            return this.hash == nThat.hash && this.pos.equals(nThat.pos);
        }
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public String toString() {
        return this.x() + ", " + this.y() + ", " + this.z();
    }
}
