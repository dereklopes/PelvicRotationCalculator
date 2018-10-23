package Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultTest {
    @Test
    void resultTest() {
        String name = "test";
        double focalFilmDistance = 72;
        double s2ToFilmLateral = 5;
        double s2ToMFHLateralFilm = 100;
        double s2ToFilmAP = 4;
        double s2ToMFHAPFilm = 10;
        String focalFilmDistanceUnits = "in";
        String s2ToFilmLateralUnits = "in";
        String s2ToMFHLateralFilmUnits = "mm";
        String s2ToFilmAPUnits = "in";
        String s2ToMFHAPFilmUnits = "mm";
        String resultsUnits = "mm";
        Result result = new Result(name, focalFilmDistance, s2ToFilmLateral, s2ToMFHLateralFilm, s2ToFilmAP,
                s2ToMFHAPFilm, focalFilmDistanceUnits, s2ToFilmLateralUnits, s2ToMFHLateralFilmUnits, s2ToFilmAPUnits,
                s2ToMFHAPFilmUnits, resultsUnits);

        // Confirmed working
        assertEquals(67, result.lateralSourceObject, 0.01);
        assertEquals(1.07, result.lateralMagFactor, 0.01);
        assertEquals(93.5, result.s2ToMFHTrue.get(), 0.01);

        // Untested
        assertEquals(195.1, result.MFHToFilmTrueProperty().get(), 0.01);

        // Confirmed wrong
        assertEquals(68 * 2.54, result.APSourceObject, 0.01);
        assertEquals(1.12, result.APMagFactor, 0.01);
        assertEquals(8.9, result.s2ToMFHOffsetProperty().get(), 0.01);
        assertEquals(5.8, result.rotationDegreeProperty().get(), 0.01);
    }
}