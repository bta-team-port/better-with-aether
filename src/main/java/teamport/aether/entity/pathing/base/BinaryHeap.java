package teamport.aether.entity.pathing.base;

public class BinaryHeap {
    private StateNode[] heap = new StateNode[1024];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.heap[i] = null;
        }
        this.size = 0;
    }

    public StateNode insert(StateNode node) {
        if (node.heapIndex >= 0) {
            throw new IllegalStateException("Cannot insert StateNode that is already in the BinaryHeap.");
        }
        this.shrinkHeapSize();
        this.heap[this.size] = node;
        node.heapIndex = this.size;
        this.upHeap(this.size++);
        return node;
    }

    private void shrinkHeapSize() {
        if (this.size != this.heap.length) {
            return;
        }
        StateNode[] resizedArray = new StateNode[this.size << 1];
        System.arraycopy(this.heap, 0, resizedArray, 0, this.size);
        this.heap = resizedArray;
    }

    public StateNode pop() {
        StateNode node = this.heap[0];
        node.heapIndex = -1;
        this.heap[0] = this.heap[--this.size];
        this.heap[this.size] = null;
        if (this.size > 0) {
            this.downHeap(0);
        }
        return node;
    }

    public void changeCost(StateNode node, double newCost) {
        double prevETCost = node.estimatedTotalCost;
        node.estimatedTotalCost = newCost;
        if (newCost < prevETCost) {
            this.upHeap(node.heapIndex);
            return;
        }
        this.downHeap(node.heapIndex);
    }

    private void upHeap(int index) {
        StateNode node = this.heap[index];
        int parentIndex;
        for(double cost = node.estimatedTotalCost; index > 0; index = parentIndex) {
            parentIndex = index - 1 >> 1;
            StateNode parent = this.heap[parentIndex];
            if (cost >= parent.estimatedTotalCost) {
                break;
            }
            this.heap[index] = parent;
            parent.heapIndex = index;
        }
        this.heap[index] = node;
        node.heapIndex = index;
    }

    private void downHeap(int index) {
        StateNode nodeAtIndex = this.heap[index];
        double estimatedTotalCost = nodeAtIndex.estimatedTotalCost;

        while(true) {
            int leftIndex = 1 + (index << 1);
            int rightIndex = leftIndex + 1;
            if (leftIndex >= this.size) {
                break;
            }

            StateNode leftChild = this.heap[leftIndex];
            double leftETCost = leftChild.estimatedTotalCost;

            StateNode rightChild = null;
            double rightETCost = Double.POSITIVE_INFINITY;
            if (rightIndex < this.size) {
                rightChild = this.heap[rightIndex];
                rightETCost = rightChild.estimatedTotalCost;
            }

            if (leftETCost < rightETCost) {
                if (leftETCost >= estimatedTotalCost) {
                    break;
                }
                this.heap[index] = leftChild;
                leftChild.heapIndex = index;
                index = leftIndex;
            } else {
                assert rightChild != null;
                if (rightETCost >= estimatedTotalCost) {
                    break;
                }
                this.heap[index] = rightChild;
                rightChild.heapIndex = index;
                index = rightIndex;
            }
        }

        this.heap[index] = nodeAtIndex;
        nodeAtIndex.heapIndex = index;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
}
