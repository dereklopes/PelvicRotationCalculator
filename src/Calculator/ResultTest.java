package Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.*;

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

	@Test
	void resultTest2() {
		String name = "test2";
		double focalFilmDistance = 84;
		String focalFilmDistanceUnits = "in";
		double s2ToFilmLateral = 250.44;
		String s2ToFilmLateralUnits = "mm";
		double s2ToMFHLateralFilm = 93.67;
		String s2ToMFHLateralFilmUnits = "mm";
		double s2ToFilmAP = 149.65;
		String s2ToFilmAPUnits = "mm";
		double s2ToMFHAPFilm = 3.92;
		String s2ToMFHAPFilmUnits = "mm";
		String resultsUnits = "mm";
		Result result = new Result(name, focalFilmDistance, s2ToFilmLateral, s2ToMFHLateralFilm, s2ToFilmAP,
			s2ToMFHAPFilm, focalFilmDistanceUnits, s2ToFilmLateralUnits, s2ToMFHLateralFilmUnits, s2ToFilmAPUnits,
			s2ToMFHAPFilmUnits, resultsUnits);

		assertEquals(250.44, result.s2ToFilmLateralProperty().get(), 0.01);
		assertEquals(82.89, result.s2ToMFHTrueProperty().get(), 0.01);
		assertEquals(3.5, result.s2s2ToMFHOffsetProperty().get(), 0.01);
		assertEquals(2.4, result.s2RotationDegreeProperty().get(), 0.01);
		assertEquals(3.63, result.MFHs2ToMFHOffsetProperty().get(), 0.01);
		assertEquals(2.5, result.MFHRotationDegreeProperty().get(), 0.01);
	}

    @Test
    void saveAndLoadTest() {
        // Example from Research Paper
        String name = "saveAndLoadTest";
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
        File testFile = new File("testFile.tmp");
        try {
            assertTrue(testFile.createNewFile()); // if file already exists will do nothing
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        assertNotNull(testFile);
        Result loadedResult = null;
        try {
            FileOutputStream fos = new FileOutputStream(testFile, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(result);
            fos.close();
            oos.close();

            FileInputStream fis = new FileInputStream(testFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            loadedResult = (Result)ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(loadedResult);
        assertEquals(170.18, loadedResult.lateralSourceObject, 0.01);
        assertEquals(1.07, loadedResult.lateralMagFactor, 0.01);
        assertEquals(9.35, loadedResult.s2ToMFHTrueProperty().get(), 0.01);
        assertEquals(19.51, loadedResult.MFHToFilmTrueProperty().get(), 0.01);
        assertEquals(163.37, loadedResult.APSourceObject, 0.01);
        assertEquals(1.12, loadedResult.s2APMagFactor, 0.01);
        assertEquals(.89, loadedResult.s2s2ToMFHOffsetProperty().get(), 0.01);
        assertEquals(5.5, loadedResult.s2RotationDegreeProperty().get(), 0.01);
        assertEquals(1.06, loadedResult.MFHAPMagFactor, 0.01);
        assertEquals(.94, loadedResult.MFHs2ToMFHOffsetProperty().get(), 0.01);
        assertEquals(5.8, loadedResult.MFHRotationDegreeProperty().get(), 0.01);

        // cleanup
        assertTrue(testFile.delete());
    }
}