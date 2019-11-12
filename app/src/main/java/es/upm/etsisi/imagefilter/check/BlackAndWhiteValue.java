package es.upm.etsisi.imagefilter.check;

public class BlackAndWhiteValue extends GeneralValues {
    @Override
    public float getMax() {
        return 255;
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
        return "Black and White";
    }
}
