package teamport.aether.helper.unboxed;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class PriorityEntry<T> implements Comparable<PriorityEntry<T>> {
    private final double weight;
    private final T data;

    private PriorityEntry(double weight, T data) {
        this.weight = weight;
        this.data = data;
    }

    public double getWeight() {
        return weight;
    }

    public T getData() {
        return data;
    }

    public static <T> PriorityEntry<T> Entry(double priority, @NonNull T data) {
        return new PriorityEntry<>(priority, data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, data.hashCode());
    }

    @Override
    public String toString() {
        return String.format("(%f, %s)", weight, data.toString());
    }

    @Override
    public int compareTo(@NonNull PriorityEntry that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriorityEntry<?> that)) return false;
        return this.data.equals(that.data);
    }
}
