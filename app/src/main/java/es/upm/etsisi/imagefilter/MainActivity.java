package es.upm.etsisi.imagefilter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

//import es.upm.etsisi.imagefilter.Filtros;


public class MainActivity extends AppCompatActivity {

    private ImageView ivo;
    private ImageView ive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivo = findViewById(R.id.imageView_ejemploOriginal);
        ive = findViewById(R.id.imageView_ejemplo);

//        prueba(R.drawable.halftone);
//        prueba(R.drawable.valveoriginal);
        prueba(R.drawable.lenna);
//        prueba(R.drawable.nina);

        //pruebaTwo(R.drawable.valveoriginal, R.drawable.valvemod);
    }

//    private void cannyEdgeDetector(int rdrawalble){
//        ivo.setImageBitmap(BitmapFactory.decodeResource( getResources(), rdrawalble));
//
//        Bitmap bm = BitmapFactory.decodeResource( getResources(), rdrawalble);
//
//        // Suavizar imagen y eliminar ruido
//        Bitmap nbm = new FiltrosRS(this, bm)
//                .gausianBlur(10.5f)
//                .getBitmapProcessed();
//
//        // Localizar bordes con roberts, prewitt o sobel
//        FiltrosAntiguos filtro = new FiltrosAntiguos(nbm);
//        nbm = filtro.sobel(nbm);
//
//        ive.setImageBitmap(nbm);
//    }
//
//    private void pruebaTwo(int rdrawalble, int rdrawableTWO) {
//        ivo.setImageBitmap(BitmapFactory.decodeResource(getResources(), rdrawalble));
//
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), rdrawalble);
//        Bitmap bmTwo = BitmapFactory.decodeResource(getResources(), rdrawableTWO);
//
//        Bitmap nbm = new Filtros(this, bm)
//                .union(bmTwo)
//                .getBitmapProcessed();
//
//
//        ive.setImageBitmap(nbm);
//
//    }

    private void prueba(int rdrawalble) {
        ivo.setImageBitmap(BitmapFactory.decodeResource(getResources(), rdrawalble));

        Bitmap bm = BitmapFactory.decodeResource(getResources(), rdrawalble);

//        FiltrosAntiguos filtro = new FiltrosAntiguos(bm);
//        Bitmap nbm = filtro.solarizarImagen(bm);
////        Bitmap nbm = filtro.sepiaImagen(bm);
//        Bitmap nbm = filtro.prewitt(bm);
//        Bitmap nbm = filtro.pasoBajo(bm);
//        Bitmap nbm = filtro.pasoAlto(bm);
//        Bitmap nbm = filtro.gradiente(bm);
//        Bitmap nbm = filtro.sobel(bm);
//        Bitmap nbm = filtro.roberts(bm);
//        Bitmap nbm = filtro.laplaciana(bm);
//        Bitmap nbm = filtro.realceLaplaciana(bm);
//        Bitmap nbm = filtro.suavizadoRoberts(bm);
//        Bitmap nbm = filtro.freiChen(bm);


//
//        Bitmap nbm = new FiltrosRS(this, bm)
//                .histogramEqualization()
//                .gausianBlur(10.5f)
//                .solarizar()
//                .invertir()
//                .getBitmapProcessed();

//        Bitmap nbm = new FiltrosRS(this, bm)
//                .gausianBlur(10.5f)
//                .prewitt()
////                .histogramEqualization()
//                //.solarizar()
//                //.invertir()
//
//                .getBitmapProcessed();

//        Bitmap nbm = new FiltrosRS(this, bm)
//                .prewitt()
//                .getBitmapProcessed();

        //Bitmap nbm = new FiltrosRS(this, bm)
        //        .gausianBlur(10.5f)
        //        .prewitt()
        //        .negroVerdadero()
        //        .moreNeighborhood()
        //        .getBitmapProcessed();

//TODO        Bitmap nbm = new FiltrosRS(this, bm)
//TODO                .gausianBlur(10.5f)
//TODO                .prewitt()
//TODO                //.negroVerdadero()
//TODO                .moreNeighborhood()
//TODO//                .blancoVerdadero()
//TODO                //.histogramEqualization()
//TODO                .correlation()
//TODO                .getBitmapProcessed();

//        https://en.wikipedia.org/wiki/Thresholding_(image_processing)

        Bitmap nbm = new Filtros(this, bm)
//                //.escalaDeGrises(0.30f,0.59f,0.11f)
//                //.escalaDeGrisesMedia()
////                .escalaDeGrisesHDR()
////                .invertir()
////                .histogramEqualization()
////                .blancoNegro()
////                .matiz(360f)
////                .hsva(120f, 0.84f, 0.3f)
//                //.hsva(-120.0f, -1.0f, -1.0f)
////                .brillo(-120)
////                .contraste(200)
////                .posterizar(70)
////                .azulado()
////                .mediana()
////                .brillo(100)
////                .moreNeighborhood(10)
//////                .prewitt()
////                .sepia()
////                .solarizar()
//                .pasoBajo()
//                .mediana()
                //.pasoAlto()
//                .gradiente()
//                .sobel()
//                .roberts()
//                .laplaciana() // TODO no funciona correctamente
//                .realceLaplaciana()
//                .suavizadoRoberts()
                .blancoNegro()
//                .freichen() // TODO no funciona correctamente
                .getBitmapProcessed();


        ive.setImageBitmap(nbm);
    }
}
