package teamport.aether.helper;

import java.util.Objects;

/**
 * A simple paired value class
 */
public final class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> that = (Pair<?, ?>) o;
        return Objects.equals(this.first, that.first) &&
            Objects.equals(this.second, that.second);
    }
    public T getFirst() {
        return first;
    }
    public U getSecond() {
        return second;
    }
}
