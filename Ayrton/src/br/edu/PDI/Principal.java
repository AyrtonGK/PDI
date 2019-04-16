package br.edu.PDI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.frank.math.Complex;

public class Principal {
	public static void main(String[] args) {

		BufferedImage img = readFile(new File("Origem.PNG"));
		Complex[][] matriz = img2comp(img);
		Complex[][] transformada = TwoD_FFT.fft(matriz);

		try {
			ImageIO.write(Imagem.trans2img(transformada), "jpg", new File("Transformada.PNG"));
		} catch (IOException e) {
		}
		
		/* Complex[][] tratada = TwoD_FFT.ifft(transformada, true);
		 * try { ImageIO.write(comp2img(tratada), "jpg", new File("Tratada.PNG")); }
		 * catch (IOException e) { }
		 */
	}

	public static BufferedImage readFile(File arquivo) {
		BufferedImage buffImg = null;

		try {
			buffImg = ImageIO.read(arquivo);
		} catch (IOException e) {
		}
		return buffImg;
	}

	public static BufferedImage comp2img(Complex[][] matriz) {

		BufferedImage img = new BufferedImage(matriz.length, matriz[0].length, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < img.getHeight(); i++)
			for (int j = 0; j < img.getWidth(); j++)
				img.setRGB(j, i, (int) matriz[i][j].real);

		return img;
	}

	public static Complex[][] img2comp(BufferedImage img) {

		int height = img.getHeight();
		int width = img.getWidth();

		height = proximaPotencia2(height);
		width = proximaPotencia2(width);

		Complex[][] matriz = new Complex[height][width];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				if (i >= img.getHeight() || j >= img.getWidth())
					matriz[i][j] = new Complex(0, 0);
				else
					matriz[i][j] = new Complex(img.getRGB(j, i), 0);

		return matriz;
	}

	public static int proximaPotencia2(int numero) {
		int highestOneBit = Integer.highestOneBit(numero);
		return (numero == highestOneBit ? numero : highestOneBit << 1);
	}

	public static double[][] int2double(int[][] ints) {
		double[][] doubles = new double[ints.length][ints[0].length];
		for (int i = 0; i < ints.length; i++)
			for (int j = 0; j < ints[0].length; j++)
				doubles[i][j] = ints[i][j];

		return doubles;
	}

	public static int[][] double2int(double[][] doubles) {
		int[][] ints = new int[doubles.length][doubles[0].length];
		for (int i = 0; i < doubles.length; i++)
			for (int j = 0; j < doubles[0].length; j++)
				ints[i][j] = (int) doubles[i][j];

		return ints;
	}
}