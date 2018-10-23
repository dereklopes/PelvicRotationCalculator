package Calculator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Result {
    // Unknowns
    private StringProperty name;
    private DoubleProperty focalFilmDistance;
    private DoubleProperty s2ToFilmLateral;
    private DoubleProperty s2ToMFHLateralFilm;
    DoubleProperty s2ToMFHTrue;
    private DoubleProperty s2ToFilmAP;
    private DoubleProperty MFHToFilmTrue;
    private DoubleProperty s2ToMFHAPFilm;
    private DoubleProperty s2ToMFHOffset;
    private DoubleProperty rotationDegree;

    Double lateralSourceObject;
    Double lateralMagFactor;
    Double APSourceObject;
    Double APMagFactor;

    private static final double inToCm = 2.54;
    private static final double cmToIn = 0.393701;

    Result(String name, double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm, double s2ToFilmAP,
           double s2ToMFHAPFilm, String focalFilmDistanceUnits, String s2ToFilmLateralUnits,
           String s2ToMFHLateralFilmUnits, String s2ToFilmAPUnits, String s2ToMFHAPFilmUnits, String resultsUnits) {
        this.name = new SimpleStringProperty(name);
        // Get input and normalize units to cm
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        if (focalFilmDistanceUnits.equals("in")) {
            this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * inToCm, 2));
        }
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        if (s2ToFilmLateralUnits.equals("in")) {
            this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * inToCm, 2));
        }
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
        if (s2ToMFHLateralFilmUnits.equals("in")) {
            this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * inToCm, 2));
        }
        this.s2ToFilmAP = new SimpleDoubleProperty(s2ToFilmAP);
        if (s2ToFilmAPUnits.equals("in")) {
            this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * inToCm, 2));
        }
        this.s2ToMFHAPFilm = new SimpleDoubleProperty(s2ToMFHAPFilm);
        if (s2ToMFHAPFilmUnits.equals("in")) {
            this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * inToCm, 2));
        }

        // Calculate true S2 to MFH
        this.lateralSourceObject = focalFilmDistance - s2ToFilmLateral;
        this.lateralMagFactor = round((lateralSourceObject + s2ToFilmLateral) / lateralSourceObject, 2);
        this.s2ToMFHTrue = new SimpleDoubleProperty(round(s2ToMFHLateralFilm / lateralMagFactor, 1));

        // Calculate true MFH to film
        System.out.println("this.s2ToFilmAP.get() = " + this.s2ToFilmAP.get());
        System.out.println("this.s2ToMFHTrue.get() = " + this.s2ToMFHTrue.get());
        this.MFHToFilmTrue = new SimpleDoubleProperty(this.s2ToFilmAP.get() + this.s2ToMFHTrue.get());

        // Calculate rotational degree
        System.out.println("focalFilmDistance = " + this.focalFilmDistance.get());
        System.out.println("this.MFHToFilmTrue = " + this.MFHToFilmTrue.get());
        this.APSourceObject = this.focalFilmDistance.get() - this.MFHToFilmTrue.get();
        this.APMagFactor = (APSourceObject + s2ToMFHTrue.doubleValue()) / APSourceObject;
        this.s2ToMFHOffset = new SimpleDoubleProperty(s2ToMFHAPFilm / APMagFactor);
        this.rotationDegree = new SimpleDoubleProperty(
                StrictMath.asin(this.s2ToMFHOffset.doubleValue() / this.s2ToMFHTrue.doubleValue())
        );
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    StringProperty nameProperty() { return name; }

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
