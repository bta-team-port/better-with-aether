package teamport.aether.helper.unboxed;

import java.util.Objects;

/**
 * @implNote To avoid Boxing integer, frequently used by BlockPallet
 */
public class IntPair {
    public int first;
    public int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public static IntPair ipair(int first, int second) {
        return new IntPair(first, second);
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
        if (!(o instanceof IntPair)) return false;
        IntPair that = (IntPair) o;
        return this.first == that.first && this.second == that.second;
    }
}
