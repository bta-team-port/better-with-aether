package teamport.aether.world;

public final class SunSpiritDeath {
    private SunSpiritDeath() {}

    private static boolean isDead = false;
    private static long deathTimestamp = 0;

    public static boolean isDead() {
        return isDead;
    }

    public static void setDead(boolean isDead) {
        SunSpiritDeath.isDead = isDead;
        AetherDimension.initDimensionBlackList();
    }

    public static long getDeathTime() {
        return SunSpiritDeath.deathTimestamp;
    }

    public static void setDeathTime(long deathTimestamp) {
        SunSpiritDeath.deathTimestamp = deathTimestamp;
    }
}
