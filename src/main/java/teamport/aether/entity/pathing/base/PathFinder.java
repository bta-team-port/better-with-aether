package teamport.aether.entity.pathing.base;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class PathFinder {
    private final Entity entity;
    protected final BoundingBoxSize boundingBoxSize;
    protected final NodeWeightFunction getNodeWeight;
    private final Int2ObjectMap<StateNode> closedSet = new Int2ObjectOpenHashMap();
    private final BinaryHeap openSet = new BinaryHeap();

    @FunctionalInterface
    public interface NodeWeightFunction {
        double apply(WorldSource world, Vector3dc pos, BoundingBoxSize boundingBoxSize);
    }

    protected PathFinder(Entity entity, NodeWeightFunction getNodeWeight) {
        this.entity = entity;
        this.boundingBoxSize = new BoundingBoxSize(entity);
        this.getNodeWeight = getNodeWeight;
    }

    public final @Nullable Path findPath(WorldSource world, Entity target, float distance) {
        this.openSet.clear();
        this.closedSet.clear();
        StateNode startPoint = this.markNodeAt(this.entity.bb.minX, this.entity.bb.minY, this.entity.bb.minZ);
        StateNode endPoint = this.markNodeAt(target.x - entity.bbWidth / 2.0F, target.bb.minY, target.z - entity.bbWidth / 2.0F);
        return this.findPath(world, startPoint, endPoint, distance);
    }

    protected final @Nullable Path findPath(WorldSource world, StateNode startNode, StateNode endNode, float distance) {
        startNode.costSoFar = 0.0F;
        startNode.estimatedCostToGoal = startNode.distanceTo(endNode);
        startNode.estimatedTotalCost = startNode.estimatedCostToGoal;
        this.openSet.clear();
        this.openSet.insert(startNode);
        StateNode prevNode = startNode;
        while (!this.openSet.isEmpty()) {
            StateNode currentNode = this.openSet.pop();
            if (currentNode.equals(endNode)) {
                return this.reconstructPath(endNode);
            }
            if (currentNode.distanceTo(endNode) < prevNode.distanceTo(endNode)) {
                prevNode = currentNode;
            }
            currentNode.closed = true;
            List<StateNode> neighbors = this.getNeighbors(world, currentNode, endNode, distance);
            for (StateNode neighborNode : neighbors) {
                if (neighborNode.closed) {
                    continue;
                }
                double f1 = currentNode.costSoFar + this.getNodeWeight.apply(world, neighborNode.pos(), this.boundingBoxSize);
                if (neighborNode.inHeap() && f1 >= neighborNode.costSoFar) {
                    continue;
                }
                neighborNode.parent = currentNode;
                neighborNode.costSoFar = f1;
                neighborNode.estimatedCostToGoal = neighborNode.distanceTo(endNode);
                double newCost = neighborNode.costSoFar + neighborNode.estimatedCostToGoal;

                if (neighborNode.inHeap()) {
                    this.openSet.changeCost(neighborNode, newCost);
                } else {
                    neighborNode.estimatedTotalCost = newCost;
                    this.openSet.insert(neighborNode);
                }
            }
        }
        if (prevNode == startNode) {
            return null;
        }
        return this.reconstructPath(prevNode);
    }

    protected final StateNode markNodeAt(double x, double y, double z) {
        int hash = Objects.hash(x, y, z);
        return this.closedSet.computeIfAbsent(hash, k -> new StateNode(hash, x, y, z));
    }

    protected Path reconstructPath(StateNode end) {
        List<Node> pathNodes = new ArrayList<>();
        for (StateNode stateNode = end; stateNode.parent != null; stateNode = stateNode.parent) {
            pathNodes.add(stateNode.node());
        }
        Collections.reverse(pathNodes);
        return pathNodes.isEmpty() ? null : new Path(pathNodes);
    }

    protected abstract List<StateNode> getNeighbors(WorldSource world, StateNode current, StateNode end, float distance);
}
