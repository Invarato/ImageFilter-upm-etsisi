#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"

static rs_allocation current_alloc;
static uint32_t lastX;
static uint32_t lastY;


void init() {}
//https://developer.android.com/guide/topics/renderscript/reference/rs_allocation_create

uchar4 RS_KERNEL copy(uchar4 in) {
    return in;
}

// x vertical, y horizontal
uchar4 RS_KERNEL mediana(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    int arrayR[9] = {pr.topLeft.r, pr.top.r, pr.topRight.r, pr.left.r, pr.center.r, pr.right.r, pr.bottomLeft.r, pr.bottom.r, pr.bottomRight.r};
    int arrayG[9] = {pr.topLeft.g, pr.top.g, pr.topRight.g, pr.left.g, pr.center.g, pr.right.g, pr.bottomLeft.g, pr.bottom.g, pr.bottomRight.g};
    int arrayB[9] = {pr.topLeft.b, pr.top.b, pr.topRight.b, pr.left.b, pr.center.b, pr.right.b, pr.bottomLeft.b, pr.bottom.b, pr.bottomRight.b};

    bubbleSort(arrayR, 9);
    bubbleSort(arrayG, 9);
    bubbleSort(arrayB, 9);

    in.r = arrayR[4];
    in.g = arrayG[4];
    in.b = arrayB[4];
    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(mediana, current_alloc, outputImage);
}
