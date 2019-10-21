#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"

int brillo;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    in.r = bound(0, in.r + brillo, 255);
    in.g = bound(0, in.g + brillo, 255);
    in.b = bound(0, in.b + brillo, 255);
    return in;
}
