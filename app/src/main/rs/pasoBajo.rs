#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"


static rs_allocation current_alloc;
static uint32_t lastX;
static uint32_t lastY;

void init() {}


uchar4 RS_KERNEL copy(uchar4 in) {
    return in;
}

uchar4 RS_KERNEL pasobajo(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = bound255((int) 0.1*pr.topLeft.r + 0.1*pr.top.r + 0.1*pr.topRight.r
               + 0.1*pr.left.r + 0.1*pr.center.r + 0.1*pr.right.r
               + 0.1*pr.bottomLeft.r + 0.1*pr.bottom.r + 0.1*pr.bottomRight.r);
    in.g = bound255((int) 0.1*pr.topLeft.g + 0.1*pr.top.g + 0.1*pr.topRight.g
               + 0.1*pr.left.g + 0.1*pr.center.g + 0.1*pr.right.g
               + 0.1*pr.bottomLeft.g + 0.1*pr.bottom.g + 0.1*pr.bottomRight.g);
    in.b = bound255((int) 0.1*pr.topLeft.b + 0.1*pr.top.b + 0.1*pr.topRight.b
               + 0.1*pr.left.b + 0.1*pr.center.b + 0.1*pr.right.b
               + 0.1*pr.bottomLeft.b + 0.1*pr.bottom.b + 0.1*pr.bottomRight.b);

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);
    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_uchar4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(pasobajo, current_alloc, outputImage);
}