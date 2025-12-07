package teamport.aether.world;

public final class SunSpiritDeath {
    private SunSpiritDeath() {}

    private static boolean isDead = false;
    private static long deathTimestamp = 0;

    public static boolean isIsDead() {
        return isDead;
    }

    public static void setIsDead(boolean isDead) {
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
