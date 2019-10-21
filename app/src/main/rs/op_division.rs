#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"

rs_allocation diferent_alloc;

void init() {
}

// x vertical, y horizontal
uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    const uchar4 oin = rsGetElementAt_uchar4(diferent_alloc, x, y);
    in.r = bound255((int) in.r / oin.r);
    in.g = bound255((int) in.g / oin.g);
    in.b = bound255((int) in.b / oin.b);
    return in;
}
