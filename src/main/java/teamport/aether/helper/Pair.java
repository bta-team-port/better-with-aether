package teamport.aether.helper;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

/**
 * A simple paired value class
 */
public record Pair<T, U>(T first, U second) {

    @Override
    public @NonNull String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> that)) return false;
        return Objects.equals(this.first, that.first) &&
            Objects.equals(this.second, that.second);
    }
}
