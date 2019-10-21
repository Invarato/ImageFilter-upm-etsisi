#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

rs_allocation extra_alloc;

void init() {
    //rsDebug("===========init==================",0);
}


uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

    if (out.r > 0 && out.g > 0 && out.b > 0) {
        out.r = 255;
        out.g = 255;
        out.b = 255;
        out.a = 255;
    }

    return out;
}
