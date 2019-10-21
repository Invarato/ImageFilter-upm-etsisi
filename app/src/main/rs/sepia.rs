#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

int umbral;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    in.r = min((int) (in.r*0.393+in.g*0.769+in.b*0.189), 255);
    in.g = min((int) (in.r*0.349+in.g*0.686+in.b*0.168), 255);
    in.b = min((int) (in.r*0.272+in.g*0.534+in.b*0.131), 255);
    return in;
}
