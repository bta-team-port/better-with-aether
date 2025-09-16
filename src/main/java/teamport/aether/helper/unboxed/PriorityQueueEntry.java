package teamport.aether.helper.unboxed;

import java.util.Objects;

public class PriorityQueueEntry<T> implements Comparable<PriorityQueueEntry<T>> {
    double priority;
    T data;

    private PriorityQueueEntry(double priority, T data){
        this.priority = priority;
        this.data = data;
    }

    private PriorityQueueEntry<T> setPriority(double priority){
        this.priority = priority;
        return this;
    }

    public double getPriority(){
        return priority;
    }

    public T getData(){
        return data;
    }

    public static <T> PriorityQueueEntry<T> pEntry(double priority, T data) {
        return new PriorityQueueEntry<>(priority, data);
    }

    @Override
    public int hashCode(){
        return Objects.hash(priority, data.hashCode());
    }

    @Override
    public String toString(){
        return String.format("(%f, %s)", priority, data.toString());
    }

    @Override
    public int compareTo(PriorityQueueEntry that) {
        return Double.compare(this.priority, that.priority);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(!(o instanceof PriorityQueueEntry)) return false;
        PriorityQueueEntry<?> that = (PriorityQueueEntry<?>) o;
        return this.data.equals(that.data);
    }
}
