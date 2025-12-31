package teamport.aether.helper;

import java.util.Random;

public class AetherMathHelper {
    /**
     * @implNote Exponential can return any value between [0, INF) and as such this function caps it.
     */
    public static int invertedExponentialCapped(Random random, float mean, int cap) {
        return Math.min((int) Math.floor(invertedExponential(random, mean)), cap);
    }

    /**
     * @implNote Inverse Exponential function where the expected value is the parameter mean.
     */
    public static double invertedExponential(Random random, float mean) {
        return nextExponential(random) * mean;
    }

    /**
     * @implNote Generate an exponential distributed random values function with lambda set to 1.
     */
    public static double nextExponential(Random random) {
        return -Math.log(1 - random.nextDouble());
    }
}
