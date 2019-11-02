#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

void init() {
}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    uchar4 out = in;

    if (in.r < 128) {
        out.r = 255 - in.r;
    }

    if (in.g < 128) {
        out.g = 255 - in.g;
    }

    if (in.b < 128) {
       out.b = 255 - in.b;
    }

    return out;
}


