import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertEquals;

public class TestMovieRecomendator {

    @Test
    void testIdenticalVectors() {
        Main app = new Main();

        double[] v1 = {1.0, 2.0, 3.0};
        double[] v2 = {1.0, 2.0, 3.0};

        double result = app.calculateSimilarity(v1, v2);

        assertEquals(1.0, result, 1e-9, "Identical vectors should return 1.0");
    }

    @Test
    void testOrthogonalVectors() {
        Main app = new Main();

        double[] v1 = {1.0, 0.0};
        double[] v2 = {0.0, 1.0};

        double result = app.calculateSimilarity(v1, v2);

        assertEquals(0.0, result, 1e-9, "Orthogonal vectors should return 0.0");
    }

    @Test
    void testWeightedSimilarityViaMultiply() {
        Main app = new Main();

        double[] v1 = {1.0, 1.0};
        double[] v2 = {1.0, 0.0};
        double[] w  = {0.5, 0.5};

        double[] mv1 = app.multiply(w, v1);
        double[] mv2 = app.multiply(w, v2);

        double result = app.calculateSimilarity(mv1, mv2);

        // expected ~ 0.7071
        assertEquals(0.7071, result, 0.01, "Weighted calculation result should match expected math");
    }

    @Test
    void testLengthMismatch_shouldThrow() {
        Main app = new Main();

        double[] v1 = {1.0, 2.0, 3.0};
        double[] v2 = {1.0, 2.0};

        assertThrows(IllegalArgumentException.class, () -> app.calculateSimilarity(v1, v2),
                "Should throw exception when vector lengths differ");
    }

    @Test
    void testZeroVector_shouldThrow() {
        Main app = new Main();

        double[] v1 = {0.0, 0.0};
        double[] v2 = {1.0, 2.0};

        assertThrows(IllegalArgumentException.class, () -> app.calculateSimilarity(v1, v2),
                "Should throw exception when a vector has zero magnitude (division by zero)");
    }
}