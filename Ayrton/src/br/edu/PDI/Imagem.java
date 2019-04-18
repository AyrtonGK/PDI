package br.edu.PDI;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.commons.math3.util.FastMath;

import com.frank.math.Complex;

public class Imagem {

	public static BufferedImage trans2img(Complex[][] matriz) {

		double[][] modulo = new double[matriz.length][matriz[0].length];
		double maxNum = Double.MIN_VALUE;

		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[0].length; j++) {

			}

		for (int i = 0; i < modulo.length; i++)
			for (int j = 0; j < modulo[0].length; j++) {
				
				final double realQuadrado = FastMath.pow(matriz[i][j].real, 2);
				final double imaginarioQuadrado = FastMath.pow(matriz[i][j].imaginary, 2);
				modulo[i][j] = FastMath.pow(FastMath.sqrt(realQuadrado + imaginarioQuadrado), 0.3F);

				if (maxNum < modulo[i][j])
					maxNum = FastMath.abs(modulo[i][j]);
			}

		modulo = fTranslacao(modulo, matriz.length / 2, matriz[0].length / 2);
		BufferedImage img = new BufferedImage(matriz.length, matriz[0].length, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < img.getHeight(); i++)
			for (int j = 0; j < img.getWidth(); j++) {

				final double luminosidade = (modulo[i][j] * 255) / maxNum;

				final Color TonCinza = new Color((int) luminosidade, (int) luminosidade, (int) luminosidade);
				img.setRGB(j, i, TonCinza.getRGB());
			}

		return img;
	}

	public static double[][] fTranslacao(double img[][], int dx, int dy) {

		double imgTMP[][] = new double[img.length][img[0].length];
		for (int i = 0; i < img.length; i++)
			for (int j = 0; j < img[0].length; j++)
				imgTMP[i][j] = img[(i + dx) % img.length][(j + dy) % img[0].length];

		return imgTMP;
	}
	
	public static BufferedImage tonsCinza(BufferedImage origem) {

		final int altura = origem.getHeight();
		final int largura = origem.getWidth();
		final BufferedImage imagemFinal = new BufferedImage(largura, altura, origem.getType());

		for (int i = 0; i < largura; i++)
			for (int j = 0; j < altura; j++) {

				final Color cor = new Color(origem.getRGB(i, j));
				final int red = cor.getRed();
				final int blue = cor.getBlue();
				final int green = cor.getGreen();

				final int luminosidade = (int) ((red * 0.3) + (green * 0.59) + (blue * 0.11));

				imagemFinal.setRGB(i, j, new Color(luminosidade, luminosidade, luminosidade).getRGB());
			}

		return imagemFinal;
	}
}
