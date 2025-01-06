package com.balatro;


public class Util {

    public static double fract(double n) {
        return n - Math.floor(n);
    }

    public static double pseudohash(String s) {
        double num = 1;
        for (int i = s.length(); i > 0; i--) {
            num = fract(1.1239285023 / num * s.charAt(i - 1) * 3.141592653589793 + 3.141592653589793 * i);
        }
        if (Double.isNaN(num)) return Double.NaN;
        return num;
    }

    private static final double inv_prec = Math.pow(10, 13);
    private static final double two_inv_prec = Math.pow(2, 13);
    private static final double five_inv_prec = Math.pow(5, 13);

    public static double round13(double x) {
        double tentative = Math.floor(x * inv_prec) / inv_prec;
        double truncated = (x * two_inv_prec % 1.0) * five_inv_prec;
        if (tentative != x && truncated % 1.0 >= 0.5 && tentative != Math.nextAfter(x, 1)) {
            return (Math.floor(x * inv_prec) + 1) / inv_prec;
        }
        return tentative;
    }
}