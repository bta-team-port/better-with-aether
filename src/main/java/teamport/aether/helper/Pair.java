package teamport.aether.helper;

import java.util.Objects;

/**
 * A simple paired value class
 */
public final class Pair<T, U> {

    public Pair(T first, U second) {
        this.second = second;
        this.first = first;
    }

    public final T first;
    public final U second;

    // Because 'pair()' is shorter than 'new Pair<>()'.
    // Sometimes this difference might be very significant (especially in a
    // 80-ish characters boundary). Sorry diamond operator.
    public static <T, U> Pair<T, U> pair(T first, U second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public int hashCode(){
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(!(o instanceof Pair)) return false;
        Pair<?, ?> that = (Pair<?, ?>) o;
        return Objects.equals(this.first, that.first) &&
                Objects.equals(this.second, that.second);
    }
}
