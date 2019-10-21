#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

rs_allocation extra_alloc;

void init() {
    rsDebug("===========init2==================",0);
    //Non-maximum suppression
    //https://en.wikipedia.org/wiki/Edge_detection#Edge_thinning
    //https://en.wikipedia.org/wiki/Moore_neighborhood
    //https://users.fmrib.ox.ac.uk/~steve/susan/thinning/node2.html
}

static bool pixelConContenido(uchar4 pixel){
    int res = 0;
    if (pixel.r > 0 && pixel.g > 0 && pixel.b > 0) {
        res = 1;
    }
    return res;
}

static uchar4 meanTwoPixels(uchar4 pixelA, uchar4 pixelB){
    uchar4 out = pixelA;

    out.r = (int) ((pixelA.r + pixelB.r)/2);
    out.g = (int) ((pixelA.g + pixelB.g)/2);
    out.b = (int) ((pixelA.b + pixelB.b)/2);
    out.a = (int) ((pixelA.a + pixelB.a)/2);

    return out;
}

static uchar4 meanTreePixels(uchar4 pixelA, uchar4 pixelB, uchar4 pixelC){
    uchar4 out = pixelA;

    out.r = (int) ((pixelA.r + pixelB.r + pixelC.r)/3);
    out.g = (int) ((pixelA.g + pixelB.g + pixelC.g)/3);
    out.b = (int) ((pixelA.b + pixelB.b + pixelC.b)/3);
    out.a = (int) ((pixelA.a + pixelB.a + pixelC.a)/3);

    return out;
}

// x vertical, y horizontal
uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

    if (x == 0) {
        x = 1;
    }

    if (y == 0) {
        y = 1;
    }

    const uchar4 nl = rsGetElementAt_uchar4(extra_alloc, x, y-1);
    const uchar4 nr = rsGetElementAt_uchar4(extra_alloc, x, y+1);

    const uchar4 nu = rsGetElementAt_uchar4(extra_alloc, x-1, y);
    const uchar4 nd = rsGetElementAt_uchar4(extra_alloc, x+1, y);

    const uchar4 nlu = rsGetElementAt_uchar4(extra_alloc, x-1, y-1);
    const uchar4 nru = rsGetElementAt_uchar4(extra_alloc, x-1, y+1);

    const uchar4 nld = rsGetElementAt_uchar4(extra_alloc, x+1, y-1);
    const uchar4 nrd = rsGetElementAt_uchar4(extra_alloc, x+1, y+1);

    int vecinos = pixelConContenido(nl)+pixelConContenido(nr)+pixelConContenido(nu)+pixelConContenido(nd)+pixelConContenido(nlu)+pixelConContenido(nru)+pixelConContenido(nld)+pixelConContenido(nrd);
    rsDebug("vecinos:",vecinos);
    switch(vecinos) {
        case 0:
            out.r = 0;
            out.g = 0;
            out.b = 0;
            out.a = 255;
            break;
        case 1:

            break;
        case 2:
            if (pixelConContenido(nl) + pixelConContenido(nr) == 2){
                // Si izquierda y derecha con contenido, yo me calculo la media de ambos
                out = meanTwoPixels(nl, nr);
            } else if (pixelConContenido(nu) + pixelConContenido(nd) == 2){
                // Si arriba y abajo con contenido, yo me calculo la media de ambos
                out = meanTwoPixels(nu, nd);
            } else if (pixelConContenido(nlu) + pixelConContenido(nrd) == 2){
                // Si arriba izquierda y abajo derecha con contenido, yo me calculo la media de ambos
                out = meanTwoPixels(nlu, nrd);
            } else if (pixelConContenido(nru) + pixelConContenido(nld) == 2){
                // Si arriba derecha y abajo izquierda con contenido, yo me calculo la media de ambos
                out = meanTwoPixels(nru, nld);
            } else if (pixelConContenido(nl) + pixelConContenido(nu) == 2
                        || pixelConContenido(nl) + pixelConContenido(nd) == 2
                        || pixelConContenido(nr) + pixelConContenido(nu) == 2
                        || pixelConContenido(nr) + pixelConContenido(nd) == 2 ){
                // Si en L (por ejemplo, abajo y a la izquierda), me quito para suavizar la recta
                out.r = 0;
                out.g = 0;
                out.b = 0;
                out.a = 255;
            } else if (pixelConContenido(nru) + pixelConContenido(nlu) == 2
                        || pixelConContenido(nrd) + pixelConContenido(nld) == 2
                        || pixelConContenido(nru) + pixelConContenido(nrd) == 2
                        || pixelConContenido(nld) + pixelConContenido(nlu) == 2 ){
                // Si en < (por ejemplo, arriba a la derecha y abajo a la derecha), me quito porque es ruido
                out.r = 0;
                out.g = 0;
                out.b = 0;
                out.a = 255;
            }
            break;
        case 3:
            if (pixelConContenido(nlu) + pixelConContenido(nl) + pixelConContenido(nld) == 3
                || pixelConContenido(nru) + pixelConContenido(nr) + pixelConContenido(nrd) == 3
                || pixelConContenido(nlu) + pixelConContenido(nu) + pixelConContenido(nru) == 3
                || pixelConContenido(nld) + pixelConContenido(nd) + pixelConContenido(nrd) == 3 ){
                // Si en | (por ejemplo, la barra de la izquierda: arriba, centro y abajo), me quito para suavizar la recta
                out.r = 0;
                out.g = 0;
                out.b = 0;
                out.a = 255;
            } else if (pixelConContenido(nl) + pixelConContenido(nu) + pixelConContenido(nr) == 3) {
                // Si en T (por ejemplo, arriba izquierda, centro, derecha y yo), me quito para suavizar la recta
                out = meanTreePixels(nl, nu, nr);
            } else if (pixelConContenido(nl) + pixelConContenido(nd) + pixelConContenido(nr) == 3) {
                // Si en T (por ejemplo, arriba izquierda, centro, derecha y yo), me quito para suavizar la recta
                out = meanTreePixels(nl, nd, nr);
            } else if (pixelConContenido(nu) + pixelConContenido(nr) + pixelConContenido(nd) == 3) {
                // Si en T (por ejemplo, arriba izquierda, centro, derecha y yo), me quito para suavizar la recta
                out = meanTreePixels(nu, nr, nd);
            } else if (pixelConContenido(nu) + pixelConContenido(nl) + pixelConContenido(nd) == 3) {
                // Si en T (por ejemplo, arriba izquierda, centro, derecha y yo), me quito para suavizar la recta
                out = meanTreePixels(nu, nl, nd);
            }
            break;
        default:
            break;
    }

    return out;
}
