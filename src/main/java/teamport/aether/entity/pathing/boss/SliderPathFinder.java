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
    private static final int MAX_SLIDE_DISTANCE = 16;

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
        int steps = 0;
        int alignDistance = alignmentDistance(direction, from.node(), end.node());
        while (from.distanceTo(pos) < maxDistance) {
            double weight = this.getNodeWeight.apply(world, pos, this.boundingBoxSize);
            if (weight == Double.POSITIVE_INFINITY) {
                break;
            }
            ++steps;
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
        for (Direction perp : this.perpendicularDirections(direction)) {
            TilePos current = new TilePos(pos.x(), pos.y(), pos.z());
            TilePos behindBase = new TilePos(pos.x(), pos.y(), pos.z());
            TilePos behind = direction.opposite().offset(behindBase);
            boolean openNow = this.getNodeWeight.apply(world, perp.offset(current), this.boundingBoxSize) != Double.POSITIVE_INFINITY;
            boolean openBefore = this.getNodeWeight.apply(world, perp.offset(behind), this.boundingBoxSize) != Double.POSITIVE_INFINITY;
            if (openNow && !openBefore) {
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
            case UP -> endPoint.y() > from.y() ? (endPoint.y() - from.y()) : MAX_SLIDE_DISTANCE;
            case DOWN -> endPoint.y() < from.y() ? (from.y() - endPoint.y()) : MAX_SLIDE_DISTANCE;
            case NORTH -> endPoint.z() < from.z() ? (from.z() - endPoint.z()) : MAX_SLIDE_DISTANCE;
            case SOUTH -> endPoint.z() > from.z() ? (endPoint.z() - from.z()) : MAX_SLIDE_DISTANCE;
            case WEST -> endPoint.x() < from.x() ? (from.x() - endPoint.x()) : MAX_SLIDE_DISTANCE;
            case EAST -> endPoint.x() > from.x() ? (endPoint.x() - from.x()) : MAX_SLIDE_DISTANCE;
            default -> MAX_SLIDE_DISTANCE;
        };
    }
}
