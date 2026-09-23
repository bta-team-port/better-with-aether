package teamport.aether.entity.pathing.base;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Path implements Iterator<Node> {
    private final List<Node> nodes;
    private int index;

    public Path(List<Node> nodes) {
        this.nodes = Collections.unmodifiableList(nodes);
    }

    @Override
    public boolean hasNext() {
        return this.index < this.nodes.size();
    }

    public Node current() {
        if(index >= this.nodes.size()){
            return this.nodes.get(this.nodes.size() - 1);
        }
        return this.nodes.get(this.index);
    }

    @Override
    public Node next() {
        if(index >= this.nodes.size()){
            return this.nodes.get(this.nodes.size() - 1);
        }
        return this.nodes.get(this.index++);
    }
}
