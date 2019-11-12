package es.upm.etsisi.imagefilter.check;

public class GausianBlurValue extends GeneralValues {
    @Override
    public float getMax() {
        return 25;
    }

    @Override
    public float getMin() {
        return 0;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public String getName() {
        return "Gausian Blur";
    }
}
