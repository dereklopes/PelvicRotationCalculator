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
    private DoubleProperty s2s2ToMFHOffset;
    private DoubleProperty s2RotationDegree;
    private DoubleProperty MFHs2ToMFHOffset;
    private DoubleProperty MFHRotationDegree;

    Double lateralSourceObject;
    Double lateralMagFactor;
    Double APSourceObject;
    Double s2APMagFactor;
    Double MFHAPMagFactor;

    private static final double inToCm = 2.54;
    private static final double mmToCm = 0.1;
    private static final double radToDeg = 180 / StrictMath.PI;

    Result(String name, double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm, double s2ToFilmAP,
           double s2ToMFHAPFilm, String focalFilmDistanceUnits, String s2ToFilmLateralUnits,
           String s2ToMFHLateralFilmUnits, String s2ToFilmAPUnits, String s2ToMFHAPFilmUnits, String resultsUnits) {
        this.name = new SimpleStringProperty(name);
        // Get input and normalize units to cm
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        if (focalFilmDistanceUnits.equals("in")) {
            this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * inToCm, 2));
        } else if (focalFilmDistanceUnits.equals("mm")) {
            this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * mmToCm, 2));
        }
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        if (s2ToFilmLateralUnits.equals("in")) {
            this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * inToCm, 2));
        } else if (s2ToFilmLateralUnits.equals("mm")) {
            this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * mmToCm, 2));
        }
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
        if (s2ToMFHLateralFilmUnits.equals("in")) {
            this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * inToCm, 2));
        } else if (s2ToMFHLateralFilmUnits.equals("mm")) {
            this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * mmToCm, 2));
        }
        this.s2ToFilmAP = new SimpleDoubleProperty(s2ToFilmAP);
        if (s2ToFilmAPUnits.equals("in")) {
            this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * inToCm, 2));
        } else if (s2ToFilmAPUnits.equals("mm")) {
            this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * mmToCm, 2));
        }
        this.s2ToMFHAPFilm = new SimpleDoubleProperty(s2ToMFHAPFilm);
        if (s2ToMFHAPFilmUnits.equals("in")) {
            this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * inToCm, 2));
        } else if (s2ToMFHAPFilmUnits.equals("mm")) {
            this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * mmToCm, 2));
        }

        // Calculate true S2 to MFH
        this.lateralSourceObject = focalFilmDistance - s2ToFilmLateral;
        this.lateralMagFactor = round((lateralSourceObject + s2ToFilmLateral) / lateralSourceObject, 2);
        this.s2ToMFHTrue = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() / lateralMagFactor, 2));

        // Calculate true MFH to film
        this.MFHToFilmTrue = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() + this.s2ToMFHTrue.get(), 2));

        // Calculate rotational degree with S2 as axis of rotation
        this.APSourceObject = this.focalFilmDistance.get() - this.MFHToFilmTrue.get();
        this.s2APMagFactor = round((APSourceObject + this.MFHToFilmTrue.get()) / APSourceObject, 2);
        this.s2s2ToMFHOffset = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() / s2APMagFactor, 2));
        double opOverHyp = round(this.s2s2ToMFHOffset.doubleValue() / this.s2ToMFHTrue.doubleValue(), 3);
        double rotationDegreeRads = StrictMath.asin(opOverHyp);
        this.s2RotationDegree = new SimpleDoubleProperty(round(rotationDegreeRads * radToDeg, 1));

        // Calculate rotational degree with MFH as axis of rotation
        this.MFHAPMagFactor = round(focalFilmDistance / (focalFilmDistance - s2ToFilmAP), 2);
        this.MFHs2ToMFHOffset = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() / this.MFHAPMagFactor, 2));
        opOverHyp = round(this.MFHs2ToMFHOffset.doubleValue() / this.s2ToMFHTrue.doubleValue(), 3);
        rotationDegreeRads = StrictMath.asin(opOverHyp);
        this.MFHRotationDegree = new SimpleDoubleProperty(round(rotationDegreeRads * radToDeg, 1));
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    StringProperty nameProperty() {
        return name;
    }

    DoubleProperty focalFilmDistanceProperty() {
        return focalFilmDistance;
    }

    DoubleProperty s2ToFilmLateralProperty() {
        return s2ToFilmLateral;
    }

    DoubleProperty s2ToMFHLateralFilmProperty() {
        return s2ToMFHLateralFilm;
    }

    DoubleProperty s2ToMFHTrueProperty() {
        return s2ToMFHTrue;
    }

    DoubleProperty s2ToMFHAPFilmProperty() {
        return s2ToMFHAPFilm;
    }

    DoubleProperty s2ToFilmAPProperty() {
        return s2ToFilmAP;
    }

    DoubleProperty MFHToFilmTrueProperty() {
        return MFHToFilmTrue;
    }

    DoubleProperty s2s2ToMFHOffsetProperty() {
        return s2s2ToMFHOffset;
    }

    DoubleProperty s2RotationDegreeProperty() {
        return s2RotationDegree;
    }

    DoubleProperty MFHs2ToMFHOffsetProperty() {
        return MFHs2ToMFHOffset;
    }

    DoubleProperty MFHRotationDegreeProperty() {
        return MFHRotationDegree;
    }
}
