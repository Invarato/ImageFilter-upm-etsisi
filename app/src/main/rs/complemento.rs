#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

void init() {
    //rsDebug("===========init==================",0);
}


uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    in.r = 255 - in.r;
    in.g = 255 - in.g;
    in.b = 255 - in.b;
    return in;
}
