package es.upm.etsisi.imagefilter.check;

public class PosterizeValue extends GeneralValues {
    @Override
    public float getMax() {
        return 255;
    }

    @Override
    public float getMin() {
        return 1;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public String getName() {
        return "Posterize";
    }
}
