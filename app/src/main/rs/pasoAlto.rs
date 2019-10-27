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

uchar4 RS_KERNEL pasoalto(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = bound(0, (int) - 0.143*pr.topLeft.r - 0.286*pr.top.r - 0.143*pr.topRight.r
                          - 0.286*pr.left.r + 2.717*pr.center.r - 0.286*pr.right.r
                          - 0.143*pr.bottomLeft.r - 0.286*pr.bottom.r - 0.143*pr.bottomRight.r, 255);
    in.g = bound(0, (int) - 0.143*pr.topLeft.g - 0.286*pr.top.g - 0.143*pr.topRight.g
                          - 0.286*pr.left.g + 2.717*pr.center.g - 0.286*pr.right.g
                          - 0.143*pr.bottomLeft.g - 0.286*pr.bottom.g - 0.143*pr.bottomRight.g, 255);
    in.b = bound(0, (int) - 0.143*pr.topLeft.b - 0.286*pr.top.b - 0.143*pr.topRight.b
                          - 0.286*pr.left.b + 2.717*pr.center.b - 0.286*pr.right.b
                          - 0.143*pr.bottomLeft.b - 0.286*pr.bottom.b - 0.143*pr.bottomRight.b, 255);

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);

    rsForEach(pasoalto, current_alloc, outputImage);
}