package com.aefyr.eris.utils;

public class MathUtils {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float lerp(int a, int b, float t) {
        return a + (b - a) * t;
    }

    public static int clamp(int a, int min, int max) {
        if (a < min)
            return min;
        if (a > max)
            return max;
        return a;
    }

}
