package teamport.aether.entity.pathing.boss;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.WorldSource;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import teamport.aether.entity.pathing.base.Node;
import teamport.aether.entity.pathing.base.PathFinder;
import teamport.aether.entity.pathing.base.StateNode;

import java.util.ArrayList;
import java.util.List;

public class SliderPathFinder extends PathFinder {
    private static final int MAX_SLIDE_DISTANCE = 25;

    public SliderPathFinder(Entity entity, NodeWeightFunction getNodeWeight) {
        super(entity, getNodeWeight);
    }

    @Override
    protected List<StateNode> getNeighbors(WorldSource world, StateNode curent, StateNode end, float distance) {
        List<StateNode> nodes = new ArrayList<>();
        for (Direction direction : Direction.all) {
            nodes.addAll(this.rayMarch(world, direction, curent, end, distance));
        }
        return nodes;
    }

    private List<StateNode> rayMarch(WorldSource world, Direction direction, StateNode from, StateNode end, float maxDistance) {
        List<StateNode> results = new ArrayList<>();
        Vector3dc pos = from.tileInDirection(direction);
        double alignDistance = alignmentDistance(direction, from.node(), end.node());
        for(int steps = 1; steps <= MAX_SLIDE_DISTANCE && steps < maxDistance; steps++){
            double weight = this.getNodeWeight.apply(world, pos, this.boundingBoxSize);
            if (weight == Double.POSITIVE_INFINITY) {
                break;
            }
            if ((steps >= alignDistance) || (steps >= MAX_SLIDE_DISTANCE)) {
                results.add(this.markNodeAt(pos.x(), pos.y(), pos.z()));
                break;
            }
            if (this.isJumpPoint(world, pos, direction)) {
                results.add(this.markNodeAt(pos.x(), pos.y(), pos.z()));
            }
            pos = new Vector3d(pos).add(direction.offsetX(), direction.offsetY(), direction.offsetZ());
        }
        return results;
    }

    private boolean isJumpPoint(WorldSource world, Vector3dc pos, Direction direction) {
        Direction opposite = direction.opposite();
        Vector3d current = new Vector3d(pos);
        Vector3d previous = current.add(opposite.offsetX(), opposite.offsetY(), opposite.offsetZ());
        for (Direction perpendicular : perpendicularDirections(direction)) {
            Vector3d sideCurrent = new Vector3d(current).add(perpendicular.offsetX(), perpendicular.offsetY(), perpendicular.offsetZ());
            Vector3d sidePrevious = new Vector3d(previous).add(perpendicular.offsetX(), perpendicular.offsetY(), perpendicular.offsetZ());
            boolean currentOpen = this.getNodeWeight.apply(world, sideCurrent, this.boundingBoxSize) != Double.POSITIVE_INFINITY;
            boolean previousOpen = this.getNodeWeight.apply(world, sidePrevious, this.boundingBoxSize) != Double.POSITIVE_INFINITY;
            if (currentOpen && !previousOpen) {
                return true;
            }
        }
        return false;
    }

    private List<Direction> perpendicularDirections(Direction dir) {
        List<Direction> result = new ArrayList<>(4);
        for (Direction d : Direction.all) {
            if (d.axis() != dir.axis()) {
                result.add(d);
            }
        }
        return result;
    }

    private double alignmentDistance(Direction direction, Node from, Node endPoint) {
        return switch (direction) {
            case UP -> endPoint.y() >= from.y() ? (endPoint.y() - from.y()) : Double.MAX_VALUE;
            case DOWN -> endPoint.y() <= from.y() ? (from.y() - endPoint.y()) : Double.MAX_VALUE;
            case NORTH -> endPoint.z() <= from.z() ? (from.z() - endPoint.z()) : Double.MAX_VALUE;
            case SOUTH -> endPoint.z() >= from.z() ? (endPoint.z() - from.z()) : Double.MAX_VALUE;
            case WEST -> endPoint.x() <= from.x() ? (from.x() - endPoint.x()) : Double.MAX_VALUE;
            case EAST -> endPoint.x() >= from.x() ? (endPoint.x() - from.x()) : Double.MAX_VALUE;
            default -> Integer.MAX_VALUE;
        };
    }
}
