package teamport.aether.helper;

public class BlockCoordinate {
    public Integer x;
    public Integer y;
    public Integer z;

    public BlockCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] values(){
        return new int[] {x, y, z};
    }

    public float distanceTo(BlockCoordinate cord) {
        return distanceTo(cord.x, cord.y, cord.z);
    }

    public float distanceTo(int x, int y, int z) {
        return (float) Math.sqrt(
            Math.pow(Math.abs(((float) this.x - x)), 2) +
            Math.pow(Math.abs(((float) this.y - y)), 2) +
            Math.pow(Math.abs(((float) this.z - z)), 2)
        );
    }

}
