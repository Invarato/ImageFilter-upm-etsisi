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

uchar4 RS_KERNEL gradiente(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = abs(pr.center.r - pr.right.r) + abs(pr.center.r - pr.bottom.r);
    in.g = abs(pr.center.g - pr.right.g) + abs(pr.center.g - pr.bottom.g);
    in.b = abs(pr.center.b - pr.right.b) + abs(pr.center.b - pr.bottom.b);

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(gradiente, current_alloc, outputImage);
}