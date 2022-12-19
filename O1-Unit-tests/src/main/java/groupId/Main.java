package groupId;

import static java.lang.Math.sqrt;

public class Main {

    private static final double DEFAULT_E = 1e-5;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static double[] solve(double a, double b, double c) {
        return solve(a, b, c, DEFAULT_E);
    }

    public static double[] solve(double a, double b, double c, double e) {
        validateData(a, b, c, e);

        double d = b * b - 4 * a * c;
        //проверка на отрицательный дискриминант
        if (d < -e) {
            return new double[0];
        }

        double x1 = (-b + sqrt(d)) / (2 * a);
        //проверка на то, что дискриминант не нулевой
        if (d < e) {
            return new double[]{x1};
        }
        double x2 = (-b - sqrt(d)) / (2 * a);
        return new double[]{x1, x2};
    }

    private static void validateData(double a, double b, double c, double e) {
        if (a < e && a > -e) {
            throw new RuntimeException("a не может быть равна нулю");
        }
        if (isForbidden(a) || isForbidden(b) || isForbidden(c)) {
            throw new RuntimeException("Недопустимые значения переменных");
        }
    }

    private static boolean isForbidden(double a) {
        return Double.isNaN(a) || Double.isInfinite(a);
    }
}