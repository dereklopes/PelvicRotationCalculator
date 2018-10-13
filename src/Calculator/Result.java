package Calculator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

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

    Result(double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm, double s2ToFilmAP,
           double s2ToMFHAPFilm) {
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
        this.s2ToFilmAP = new SimpleDoubleProperty(s2ToFilmAP);
        this.s2ToMFHAPFilm = new SimpleDoubleProperty(s2ToMFHAPFilm);

        // Calculate true S2 to MFH
        Double lateralSourceObject = s2ToFilmLateral - focalFilmDistance;
        Double lateralMagFactor = (lateralSourceObject + s2ToFilmLateral) / lateralSourceObject;
        this.s2ToMFHTrue = new SimpleDoubleProperty(s2ToMFHLateralFilm / lateralMagFactor);

        // Calculate true MFH to film
        this.MFHToFilmTrue = new SimpleDoubleProperty(s2ToFilmAP + s2ToMFHTrue.doubleValue());

        // Calculate rotational degree
        Double APSourceObject = focalFilmDistance + s2ToMFHTrue.doubleValue();
        Double APMagFactor = (APSourceObject + s2ToMFHTrue.doubleValue()) / APSourceObject;
        this.s2ToMFHOffset = new SimpleDoubleProperty(s2ToMFHAPFilm / APMagFactor);
        this.rotationDegree = new SimpleDoubleProperty(
                StrictMath.asin(this.s2ToMFHOffset.doubleValue() / this.s2ToMFHTrue.doubleValue())
        );
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
