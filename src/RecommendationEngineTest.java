import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecommendationEngineTest {

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
    void testWeightedSimilarity() {
        Main app = new Main();

        double[] v1 = {1.0, 1.0};
        double[] v2 = {1.0, 0.0};

        double[] wEqual = {0.5, 0.5};
        double[] mv1Equal = app.multiply(wEqual, v1);
        double[] mv2Equal = app.multiply(wEqual, v2);
        double resultEqual = app.calculateSimilarity(mv1Equal, mv2Equal);
        assertEquals(0.7071, resultEqual, 0.01, "Equal weights should yield ~0.7071");

        double[] wHighFirst = {0.9, 0.1};
        double[] mv1High = app.multiply(wHighFirst, v1);
        double[] mv2High = app.multiply(wHighFirst, v2);
        double resultHigh = app.calculateSimilarity(mv1High, mv2High);

        assertTrue(resultHigh > resultEqual,
                "Higher weight on the matching feature should increase similarity compared to equal weights");
        assertTrue(resultHigh > 0.9,
                "With a very high weight on the matching feature, similarity should be close to 1.0");
    }

    @Test
    void testLengthMismatch() {
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