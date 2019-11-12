package es.upm.etsisi.imagefilter.check;

public class MatizValue extends GeneralValues {
    @Override
    public float getMax() {
        return 360.0f;
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
        return "Matiz";
    }
}
