package Engine.Math;

public class Util {
    static public float LinearInterpolate(float a, float b, float alpha) {
        return a + alpha * (b - a);
    }
}
