package es.upm.etsisi.imagefilter;

import android.content.Context;
import android.graphics.Bitmap;

//https://developer.android.com/studio/projects/android-library?hl=es-419

public class Filtros extends FiltrosRS {

    /**
     * Clase intermedia para ofrecer posibilidades preconstruidas
     *
     * @param context Context de Android
     * @param bmIn Bitmap al que aplicar filtros
     */
    public Filtros(Context context, Bitmap bmIn) {
        super(context, bmIn);
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
    public FiltrosRS escalaDeGrisesPALNTSC(){
        return this.escalaDeGrises(0.299f,0.587f,0.114f);
    }

    /**
     * Escala de grises con factores por defecto con el método BT.709 (HDTV)
     *  modr = 0.2126f
     *  modg = 0.7152f
     *  modb = 0.0722f
     *  Más información en https://en.wikipedia.org/wiki/Grayscale
     * @return this
     */
    public FiltrosRS escalaDeGrisesHDTV(){
        return this.escalaDeGrises(0.2126f,0.7152f,0.0722f);
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
    public FiltrosRS escalaDeGrisesHDR(){
        return this.escalaDeGrises(0.2627f,0.6780f,0.0593f);
    }

    /**
     * Blanco y negro a mitad de valor
     *  umbral = 128
     *
     * @return this
     */
    public FiltrosRS blancoNegro(){
        return this.blancoNegro(128);
    }

    /**
     * Pasa el canal Alpha al máximo de su valor
     *  newAlpha = 255
     *
     * @return this
     */
    public FiltrosRS quitarCanalAlpha(){
        return this.canalAlpha(255);
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
    public FiltrosRS matiz(float anguloMatiz){
        if (0.0 > anguloMatiz || anguloMatiz > 360.0){
            throw new IllegalArgumentException("Bad Angle. Angle only in range " +
                    "0.0 <= Angle <= 360.0. Current Angle: " + anguloMatiz);
        }
        return this.hsva(anguloMatiz, -1.0f, -1.0f);
    }

    /**
     * Saturación
     *
     * Es una operación de HSVA. Para más de una operación es más óptimo si se utiliza hsva()
     *
     * @param newSaturation 0.0f a 1.0f
     * @return this
     */
    public FiltrosRS saturacion(float newSaturation){
        if (0.0 > newSaturation || newSaturation > 1.0){
            throw new IllegalArgumentException("Bad saturacion. Saturacion only in range " +
                    "0.0 <= Saturacion <= 1.0. Current Saturacion: " + newSaturation);
        }
        return this.hsva(-1.0f, newSaturation, -1.0f);
    }

    /**
     * Intensidad
     *
     * Es una operación de HSVA. Para más de una operación es más óptimo si se utiliza hsva()
     *
     * @param newIntensity 0.0f a 1.0f
     * @return this
     */
    public FiltrosRS intensidad(float newIntensity){
        if (0.0 > newIntensity || newIntensity > 1.0){
            throw new IllegalArgumentException("Bad intensity. Intensity only in range " +
                    "0.0 <= Intensity <= 1.0. Current Intensity: " + newIntensity);
        }
        return this.hsva(-1.0f, -1.0f, newIntensity);
    }

    /**
     * Cambia los colores de un píxel de la imagen por otros con tonalidades azuladas.
     *
     * @return this
     */
    public FiltrosRS azulado(){
        return this.invertir().sepia().invertir();
    }

}