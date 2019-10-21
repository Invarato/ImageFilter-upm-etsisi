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

uchar4 RS_KERNEL sobel(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = bound(0, abs(-pr.topLeft.r + pr.top.r - 2*pr.left.r + 2*pr.right.r - pr.bottomLeft.r + pr.bottomRight.r) + abs(-pr.topLeft.r + pr.top.r - 2*pr.topRight.r + 2*pr.bottomLeft.r - pr.bottom.r + pr.bottomRight.r), 255);
    in.g = bound(0, abs(-pr.topLeft.g + pr.top.g - 2*pr.left.g + 2*pr.right.g - pr.bottomLeft.g + pr.bottomRight.g) + abs(-pr.topLeft.g + pr.top.g - 2*pr.topRight.g + 2*pr.bottomLeft.g - pr.bottom.g + pr.bottomRight.g), 255);
    in.b = bound(0, abs(-pr.topLeft.b + pr.top.b - 2*pr.left.b + 2*pr.right.b - pr.bottomLeft.b + pr.bottomRight.b) + abs(-pr.topLeft.b + pr.top.b - 2*pr.topRight.b + 2*pr.bottomLeft.b - pr.bottom.b + pr.bottomRight.b), 255);

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    //current_alloc = inputImage;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);

    rsForEach(sobel, current_alloc, current_alloc);

    outputImage = current_alloc;
}