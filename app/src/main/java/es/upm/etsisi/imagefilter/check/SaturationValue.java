package es.upm.etsisi.imagefilter.check;

public class SaturationValue extends GeneralValues {
    @Override
    public float getMax() {
        return 1.0f;
    }

    @Override
    public float getMin() {
        return 0.0f;
    }

    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public String getName() {
        return "Saturation";
    }
}
