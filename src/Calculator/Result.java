package Calculator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

class Result {
    // Unknowns
    private DoubleProperty focalFilmDistance;
    private DoubleProperty s2ToFilmLateral;
    private DoubleProperty s2ToMFHLateralFilm;
    private DoubleProperty s2ToMFHTrue;
    private DoubleProperty s2ToMFHAPFilm;
    private DoubleProperty s2ToFilmAP;
    private DoubleProperty MFHToFilmTrue;
    private DoubleProperty S2ToMFHOffset;
    private DoubleProperty rotationDegree;

    // Calculations
    private Double magnificationFactor;

    Result(double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm) {
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
    }

    public double getFocalFilmDistance() {
        return focalFilmDistance.get();
    }

    public DoubleProperty focalFilmDistanceProperty() {
        return focalFilmDistance;
    }

    public void setFocalFilmDistance(double focalFilmDistance) {
        this.focalFilmDistance.set(focalFilmDistance);
    }

    public double getS2ToFilmLateral() {
        return s2ToFilmLateral.get();
    }

    public DoubleProperty s2ToFilmLateralProperty() {
        return s2ToFilmLateral;
    }

    public void setS2ToFilmLateral(double s2ToFilmLateral) {
        this.s2ToFilmLateral.set(s2ToFilmLateral);
    }

    public double getS2ToMFHLateralFilm() {
        return s2ToMFHLateralFilm.get();
    }

    public DoubleProperty s2ToMFHLateralFilmProperty() {
        return s2ToMFHLateralFilm;
    }

    public void setS2ToMFHLateralFilm(double s2ToMFHLateralFilm) {
        this.s2ToMFHLateralFilm.set(s2ToMFHLateralFilm);
    }

    public double getS2ToMFHTrue() {
        return s2ToMFHTrue.get();
    }

    public DoubleProperty s2ToMFHTrueProperty() {
        return s2ToMFHTrue;
    }

    public void setS2ToMFHTrue(double s2ToMFHTrue) {
        this.s2ToMFHTrue.set(s2ToMFHTrue);
    }

    public double getS2ToMFHAPFilm() {
        return s2ToMFHAPFilm.get();
    }

    public DoubleProperty s2ToMFHAPFilmProperty() {
        return s2ToMFHAPFilm;
    }

    public void setS2ToMFHAPFilm(double s2ToMFHAPFilm) {
        this.s2ToMFHAPFilm.set(s2ToMFHAPFilm);
    }

    public double getS2ToFilmAP() {
        return s2ToFilmAP.get();
    }

    public DoubleProperty s2ToFilmAPProperty() {
        return s2ToFilmAP;
    }

    public void setS2ToFilmAP(double s2ToFilmAP) {
        this.s2ToFilmAP.set(s2ToFilmAP);
    }

    public double getMFHToFilmTrue() {
        return MFHToFilmTrue.get();
    }

    public DoubleProperty MFHToFilmTrueProperty() {
        return MFHToFilmTrue;
    }

    public void setMFHToFilmTrue(double MFHToFilmTrue) {
        this.MFHToFilmTrue.set(MFHToFilmTrue);
    }

    public double getS2ToMFHOffset() {
        return S2ToMFHOffset.get();
    }

    public DoubleProperty s2ToMFHOffsetProperty() {
        return S2ToMFHOffset;
    }

    public void setS2ToMFHOffset(double s2ToMFHOffset) {
        this.S2ToMFHOffset.set(s2ToMFHOffset);
    }

    public double getRotationDegree() {
        return rotationDegree.get();
    }

    public DoubleProperty rotationDegreeProperty() {
        return rotationDegree;
    }

    public void setRotationDegree(double rotationDegree) {
        this.rotationDegree.set(rotationDegree);
    }

    public Double getMagnificationFactor() {
        return magnificationFactor;
    }

    public void setMagnificationFactor(Double magnificationFactor) {
        this.magnificationFactor = magnificationFactor;
    }
}
