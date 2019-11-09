package es.upm.etsisi.imagefilter;


import android.graphics.Bitmap;
import android.graphics.Color;

public class FiltrosAntiguos {

	private Bitmap bitmap_original;
	
	public FiltrosAntiguos(Bitmap bitmap_original){
		this.bitmap_original = bitmap_original;
	}
	
	
/*
* Métodos Aritméticos
*/
	
	
	public enum SIMBOLO {
		MAS("+"),
		MENOS("-"),
		MULTIPLICAR("x"),
		DIVIDIR("/");
		
		String simbolo;
		SIMBOLO (String simbolo){
			this.simbolo = simbolo;
		}
		
		public String toString (){
			return simbolo;
		}
	}
	
	


	/** Cambia el color del píxel indicado en la imagen pasada por parámetro con los valores indicados
	 * @param simbolo Símbolo de la operación de tipo SIMBOLO
	 * @param escalar Número a escalar cada píxel
	 * @param bitmap_region
	 * @param x Coordenada de la imagen horizontal
	 * @param y Coordenada de la imagen vertical
	 */
	public void cambiar_color_pixel (SIMBOLO simbolo, int escalar, Bitmap bitmap_region, int x, int y){
		int a,r,g,b;
		
		int color_actual = bitmap_region.getPixel(x, y); //color actual es un int de la forma argb
		
		a = Color.alpha(color_actual);
		r = Color.red(color_actual);
		g = Color.green(color_actual);
		b = Color.blue(color_actual);

		switch (simbolo){
			case MAS:{
				r += escalar;
				g += escalar;
				b += escalar;
			break;}
			case MENOS:{
				r -= escalar;
				g -= escalar;
				b -= escalar;
			break;}
			case MULTIPLICAR:{
				r *= escalar;
				g *= escalar;
				b *= escalar;
			break;}
			case DIVIDIR:{
				r /= escalar;
				g /= escalar;
				b /= escalar;
			break;}
		}
		
		int nuevoColor = Color.argb(a, r, g, b); //El nuevo color se compone del alfa y del azul antiguos
		bitmap_region.setPixel(x, y, nuevoColor);
	}
	//.......................................................................................................................
	
	
	public enum ORDEN {
		SEPIA("Sepia"),
		BYN("Blanco y Negro"),
		GRIS("Gris"),
		INVERT("Invertir"),
		AZUL("Azulado"),
		TRANS("Transparencia"),
		PBAJO("Difusion"),
		MATIZ("Matiz"),
		SATURA("Saturacion"),
		INTENSIDAD("Intensidad"),
		BRILLO("Brillo"),
		SOLAR("Solarizar"),
		POST("Posterizar"),
		CONTRASTE("Contraste"),
		MEDIANA("Mediana"),
		GRADIENTE("Gradiente"),
		SOBEL("Sobel"),
		PREWITT("Prewitt"),
		ROBERTS("Roberts"),
		FREICHEN("Frei-Chen"),
		PALTO("Realce"),
		LAPLACE("Laplaciana"),
		RLAPLACE("Realce Laplaciana"),
		SROBERTS("Suavizado Roberts");

		String orden;
		ORDEN (String orden){
			this.orden = orden;
		}
		
		public String toString (){
			return orden;
		}
	}
	
	
	/**
	 * Método que modifica una imagen completamente. Ya sea solo el color, o algún filtrado.
	 *@param orden Elige la modificación que se va a efectuar
	 */
	public void cambiar_color(ORDEN orden){
		
		switch (orden){
//			case SEPIA:{
//				this.bitmap_original = sepiaImagen(this.bitmap_original);
//			break;}
//			case GRIS:{
//				this.bitmap_original = escalaGrisesImagen(this.bitmap_original);
//			break;}
//			case INVERT:{
//				this.bitmap_original = invertirColoresImagen(this.bitmap_original);
//			break;}
//			case AZUL:{
//				this.bitmap_original = azuladoImagen(this.bitmap_original);
//			break;}
//			case SOLAR:{
//				this.bitmap_original = solarizarImagen(this.bitmap_original);
//			break;}
			case PBAJO:{
				this.bitmap_original = pasoBajo(this.bitmap_original);
			break;}
			case MEDIANA:{
				this.bitmap_original = mediana(this.bitmap_original);
			break;}
			case GRADIENTE:{
				this.bitmap_original = gradiente(this.bitmap_original);
			break;}
			case PALTO:{
				this.bitmap_original = pasoAlto(this.bitmap_original);
			break;}
			case SOBEL:{
				this.bitmap_original = sobel(this.bitmap_original);
			break;}
			case PREWITT:{
				this.bitmap_original = prewitt(this.bitmap_original);
			break;}
			case ROBERTS:{
				this.bitmap_original = roberts(this.bitmap_original);
			break;}
			case FREICHEN:{
				this.bitmap_original = freiChen(this.bitmap_original);
			break;}
			case LAPLACE:{
				this.bitmap_original = laplaciana(this.bitmap_original);
			break;}
			case RLAPLACE:{
				this.bitmap_original = realceLaplaciana(this.bitmap_original);
			break;}
			case SROBERTS:{
				this.bitmap_original = suavizadoRoberts(this.bitmap_original);
			break;}
			
		}
		//generar_previsulizacion_imagen ();
	}
	
	
	/**
	 * Método que modifica una imagen según un valor prefijado.
	 *@param orden Elige la modificación que se va a efectuar
	 *@param umbral Elige el umbral sobre el que tiene que actuar el filtro
	 */
	public void cambiar_color_variable(ORDEN orden, int umbral){
		
		switch (orden){
//			case BYN:{
//				this.bitmap_original = blancoNegroImagen(this.bitmap_original,umbral);
//			break;}
//			case TRANS:{
//				this.bitmap_original = transparenciaImagen(this.bitmap_original, umbral);
//			break;}
//			case MATIZ:{
//				this.bitmap_original = matizImagen(this.bitmap_original, umbral);
//			break;}
//			case SATURA:{
//				this.bitmap_original = saturacionImagen(this.bitmap_original, umbral);
//			break;}
//			case INTENSIDAD:{
//				this.bitmap_original = intensidadImagen(this.bitmap_original, umbral);
//			break;}
//			case BRILLO:{
//				this.bitmap_original = brilloImagen(this.bitmap_original, umbral);
//			break;}
//			case CONTRASTE:{
//				this.bitmap_original = contrasteImagen(this.bitmap_original, umbral);
//			break;}
//			case POST:{
//				this.bitmap_original = posterizarImagen(this.bitmap_original, umbral);
//			break;}
		}
		
		//generar_previsulizacion_imagen ();
		
	}
	
	
/*
* Métodos Binarios
*/
	
	/**
	 * Cambia los colores de un píxel de la imagen por colores binarios, blanco o negro.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
//	public int blancoNegroPixel(Bitmap bitmap_original, int x, int y, int umbral){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		// Obtención de las tonalidades originales del píxel
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//		int nuevoColor = color_actual;
//
//		if (a != 0){
//			int gry = (r + g + b) / 3;
//
//			//int nuevoColor;
//			if(gry<umbral){
//				nuevoColor = Color.BLACK;
//			}else nuevoColor = Color.WHITE;
//		}
//
//		return nuevoColor;
//	}
	
	/**
	 * Cambia los colores de una imagen por colores binarios, blanco o negro.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 */
//	public Bitmap blancoNegroImagen(Bitmap bitmap_original, int umbral){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = blancoNegroPixel(bitmap_original,x,y,umbral);
//				bitmap_aux.setPixel(x, y, color);
//			}
//
//		return bitmap_aux;
//	}
	
//	/**
//	 * Cambia los colores de un píxel de la imagen por colores de la escala de grises.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 * @param x Coordenada x del píxel
//	 * @param y Coordenada y del píxel
//	 */
//	public int escalaGrisesPixel(Bitmap bitmap_original, int x, int y){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//
//		int nuevo_color = (int) (r*0.30+g*0.59+b*0.11);
//		int colorGris = Color.argb(a,nuevo_color,nuevo_color,nuevo_color);
//
//		return colorGris;
//	}
//
//
//	/**
//	 * Cambia los colores de una imagen por colores de la escala de grises.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 */
//	public Bitmap escalaGrisesImagen(Bitmap bitmap_original){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = escalaGrisesPixel(this.bitmap_original,x,y);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//
//	}
	
	
/*
* Otros métodos de tratamiento de imágenes.
*/
	
	/**
	 * Cambia el nivel de transparencia de un pixel.
	 * @param bitmap_original Bitmap sobre el que se va a escribir el nuevo pix
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 * @param umbral Elige el umbral sobre el que tiene que actuar el filtro
	 */
//	public int transparenciaPixel(Bitmap bitmap_original, int x, int y, int umbral){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//		int nuevoA = 0;
//
//		if (umbral <= 20) umbral = 20;
//
//		if (a != 0){
//			nuevoA = umbral;
//		}
//		int nuevoColor = Color.argb(nuevoA, r, g, b);
//
//		return nuevoColor;
//	}
	
	
	/**
	 * Cambia el nivel de transparencia de una imágen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param umbral Elige el umbral sobre el que tiene que actuar el filtro
	 * @return 
	 */
//	public Bitmap transparenciaImagen(Bitmap bitmap_original, int umbral){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = transparenciaPixel(bitmap_original,x,y,umbral);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
//
////	/**
////	* Cambia los colores de un píxel de la imagen por otros con tonalidades sepias
////	* @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
////	* @param x Coordenada x del píxel
////	* @param y Coordenada y del píxel
////	*/
////	public int sepiaPixel(Bitmap bitmap_original, int x, int y){
////
////		int color_actual = bitmap_original.getPixel(x, y);
////
////		// Obtención de las tonalidades originales del píxel
////		int a = Color.alpha(color_actual);
////		int r = Color.red(color_actual);
////		int g = Color.green(color_actual);
////		int b = Color.blue(color_actual);
////
////		// Eliminación de los tonos más azulados
////		int nuevoR = (int) (r*0.393+g*0.769+b*0.189);
////		int nuevoG = (int) (r*0.349+g*0.686+b*0.168);
////		int nuevoB = (int) (r*0.272+g*0.534+b*0.131);
////
////		if(nuevoR > 255) {
////			nuevoR = 255;
////        }
////        if(nuevoG > 255) {
////        	nuevoG = 255;
////        }
////        if(nuevoB > 255) {
////        	nuevoB = 255;
////        }
////
////		int color_nuevo = Color.argb(a,nuevoR,nuevoG,nuevoB);
////
////		return color_nuevo;
////	}
////
////	/**
////	* Cambia los colores de una imagen por otros con tonalidades sepias
////	* @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
////	*/
////	public Bitmap sepiaImagen(Bitmap bitmap_original){
////
////		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
////		int color;
////		for(int x=0; x<bitmap_original.getWidth(); x++)
////			for(int y=0; y<bitmap_original.getHeight(); y++){
////				color = sepiaPixel(bitmap_original,x,y);
////				bitmap_aux.setPixel(x, y, color);
////			}
////		return bitmap_aux;
////	}
	
	
//	/**
//	 * Cambia los colores de un píxel de la imagen por colores invertidos de éste.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 * @param x Coordenada x del píxel
//	 * @param y Coordenada y del píxel
//	 */
//	public int invertirColoresPixel(Bitmap bitmap_original, int x, int y){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//
//		//Función Inversa;
//		int nuevoR = 255 - r;
//		int nuevoG = 255 - g;
//		int nuevoB = 255 - b;
//
//		int colorInvertido = Color.argb(a,nuevoR,nuevoG,nuevoB);
//
//		return colorInvertido;
//	}
//
//	/**
//	 * Cambia los colores de una imagen por colores invertidos de ésta.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 */
//	public Bitmap invertirColoresImagen(Bitmap bitmap_original){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = invertirColoresPixel(bitmap_original,x,y);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
//	/**
//	 * Cambia los colores de un píxel de la imagen por otros con tonalidades azuladas.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 * @param x Coordenada x del píxel
//	 * @param y Coordenada y del píxel
//	 */
//	public int azuladoPixel(Bitmap bitmap_original, int x, int y){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//
//		//Invertir Colores
//		int InvertR = 255 - r;
//		int InvertG = 255 - g;
//		int InvertB = 255 - b;
//
//		//Sepia de colores invertidos
//		int nuevoR = (int) (InvertR*0.393+InvertG*0.769+InvertB*0.189);
//		int nuevoG = (int) (InvertR*0.349+InvertG*0.686+InvertB*0.168);
//		int nuevoB = (int) (InvertR*0.272+InvertG*0.534+InvertB*0.131);
//
//		if(nuevoR > 255) {
//			nuevoR = 255;
//        }
//        if(nuevoG > 255) {
//        	nuevoG = 255;
//        }
//        if(nuevoB > 255) {
//        	nuevoB = 255;
//        }
//		//Inversión de tonalidades sepias para conseguir el tono Azulado
//		int InvertNuevoR = 255 - nuevoR;
//		int InvertNuevoG = 255 - nuevoG;
//		int InvertNuevoB = 255 - nuevoB;
//
//		int colorAzulado = Color.argb(a,InvertNuevoR,InvertNuevoG,InvertNuevoB);
//
//		return colorAzulado;
//	}
//
//	/**
//	 * Cambia los colores de una imagen por otros con tonalidades azuladas.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
//	 */
//	public Bitmap azuladoImagen(Bitmap bitmap_original){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = azuladoPixel(bitmap_original,x,y);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
	
	
	/**
	 * Cambia el nivel de matiz de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param x Coordenada x del píxel.
	 * @param y Coordenada y del píxel.
	 * @param matiz Nivel del nuevo matiz de la imagen.
	 */
//	public int matizPixel(Bitmap bitmap_original, int x, int y, int matiz){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//		int a = Color.alpha(color_actual);
//
//		float[] hsv = new float [3];
//		Color.colorToHSV(color_actual, hsv);
//
//		//Introducción del nuevo matiz
//		hsv[0] = matiz;
//
//		int nuevo_color = Color.HSVToColor(a, hsv);
//
//		return nuevo_color;
//	}
	
	/**
	 * Cambia el nivel de matiz de una imagen, dentro del rango 0..360.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param matiz Nivel del nuevo matiz de la imagen.
	 */
//	public Bitmap matizImagen(Bitmap bitmap_original, int matiz){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = matizPixel(bitmap_original,x,y,matiz);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
	/**
	 * Cambia el nivel de saturación del color de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param x Coordenada x del píxel.
	 * @param y Coordenada y del píxel.
	 * @param saturacion Nivel de la nueva saturacion del píxel.
	 */
//	public int saturacionPixel(Bitmap bitmap_original, int x, int y, int saturacion){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//
//		float[] hsv = new float [3];
//		Color.colorToHSV(color_actual, hsv);
//
//		//Introducción del nuevo valor de saturacion [0..1]
//		hsv[1] = hsv[1] + ((float) saturacion / 100);
//
//		if(hsv[1]>1){
//			hsv[1] = 1;
//		}else if (hsv[1]<0) hsv[1] = 0;
//
//		int nuevo_color = Color.HSVToColor(a, hsv);
//
//		return nuevo_color;
//	}
	
	/**
	 * Cambia el nivel de saturación del color de una imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param saturacion Nivel de la nueva saturacion del píxel.
	 */
//	public Bitmap saturacionImagen(Bitmap bitmap_original, int saturacion){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = saturacionPixel(bitmap_original,x,y,saturacion);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
	/**
	 * Cambia el nivel de intensidad del color de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param x Coordenada x del píxel.
	 * @param y Coordenada y del píxel.
	 * @param intensidad Nivel de la nueva intensidad de la imagen.
	 */
//	public int intensidadPixel(Bitmap bitmap_original, int x, int y, int intensidad){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//
//		float[] hsv = new float [3];
//		Color.colorToHSV(color_actual, hsv);
//
//		// Introduccion del nuevo nivel de intensidad de color [0..1]
//		hsv[2] = hsv[2] + ((float) intensidad/100);
//
//		if(hsv[2]>1){
//			hsv[2] = 1;
//		}else if (hsv[2]<0) hsv[2] = 0;
//
//		int nuevo_color = Color.HSVToColor(a, hsv);
//
//		return nuevo_color;
//	}
	
	/**
	 * Cambia el nivel de intensidad del color de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param intensidad Nivel de la nueva intensidad de la imagen.
	 */
//	public Bitmap intensidadImagen(Bitmap bitmap_original, int intensidad){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = intensidadPixel(bitmap_original,x,y,intensidad);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
	
	
	/**
	 * Cambia el nivel de brillo de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param x Coordenada x del píxel.
	 * @param y Coordenada y del píxel.
	 * @param brillo Nivel de la nueva intensidad de la imagen.
	 */
//	public int brilloPixel(Bitmap bitmap_original, int x, int y, int brillo){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//
//		int nuevoR = r + brillo;
//		int nuevoG = g + brillo;
//		int nuevoB = b + brillo;
//
//		if (nuevoR >255) nuevoR = 255;
//		if (nuevoG >255) nuevoG = 255;
//		if (nuevoB >255) nuevoB = 255;
//
//		if (nuevoR <0) nuevoR = 0;
//		if (nuevoG <0) nuevoG = 0;
//		if (nuevoB <0) nuevoB = 0;
//
//		int nuevo_color = Color.argb(a, nuevoR, nuevoG, nuevoB);
//
//		return nuevo_color;
//	}
	
	/**
	 * Cambia el nivel de brillo de una imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param brillo Nivel de la nueva intensidad de la imagen.
	 */
//	public Bitmap brilloImagen(Bitmap bitmap_original, int brillo){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = brilloPixel(bitmap_original,x,y,brillo);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
//
//
//	/**
//	 * Solariza el píxel en caso de que éste supere el nivel de brillo permitido, en este caso 128 sobre 255
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
//	 * @param x Coordenada x del píxel.
//	 * @param y Coordenada y del píxel.
//	 */
//	public int solarizarPixel(Bitmap bitmap_original, int x, int y){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//		int nuevoColor, nuevoR, nuevoG, nuevoB;
//
//		if(r < 128){
//			nuevoR = 255 - r;
//		}else nuevoR = r;
//
//		if(g < 128){
//			nuevoG = 255 - g;
//		}else nuevoG = g;
//
//		if(b < 128){
//			nuevoB = 255 - b;
//		}else nuevoB = b;
//
//		nuevoColor = Color.argb(a,nuevoR,nuevoG,nuevoB);
//
//		return nuevoColor;
//	}
//
//	/**
//	 * Solariza la imagen, utilizando el metodo solarizarPixel para todos los píxeles.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
//	 */
//	public Bitmap solarizarImagen(Bitmap bitmap_original){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = solarizarPixel(bitmap_original,x,y);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
//
//	/**
//	 * Posteriza el píxel según el umbral estimado, esto es que limita el número de colores del píxel.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
//	 * @param x Coordenada x del píxel.
//	 * @param y Coordenada y del píxel.
//	 */
//	public int posterizarPixel(Bitmap bitmap_original, int x, int y, int umbral){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//		int nuevoColor;
//
//		if (umbral < 1) umbral = 1;
//
//		int nuevoR = (r-(r%umbral));
//		int nuevoG = (g-(g%umbral));
//		int nuevoB = (b-(b%umbral));
//
//		if(nuevoR < 0) nuevoR =0;
//		if(nuevoG < 0) nuevoG =0;
//		if(nuevoB < 0) nuevoB =0;
//
//		if(nuevoR > 255) nuevoR =255;
//		if(nuevoG > 255) nuevoG =255;
//		if(nuevoB > 255) nuevoB =255;
//
//		nuevoColor = Color.argb(a,nuevoR,nuevoG,nuevoB);
//
//		return nuevoColor;
//	}
//
//	/**
//	 * Posteriza la imagen según el umbral estimado, esto es que limita el número de colores del píxel.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
//	 */
//	public Bitmap posterizarImagen(Bitmap bitmap_original, int umbral){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = posterizarPixel(bitmap_original,x,y,umbral);
//				bitmap_aux.setPixel(x, y, color);
//			}
//		return bitmap_aux;
//	}
//
//	/**
//	 * Cambia el nivel de contraste de un píxel de la imagen.
//	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
//	 * @param x Coordenada x del píxel.
//	 * @param y Coordenada y del píxel.
//	 * @param contrasteAngulo Nivel del contraste de la nueva imagen.
//	 */
//	public int contrastePixel(Bitmap bitmap_original, int x, int y, int contrasteAngulo){
//
//		int color_actual = bitmap_original.getPixel(x, y);
//
//		int a = Color.alpha(color_actual);
//		int r = Color.red(color_actual);
//		int g = Color.green(color_actual);
//		int b = Color.blue(color_actual);
//
//		double contraste = Math.tan(contrasteAngulo*Math.PI/180.0); //Contraste en radianes
//
//		int nR,nG,nB;
//		double cValorR,cValorG,cValorB;
//
//
//		cValorR = 128+(r-128)*contraste;
//		if(cValorR<0) cValorR = 0;
//		if(cValorR>255) cValorR = 255;
//
//		cValorG = 128+(g-128)*contraste;
//		if(cValorG<0) cValorG = 0;
//		if(cValorG>255) cValorG = 255;
//
//		cValorB = 128+(b-128)*contraste;
//		if(cValorB<0) cValorB = 0;
//		if(cValorB>255) cValorB = 255;
//
//		nR = (int) cValorR;
//		nG = (int) cValorG;
//		nB = (int) cValorB;
//
//		int nuevo_color = Color.argb(a, nR, nG, nB);
//
//		return nuevo_color;
//	}
	
	/**
	 * Cambia el nivel de contraste de un píxel de la imagen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen.
	 * @param contrasteAngulo Nivel del contraste de la nueva imagen.
	 */
//	public Bitmap contrasteImagen(Bitmap bitmap_original, int contrasteAngulo){
//
//		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
//		int color;
//		for(int x=0; x<bitmap_original.getWidth(); x++)
//			for(int y=0; y<bitmap_original.getHeight(); y++){
//				color = contrastePixel(bitmap_original,x,y,contrasteAngulo);
//				bitmap_aux.setPixel(x, y, color);
//			}
//
//		return bitmap_aux;
//	}
	
/*
 *Métodos Avanzados
 */
	
	/**
	 * Método que genera una nueva imgen atenuada utilizando el método filtroPasoBajo.
	 * 
	 */
	public Bitmap pasoBajo(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroPasoBajo(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método que cambia el contraste de un solo píxel, aplicando una máscara de vecindad.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroPasoBajo(Bitmap bitmap_original,int x, int y){
		
		int pixelNuevoR,pixelNuevoG,pixelNuevoB;
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1);
		int p2 = bitmap_original.getPixel(x, y-1);
		int p3 = bitmap_original.getPixel(x+1, y-1);
		int p4 = bitmap_original.getPixel(x-1, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p6 = bitmap_original.getPixel(x-1, y+1);
		int p7 = bitmap_original.getPixel(x, y+1);
		int p8 = bitmap_original.getPixel(x+1, y+1);
		int a = Color.alpha(pixel);
		
		
		pixelNuevoR = (int) (0.1*Color.red(p1) +0.1*Color.red(p2) +0.1*Color.red(p3)
				+0.1*Color.red(p4) +0.1*Color.red(pixel) +0.1*Color.red(p5)
				+0.1*Color.red(p6) +0.1*Color.red(p7) +0.1*Color.red(p8));
		
		pixelNuevoG = (int) (0.1*Color.green(p1) +0.1*Color.green(p2) +0.1*Color.green(p3)
				+0.1*Color.green(p4) +0.1*Color.green(pixel) +0.1*Color.green(p5)
				+0.1*Color.green(p6) +0.1*Color.green(p7) +0.1*Color.green(p8));
		
		pixelNuevoB = (int) (0.1*Color.blue(p1) +0.1*Color.blue(p2) +0.1*Color.blue(p3)
				+0.1*Color.blue(p4) +0.1*Color.blue(pixel) +0.1*Color.blue(p5)
				+0.1*Color.blue(p6) +0.1*Color.blue(p7) +0.1*Color.blue(p8));
		
		
		int pixelNuevo = Color.argb(a,pixelNuevoR,pixelNuevoG,pixelNuevoB);
		return pixelNuevo;
	}
	
	/**
	 * Método que genera una nueva imgen atenuada utilizando el método filtroMediana.
	 * 
	 */
	public Bitmap mediana(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroMediana(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}

	/**
	 * Método que cambia suaviza un píxel, aplicando una máscara de vecindad, mediante el filtro de mediana.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroMediana(Bitmap bitmap_original,int x, int y){
		
		int pixelNuevoR,pixelNuevoG,pixelNuevoB;
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1);
		int p2 = bitmap_original.getPixel(x, y-1);
		int p3 = bitmap_original.getPixel(x+1, y-1);
		int p4 = bitmap_original.getPixel(x-1, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p6 = bitmap_original.getPixel(x-1, y+1);
		int p7 = bitmap_original.getPixel(x, y+1);
		int p8 = bitmap_original.getPixel(x+1, y+1);
		int a = Color.alpha(pixel);
			
		int [] arrayVecinosR = new int [9];
		int [] arrayVecinosG = new int [9];
		int [] arrayVecinosB = new int [9];
				
		arrayVecinosR[0]=Color.red(p1);
		arrayVecinosR[1]=Color.red(p2);
		arrayVecinosR[2]=Color.red(p3);
		arrayVecinosR[3]=Color.red(p4);
		arrayVecinosR[4]=Color.red(pixel);
		arrayVecinosR[5]=Color.red(p5);
		arrayVecinosR[6]=Color.red(p6);
		arrayVecinosR[7]=Color.red(p7);
		arrayVecinosR[8]=Color.red(p8);
				
		arrayVecinosG[0]=Color.green(p1);
		arrayVecinosG[1]=Color.green(p2);
		arrayVecinosG[2]=Color.green(p3);
		arrayVecinosG[3]=Color.green(p4);
		arrayVecinosG[4]=Color.green(pixel);
		arrayVecinosG[5]=Color.green(p5);
		arrayVecinosG[6]=Color.green(p6);
		arrayVecinosG[7]=Color.green(p7);
		arrayVecinosG[8]=Color.green(p8);
		
		arrayVecinosB[0]=Color.blue(p1);
		arrayVecinosB[1]=Color.blue(p2);
		arrayVecinosB[2]=Color.blue(p3);
		arrayVecinosB[3]=Color.blue(p4);
		arrayVecinosB[4]=Color.blue(pixel);
		arrayVecinosB[5]=Color.blue(p5);
		arrayVecinosB[6]=Color.blue(p6);
		arrayVecinosB[7]=Color.blue(p7);
		arrayVecinosB[8]=Color.blue(p8);
			
		burbuja(arrayVecinosR);
		burbuja(arrayVecinosG);
		burbuja(arrayVecinosB);
				
		pixelNuevoR = arrayVecinosR[4];
		pixelNuevoG = arrayVecinosG[4];
		pixelNuevoB = arrayVecinosB[4];
		
		int pixelNuevo = Color.argb(a,pixelNuevoR,pixelNuevoG,pixelNuevoB);
		return pixelNuevo;
	}
	
	
	
	/**
	 * Algoritmo de ordenación que ordena los valores de un vector.
	 * @param array Vector que pretende ordenarse
	 */
	private void burbuja(int [] array){
		int aux;
		
		for(int i=0; i<array.length-1;i++){ //Hace n-1 pasadas
			for(int j=0; j<array.length-i-1;j++){ //Mirar los N-i-1 pares.
				
				if(array[j+1]<array[j]){ // Si el elemento j+1 es menor que el elemento j:
					aux=array[j+1]; // Se intercambian los elementos
				    array[j+1]=array[j]; // de las posiciones j y j+1
				    array[j]=aux; // usando una variable auxiliar.
				}
			}
		}
	}
	
	
	
	/**
	 * Método que genera una nueva imgen mostrando el gradiente.
	 * 
	 */
	public Bitmap gradiente(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroGradiente(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}

	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante su gradiente.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroGradiente(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p7 = bitmap_original.getPixel(x, y+1);
		int a = Color.alpha(pixel);
		
		int Gr = Math.abs(Color.red(pixel) - Color.red(p5)) + Math.abs(Color.red(pixel) - Color.red(p7));
		int Gg = Math.abs(Color.green(pixel) - Color.green(p5)) + Math.abs(Color.green(pixel) - Color.green(p7));
		int Gb = Math.abs(Color.blue(pixel) - Color.blue(p5)) + Math.abs(Color.blue(pixel) - Color.blue(p7));
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
	}
	
	
	/**
	 * Método que genera una nueva imgen mostrando los bordes obtenidos con el método de Sobel.
	 * 
	 */
	public Bitmap sobel(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroSobel(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante la máscara de Sobel.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroSobel(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1); //topLeft
		int p2 = bitmap_original.getPixel(x, y-1); //top
		int p3 = bitmap_original.getPixel(x+1, y-1); //topRight
		int p4 = bitmap_original.getPixel(x-1, y); //Left
		int p5 = bitmap_original.getPixel(x+1,y); // Right
		int p6 = bitmap_original.getPixel(x-1, y+1); // bottomLeft
		int p7 = bitmap_original.getPixel(x, y+1); // bottom
		int p8 = bitmap_original.getPixel(x+1, y+1); // bottomRight
		int a = Color.alpha(pixel);
		
		/* Gx Vertical	Gy Horizontal
		 * -1	0	1	-1	-2	-1
		 * -2	0	2	0	0	0
		 * -1	0	1	1	2	1
		 */
		
		int Gr = Math.abs(-Color.red(p1)+Color.red(p3)
				-2*Color.red(p4)+2*Color.red(p5)
				-Color.red(p6)+Color.red(p8))

				+ Math.abs(-Color.red(p1)-2*Color.red(p2)
				-Color.red(p3)+Color.red(p6)
				+2*Color.red(p7)+Color.red(p8));
		int Gg = Math.abs(-Color.green(p1)+Color.green(p3)-2*Color.green(p4)+2*Color.green(p5)-Color.green(p6)+Color.green(p8))
				+ Math.abs(-Color.green(p1)-2*Color.green(p2)-Color.green(p3)+Color.green(p6)+2*Color.green(p7)+Color.green(p8));
		int Gb = Math.abs(-Color.blue(p1)+Color.blue(p3)-2*Color.blue(p4)+2*Color.blue(p5)-Color.blue(p6)+Color.blue(p8))
				+ Math.abs(-Color.blue(p1)-2*Color.blue(p2)-Color.blue(p3)+Color.blue(p6)+2*Color.blue(p7)+Color.blue(p8));
		
		if(Gr>255) Gr=255;
		else if(Gr<0) Gr =0;
		
		if(Gg>255) Gg=255;
		else if(Gg<0) Gg =0;
		
		if(Gb>255) Gb=255;
		else if(Gb<0) Gb =0;
		
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
		
	}
	
	
	
	/**
	 * Método que genera una nueva imgen mostrando los bordes obtenidos con el método de Prewitt.
	 * 
	 */
	public Bitmap prewitt(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroPrewitt(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante la máscara de Prewitt.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroPrewitt(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1);
		int p2 = bitmap_original.getPixel(x, y-1);
		int p3 = bitmap_original.getPixel(x+1, y-1);
		int p4 = bitmap_original.getPixel(x-1, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p6 = bitmap_original.getPixel(x-1, y+1);
		int p7 = bitmap_original.getPixel(x, y+1);
		int p8 = bitmap_original.getPixel(x+1, y+1);
		int a = Color.alpha(pixel);
		
		/* Gx Vertical	Gy Horizontal
		 * -1	0	1	-1	-1	-1
		 * -1	0	1	0	0	0
		 * -1	0	1	1	1	1
		 */

		int Gr= Math.abs(-Color.red(p1)+Color.red(p3)-Color.red(p4)+Color.red(p5)-Color.red(p6)+Color.red(p8)) 
				+ Math.abs(Color.red(p1)+Color.red(p2)+Color.red(p3)-Color.red(p6)-Color.red(p7)-Color.red(p8));
		int Gg= Math.abs(-Color.green(p1)+Color.green(p3)-Color.green(p4)+Color.green(p5)-Color.green(p6)+Color.green(p8))
				+ Math.abs(Color.green(p1)+Color.green(p2)+Color.green(p3)-Color.green(p6)-Color.green(p7)-Color.green(p8));
		int Gb= Math.abs(-Color.blue(p1)+Color.blue(p3)-Color.blue(p4)+Color.blue(p5)-Color.blue(p6)+Color.blue(p8))
				+ Math.abs(Color.blue(p1)+Color.blue(p2)+Color.blue(p3)-Color.blue(p6)-Color.blue(p7)-Color.blue(p8));
		
		if(Gr>255) Gr=255;
		else if(Gr<0) Gr =0;
		
		if(Gg>255) Gg=255;
		else if(Gg<0) Gg =0;
		
		if(Gb>255) Gb=255;
		else if(Gb<0) Gb =0;
		
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
		
	}
	
	
	/**
	 * Método que genera una nueva imgen mostrando los bordes obtenidos con el método de Prewitt.
	 * 
	 */
	public Bitmap roberts(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroRoberts(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante la máscara de Prewitt.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroRoberts(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p7 = bitmap_original.getPixel(x, y+1);
		int p8 = bitmap_original.getPixel(x+1, y+1);
		int a = Color.alpha(pixel);
		
		/* Gx Vertical	Gy Horizontal
		 * 1	0		0	1
		 * 0	-1		-1	0
		 */
		
		int Gr = Math.abs(Color.red(pixel) - Color.red(p8)) + Math.abs(Color.red(p5)-Color.red(p7)); 
		int Gg = Math.abs(Color.green(pixel) - Color.green(p8)) + Math.abs(Color.green(p5)-Color.green(p7));
		int Gb = Math.abs(Color.blue(pixel) - Color.blue(p8)) + Math.abs(Color.blue(p5)-Color.blue(p7));
		
		if(Gr>255) Gr=255;
		else if(Gr<0) Gr =0;
		
		if(Gg>255) Gg=255;
		else if(Gg<0) Gg =0;
		
		if(Gb>255) Gb=255;
		else if(Gb<0) Gb =0;
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
	}
	
	
	
	/**
	 * Método que genera una nueva imgen mostrando los bordes obtenidos con el método de Frei-Chen.
	 * 
	 */
	public Bitmap freiChen(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroFreiChen(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante las máscaras de Frei-Chen.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroFreiChen(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1); //topLeft
		int p2 = bitmap_original.getPixel(x, y-1); //top
		int p3 = bitmap_original.getPixel(x+1, y-1); //topRight
		int p4 = bitmap_original.getPixel(x-1, y); //Left
		int p5 = bitmap_original.getPixel(x+1,y); // Right
		int p6 = bitmap_original.getPixel(x-1, y+1); // bottomLeft
		int p7 = bitmap_original.getPixel(x, y+1); // bottom
		int p8 = bitmap_original.getPixel(x+1, y+1); // bottomRight
		int a = Color.alpha(pixel);
		float R2 = (float) Math.sqrt(2);

		//p1  topLeft
		//p2  top
		//p3  topRight
		//p4  Left
		//p5  Right
		//p6  bottomLeft
		//p7  bottom
		//p8  bottomRight


		/* Gx Vertical	Gy Horizontal
		 * -1	0	1	-1	-1	-1
		 * -1	0	1	0	0	0
		 * -1	0	1	1	1	1
		 */

		
		int Gr= (int) ((1/(2*R2))*(Math.abs(Color.red(p1)+R2*Color.red(p2)+Color.red(p3)
				-Color.red(p6)-R2*Color.red(p7)-Color.red(p8))
				+(1/(2*R2))*(Math.abs(Color.red(p1)-Color.red(p3)+R2*Color.red(p4)
				-R2*Color.red(p5)+Color.red(p6)-Color.red(p8)))
				+(1/(2*R2))*(Math.abs(-Color.red(p2)+R2*Color.red(p3)+Color.red(p4)
				-Color.red(p5)-R2*Color.red(p6)+Color.red(p7)))
				+(1/(2*R2))*(Math.abs(R2*Color.red(p1)-Color.red(p2)-Color.red(p4)
				+Color.red(p5)+Color.red(p7)-R2*Color.red(p8))))
				+(1/2)*(Math.abs(Color.red(p2)-Color.red(p4)-Color.red(p5)+Color.red(p7)))
				+(1/2)*(Math.abs(-Color.red(p1)+Color.red(p3)+Color.red(p6)-Color.red(p8)))
				+(1/6)*(Math.abs(Color.red(p1)-2*Color.red(p2)+Color.red(p3)
				-2*Color.red(p4)+4*Color.red(pixel)-2*Color.red(p5)
				+Color.red(p6)-2*Color.red(p7)+Color.red(p8)))
				+(1/6)*(Math.abs(-2*Color.red(p1)+Color.red(p2)-2*Color.red(p3)
				+Color.red(p4)+4*Color.red(pixel)+Color.red(p5)
				-2*Color.red(p6)+Color.red(p7)-2*Color.red(p8)))
				+(1/3)*(Math.abs(Color.red(p1)+Color.red(p2)+Color.red(p3)
				+Color.red(p4)+Color.red(pixel)+Color.red(p5)
				+Color.red(p6)+Color.red(p7)+Color.red(p8))));
		
		int Gg= (int) ((1/(2*R2))*(Math.abs(Color.green(p1)+R2*Color.green(p2)+Color.green(p3)-Color.green(p6)-R2*Color.green(p7)-Color.green(p8)) 
				+(1/(2*R2))*(Math.abs(Color.green(p1)-Color.green(p3)+R2*Color.green(p4)-R2*Color.green(p5)+Color.green(p6)-Color.green(p8)))			
				+(1/(2*R2))*(Math.abs(-Color.green(p2)+R2*Color.green(p3)+Color.green(p4)-Color.green(p5)-R2*Color.green(p6)+Color.green(p7)))
				+(1/(2*R2))*(Math.abs(R2*Color.green(p1)-Color.green(p2)-Color.green(p4)+Color.green(p5)+Color.green(p7)-R2*Color.green(p8))))
				+(1/2)*(Math.abs(Color.green(p2)-Color.green(p4)-Color.green(p5)+Color.green(p7)))
				+(1/2)*(Math.abs(-Color.green(p1)+Color.green(p3)+Color.green(p6)-Color.green(p8)))
				+(1/6)*(Math.abs(Color.green(p1)-2*Color.green(p2)+Color.green(p3)-2*Color.green(p4)+4*Color.green(pixel)-2*Color.green(p5)+Color.green(p6)-2*Color.green(p7)+Color.green(p8)))
				+(1/6)*(Math.abs(-2*Color.green(p1)+Color.green(p2)-2*Color.green(p3)+Color.green(p4)+4*Color.green(pixel)+Color.green(p5)-2*Color.green(p6)+Color.green(p7)-2*Color.green(p8)))
				+(1/3)*(Math.abs(Color.green(p1)+Color.green(p2)+Color.green(p3)+Color.green(p4)+Color.green(pixel)+Color.green(p5)+Color.green(p6)+Color.green(p7)+Color.green(p8))));
		
		int Gb= (int) ((1/(2*R2))*(Math.abs(Color.blue(p1)+R2*Color.blue(p2)+Color.blue(p3)-Color.blue(p6)-R2*Color.blue(p7)-Color.blue(p8)) 
				+(1/(2*R2))*(Math.abs(Color.blue(p1)-Color.blue(p3)+R2*Color.blue(p4)-R2*Color.blue(p5)+Color.blue(p6)-Color.blue(p8)))
				+(1/(2*R2))*(Math.abs(-Color.blue(p2)+R2*Color.blue(p3)+Color.blue(p4)-Color.blue(p5)-R2*Color.blue(p6)+Color.blue(p7)))
				+(1/(2*R2))*(Math.abs(R2*Color.blue(p1)-Color.blue(p2)-Color.blue(p4)+Color.blue(p5)+Color.blue(p7)-R2*Color.blue(p8))))
				+(1/2)*(Math.abs(Color.blue(p2)-Color.blue(p4)-Color.blue(p5)+Color.blue(p7)))
				+(1/2)*(Math.abs(-Color.blue(p1)+Color.blue(p3)+Color.blue(p6)-Color.blue(p8)))
				+(1/6)*(Math.abs(Color.blue(p1)-2*Color.blue(p2)+Color.blue(p3)-2*Color.blue(p4)+4*Color.blue(pixel)-2*Color.blue(p5)+Color.blue(p6)-2*Color.blue(p7)+Color.blue(p8)))
				+(1/6)*(Math.abs(-2*Color.blue(p1)+Color.blue(p2)-2*Color.blue(p3)+Color.blue(p4)+4*Color.blue(pixel)+Color.blue(p5)-2*Color.blue(p6)+Color.blue(p7)-2*Color.blue(p8)))
				+(1/3)*(Math.abs(Color.blue(p1)+Color.blue(p2)+Color.blue(p3)+Color.blue(p4)+Color.blue(pixel)+Color.blue(p5)+Color.blue(p6)+Color.blue(p7)+Color.blue(p8))));
		
		if(Gr>255) Gr=255;
		else if(Gr<0) Gr =0;
		
		if(Gg>255) Gg=255;
		else if(Gg<0) Gg =0;
		
		if(Gb>255) Gb=255;
		else if(Gb<0) Gb =0;
		
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
		
	}
	
	
	/**
	 * Método que genera una nueva imagen realzada utilizando una imagen con el operador Laplaciana.
	 */
	public Bitmap realceLaplaciana(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroLaplaciana(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return restaImag(bitmap_original,bitmap_aux);
	}
	
	
	/**
	 * Método que genera una nueva imgen filtrada con una operador Lapalaciana.
	 * 
	 */
	public Bitmap laplaciana(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroLaplaciana(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return bitmap_aux;
	}
	
	/**
	 * Método de extracción de bordes, extrae los bordes de la imagen mediante las máscaras de Laplaciana.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroLaplaciana(Bitmap bitmap_original,int x, int y){
		
		int pixel = bitmap_original.getPixel(x, y);
		int p2 = bitmap_original.getPixel(x, y-1);
		int p4 = bitmap_original.getPixel(x-1, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p7 = bitmap_original.getPixel(x, y+1);
		int a = Color.alpha(pixel);
		
		/* Máscara laplaciana
		 *  0	-1	 0
		 * -1	 4	-1
		 *  0	-1	 0
		 */

		int Gr= -1*(-Color.red(p2)-Color.red(p4)+4*Color.red(pixel)-Color.red(p5)-Color.red(p7));
		int Gg= -1*(-Color.red(p2)-Color.red(p4)+4*Color.red(pixel)-Color.red(p5)-Color.red(p7));
		int Gb= -1*(-Color.red(p2)-Color.red(p4)+4*Color.red(pixel)-Color.red(p5)-Color.red(p7));

		if(Gr>255) Gr=255;
		else if(Gr<0) Gr =0;
		
		if(Gg>255) Gg=255;
		else if(Gg<0) Gg =0;
		
		if(Gb>255) Gb=255;
		else if(Gb<0) Gb =0;
		
		int pixelNuevo = Color.argb(a,Gr,Gg,Gb);
		return pixelNuevo;
		
	}
	
	
	/**
	 * Método que genera una nueva imgen con los bordes resaltados utilizando el método filtroPasoAlto.
	 * 
	 */
	public Bitmap pasoAlto(Bitmap bitmap_original){
	
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroPasoAlto(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		
		return bitmap_aux;		
	}
	
	
	/**
	 * Método que cambia el contraste de un solo píxel, aplicando una máscara de vecindad.
	 * @param bitmap_original Bitmap sobre el que se va a escribir la nueva imagen
	 * @param x Coordenada x del píxel
	 * @param y Coordenada y del píxel
	 */
	public int filtroPasoAlto(Bitmap bitmap_original,int x, int y){
		
		int pixelNuevoR,pixelNuevoG,pixelNuevoB;
		
		int pixel = bitmap_original.getPixel(x, y);
		int p1 = bitmap_original.getPixel(x-1, y-1);
		int p2 = bitmap_original.getPixel(x, y-1);
		int p3 = bitmap_original.getPixel(x+1, y-1);
		int p4 = bitmap_original.getPixel(x-1, y);
		int p5 = bitmap_original.getPixel(x+1,y);
		int p6 = bitmap_original.getPixel(x-1, y+1);
		int p7 = bitmap_original.getPixel(x, y+1);
		int p8 = bitmap_original.getPixel(x+1, y+1);
		int a = Color.alpha(pixel);
		
		pixelNuevoR = (int) ((-0.143*Color.red(p1) -0.286*Color.red(p2) -0.143*Color.red(p3)
				-0.286*Color.red(p4) +2.717*Color.red(pixel) -0.286*Color.red(p5)
				-0.143*Color.red(p6)) -0.286*Color.red(p7) -0.143*Color.red(p8));

		pixelNuevoG = (int) ((-0.143*Color.green(p1) -0.286*Color.green(p2) -0.143*Color.green(p3)
				-0.286*Color.green(p4) +2.717*Color.green(pixel) -0.286*Color.green(p5)
				-0.143*Color.green(p6)) -0.286*Color.green(p7) -0.143*Color.green(p8));
				
		pixelNuevoB = (int) ((-0.143*Color.blue(p1) -0.286*Color.blue(p2) -0.143*Color.blue(p3)
				-0.286*Color.blue(p4) +2.717*Color.blue(pixel) -0.286*Color.blue(p5)
				-0.143*Color.blue(p6)) -0.286*Color.blue(p7) -0.143*Color.blue(p8));
		
		
		if (pixelNuevoR > 255) pixelNuevoR = 255;
		else if(pixelNuevoR<0) pixelNuevoR = 0;
		
		if (pixelNuevoG > 255) pixelNuevoG = 255;
		else if(pixelNuevoG<0) pixelNuevoG = 0;
		
		if (pixelNuevoB > 255) pixelNuevoB = 255;
		else if(pixelNuevoB<0) pixelNuevoB = 0;
	
		int pixelNuevo = Color.argb(a,pixelNuevoR,pixelNuevoG,pixelNuevoB);
		return pixelNuevo;
	}
	
	
	/**
	 * Método que resta dos imágenes.
	 * 
	 * @param imag1 Imagen 1, minuendo
	 * @param imag2 Imagen 2, sustraendo
	 * @return bitmap_destino Imagen resta
	 */
	public Bitmap restaImag(Bitmap imag1, Bitmap imag2){
		
		Bitmap bitmap_destino = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);

		for(int x=2; x<bitmap_original.getWidth()-2; x++)
			for(int y=2; y<bitmap_original.getHeight()-2; y++){
				
				int a1 = Color.alpha(imag1.getPixel(x, y));
				int a2 = Color.alpha(imag2.getPixel(x, y));
				int r1 = Color.red(imag1.getPixel(x, y));
				int r2 = Color.red(imag2.getPixel(x, y));
				int g1 = Color.green(imag1.getPixel(x, y));
				int g2 = Color.green(imag2.getPixel(x, y));
				int b1 = Color.blue(imag1.getPixel(x, y));
				int b2 = Color.blue(imag2.getPixel(x, y));
				
				int nuevoA = Color.TRANSPARENT;
				
				if(a1 >= a2){
					nuevoA = a1;
				}else nuevoA = a2;
				
				int nuevoR = r1-r2;
				int nuevoG = g1-g2;
				int nuevoB = b1-b2;
				
				if(nuevoR < 0) nuevoR =0;
				if(nuevoG < 0) nuevoG =0;
				if(nuevoB < 0) nuevoB =0;
				
				int pixelNuevo = Color.argb(nuevoA,nuevoR,nuevoG,nuevoB);
				
				bitmap_destino.setPixel(x, y, pixelNuevo);
			}
		return bitmap_destino;
	}
	
	/**
	 * Método que suma dos imágenes.
	 * 
	 * @param imag1 Imagen 1, sumando 1
	 * @param imag2 Imagen 2, sumando 2
	 * @return bitmap_destino Imagen suma
	 */
	public Bitmap sumaImag(Bitmap imag1, Bitmap imag2){
		
		Bitmap bitmap_destino = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);

		for(int x=2; x<bitmap_original.getWidth()-2; x++)
			for(int y=2; y<bitmap_original.getHeight()-2; y++){
				
				int a1 = Color.alpha(imag1.getPixel(x, y));
				int a2 = Color.alpha(imag2.getPixel(x, y));
				int r1 = Color.red(imag1.getPixel(x, y));
				int r2 = Color.red(imag2.getPixel(x, y));
				int g1 = Color.green(imag1.getPixel(x, y));
				int g2 = Color.green(imag2.getPixel(x, y));
				int b1 = Color.blue(imag1.getPixel(x, y));
				int b2 = Color.blue(imag2.getPixel(x, y));
				
				int nuevoA = Color.TRANSPARENT;
				
				if(a1 >= a2){
					nuevoA = a1;
				}else nuevoA = a2;
				
				int nuevoR = r1+r2;
				int nuevoG = g1+g2;
				int nuevoB = b1+b2;
				
				if(nuevoR > 255) nuevoR =255;
				if(nuevoG > 255) nuevoG =255;
				if(nuevoB > 255) nuevoB =255;
				
				int pixelNuevo = Color.argb(nuevoA,nuevoR,nuevoG,nuevoB);
				
				bitmap_destino.setPixel(x, y, pixelNuevo);
			}
		return bitmap_destino;
	}
	
	
	/**
	 * Método que genera una nueva imagen realzada utilizando una imagen con el operador Laplaciana.
	 */
	public Bitmap suavizadoRoberts(Bitmap bitmap_original){
		
		Bitmap bitmap_aux = Bitmap.createBitmap (bitmap_original.getWidth(),bitmap_original.getHeight(), Bitmap.Config.ARGB_8888);
		int pixel;
		for(int x=1; x<bitmap_original.getWidth()-1; x++)
			for(int y=1; y<bitmap_original.getHeight()-1; y++){
				pixel = filtroRoberts(bitmap_original,x,y);
				bitmap_aux.setPixel(x, y, pixel);
			}
		return sumaImag(bitmap_original,bitmap_aux);
	}
	
	
}
