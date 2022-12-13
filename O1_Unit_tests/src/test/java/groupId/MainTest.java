package groupId;


import static groupId.Main.solve;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MainTest {

    private final double TOO_SMALL_NUMBER = 8e-10;

    @ParameterizedTest
    @ValueSource(doubles = {0, TOO_SMALL_NUMBER})
    @DisplayName(" Нет корней для случая, когда дискриминант отрицательный ")
    void noRootsCase(double number) {
        double[] result = solve(1, number, 1);
        assertEquals(0, result.length, "При отрицательном дискриминанте корней быть не должно");
    }

    @Test
    @DisplayName(" Нормальный случай для целых чисел ")
    void normalCase() {
        double[] result = solve(1, 0, -1);
        assertEquals(2, result.length, "В общем случае должно быть два корня");
        assertEquals(1, Math.abs(result[0]));
        assertEquals(1, Math.abs(result[1]));
    }

    @Test
    @DisplayName("Cлучай для целых чисел c одним корнем")
    void oneRootCase() {
        double[] result = solve(1, 2, 1);
        assertEquals(1, result.length, "Когда дискриминант равен нулю, корень всегда один");
        assertEquals(1, Math.abs(result[0]));
    }

    @Test
    @DisplayName("Cлучай c одним корнем для чисел с плавающей точкой, изоброжающий округление чисел")
    void oneRootCaseWithPointer() {
        double[] result = solve(1, 2.00000001, 1);
        assertEquals(1, result.length, "Когда дискриминант равен нулю, корень всегда один");
    }

    @DisplayName("Проверка на то, что уравнение квадратное")
    @ParameterizedTest
    @ValueSource(doubles = {0, TOO_SMALL_NUMBER})
    void isQuadraticFormula(double number) {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> solve(number, 45, 1),
                "При отрицательном A уравнение не является квадратным");
        assertTrue(runtimeException.getMessage().contains("a не может быть равна нулю"));
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    @DisplayName("Проверка на некорректные значения")
    public void testParametersFromMethod(double a, double b , double c) {
        assertThrows(RuntimeException.class, () -> solve(a, b, c));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(Double.NaN, 2, 1),
                Arguments.of(1,Double.NEGATIVE_INFINITY, 1),
                Arguments.of(1, 2, Double.POSITIVE_INFINITY)
        );
    }


}