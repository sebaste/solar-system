package solarsystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordinatesCartesianTest {

    @Test
    void convertsFromSphericalToCartesianCorrectly() {
        CoordinatesSpherical coordinatesSpherical = new CoordinatesSpherical(42.0, 3.14, 2.78);

        CoordinatesCartesian coordinatesCartesian = new CoordinatesCartesian(coordinatesSpherical);

        assertEquals(-14.85808365852579, coordinatesCartesian.getX());
        assertEquals(0.023663800284302594, coordinatesCartesian.getY());
        assertEquals(-39.284052617071026, coordinatesCartesian.getZ());
    }

    @Test
    void calculatesDistanceCorrectly() {
        CoordinatesCartesian coordinatesCartesian1 = new CoordinatesCartesian(1.0, 2.0, 3.0);
        CoordinatesCartesian coordinatesCartesian2 = new CoordinatesCartesian(4.0, 5.0, 6.0);

        double result = coordinatesCartesian1.dist(coordinatesCartesian2);

        assertEquals(5.196152422706632, result);
    }
}
