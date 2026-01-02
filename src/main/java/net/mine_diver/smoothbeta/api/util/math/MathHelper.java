package net.mine_diver.smoothbeta.api.util.math;

public class MathHelper {
    public static final float SQUARE_ROOT_OF_TWO = sqrt(2.0F);

    public static int ceil(float value) {
        int i = (int) value;
        return value > (float) i ? i + 1 : i;
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }

    public static double square(double n) {
        return n * n;
    }

    public static int lerp(double delta, int start, int end) {
        return (int) Math.round(start + (end - start) * delta);
    }

    public static float lerp(double delta, float start, float end) {
        return (float) (start + (end - start) * delta);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static float interpolate2D(
            double x, double y,
            float v1, float v2, float v3, float v4) {
        return lerp(y, lerp(x, v1, v2), lerp(x, v3, v4));
    }

    public static float interpolate3D(
            double x, double y, double z,
            float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8) {
        return lerp(z, interpolate2D(x, y, v1, v2, v3, v4), interpolate2D(x, y, v5, v6, v7, v8));
    }

    public static float clamp(float value, float min, float max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    public static int clamp(int value, int min, int max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    public static float fastInverseSqrt(float x) {
        float f = 0.5F * x;
        int i = Float.floatToIntBits(x);
        i = 1597463007 - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= 1.5F - f * x * x;
        return x;
    }

    public static boolean isPowerOfTwo(int i) {
        return i != 0 && (i & i - 1) == 0;
    }

    public static int roundUpToMultiple(int value, int divisor) {
        return (int) (Math.ceil(value / (double) divisor) * divisor);
    }
}
