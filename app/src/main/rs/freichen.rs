#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"


static rs_allocation current_alloc;
static uint32_t lastX;
static uint32_t lastY;

//static float R2;
//static float HALF2R2;
static const float R2 = 1.414214;
static const float HALF2R2 = 0.353553;
static const float HALF = 1.0/2.0;
static const float ONESIXTH = 1.0/6.0;
static const float ONETHIRD = 1.0/3.0;

static const float g1[9] = {1.0, R2, 1.0,
                            0.0, 0.0, 0.0,
                            -1.0, -R2, -1.0};
static const float g2[9] = {1.0, 0.0, -1.0,
                            R2, 0.0, -R2,
                            1.0, 0.0, -1.0};
static const float g3[9] = {0.0, -1.0, R2,
                            1.0, 0.0, -1.0,
                            -R2, 1.0, 0.0};
static const float g4[9] = {R2, -1.0, 0.0,
                            -1.0, 0.0, 1.0,
                            0.0, 1.0, -R2};
static const float g5[9] = {0.0, 1.0, 0.0,
                            -1.0, 0.0, -1.0,
                            0.0, 1.0, 0.0};
static const float g6[9] = {-1.0, 0.0, 1.0,
                            0.0, 0.0, 0.0,
                            1.0, 0.0, -1.0};
static const float g7[9] = {1.0, -2.0, 1.0,
                            -2.0, 4.0, -2.0,
                            1.0, -2.0, 1.0};
static const float g8[9] = {-2.0, 1.0, -2.0,
                            1.0, 4.0, 1.0,
                            -2.0, 1.0, -2.0};
static const float g9[9] = {1.0, 1.0, 1.0,
                            1.0, 1.0, 1.0,
                            1.0, 1.0, 1.0};

void init() {
    //R2 = sqrt((float) 2.0);
    //HALF2R2 = 1.0/(2.0*R2);

    //rsDebug("=====R2",R2);
    //rsDebug("=====HALF2R2",HALF2R2);
    //rsDebug("=====HALF",HALF);
    //rsDebug("=====ONESIXTH",ONESIXTH);
    //rsDebug("=====ONETHIRD",ONETHIRD);
}


uchar4 RS_KERNEL copy(uchar4 in) {
    return in;
}

/* Gx Vertical	Gy Horizontal
 * -1	0	1	-1	-1	-1
 * -1	0	1	0	0	0
 * -1	0	1	1	1	1
 */


static float3 calcMatrixG (uchar4 pp[9], float g[9], float mult) {
    float3 rgbres = {0.0, 0.0, 0.0};
    for (int i=0; i<9; i++) {
        rgbres[0] += (g[i] * (float) pp[i].r);
        rgbres[1] += (g[i] * (float) pp[i].g);
        rgbres[2] += (g[i] * (float) pp[i].b);
    }

    rgbres[0] = fabs(rgbres[0]) * mult;
    rgbres[1] = fabs(rgbres[1]) * mult;
    rgbres[2] = fabs(rgbres[2]) * mult;
    return rgbres;
}

static int3 calcAllMatrixG (PixelRegion pr) {
    uchar4 pp[9] = {pr.topLeft, pr.top, pr.topRight,
                    pr.left, pr.center, pr.right,
                    pr.bottomLeft, pr.bottom, pr.bottomRight};

    //rsDebug("=====rr1",calcMatrixG (pp, g1, HALF2R2));
    //rsDebug("=====rr2",calcMatrixG (pp, g2, HALF2R2));
    //rsDebug("=====rrT",calcMatrixG (pp, g1, HALF2R2) + calcMatrixG (pp, g2, HALF2R2));

    float3 res = calcMatrixG (pp, g1, HALF2R2)
                + calcMatrixG (pp, g2, HALF2R2)
                + calcMatrixG (pp, g3, HALF2R2)
                + calcMatrixG (pp, g4, HALF2R2)
                + calcMatrixG (pp, g5, HALF)
                + calcMatrixG (pp, g6, HALF)
                + calcMatrixG (pp, g7, ONESIXTH)
                + calcMatrixG (pp, g8, ONESIXTH)
                + calcMatrixG (pp, g9, ONETHIRD);

    int3 resint = {bound255((int) res[0]),
                   bound255((int) res[1]),
                   bound255((int) res[2])};

    return resint;

}

uchar4 RS_KERNEL freichen(uchar4 in, uint32_t x, uint32_t y) {
    PixelRegion pr = getPixelRegion (current_alloc, lastX, lastY, x, y);
    int3 rgbnew = calcAllMatrixG(pr);
    in.r = rgbnew.r;
    in.g = rgbnew.g;
    in.b = rgbnew.b;

    //in.r = bound255((int)
    //  HALF2R2*fabs((float) pr.topLeft.r + R2*pr.top.r + pr.topRight.r
    //                     - pr.bottomLeft.r - R2*pr.bottom.r - pr.bottomRight.r)
    // +HALF2R2*fabs((float) pr.topLeft.r - pr.topRight.r
    //                     + R2*pr.left.r - R2*pr.right.r
    //                     + pr.bottomLeft.r - pr.bottomRight.r)
    // +HALF2R2*fabs((float) - pr.top.r + R2*pr.topRight.r
    //                       + pr.left.r - pr.right.r
    //                       - R2*pr.bottomLeft.r + pr.bottom.r)
    // +HALF2R2*fabs((float) R2*pr.topLeft.r - pr.top.r
    //                     - pr.left.r + pr.right.r
    //                     + pr.bottom.r - R2*pr.bottomRight.r)
    // +HALF*(float) abs(pr.top.r
    //                 - pr.left.r - pr.right.r
    //                 + pr.bottom.r)
    // +HALF*(float) abs(- pr.topLeft.r + pr.topRight.r
    //                   + pr.bottomLeft.r - pr.bottomRight.r)
    // +ONESIXTH*(float) abs(pr.topLeft.r - 2*pr.top.r + pr.topRight.r
    //                     - 2*pr.left.r + 4*pr.center.r - 2*pr.right.r
    //                     + pr.bottomLeft.r - 2*pr.bottom.r + pr.bottomRight.r)
    // +ONESIXTH*(float) abs(- 2*pr.topLeft.r + pr.top.r - 2*pr.topRight.r
    //                       + pr.left.r + 4*pr.center.r + pr.right.r
    //                       - 2*pr.bottomLeft.r + pr.bottom.r - 2*pr.bottomRight.r)
    // +ONETHIRD*(float) abs(pr.topLeft.r + pr.top.r + pr.topRight.r
    //                     + pr.left.r + pr.center.r + pr.right.r
    //                     + pr.bottomLeft.r + pr.bottom.r + pr.bottomRight.r)
    //             );
//
    //in.g = bound255((int)
    //  HALF2R2*fabs((float) pr.topLeft.g + R2*pr.top.g + pr.topRight.g
    //              - pr.bottomLeft.g - R2*pr.bottom.g - pr.bottomRight.g)
    // +HALF2R2*fabs((float) pr.topLeft.g - pr.topRight.g + R2*pr.left.g
    //               - R2*pr.right.g + pr.bottomLeft.g - pr.bottomRight.g)
    // +HALF2R2*fabs((float) -pr.top.g + R2*pr.topRight.g + pr.left.g
    //               - pr.right.g - R2*pr.bottomLeft.g + pr.bottom.g)
    // +HALF2R2*fabs((float) R2*pr.topLeft.g - pr.top.g - pr.left.g
    //               + pr.right.g + pr.bottom.g - R2*pr.bottomRight.g)
    // +HALF*(float) abs(pr.top.g - pr.left.g - pr.right.g + pr.bottom.g)
    // +HALF*(float) abs(-pr.topLeft.g + pr.topRight.g + pr.bottomLeft.g - pr.bottomRight.g)
    // +ONESIXTH*(float) abs(pr.topLeft.g - 2*pr.top.g + pr.topRight.g
    //             - 2*pr.left.g + 4*pr.center.g - 2*pr.right.g
    //             + pr.bottomLeft.g - 2*pr.bottom.g + pr.bottomRight.g)
    // +ONESIXTH*(float) abs(-2*pr.topLeft.g + pr.top.g - 2*pr.topRight.g
    //             + pr.left.g + 4*pr.center.g + pr.right.g
    //             - 2*pr.bottomLeft.g + pr.bottom.g - 2*pr.bottomRight.g)
    // +ONETHIRD*(float) abs(pr.topLeft.g + pr.top.g + pr.topRight.g
    //             + pr.left.g + pr.center.g + pr.right.g
    //             + pr.bottomLeft.g + pr.bottom.g + pr.bottomRight.g)
    //             );
//
    //in.b = bound255((int)
    //  HALF2R2*fabs((float) pr.topLeft.b + R2*pr.top.b + pr.topRight.b
    //              - pr.bottomLeft.b - R2*pr.bottom.b - pr.bottomRight.b)
    // +HALF2R2*fabs((float) pr.topLeft.b - pr.topRight.b + R2*pr.left.b
    //               - R2*pr.right.b + pr.bottomLeft.b - pr.bottomRight.b)
    // +HALF2R2*fabs((float) -pr.top.b + R2*pr.topRight.b + pr.left.b
    //               - pr.right.b - R2*pr.bottomLeft.b + pr.bottom.b)
    // +HALF2R2*fabs((float) R2*pr.topLeft.b - pr.top.b - pr.left.b
    //               + pr.right.b + pr.bottom.b - R2*pr.bottomRight.b)
    // +HALF*(float) abs(pr.top.b - pr.left.b - pr.right.b + pr.bottom.b)
    // +HALF*(float) abs(-pr.topLeft.b + pr.topRight.b + pr.bottomLeft.b - pr.bottomRight.b)
    // +ONESIXTH*(float) abs(pr.topLeft.b - 2*pr.top.b + pr.topRight.b
    //             - 2*pr.left.b + 4*pr.center.b - 2*pr.right.b
    //             + pr.bottomLeft.b - 2*pr.bottom.b + pr.bottomRight.b)
    // +ONESIXTH*(float) abs(-2*pr.topLeft.b + pr.top.b - 2*pr.topRight.b
    //             + pr.left.b + 4*pr.center.b + pr.right.b
    //             - 2*pr.bottomLeft.b + pr.bottom.b - 2*pr.bottomRight.b)
    // +ONETHIRD*(float) abs(pr.topLeft.b + pr.top.b + pr.topRight.b
    //             + pr.left.b + pr.center.b + pr.right.b
    //             + pr.bottomLeft.b + pr.bottom.b + pr.bottomRight.b)
    //             );


    //in.g = bound255((int)
    //  HALF2R2*(fabs((float) pr.topLeft.g + R2*pr.top.g + pr.topRight.g
    //              - pr.bottomLeft.g - R2*pr.bottom.g - pr.bottomRight.g)
    // +HALF2R2*fabs((float) pr.topLeft.g - pr.topRight.g + R2*pr.left.g
    //               - R2*pr.right.g + pr.bottomLeft.g - pr.bottomRight.g)
    // +HALF2R2*fabs((float) -pr.top.g + R2*pr.topRight.g + pr.left.g
    //               - pr.right.g - R2*pr.bottomLeft.g + pr.bottom.g)
    // +HALF2R2*fabs((float) R2*pr.topLeft.g - pr.top.g - pr.left.g
    //               + pr.right.g + pr.bottom.g - R2*pr.bottomRight.g)
    //               )
    // +HALF*fabs((float) pr.top.g - pr.left.g - pr.right.g + pr.bottom.g)
    // +HALF*fabs((float) -pr.topLeft.g + pr.topRight.g + pr.bottomLeft.g - pr.bottomRight.g)
    // +ONESIXTH*fabs((float) pr.topLeft.g - 2.0*pr.top.g + pr.topRight.g
    //             - 2.0*pr.left.g + 4*pr.center.g - 2.0*pr.right.g
    //             + pr.bottomLeft.g - 2.0*pr.bottom.g + pr.bottomRight.g)
    // +ONESIXTH*fabs((float) -2.0*pr.topLeft.g + pr.top.g - 2.0*pr.topRight.g
    //             + pr.left.g + 4.0*pr.center.g + pr.right.g
    //             - 2.0*pr.bottomLeft.g + pr.bottom.g - 2.0*pr.bottomRight.g)
    // +ONETHIRD*fabs((float) pr.topLeft.g + pr.top.g + pr.topRight.g
    //             + pr.left.g + pr.center.g + pr.right.g
    //             + pr.bottomLeft.g + pr.bottom.g + pr.bottomRight.g)
    //             );

    //in.b = bound255((int)
    //  HALF2R2*(fabs((float) pr.topLeft.b + R2*pr.top.b + pr.topRight.b
    //              - pr.bottomLeft.b - R2*pr.bottom.b - pr.bottomRight.b)
    // +HALF2R2*fabs((float) pr.topLeft.b - pr.topRight.b + R2*pr.left.b
    //               - R2*pr.right.b + pr.bottomLeft.b - pr.bottomRight.b)
    // +HALF2R2*fabs((float) -pr.top.b + R2*pr.topRight.b + pr.left.b
    //               - pr.right.b - R2*pr.bottomLeft.b + pr.bottom.b)
    // +HALF2R2*fabs((float) R2*pr.topLeft.b - pr.top.b - pr.left.b
    //               + pr.right.b + pr.bottom.b - R2*pr.bottomRight.b)
    //               )
    // +HALF*fabs((float) pr.top.b - pr.left.b - pr.right.b + pr.bottom.b)
    // +HALF*fabs((float) -pr.topLeft.b + pr.topRight.b + pr.bottomLeft.b - pr.bottomRight.b)
    // +ONESIXTH*fabs((float) pr.topLeft.b - 2.0*pr.top.b + pr.topRight.b
    //             - 2.0*pr.left.b + 4*pr.center.b - 2.0*pr.right.b
    //             + pr.bottomLeft.b - 2.0*pr.bottom.b + pr.bottomRight.b)
    // +ONESIXTH*fabs((float) -2.0*pr.topLeft.b + pr.top.b - 2.0*pr.topRight.b
    //             + pr.left.b + 4.0*pr.center.b + pr.right.b
    //             - 2.0*pr.bottomLeft.b + pr.bottom.b - 2.0*pr.bottomRight.b)
    // +ONETHIRD*fabs((float) pr.topLeft.b + pr.top.b + pr.topRight.b
    //             + pr.left.b + pr.center.b + pr.right.b
    //             + pr.bottomLeft.b + pr.bottom.b + pr.bottomRight.b)
    //             );


    //in.g = bound255((int)
    //  HALF2R2*(fabs((float) pr.topLeft.g + R2*pr.top.g + pr.topRight.g
    //              - pr.bottomLeft.g - R2*pr.bottom.g - pr.bottomRight.g)
    // +HALF2R2*(fabs((float) pr.topLeft.g - pr.topRight.g + R2*pr.left.g
    //               - R2*pr.right.g + pr.bottomLeft.g - pr.bottomRight.g))
    // +HALF2R2*(fabs((float) -pr.top.g + R2*pr.topRight.g + pr.left.g
    //               - pr.right.g - R2*pr.bottomLeft.g + pr.bottom.g))
    // +HALF2R2*(fabs((float) R2*pr.topLeft.g - pr.top.g - pr.left.g
    //               + pr.right.g + pr.bottom.g - R2*pr.bottomRight.g))
    //               )
    // +HALF*(fabs((float) pr.top.g - pr.left.g - pr.right.g + pr.bottom.g))
    // +HALF*(fabs((float) -pr.topLeft.g + pr.topRight.g + pr.bottomLeft.g - pr.bottomRight.g))
    // +ONESIXTH*(fabs((float) pr.topLeft.g - 2*pr.top.g + pr.topRight.g
    //             - 2*pr.left.g + 4*pr.center.g - 2*pr.right.g
    //             + pr.bottomLeft.g - 2*pr.bottom.g + pr.bottomRight.g))
    // +ONESIXTH*(fabs((float) -2*pr.topLeft.g + pr.top.g - 2*pr.topRight.g
    //             + pr.left.g + 4*pr.center.g + pr.right.g
    //             - 2*pr.bottomLeft.g + pr.bottom.g - 2*pr.bottomRight.g))
    // +ONETHIRD*(fabs((float) pr.topLeft.g + pr.top.g + pr.topRight.g
    //             + pr.left.g + pr.center.g + pr.right.g
    //             + pr.bottomLeft.g + pr.bottom.g + pr.bottomRight.g))
    //             );
//
    //in.b = bound255((int)
    //  HALF2R2*(fabs((float) pr.topLeft.b + R2*pr.top.b + pr.topRight.b
    //              - pr.bottomLeft.b - R2*pr.bottom.b - pr.bottomRight.b)
    // +HALF2R2*(fabs((float) pr.topLeft.b - pr.topRight.b + R2*pr.left.b
    //               - R2*pr.right.b + pr.bottomLeft.b - pr.bottomRight.b))
    // +HALF2R2*(fabs((float) -pr.top.b + R2*pr.topRight.b + pr.left.b
    //               - pr.right.b - R2*pr.bottomLeft.b + pr.bottom.b))
    // +HALF2R2*(fabs((float) R2*pr.topLeft.b - pr.top.b - pr.left.b
    //               + pr.right.b + pr.bottom.b - R2*pr.bottomRight.b))
    //               )
    // +HALF*(fabs((float) pr.top.b - pr.left.b - pr.right.b + pr.bottom.b))
    // +HALF*(fabs((float) -pr.topLeft.b + pr.topRight.b + pr.bottomLeft.b - pr.bottomRight.b))
    // +ONESIXTH*(fabs((float) pr.topLeft.b - 2*pr.top.b + pr.topRight.b
    //             - 2*pr.left.b + 4*pr.center.b - 2*pr.right.b
    //             + pr.bottomLeft.b - 2*pr.bottom.b + pr.bottomRight.b))
    // +ONESIXTH*(fabs((float) -2*pr.topLeft.b + pr.top.b - 2*pr.topRight.b
    //             + pr.left.b + 4*pr.center.b + pr.right.b
    //             - 2*pr.bottomLeft.b + pr.bottom.b - 2*pr.bottomRight.b))
    // +ONETHIRD*(fabs((float) pr.topLeft.b + pr.top.b + pr.topRight.b
    //             + pr.left.b + pr.center.b + pr.right.b
    //             + pr.bottomLeft.b + pr.bottom.b + pr.bottomRight.b))
    //             );

    return in;
}


void process(rs_allocation inputImage, rs_allocation outputImage) {
    uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    uint32_t imageHeight = rsAllocationGetDimY(inputImage);
    lastX = imageWidth - 1;
    lastY = imageHeight - 1;

    current_alloc = rsCreateAllocation_uchar4(imageWidth, imageHeight);
    rsForEach(copy, inputImage, current_alloc);
    rsForEach(freichen, current_alloc, outputImage);
}