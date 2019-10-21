#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

rs_allocation extra_alloc;

void init() {
    //rsDebug("===========init==================",0);
}

static int bound255 (int val) {
    return min(255, max(0, val));
}


uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

    if (x == 0) {
        x = 1;
    }

    if (y == 0) {
        y = 1;
    }

    const uchar4 in1 = rsGetElementAt_uchar4(extra_alloc, x-1, y-1);
    const uchar4 in2 = rsGetElementAt_uchar4(extra_alloc, x, y-1);
    const uchar4 in3 = rsGetElementAt_uchar4(extra_alloc, x+1, y-1);
    const uchar4 in4 = rsGetElementAt_uchar4(extra_alloc, x-1, y);
    const uchar4 in5 = rsGetElementAt_uchar4(extra_alloc, x+1,y);
    const uchar4 in6 = rsGetElementAt_uchar4(extra_alloc, x-1, y+1);
    const uchar4 in7 = rsGetElementAt_uchar4(extra_alloc, x, y+1);
    const uchar4 in8 = rsGetElementAt_uchar4(extra_alloc, x+1, y+1);

    out.r = bound255(abs(-in1.r+in3.r-in4.r+in5.r-in6.r+in8.r) + abs(in1.r+in2.r+in3.r-in6.r-in7.r-in8.r));
    out.g = bound255(abs(-in1.g+in3.g-in4.g+in5.g-in6.g+in8.g) + abs(in1.g+in2.g+in3.g-in6.g-in7.g-in8.g));
    out.b = bound255(abs(-in1.b+in3.b-in4.b+in5.b-in6.b+in8.b) + abs(in1.b+in2.b+in3.b-in6.b-in7.b-in8.b));

    return out;
}
