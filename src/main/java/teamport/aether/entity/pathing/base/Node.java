package teamport.aether.entity.pathing.base;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Node {
    private final int hash;
    private final TilePosc tilePos;

    public Node(int x, int y, int z) {
        this.hash = Node.createHash(x, y, z);
        this.tilePos = new TilePos(x, y, z);
    }

    public Node(int hash, int x, int y, int z) {
        this.hash = hash;
        this.tilePos = new TilePos(x, y, z);
    }

    /**
     * Return the position as {@link TilePosc} of the node.#
     * Alternatively {@link Node#x()}, {@link Node#y()}, {@link Node#z()} can be used
     * to derive the individual coordinates.
     * */
    public TilePosc tilePosc() {
        return this.tilePos;
    }

    public int x() {
        return this.tilePos.x();
    }
    public int y() {
        return this.tilePos.y();
    }
    public int z() {
        return this.tilePos.z();
    }

    public double distanceTo(Node other) {
        return this.tilePos.distance(other.tilePos);
    }

    public double distanceTo(TilePosc tilePosc) {
        return this.tilePos.distance(tilePosc);
    }

    public static int createHash(int x, int y, int z) {
        return y & 255 | (x & 32767) << 8 | (z & 32767) << 24 | (x >= 0 ? 0 : Integer.MIN_VALUE) | (z >= 0 ? 0 : '耀');
    }

    public @Nullable Vector3dc getPos(@NotNull Entity entity) {
        double offset = Math.floor((entity.bbWidth + 2.0F)) * 0.5F;
        double x = this.x() + offset;
        double y = this.y();
        double z = this.z() + offset;
        return new Vector3d(x, y, z);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Node nThat)) {
            return false;
        } else {
            return this.hash == nThat.hash && this.tilePos.equals(nThat.tilePos);
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
