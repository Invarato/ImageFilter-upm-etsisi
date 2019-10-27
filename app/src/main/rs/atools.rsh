// Autor Ramón Invarato Ménendez
#pragma version(1)

//https://es.wikipedia.org/wiki/Modelo_de_color_HSV
// Obtiene el float4 HSVA del pixel uchar4 RGBA (Hue o Matiz, Saturation o Saturación, Value o Valor, Alpha o Transparencia)
// 0 ≤ H < 360, 0 ≤ S ≤ 1, 0 ≤ V ≤ 1, 0 ≤ A ≤ 1
static float4 RGBAtoHSVA(uchar4 in){
    float r = in.r/255.0;
    float g = in.g/255.0;
    float b = in.b/255.0;
    float a = in.a/255.0;

    float cmax = fmax(r, fmax(g, b));
    float cmin = fmin(r, fmin(g, b));

    float chroma = cmax - cmin;

    float hue = 0.0;

    if (chroma > 0) {
        if (cmax == r) {
            hue = fmod((g - b)/chroma, 6.0);
        } else if (cmax == g) {
            hue = ((b-r)/chroma) + 2.0;
        } else if (cmax == b) {
            hue = ((r-b)/chroma) + 4.0;
        }
        hue = 60 * fabs(hue);
    }

    float saturation = cmax > 0? 1 - (cmin/cmax): 0.0;

    return (float4){hue, saturation, cmax, a};
}

// https://www.rapidtables.com/convert/color/hsv-to-rgb.html
// Obtiene el uchar4 RGBA desde un float4 en HSVA
static uchar4 HSVAtoRGBA(float4 inhsva){
    float h = inhsva.s0;
    float s = inhsva.s1;
    float v = inhsva.s2;
    float a = inhsva.s3;

    float c = v * s;

    float aux = h/60.0;
    float auxb = fmod(aux, 2.0) - 1.0;
    float auxc = 1.0 - fabs(auxb);
    float x = c * auxc;

    float m = v - c;

    float3 rgbaux = {0.0, 0.0, 0.0};
    if (0.0 <= h && h < 60.0) {
        rgbaux.s0 = c;
        rgbaux.s1 = x;
    } else if (60.0 <= h && h < 120.0){
        rgbaux.s0 = x;
        rgbaux.s1 = c;
    } else if (120.0 <= h && h < 180.0){
        rgbaux.s1 = c;
        rgbaux.s2 = x;
    } else if (180.0 <= h && h < 240.0){
        rgbaux.s1 = x;
        rgbaux.s2 = c;
    } else if (240.0 <= h && h < 300.0){
        rgbaux.s0 = x;
        rgbaux.s2 = c;
    } else if (300.0 <= h && h < 360.0){
        rgbaux.s0 = c;
        rgbaux.s2 = x;
    }

    return rsPackColorTo8888(rgbaux.s0 + m, rgbaux.s1 + m, rgbaux.s2 + m, a);
}

static float fbound (float mmin, float val, float mmax) {
    float m = fmax(mmin, val);
    return fmin(mmax, m);
}

static int bound (int mmin, int val, int mmax) {
    int m = max(mmin, val);
    return min(mmax, m);
}

static int bound255 (int val) {
    return bound(0, val, 255);
}

static void bubbleSort(int a[], int array_size){
    //int array_size = sizeof(a);
    //int array_size = sizeof(a) / sizeof(a[0]);
    int i, j, temp;
    for (i = 0; i < (array_size - 1); ++i) {
         for (j = 0; j < array_size - 1 - i; ++j ) {
              if (a[j] > a[j+1]) {
                   temp = a[j+1];
                   a[j+1] = a[j];
                   a[j] = temp;
              }
         }
    }
}


typedef struct {
  uchar4 topLeft, top, topRight,
         left, center, right,
         bottomLeft, bottom, bottomRight;
} PixelRegion;


static PixelRegion getPixelRegion (rs_allocation current_alloc,
                                   uint32_t lastX,
                                   uint32_t lastY,
                                   uint32_t x,
                                   uint32_t y) {
    uint32_t y_less_one;
    uint32_t y_plus_one;
    uint32_t x_less_one;
    uint32_t x_plus_one;

    if (x == 0) {
        x_less_one = x;
        x_plus_one = x+1;
    } else if (x == lastX) {
        x_less_one = x-1;
        x_plus_one = x;
    } else {
        x_less_one = x-1;
        x_plus_one = x+1;
    }

    if (y == 0) {
        y_less_one = y;
        y_plus_one = y+1;
    } else if (y == lastY) {
        y_less_one = y-1;
        y_plus_one = y;
    } else {
        y_less_one = y-1;
        y_plus_one = y+1;
    }

    PixelRegion pr;
    pr.topLeft = rsGetElementAt_uchar4(current_alloc, x_less_one, y_less_one);
    pr.top = rsGetElementAt_uchar4(current_alloc, x_less_one, y);
    pr.topRight = rsGetElementAt_uchar4(current_alloc, x_less_one, y_plus_one);

    pr.left = rsGetElementAt_uchar4(current_alloc, x, y_less_one);
    pr.center = rsGetElementAt_uchar4(current_alloc, x, y);
    pr.right = rsGetElementAt_uchar4(current_alloc, x, y_plus_one);

    pr.bottomLeft = rsGetElementAt_uchar4(current_alloc, x_plus_one, y_less_one);
    pr.bottom = rsGetElementAt_uchar4(current_alloc, x_plus_one, y);
    pr.bottomRight = rsGetElementAt_uchar4(current_alloc, x_plus_one, y_plus_one);

    return pr;
}
