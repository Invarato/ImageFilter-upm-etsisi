#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

rs_allocation extra_alloc;

// TODO static para que no pueda ser accedido desde Java y ¿se pueda inicializar?
//static int correlationKernel[3][3] = { { 8, 1, 6 },
//                                { 3, 5, 7 },
//                                { 4, 9, 2 } };

//int correlationKernel[3][3];

void init() {
    rsDebug("===========init3==================",0);
    // https://www.mathworks.com/help/images/what-is-image-filtering-in-the-spatial-domain.html
    //correlationKernel = { { 8, 1, 6 },
    //                      { 3, 5, 7 },
    //                      { 4, 9, 2 } };

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

    int correlationKernel[3][3] = { { 8, 1, 6 },
                                    { 3, 5, 7 },
                                    { 4, 9, 2 } };

    //int correlationKernel[3][3] = { { 5, 5, 5 },
    //                                { 5, 1, 5 },
    //                                { 5, 5, 5 } };

    //const uchar4 nl = rsGetElementAt_uchar4(extra_alloc, x, y-1);
    //const uchar4 nr = rsGetElementAt_uchar4(extra_alloc, x, y+1);
//
    //const uchar4 nu = rsGetElementAt_uchar4(extra_alloc, x-1, y);
    //const uchar4 nd = rsGetElementAt_uchar4(extra_alloc, x+1, y);
//
    //const uchar4 nlu = rsGetElementAt_uchar4(extra_alloc, x-1, y-1);
    //const uchar4 nru = rsGetElementAt_uchar4(extra_alloc, x-1, y+1);
//
    //const uchar4 nld = rsGetElementAt_uchar4(extra_alloc, x+1, y-1);
    //const uchar4 nrd = rsGetElementAt_uchar4(extra_alloc, x+1, y+1);
//
    //int vecinos = pixelConContenido(nl)+pixelConContenido(nr)+pixelConContenido(nu)+pixelConContenido(nd)+pixelConContenido(nlu)+pixelConContenido(nru)+pixelConContenido(nld)+pixelConContenido(nrd);


    //uchar4 out = in;
    out.r = 0;
    out.g = 0;
    out.b = 0;
    out.a = 0;

    if (x < 400 && y < 400) {
    //for (ax = x - 1; ax < x + 1; ++ax) {
    //    for (ay = y - 1; ay < y + 1; ++ay) {

    for(int i = 0; i < 2; ++i) {
        for (int j = 0; j < 2; ++j) {
            uint32_t ax = x - 1 + i;
            uint32_t ay = y - 1 + j;
            const uchar4 neigh = rsGetElementAt_uchar4(extra_alloc, ax, ay);

            out.r = out.r + neigh.r * correlationKernel[ax][ay];
            out.g = out.g + neigh.g * correlationKernel[ax][ay];
            out.b = out.b + neigh.b * correlationKernel[ax][ay];
            out.a = out.a + neigh.a * correlationKernel[ax][ay];
        }
    }

    }



    return out;
}
//TODO para obtener el tamaño de la imagen y realizar varios procesos en cadena
// https://developer.android.com/guide/topics/renderscript/compute#single-source-rs
//TODO void process(rs_allocation inputImage, rs_allocation outputImage) {
//TODO   const uint32_t imageWidth = rsAllocationGetDimX(inputImage);
//TODO   const uint32_t imageHeight = rsAllocationGetDimY(inputImage);
//TODO   rs_allocation tmp = rsCreateAllocation_uchar4(imageWidth, imageHeight);
//TODO   rsForEach(invert, inputImage, tmp);
//TODO   rsForEach(greyscale, tmp, outputImage);
//TODO }
