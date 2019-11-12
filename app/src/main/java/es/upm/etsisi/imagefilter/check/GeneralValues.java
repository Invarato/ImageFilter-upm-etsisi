package es.upm.etsisi.imagefilter.check;

public abstract class GeneralValues {
    public abstract float getMax();
    public abstract float getMin();
    public abstract boolean isInteger();
    public abstract String getName();

    public void checkValue(float value){
        if (this.isInteger()) {
            if ((int) this.getMax() < value || value < (int) this.getMin()){
                throw new IllegalArgumentException("Value Error. '" + this.getName() + "' threshold only in range "
                        + (int) this.getMax() + " >= value >= " + (int) this.getMin() + ". Current umbral: " + value);
            }
        } else {
            if (this.getMax() < value || value < this.getMin()){
                throw new IllegalArgumentException("Value Error. '" + this.getName() + "' threshold only in range "
                        + this.getMax() + " >= value >= " + this.getMin() + ". Current umbral: " + value);
            }
        }
    }

    public void checkValue(int value){
        if ((int) this.getMax() < value || value < (int) this.getMin()){
            throw new IllegalArgumentException("Value Error. '" + this.getName() + "' threshold only in range "
                    + (int) this.getMax() + " >= value >= " + (int) this.getMin() + ". Current umbral: " + value);
        }
    }

}