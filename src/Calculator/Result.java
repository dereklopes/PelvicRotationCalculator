package Calculator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ChoiceBox;

import java.util.HashMap;

class Result {
    // Unknowns
    private DoubleProperty focalFilmDistance;
    private DoubleProperty s2ToFilmLateral;
    private DoubleProperty s2ToMFHLateralFilm;
    private DoubleProperty s2ToMFHTrue;
    private DoubleProperty s2ToFilmAP;
    private DoubleProperty MFHToFilmTrue;
    private DoubleProperty s2ToMFHAPFilm;
    private DoubleProperty s2ToMFHOffset;
    private DoubleProperty rotationDegree;

    private static final double inToCm = 2.54;
    private static final double cmToIn = 0.393701;

    Result(double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm, double s2ToFilmAP,
           double s2ToMFHAPFilm, String focalFilmDistanceUnits, String s2ToFilmLateralUnits,
           String s2ToMFHLateralFilmUnits, String s2ToFilmAPUnits, String s2ToMFHAPFilmUnits, String resultsUnits) {
        // Get input and normalize units to cm
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        if (focalFilmDistanceUnits.equals("in")) {
            this.focalFilmDistance = new SimpleDoubleProperty(this.focalFilmDistance.get() * inToCm);
        }
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        if (s2ToFilmLateralUnits.equals("in")) {
            this.s2ToFilmLateral = new SimpleDoubleProperty(this.s2ToFilmLateral.get() * inToCm);
        }
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
        if (s2ToMFHLateralFilmUnits.equals("in")) {
            this.s2ToMFHLateralFilm = new SimpleDoubleProperty(this.s2ToMFHLateralFilm.get() * inToCm);
        }
        this.s2ToFilmAP = new SimpleDoubleProperty(s2ToFilmAP);
        if (s2ToFilmAPUnits.equals("in")) {
            this.s2ToFilmAP = new SimpleDoubleProperty(this.s2ToFilmAP.get() * inToCm);
        }
        this.s2ToMFHAPFilm = new SimpleDoubleProperty(s2ToMFHAPFilm);
        if (s2ToMFHAPFilmUnits.equals("in")) {
            this.s2ToMFHAPFilm = new SimpleDoubleProperty(this.s2ToMFHAPFilm.get() * inToCm);
        }

        // Calculate true S2 to MFH
        Double lateralSourceObject = s2ToFilmLateral - focalFilmDistance;
        Double lateralMagFactor = (lateralSourceObject + s2ToFilmLateral) / lateralSourceObject;
        this.s2ToMFHTrue = new SimpleDoubleProperty(s2ToMFHLateralFilm / lateralMagFactor);
        if (resultsUnits.equals("in")) {
            this.s2ToMFHTrue = new SimpleDoubleProperty(this.s2ToMFHTrue.get() * cmToIn);
        }

        // Calculate true MFH to film
        this.MFHToFilmTrue = new SimpleDoubleProperty(s2ToFilmAP + s2ToMFHTrue.doubleValue());
        if (resultsUnits.equals("in")) {
            this.MFHToFilmTrue = new SimpleDoubleProperty(this.MFHToFilmTrue.get() * cmToIn);
        }

        // Calculate rotational degree
        Double APSourceObject = focalFilmDistance + s2ToMFHTrue.doubleValue();
        Double APMagFactor = (APSourceObject + s2ToMFHTrue.doubleValue()) / APSourceObject;
        this.s2ToMFHOffset = new SimpleDoubleProperty(s2ToMFHAPFilm / APMagFactor);
        if (resultsUnits.equals("in")) {
            this.s2ToMFHOffset = new SimpleDoubleProperty(this.s2ToMFHOffset.get() * cmToIn);
        }
        this.rotationDegree = new SimpleDoubleProperty(
                StrictMath.asin(this.s2ToMFHOffset.doubleValue() / this.s2ToMFHTrue.doubleValue())
        );
        if (resultsUnits.equals("in")) {
            this.rotationDegree = new SimpleDoubleProperty(this.rotationDegree.get() * cmToIn);
        }
    }

    DoubleProperty focalFilmDistanceProperty() { return focalFilmDistance; }

    DoubleProperty s2ToFilmLateralProperty() { return s2ToFilmLateral; }

    DoubleProperty s2ToMFHLateralFilmProperty() { return s2ToMFHLateralFilm; }

    DoubleProperty s2ToMFHTrueProperty() { return s2ToMFHTrue; }

    DoubleProperty s2ToMFHAPFilmProperty() { return s2ToMFHAPFilm; }

    DoubleProperty s2ToFilmAPProperty() { return s2ToFilmAP; }

    DoubleProperty MFHToFilmTrueProperty() { return MFHToFilmTrue; }

    DoubleProperty s2ToMFHOffsetProperty() { return s2ToMFHOffset; }

    DoubleProperty rotationDegreeProperty() { return rotationDegree; }
}
