package teamport.aether.entity.pathing.base;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterable<Node>{
    private final List<Node> nodes;

    public Path(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public @NotNull Iterator<Node> iterator() {
        return Collections.unmodifiableList(this.nodes).iterator();
    }
}
