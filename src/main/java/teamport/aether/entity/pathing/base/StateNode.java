package teamport.aether.entity.pathing.base;

import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;

public class StateNode {
    // actual node
    private final Node node;
    public double costSoFar;             // g
    public double estimatedCostToGoal;   // h
    public double estimatedTotalCost;    // f = g + h

    /// Parent represents the linked node path structure.
    public StateNode parent;

    /// Whether this node has been processed by A*
    public boolean closed = false;

    /// BinaryHeap membership test for picking next node
    public int heapIndex = -1;

    public StateNode(int ix, int iy, int iz) {
        this(new Node(ix, iy, iz));
    }

    public StateNode(int hash, int ix, int iy, int iz) {
        this(new Node(hash, ix, iy, iz));
    }

    public StateNode(Node node) {
        this.node = node;
    }

    /**
     * Finds neighboring node using direction, calls to implement function
     * {@link StateNode#tileInDirection(int, int, int)} with directional offsets.
     */
    public TilePosc tileInDirection(int xOff, int yOff, int zOff) {
        return this.tilePosc().add(xOff, yOff, zOff, new TilePos());
    }

    public TilePosc tileInDirection(Direction direction) {
        return this.tilePosc().add(direction, new TilePos());
    }

    public Node node() {
        return this.node;
    }

    public TilePosc tilePosc() {
        return this.node.tilePosc();
    }

    public boolean inHeap(){
        return this.heapIndex >= 0;
    }

    public double distanceTo(StateNode other){
        return this.node().distanceTo(other.node());
    }

    public double distanceTo(TilePosc tilePosc){
        return this.node().distanceTo(tilePosc);
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof StateNode nThat)) {
            return false;
        } else {
            return this.hashCode() == nThat.hashCode() && this.tilePosc().equals(nThat.tilePosc());
        }
    }

    @Override
    public int hashCode() {
        return this.node.hashCode();
    }
}
