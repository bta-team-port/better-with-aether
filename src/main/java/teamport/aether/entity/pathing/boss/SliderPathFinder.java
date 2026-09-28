package teamport.aether.entity.pathing.boss;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
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
        TilePosc pos = from.tileInDirection(direction);
        int alignDistance = alignmentDistance(direction, from.node(), end.node());
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
            pos = direction.offset(new TilePos(pos));
        }
        return results;
    }

    private boolean isJumpPoint(WorldSource world, TilePosc pos, Direction direction) {
        TilePos current = new TilePos(pos);
        TilePos previous = direction.opposite().offset(new TilePos(pos));
        for (Direction perpendicular : perpendicularDirections(direction)) {
            TilePos sideCurrent = perpendicular.offset(new TilePos(current));
            TilePos sidePrevious = perpendicular.offset(new TilePos(previous));
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

    private int alignmentDistance(Direction direction, Node from, Node endPoint) {
        return switch (direction) {
            case UP -> endPoint.y() > from.y() ? (endPoint.y() - from.y()) : Integer.MAX_VALUE;
            case DOWN -> endPoint.y() < from.y() ? (from.y() - endPoint.y()) : Integer.MAX_VALUE;
            case NORTH -> endPoint.z() < from.z() ? (from.z() - endPoint.z()) : Integer.MAX_VALUE;
            case SOUTH -> endPoint.z() > from.z() ? (endPoint.z() - from.z()) : Integer.MAX_VALUE;
            case WEST -> endPoint.x() < from.x() ? (from.x() - endPoint.x()) : Integer.MAX_VALUE;
            case EAST -> endPoint.x() > from.x() ? (endPoint.x() - from.x()) : Integer.MAX_VALUE;
            default -> Integer.MAX_VALUE;
        };
    }
}
