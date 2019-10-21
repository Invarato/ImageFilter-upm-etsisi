#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"

float modr;
float modg;
float modb;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
   int newval = (int) (in.r*modr + in.g*modg + in.b*modb);
   in.r = newval;
   in.g = newval;
   in.b = newval;
   return in;
}
