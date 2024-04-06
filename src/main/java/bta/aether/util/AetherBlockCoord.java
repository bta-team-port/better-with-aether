package bta.aether.util;

public class AetherBlockCoord {

    private final int x;
    private final int y;
    private final int z;

    public AetherBlockCoord(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] values(){
        return new int[] {x, y, z};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
