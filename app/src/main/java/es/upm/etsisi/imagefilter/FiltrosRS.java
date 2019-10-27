package es.upm.etsisi.imagefilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.renderscript.ScriptIntrinsicBlur;

import es.upm.etsisi.imagefilter.renders.ScriptC_blancoNegro;
import es.upm.etsisi.imagefilter.renders.ScriptC_blancoverdadero;
import es.upm.etsisi.imagefilter.renders.ScriptC_brillo;
import es.upm.etsisi.imagefilter.renders.ScriptC_canalAlpha;
import es.upm.etsisi.imagefilter.renders.ScriptC_complemento;
import es.upm.etsisi.imagefilter.renders.ScriptC_contraste;
import es.upm.etsisi.imagefilter.renders.ScriptC_correlation;
import es.upm.etsisi.imagefilter.renders.ScriptC_escalaDeGrises;
import es.upm.etsisi.imagefilter.renders.ScriptC_escalaDeGrisesMedia;
import es.upm.etsisi.imagefilter.renders.ScriptC_hsva;
import es.upm.etsisi.imagefilter.renders.ScriptC_invertir;
import es.upm.etsisi.imagefilter.renders.ScriptC_mediana;
import es.upm.etsisi.imagefilter.renders.ScriptC_mooreneighborhood;
import es.upm.etsisi.imagefilter.renders.ScriptC_negroverdadero;
import es.upm.etsisi.imagefilter.renders.ScriptC_posterizar;
import es.upm.etsisi.imagefilter.renders.ScriptC_prewitt;
import es.upm.etsisi.imagefilter.renders.ScriptC_sepia;
import es.upm.etsisi.imagefilter.renders.ScriptC_solarizar;
import es.upm.etsisi.imagefilter.renders.ScriptC_op_union;
import es.upm.etsisi.imagefilter.renders.ScriptC_op_division;
import es.upm.etsisi.imagefilter.renders.ScriptC_op_multiplicacion;
import es.upm.etsisi.imagefilter.renders.ScriptC_op_resta;
import es.upm.etsisi.imagefilter.renders.ScriptC_op_suma;
import es.upm.etsisi.imagefilter.renders.ScriptC_pasoBajo;
import es.upm.etsisi.imagefilter.renders.ScriptC_pasoAlto;
import es.upm.etsisi.imagefilter.renders.ScriptC_gradiente;
import es.upm.etsisi.imagefilter.renders.ScriptC_sobel;
import es.upm.etsisi.imagefilter.renders.ScriptC_roberts;
import es.upm.etsisi.imagefilter.renders.ScriptC_laplaciana;


public class FiltrosRS {

    private Allocation tmpAllocation;
    private RenderScript rs;
    private Bitmap bmRes;


    public FiltrosRS(Context context, Bitmap bmIn){
        this.bmRes = bmIn;

        this.rs = RenderScript.create(context);
        this.tmpAllocation = Allocation.createFromBitmap(rs, this.bmRes);
//        this.tmpAllocation = Allocation.createFromBitmap(rs, this.bmRes, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    }

    /***
     * Escala de grises con factores de multiplicación
     * @param modr 0.0 a 1.0 para el rojo
     * @param modg 0.0 a 1.0 para el verde
     * @param modb 0.0 a 1.0 para el azul
     * @return
     */
    public FiltrosRS escalaDeGrises(float modr, float modg, float modb){
        ScriptC_escalaDeGrises mEqScript = new ScriptC_escalaDeGrises(rs);
        mEqScript.set_modr(modr);
        mEqScript.set_modg(modg);
        mEqScript.set_modb(modb);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    /***
     * Escala de grises con factores por defecto con el método BT-601 (PAL/NTSC)
     *  modr = 0.30f
     *  modg = 0.59f
     *  modb = 0.11f
     * @return
     */
    public FiltrosRS escalaDeGrisesPAL(){
        return this.escalaDeGrises(0.30f,0.59f,0.11f);
    }

    /***
     * Escala de grises con factores por defecto con el método BT-709 (HDTV)
     *  modr = 0.21f
     *  modg = 0.72f
     *  modb = 0.07f
     * @return
     */
    public FiltrosRS escalaDeGrisesHDTV(){
        return this.escalaDeGrises(0.21f,0.72f,0.07f);
    }


    public FiltrosRS escalaDeGrisesMedia(){
        ScriptC_escalaDeGrisesMedia mEqScript = new ScriptC_escalaDeGrisesMedia(rs);
        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    public FiltrosRS solarizar(){
        ScriptC_solarizar mEqScript = new ScriptC_solarizar(rs);
        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        return this;
    }

    public FiltrosRS invertir(){
        ScriptC_invertir mEqScript = new ScriptC_invertir(rs);
        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        return this;
    }

    //TODO complemento e invertir son iguales
    public FiltrosRS complemento(){
        ScriptC_complemento mEqScript = new ScriptC_complemento(rs);
        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        return this;
    }


    public FiltrosRS prewitt(){
        ScriptC_prewitt mEqScript = new ScriptC_prewitt(rs);

        Allocation extraAllocation = this.getCopyOfTmpAllocation();
        mEqScript.set_extra_alloc(extraAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        extraAllocation.destroy();

        return this;
    }

    public FiltrosRS mediana(){
        ScriptC_mediana mEqScript = new ScriptC_mediana(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }


    public FiltrosRS pasoBajo(){
        ScriptC_pasoBajo mEqScript = new ScriptC_pasoBajo(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        return this;
    }

    public FiltrosRS pasoAlto(){
        ScriptC_pasoAlto mEqScript = new ScriptC_pasoAlto(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }

    public FiltrosRS gradiente(){
        ScriptC_gradiente mEqScript = new ScriptC_gradiente(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }

    public FiltrosRS sobel(){
        ScriptC_sobel mEqScript = new ScriptC_sobel(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }

    public FiltrosRS roberts(){
        ScriptC_roberts mEqScript = new ScriptC_roberts(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }

    public FiltrosRS laplaciana(){
        ScriptC_laplaciana mEqScript = new ScriptC_laplaciana(rs);
        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }


    public FiltrosRS negroVerdadero(){
        ScriptC_negroverdadero mEqScript = new ScriptC_negroverdadero(rs);

        Allocation extraAllocation = this.getCopyOfTmpAllocation();
        mEqScript.set_extra_alloc(extraAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        extraAllocation.destroy();

        return this;
    }

    public FiltrosRS correlation(){
        ScriptC_correlation mEqScript = new ScriptC_correlation(rs);

        Allocation extraAllocation = this.getCopyOfTmpAllocation();
        mEqScript.set_extra_alloc(extraAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        extraAllocation.destroy();

        return this;
    }

    public FiltrosRS blancoVerdadero(){
        ScriptC_blancoverdadero mEqScript = new ScriptC_blancoverdadero(rs);

        Allocation extraAllocation = this.getCopyOfTmpAllocation();
        mEqScript.set_extra_alloc(extraAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        extraAllocation.destroy();

        return this;
    }

    public FiltrosRS moreNeighborhood(){
        ScriptC_mooreneighborhood mEqScript = new ScriptC_mooreneighborhood(rs);

        Allocation extraAllocation = this.getCopyOfTmpAllocation();
//TODO        mEqScript.set_extra_alloc(extraAllocation);
//TODO
//TODO        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        extraAllocation.destroy();

        return this;
    }

//    public FiltrosRS moreNeighborhood(int umbral){
//        ScriptC_mooreneighborhood mEqScript = new ScriptC_mooreneighborhood(rs);
//        mEqScript.set_umbral(umbral);
//
//        mEqScript.invoke_process(tmpAllocation, tmpAllocation);
//        mEqScript.destroy();
//        return this;
//    }

    public FiltrosRS gausianBlur(float blurRadius) {
        //Set the radius of the Blur. Supported range 0 < blurRadius <= 25
        if (blurRadius == 0) {
            return this;
        } else {
            if (blurRadius <= 0 && 25 > blurRadius){
                throw new IllegalArgumentException("gausianBurFilter: Supported range 0 < blurRadius <= 25");
            }
        }

        ScriptIntrinsicBlur sib = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        sib.setRadius(blurRadius);
        sib.setInput(tmpAllocation);
        sib.forEach(tmpAllocation);

        sib.destroy();

        return this;
    }

    public FiltrosRS op_union(Bitmap otherBitmap){
        if (otherBitmap.getHeight()<this.bmRes.getHeight()
                ||  otherBitmap.getWidth()<this.bmRes.getWidth()){
            throw new IllegalArgumentException("New bitmap should be greather than current");
        }

        ScriptC_op_union mEqScript = new ScriptC_op_union(rs);

        Allocation otherAllocation = this.getOtherAllocation(otherBitmap);
        mEqScript.set_diferent_alloc(otherAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        otherAllocation.destroy();

        return this;
    }

    public FiltrosRS op_division(Bitmap otherBitmap){
        if (otherBitmap.getHeight()<this.bmRes.getHeight()
                ||  otherBitmap.getWidth()<this.bmRes.getWidth()){
            throw new IllegalArgumentException("New bitmap should be greather than current");
        }

        ScriptC_op_division mEqScript = new ScriptC_op_division(rs);

        Allocation otherAllocation = this.getOtherAllocation(otherBitmap);
        mEqScript.set_diferent_alloc(otherAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        otherAllocation.destroy();

        return this;
    }

    public FiltrosRS op_multiplicacion(Bitmap otherBitmap){
        if (otherBitmap.getHeight()<this.bmRes.getHeight()
                ||  otherBitmap.getWidth()<this.bmRes.getWidth()){
            throw new IllegalArgumentException("New bitmap should be greather than current");
        }

        ScriptC_op_multiplicacion mEqScript = new ScriptC_op_multiplicacion(rs);

        Allocation otherAllocation = this.getOtherAllocation(otherBitmap);
        mEqScript.set_diferent_alloc(otherAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        otherAllocation.destroy();

        return this;
    }

    public FiltrosRS op_resta(Bitmap otherBitmap){
        if (otherBitmap.getHeight()<this.bmRes.getHeight()
                ||  otherBitmap.getWidth()<this.bmRes.getWidth()){
            throw new IllegalArgumentException("New bitmap should be greather than current");
        }

        ScriptC_op_resta mEqScript = new ScriptC_op_resta(rs);

        Allocation otherAllocation = this.getOtherAllocation(otherBitmap);
        mEqScript.set_diferent_alloc(otherAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        otherAllocation.destroy();

        return this;
    }

    public FiltrosRS op_suma(Bitmap otherBitmap){
        if (otherBitmap.getHeight()<this.bmRes.getHeight()
                ||  otherBitmap.getWidth()<this.bmRes.getWidth()){
            throw new IllegalArgumentException("New bitmap should be greather than current");
        }

        ScriptC_op_suma mEqScript = new ScriptC_op_suma(rs);

        Allocation otherAllocation = this.getOtherAllocation(otherBitmap);
        mEqScript.set_diferent_alloc(otherAllocation);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();

        otherAllocation.destroy();

        return this;
    }

    /***
     * Escala de grises con factores de multiplicación
     * @param umbral 0 a 255
     * @return this
     */
    public FiltrosRS blancoNegro(int umbral){
        if (0 > umbral || umbral > 255){
            throw new IllegalArgumentException("Bad umbral. Umbral only in range " +
                    "0 <= umbral <= 255. Current umbral: " + umbral);
        }

        ScriptC_blancoNegro mEqScript = new ScriptC_blancoNegro(rs);
        mEqScript.set_umbral(umbral);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    /***
     * Establece un nuevo canal Alpha para toda la imagen
     * @param newAlpha 0 a 255
     * @return this
     */
    public FiltrosRS canalAlpha(int newAlpha){
        if (0 > newAlpha || newAlpha > 255){
            throw new IllegalArgumentException("Bad Alpha. Alpha only in range " +
                    "0 <= newAlpha <= 255. Current newAlpha: " + newAlpha);
        }

        ScriptC_canalAlpha mEqScript = new ScriptC_canalAlpha(rs);
        mEqScript.set_alpha(newAlpha);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    /***
     * Modifica el brillo por adicción
     *
     * @param addBrightness -255 a 255
     * @return this
     */
    public FiltrosRS brillo(int addBrightness){
        if (-255 > addBrightness || addBrightness > 255){
            throw new IllegalArgumentException("Bad Brightness. Brightness only in range " +
                    "0 <= Brightness <= 255. Current Brightness: " + addBrightness);
        }

        ScriptC_brillo mEqScript = new ScriptC_brillo(rs);
        mEqScript.set_brillo(addBrightness);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    /***
     * Modifica el contraste
     *
     * @param contrastAngle -255 a 255
     * @return this
     */
    public FiltrosRS contraste(int contrastAngle){
        if (-255 > contrastAngle || contrastAngle > 255){
            throw new IllegalArgumentException("Bad contrastAngle. contrastAngle only in range " +
                    "0 <= contrastAngle <= 255. Current contrastAngle: " + contrastAngle);
        }

        ScriptC_contraste mEqScript = new ScriptC_contraste(rs);
        mEqScript.set_anguloContraste(contrastAngle);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }

    /**
     * Posteriza el píxel según el umbral estimado, esto es que limita el número de colores del píxel.
     *
     * @param umbral 1 a 255
     * @return this
     */
    public FiltrosRS posterizar(int umbral){
        if (1 > umbral || umbral > 255){
            throw new IllegalArgumentException("Bad umbral. umbral only in range " +
                    "1 <= umbral <= 255. Current umbral: " + umbral);
        }

        ScriptC_posterizar mEqScript = new ScriptC_posterizar(rs);
        mEqScript.set_umbral(umbral);

        mEqScript.forEach_root(tmpAllocation, tmpAllocation);

        mEqScript.destroy();

        return this;
    }


    /**
     * Cambia los colores de un píxel de la imagen por otros con tonalidades sepias
     *
     * @return this
     */
    public FiltrosRS sepia(){
        ScriptC_sepia mEqScript = new ScriptC_sepia(rs);
        mEqScript.forEach_root(tmpAllocation, tmpAllocation);
        mEqScript.destroy();
        return this;
    }

    /***
     * Operaciones sobre HSVA: matiz, saturación, intensidad
     *
     * @param anguloMatiz 0.0f a 360.0f. No se aplica si < 0.0
     *  0.0f or 360.0f: red
     *  60.0f: yellow
     *  20.0f: green
     *  180.0f: cyan
     *  240.0f: blue
     *  300.0f: magenta.
     * @param saturation 0.0f a 1.0f. No se aplica si < 0.0
     * @param intensidad 0.0f a 1.0f. No se aplica si < 0.0
     * @return this
     */
    public FiltrosRS hsva(float anguloMatiz, float saturation, float intensidad){
        ScriptC_hsva mEqScript = new ScriptC_hsva(rs);
        mEqScript.set_anguloMatiz(anguloMatiz);
        mEqScript.set_nsaturacion(saturation);
        mEqScript.set_nintensidad(intensidad);

        mEqScript.invoke_process(tmpAllocation, tmpAllocation);

        mEqScript.destroy();
        return this;
    }


    // ==================================================================


    public Allocation getOtherAllocation(Bitmap otherBitmap) {
        /**
         * Obtiene una allocation (almacenamiento) extra con otra imagen
         * Normalmente se usa para copiar otra imagen y procesarlas en conjunto con la actual
         * Nota: hay que destruirla ( Allocation.destroy() ) después de usar
         */
        //Bitmap bmtmp = Bitmap.createBitmap(this.bmRes.getWidth(),
               // this.bmRes.getHeight(), this.bmRes.getConfig());
//        tmpAllocation.copyTo(bmtmp);
        return Allocation.createFromBitmap(this.rs, otherBitmap);
    }


    public Allocation getCopyOfTmpAllocation() {
        /**
         * Obtiene una allocation (almacenamiento) extra con la copia actual
         * Normalmente se usa para procesar sus píxeles con Render
         * Nota: hay que destruirla ( Allocation.destroy() ) después de usar
         */
        Bitmap bmtmp = Bitmap.createBitmap(this.bmRes.getWidth(),
                this.bmRes.getHeight(), this.bmRes.getConfig());
        tmpAllocation.copyTo(bmtmp);
        return Allocation.createFromBitmap(this.rs, bmtmp);
    }

    public Bitmap getBitmapProcessed(boolean rs_destroy) {
        tmpAllocation.copyTo(this.bmRes);

        if (rs_destroy) {
            this.destroyRenderScript();
        }

        return this.bmRes;
    }

    public Bitmap getBitmapProcessed() {
        return this.getBitmapProcessed(false);
    }

    public void destroyRenderScript(){
        rs.destroy();
        tmpAllocation.destroy();
    }
}