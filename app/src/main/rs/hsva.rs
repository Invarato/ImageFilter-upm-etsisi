#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(es.upm.etsisi.imagefilter.renders)

#include "rs_debug.rsh"
#include "atools.rsh"


float anguloMatiz; // 0.0 - 360.0
float nsaturacion;  // 0.0 - 1.0
float nintensidad;  // 0.0 - 1.0

void init() {}

float4 RS_KERNEL tohsva(uchar4 in) {
    return RGBAtoHSVA(in);
}

float4 RS_KERNEL matiz(float4 inhsva) {
    inhsva.s0 = anguloMatiz;
    return inhsva;
}

float4 RS_KERNEL saturacion(float4 inhsva) {
    inhsva.s1 = fbound (0.0, inhsva.s1 + nsaturacion, 1.0);
    return inhsva;
}

float4 RS_KERNEL intensidad(float4 inhsva) {
    inhsva.s2 = fbound (0.0, inhsva.s2 + nintensidad, 1.0);
    return inhsva;
}

uchar4 RS_KERNEL torgba(float4 inhsva) {
    return HSVAtoRGBA(inhsva);
}

void process(rs_allocation inputImage, rs_allocation outputImage) {
    const uint32_t imageWidth = rsAllocationGetDimX(inputImage);
    const uint32_t imageHeight = rsAllocationGetDimY(inputImage);

    rs_allocation tmp = rsCreateAllocation_float4(imageWidth, imageHeight);

    rsForEach(tohsva, inputImage, tmp);

    if (anguloMatiz >= 0.0) {
        rsForEach(matiz, tmp, tmp);
    }

    if (nsaturacion >= 0.0) {
        rsForEach(saturacion, tmp, tmp);
    }

    if (nintensidad >= 0.0) {
        rsForEach(intensidad, tmp, tmp);
    }

    rsForEach(torgba, tmp, outputImage);
}