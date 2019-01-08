package Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultTest {
    @Test
    void resultTest() {
        // Example from Research Paper
        String name = "test1";
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
        String resultsUnits = "cm";
        Result result = new Result(name, focalFilmDistance, s2ToFilmLateral, s2ToMFHLateralFilm, s2ToFilmAP,
                s2ToMFHAPFilm, focalFilmDistanceUnits, s2ToFilmLateralUnits, s2ToMFHLateralFilmUnits, s2ToFilmAPUnits,
                s2ToMFHAPFilmUnits, resultsUnits);

        assertEquals(170.18, result.lateralSourceObject, 0.01);
        assertEquals(1.07, result.lateralMagFactor, 0.01);
        assertEquals(9.35, result.s2ToMFHTrueProperty().get(), 0.01);
        assertEquals(19.51, result.MFHToFilmTrueProperty().get(), 0.01);
        assertEquals(163.37, result.APSourceObject, 0.01);
        assertEquals(1.12, result.s2APMagFactor, 0.01);
        assertEquals(.89, result.s2s2ToMFHOffsetProperty().get(), 0.01);
        assertEquals(5.5, result.s2RotationDegreeProperty().get(), 0.01);
        assertEquals(1.06, result.MFHAPMagFactor, 0.01);
        assertEquals(.94, result.MFHs2ToMFHOffsetProperty().get(), 0.01);
        assertEquals(5.8, result.MFHRotationDegreeProperty().get(), 0.01);

        // Example 2
        name = "test2";
        focalFilmDistance = 40;
        s2ToFilmLateral = 6;
        s2ToMFHLateralFilm = 110;
        s2ToFilmAP = 5;
        s2ToMFHAPFilm = 9;
        focalFilmDistanceUnits = "in";
        s2ToFilmLateralUnits = "in";
        s2ToMFHLateralFilmUnits =  "mm";
        s2ToFilmAPUnits = "in";
        s2ToMFHAPFilmUnits = "mm";
        resultsUnits = "cm";
        result = new Result(name, focalFilmDistance, s2ToFilmLateral, s2ToMFHLateralFilm, s2ToFilmAP,
                s2ToMFHAPFilm, focalFilmDistanceUnits, s2ToFilmLateralUnits, s2ToMFHLateralFilmUnits, s2ToFilmAPUnits,
                s2ToMFHAPFilmUnits, resultsUnits);
        assertEquals(4.3, result.s2RotationDegreeProperty().get(), 0.01);
    }
}