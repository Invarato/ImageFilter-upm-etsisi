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
 * 1	0		0	1
 * 0	-1		-1	0
 */
uchar4 RS_KERNEL roberts(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);

    in.r = bound255(abs(pr.center.r - pr.bottomRight.r)
                  + abs(pr.right.r - pr.bottom.r));
    in.g = bound255(abs(pr.center.g - pr.bottomRight.g)
                  + abs(pr.right.g - pr.bottom.g));
    in.b = bound255(abs(pr.center.b - pr.bottomRight.b)
                  + abs(pr.right.b - pr.bottom.b));

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_float4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(roberts, current_alloc, outputImage);
}