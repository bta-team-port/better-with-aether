package teamport.aether.helper.unboxed;

import org.jspecify.annotations.NonNull;

/**
 * @implNote To avoid Boxing integer, frequently used by BlockPallet
 */
public record IntPair(int first, int second) {

    @Override
    public @NonNull String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntPair that)) return false;
        return this.first == that.first && this.second == that.second;
    }
}
