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


/* Gx Vertical	Gy Horizontal
 * -1	0	1	-1	-2	-1
 * -2	0	2	0	0	0
 * -1	0	1	1	2	1
 */
uchar4 RS_KERNEL sobel(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = bound255(abs(-pr.topLeft.r + pr.topRight.r
                        - 2*pr.left.r + 2*pr.right.r
                        - pr.bottomLeft.r + pr.bottomRight.r)
                  + abs(-pr.topLeft.r - 2*pr.top.r
                        - pr.topRight.r + pr.bottomLeft.r
                        + 2*pr.bottom.r + pr.bottomRight.r));
    in.g = bound255(abs(-pr.topLeft.g + pr.topRight.g
                        - 2*pr.left.g + 2*pr.right.g
                        - pr.bottomLeft.g + pr.bottomRight.g)
                  + abs(-pr.topLeft.g - 2*pr.top.g
                        - pr.topRight.g + pr.bottomLeft.g
                        + 2*pr.bottom.g + pr.bottomRight.g));
    in.b = bound255(abs(-pr.topLeft.b + pr.topRight.b
                        - 2*pr.left.b + 2*pr.right.b
                        - pr.bottomLeft.b + pr.bottomRight.b)
                  + abs(-pr.topLeft.b - 2*pr.top.b
                        - pr.topRight.b + pr.bottomLeft.b
                        + 2*pr.bottom.b + pr.bottomRight.b));

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(sobel, current_alloc, outputImage);
}