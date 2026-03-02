package Calculator;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

class Result implements Serializable {
    private transient StringProperty name;
    private transient StringProperty resultUnits;
    private transient DoubleProperty focalFilmDistance;
    private transient DoubleProperty s2ToFilmLateral;
    private transient DoubleProperty s2ToMFHLateralFilm;
    private transient DoubleProperty s2ToMFHTrue;
    private transient DoubleProperty s2ToFilmAP;
    private transient DoubleProperty MFHToFilmTrue;
    private transient DoubleProperty s2ToMFHAPFilm;
    private transient DoubleProperty s2s2ToMFHOffset;
    private transient DoubleProperty s2RotationDegree;
    private transient DoubleProperty MFHs2ToMFHOffset;
    private transient DoubleProperty MFHRotationDegree;

    private String focalFilmDistanceUnits;
    private String s2ToFilmLateralUnits;
    private String s2ToMFHLateralFilmUnits;
    private String s2ToFilmAPUnits;
    private String s2ToMFHAPFilmUnits;

    Double lateralSourceObject;
    Double lateralMagFactor;
    Double APSourceObject;
    Double s2APMagFactor;
    Double MFHAPMagFactor;

    private static final double inToCm = 2.54;
    private static final double mmToCm = 0.1;
    private static final double cmToIn = 0.393701;
    private static final double mmToIn = 0.0393701;
    private static final double cmToMm = 10;
    private static final double inToMm = 25.4;
    private static final double radToDeg = 180 / StrictMath.PI;

    Result(String name, double focalFilmDistance, double s2ToFilmLateral, double s2ToMFHLateralFilm, double s2ToFilmAP,
           double s2ToMFHAPFilm, String focalFilmDistanceUnits, String s2ToFilmLateralUnits,
           String s2ToMFHLateralFilmUnits, String s2ToFilmAPUnits, String s2ToMFHAPFilmUnits, String resultsUnits) {
        this.name = new SimpleStringProperty(name);
        this.resultUnits = new SimpleStringProperty(resultsUnits);

        // Get input
        this.focalFilmDistance = new SimpleDoubleProperty(focalFilmDistance);
        this.s2ToFilmLateral = new SimpleDoubleProperty(s2ToFilmLateral);
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty(s2ToMFHLateralFilm);
        this.s2ToFilmAP = new SimpleDoubleProperty(s2ToFilmAP);
        this.s2ToMFHAPFilm = new SimpleDoubleProperty(s2ToMFHAPFilm);

        // Initialize units and normalize to results units
        this.focalFilmDistanceUnits = focalFilmDistanceUnits;
        this.s2ToFilmLateralUnits = s2ToFilmLateralUnits;
        this.s2ToMFHLateralFilmUnits = s2ToMFHLateralFilmUnits;
        this.s2ToFilmAPUnits = s2ToFilmAPUnits;
        this.s2ToMFHAPFilmUnits = s2ToMFHAPFilmUnits;
        this.normalize(resultsUnits);

        // Calculate true S2 to MFH
        this.lateralSourceObject = this.focalFilmDistance.get() - this.s2ToFilmLateral.get();
        this.lateralMagFactor = round((this.lateralSourceObject + this.s2ToFilmLateral.get()) / lateralSourceObject, 2);
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
        this.MFHAPMagFactor = round(this.focalFilmDistance.get() / (this.focalFilmDistance.get() - this.s2ToFilmAP.get()), 2);
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

    private void normalize(String units) {
        switch (units) {
            case "in":
                if (focalFilmDistanceUnits.equals("cm")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * cmToIn, 2));
                } else if (focalFilmDistanceUnits.equals("mm")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * mmToIn, 2));
                }
                if (s2ToFilmLateralUnits.equals("cm")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * cmToIn, 2));
                } else if (s2ToFilmLateralUnits.equals("mm")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * mmToIn, 2));
                }
                if (s2ToMFHLateralFilmUnits.equals("cm")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * cmToIn, 2));
                } else if (s2ToMFHLateralFilmUnits.equals("mm")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * mmToIn, 2));
                }
                if (s2ToFilmAPUnits.equals("cm")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * cmToIn, 2));
                } else if (s2ToFilmAPUnits.equals("mm")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * mmToIn, 2));
                }
                if (s2ToMFHAPFilmUnits.equals("cm")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * cmToIn, 2));
                } else if (s2ToMFHAPFilmUnits.equals("mm")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * mmToIn, 2));
                }
                break;
            case "cm":
                if (focalFilmDistanceUnits.equals("in")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * inToCm, 2));
                } else if (focalFilmDistanceUnits.equals("mm")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * mmToCm, 2));
                }
                if (s2ToFilmLateralUnits.equals("in")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * inToCm, 2));
                } else if (s2ToFilmLateralUnits.equals("mm")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * mmToCm, 2));
                }
                if (s2ToMFHLateralFilmUnits.equals("in")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * inToCm, 2));
                } else if (s2ToMFHLateralFilmUnits.equals("mm")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * mmToCm, 2));
                }
                if (s2ToFilmAPUnits.equals("in")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * inToCm, 2));
                } else if (s2ToFilmAPUnits.equals("mm")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * mmToCm, 2));
                }
                if (s2ToMFHAPFilmUnits.equals("in")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * inToCm, 2));
                } else if (s2ToMFHAPFilmUnits.equals("mm")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * mmToCm, 2));
                }
                break;
            case "mm":
                if (focalFilmDistanceUnits.equals("cm")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * cmToMm, 2));
                } else if (focalFilmDistanceUnits.equals("in")) {
                    this.focalFilmDistance = new SimpleDoubleProperty(round(this.focalFilmDistance.get() * inToMm, 2));
                }
                if (s2ToFilmLateralUnits.equals("cm")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * cmToMm, 2));
                } else if (s2ToFilmLateralUnits.equals("in")) {
                    this.s2ToFilmLateral = new SimpleDoubleProperty(round(this.s2ToFilmLateral.get() * inToMm, 2));
                }
                if (s2ToMFHLateralFilmUnits.equals("cm")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * cmToMm, 2));
                } else if (s2ToMFHLateralFilmUnits.equals("in")) {
                    this.s2ToMFHLateralFilm = new SimpleDoubleProperty(round(this.s2ToMFHLateralFilm.get() * inToMm, 2));
                }
                if (s2ToFilmAPUnits.equals("cm")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * cmToMm, 2));
                } else if (s2ToFilmAPUnits.equals("in")) {
                    this.s2ToFilmAP = new SimpleDoubleProperty(round(this.s2ToFilmAP.get() * inToMm, 2));
                }
                if (s2ToMFHAPFilmUnits.equals("cm")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * cmToMm, 2));
                } else if (s2ToMFHAPFilmUnits.equals("in")) {
                    this.s2ToMFHAPFilm = new SimpleDoubleProperty(round(this.s2ToMFHAPFilm.get() * inToMm, 2));
                }
                break;
        }

    }

    StringProperty nameProperty() {
        return name;
    }

    StringProperty resultUnitsProperty() {
        return resultUnits;
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

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.defaultWriteObject();
        oos.writeObject(this.name.get());
        oos.writeObject(this.resultUnits.get());
        oos.writeObject(this.focalFilmDistance.get());
        oos.writeObject(this.s2ToFilmLateral.get());
        oos.writeObject(this.s2ToMFHLateralFilm.get());
        oos.writeObject(this.s2ToMFHTrue.get());
        oos.writeObject(this.s2ToMFHAPFilm.get());
        oos.writeObject(this.s2ToFilmAP.get());
        oos.writeObject(this.MFHToFilmTrue.get());
        oos.writeObject(this.s2s2ToMFHOffset.get());
        oos.writeObject(this.s2RotationDegree.get());
        oos.writeObject(this.MFHs2ToMFHOffset.get());
        oos.writeObject(this.MFHRotationDegree.get());
    }

    private void readObject(ObjectInputStream ois) throws Exception {
        ois.defaultReadObject();
        this.name = new SimpleStringProperty((String)ois.readObject());
        this.resultUnits = new SimpleStringProperty((String)ois.readObject());
        this.focalFilmDistance = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2ToFilmLateral = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2ToMFHLateralFilm = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2ToMFHTrue = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2ToMFHAPFilm = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2ToFilmAP = new SimpleDoubleProperty((Double)ois.readObject());
        this.MFHToFilmTrue = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2s2ToMFHOffset = new SimpleDoubleProperty((Double)ois.readObject());
        this.s2RotationDegree = new SimpleDoubleProperty((Double)ois.readObject());
        this.MFHs2ToMFHOffset = new SimpleDoubleProperty((Double)ois.readObject());
        this.MFHRotationDegree = new SimpleDoubleProperty((Double)ois.readObject());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(this.name.get());
        sb.append("\n");
        sb.append("Result Units: ");
        sb.append(this.resultUnits.get());
        sb.append("\n");
        sb.append("Focal Film Distance: ");
        sb.append(this.focalFilmDistance.get());
        sb.append("\n");
        sb.append("S2 To Film Lateral: ");
        sb.append(this.s2ToFilmLateral.get());
        sb.append("\n");
        sb.append("S2 To MFH Lateral Film: ");
        sb.append(this.s2ToMFHLateralFilm.get());
        sb.append("\n");
        sb.append("S2 To MFH True: ");
        sb.append(this.s2ToMFHTrue.get());
        sb.append("\n");
        sb.append("S2 To MFH AP Film: ");
        sb.append(this.s2ToMFHAPFilm.get());
        sb.append("\n");
        sb.append("S2 To Film AP: ");
        sb.append(this.s2ToFilmAP.get());
        sb.append("\n");
        sb.append("MFH To Film True: ");
        sb.append(this.MFHToFilmTrue.get());
        sb.append("\n");
        sb.append("S2 To MFH Offset (S2): ");
        sb.append(this.s2s2ToMFHOffset.get());
        sb.append("\n");
        sb.append("Rotation Degree (S2): ");
        sb.append(this.s2RotationDegree.get());
        sb.append("\n");
        sb.append("S2 To MFH Offset (MFH): ");
        sb.append(this.MFHs2ToMFHOffset.get());
        sb.append("\n");
        sb.append("Rotation Degree (MFH): ");
        sb.append(this.MFHRotationDegree.get());
        return sb.toString();
    }
}
