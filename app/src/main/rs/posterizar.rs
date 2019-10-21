#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
//#include "atools.rsh"

int umbral;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    //in.r = bound(0, in.r - (in.r % umbral), 255);
    //in.g = bound(0, in.g - (in.g % umbral), 255);
    //in.b = bound(0, in.b - (in.b % umbral), 255);

    in.r = in.r - (in.r % umbral);
    in.g = in.g - (in.g % umbral);
    in.b = in.b - (in.b % umbral);

    return in;
}
