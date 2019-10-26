# ImageFilter-upm-etsisi. Version 1

## Abstract
This library have several filters to process images in Android directly in Render Script (C99).
As developer than want to use filters, you can call easily via Java build Software pattern the 
different filters and concatenate these.
This library is part of TFG of Ramón Invarato for UPM University (ETSISI) <https://www.etsisi.upm.es/>


## Import last AAR
You can import last AAR compiled from `es-upm-etsisi-imagefilter.aar` in `finalAAR` folder.

In AndroidStudio you need to go to menu in `File` and select `/New/New Module…`. In the new Windows 
you need to choose `Import .JAR/.AAR Package` option and import AAR file to your project.

It is important check in `build.gradle` project level if was included this library (if not, you need 
to include as dependency):
```
dependencies {
    // ...
    implementation project(":es-upm-etsisi-imagefilter")
}
```

And in the top of global `settings.gradle` file, it is necessary to exists:
```
include ':app', ':es-upm-etsisi-imagefilter'
```


## Compile a new AAR
It is not necessary, only if you need to compile.
Execute the gradle command `build`. The new file AAR will build in `\build\outputs\aar\`.


## Mode of use
This library you can use with build Software pattern.

For initialice, you need to pass to constructor the context and the bitmap than you want to filter:
```
new Filtros(context, bitmap);
```

You can import a Bitman from a PNG in resources folder (example `res\drawable\lenna.png`) with:

```
Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.lenna);
```

Considering you are use this library from Activity class, you can get the context with `this` and 
use the library in this way:

```
Bitmap nbm = new Filtros(this, bm)
                .prewitt()
                .solarizar()
                .invertir()
                .getBitmapProcessed();
```
Here I have applied first `prewitt` filter, secondly `solarizar` filter and in last instance 
`invertir` filter filters. The build method is `getBitmapProcessed` than return the new bitmap
generated (`nbn`) after apply previous filters.


## Current filters
In this library you can use follow filters:
* blancoNegro
* blancoverdadero
* brillo
* canalAlpha
* complemento
* contraste
* correlation
* escalaDeGrises
* escalaDeGrisesMedia
* hsva
* invertir
* mediana
* mooreneighborhood
* negroverdadero
* posterizar
* prewitt
* sepia
* solarizar
* op_union
* op_division
* op_multiplicacion
* op_resta
* op_suma
* pasoBajo
* pasoAlto
* gradiente
* sobel
* roberts
* laplaciana

And you can use the follow built-in methods:
* escalaDeGrisesPALNTSC
* escalaDeGrisesHDTV
* escalaDeGrisesHDR
* blancoNegro
* quitarCanalAlpha
* matiz
* saturacion
* intensidad
* azulado
* realceLaplaciana
* suavizadoRoberts
