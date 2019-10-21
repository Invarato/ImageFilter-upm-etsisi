#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"

int anguloContraste;

static float radiansContrast = -10000.0;
static bool needFirstCalculation = true;

void init() {}

uchar4 RS_KERNEL root(uchar4 in, uint32_t x, uint32_t y) {
    if (needFirstCalculation) {
        float aux = (anguloContraste*M_PI)/180.0;
        radiansContrast = (float) tan(aux);
        needFirstCalculation = false;
    }

    in.r = bound(0, (int) 128+(in.r-128)*radiansContrast, 255);
    in.g = bound(0, (int) 128+(in.g-128)*radiansContrast, 255);
    in.b = bound(0, (int) 128+(in.b-128)*radiansContrast, 255);
    return in;
}
