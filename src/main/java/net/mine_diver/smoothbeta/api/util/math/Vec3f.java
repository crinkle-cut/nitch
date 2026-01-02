package net.mine_diver.smoothbeta.api.util.math;

public final class Vec3f {
    public static Vec3f ZERO = new Vec3f(0.0F, 0.0F, 0.0F);
    private float x;
    private float y;
    private float z;

    public Vec3f() {
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f copy() {
        return new Vec3f(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}
