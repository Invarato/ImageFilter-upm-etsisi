#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

int alpha;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    in.a = alpha;
    return in;
}
