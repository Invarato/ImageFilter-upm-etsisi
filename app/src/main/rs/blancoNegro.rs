#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

int umbral;
static const uchar4 pblack = {0, 0, 0, 255};
static const uchar4 pwhite = {255, 255, 255, 255};

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {

    if (in.a > 0) {
        int grey = (int) ( (in.r + in.g + in.b)/3);
        uchar4 out = pwhite;
        if (grey < umbral) {
            out = pblack;
        }
        out.a = in.a;
        in = out;
    }

    return in;
}
