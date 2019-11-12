package es.upm.etsisi.imagefilter;

import android.content.Context;
import android.graphics.Bitmap;

import es.upm.etsisi.imagefilter.check.IntensityValue;
import es.upm.etsisi.imagefilter.check.MatizValue;
import es.upm.etsisi.imagefilter.check.SaturationValue;

//https://developer.android.com/studio/projects/android-library?hl=es-419

public class Filtros extends FiltrosRS {

    private Context context;

    /**
     * Clase intermedia para ofrecer posibilidades preconstruidas
     *
     * @param context Context de Android
     * @param bmIn Bitmap al que aplicar filtros
     */
    public Filtros(Context context, Bitmap bmIn) {
        super(context, bmIn);
        this.context = context;
    }

    /**
     * Escala de grises con factores por defecto con el método BT.601 (PAL/NTSC)
     *  modr = 0.299f
     *  modg = 0.587f
     *  modb = 0.114f
     * Más información en https://en.wikipedia.org/wiki/Grayscale
     *
     * @return this
     */
    public Filtros escalaDeGrisesPALNTSC(){
        this.escalaDeGrises(0.299f,0.587f,0.114f);
        return this;
    }

    /**
     * Escala de grises con factores por defecto con el método BT.709 (HDTV)
     *  modr = 0.2126f
     *  modg = 0.7152f
     *  modb = 0.0722f
     *  Más información en https://en.wikipedia.org/wiki/Grayscale
     * @return this
     */
    public Filtros escalaDeGrisesHDTV(){
        this.escalaDeGrises(0.2126f,0.7152f,0.0722f);
        return this;
    }

    /**
     * Escala de grises con factores por defecto con el método BT.2100 (HDR)
     *  modr = 0.2627f
     *  modg = 0.6780f
     *  modb = 0.0593f
     * Más información en https://en.wikipedia.org/wiki/Grayscale
     *
     * @return this
     */
    public Filtros escalaDeGrisesHDR(){
        this.escalaDeGrises(0.2627f,0.6780f,0.0593f);
        return this;
    }

    /**
     * Blanco y negro a mitad de valor
     *  umbral = 128
     *
     * @return this
     */
    public Filtros blancoNegro(){
        this.blancoNegro(128);
        return this;
    }

    /**
     * Pasa el canal Alpha al máximo de su valor
     *  newAlpha = 255
     *
     * @return this
     */
    public Filtros quitarCanalAlpha(){
        this.canalAlpha(255);
        return this;
    }


    /**
     * Matiz
     *
     * Es una operación de HSVA. Para más de una operación es más óptimo si se utiliza hsva()
     *
     * @param anguloMatiz 0.0f a 360.0f
     *  0.0f or 360.0f: red
     *  60.0f: yellow
     *  20.0f: green
     *  180.0f: cyan
     *  240.0f: blue
     *  300.0f: magenta.
     * @return this
     */
    public Filtros matiz(float anguloMatiz){
        new MatizValue().checkValue(anguloMatiz);

        this.hsva(anguloMatiz, -1.0f, -1.0f);
        return this;
    }

    /**
     * Saturación
     *
     * Es una operación de HSVA. Para más de una operación es más óptimo si se utiliza hsva()
     *
     * @param newSaturation 0.0f a 1.0f
     * @return this
     */
    public Filtros saturacion(float newSaturation){
        new SaturationValue().checkValue(newSaturation);

        this.hsva(-1.0f, newSaturation, -1.0f);
        return this;
    }

    /**
     * Intensidad
     *
     * Es una operación de HSVA. Para más de una operación es más óptimo si se utiliza hsva()
     *
     * @param newIntensity 0.0f a 1.0f
     * @return this
     */
    public Filtros intensidad(float newIntensity){
        new IntensityValue().checkValue(newIntensity);

        this.hsva(-1.0f, -1.0f, newIntensity);
        return this;
    }

    /**
     * Cambia los colores de un píxel de la imagen por otros con tonalidades azuladas.
     *
     * @return this
     */
    public Filtros azulado(){
        this.invertir().sepia().invertir();
        return this;
    }

    public Filtros realceLaplaciana(){
        Bitmap bmRes = this.getBitmapProcessed();
        this.laplaciana().op_resta(bmRes);
        return this;
    }

    public Filtros suavizadoRoberts(){
        Bitmap bmRes = this.getBitmapProcessed();
        this.roberts().op_suma(bmRes);
        return this;
    }

}