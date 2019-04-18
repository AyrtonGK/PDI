package br.edu.PDI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Principal {
	public static void main(String[] args) {

		BufferedImage img = readFile(new File("Origem.PNG"));
		int[][] matriz = img2mat(img);

		System.out.print(matriz.length + "-" + img.getHeight());
	}

	public static BufferedImage readFile(File arquivo) {
		BufferedImage buffImg = null;

		try {
			buffImg = ImageIO.read(arquivo);
		} catch (IOException e) {
		}

		return buffImg;
	}

	public static int[][] img2mat(BufferedImage img) {

		int height = img.getHeight();
		int width = img.getWidth();

		height = proximaPotencia2(height);
		width = proximaPotencia2(width);

		int[][] matriz = new int[height][width];

		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				matriz[i][j] = 0;

		for (int i = 0; i < img.getHeight(); i++)
			for (int j = 0; j < img.getWidth(); j++)
				matriz[i][j] = img.getRGB(j, i);

		return matriz;
	}

	public static int proximaPotencia2(int numero) {
		int highestOneBit = Integer.highestOneBit(numero);
		return (numero == highestOneBit ? numero : highestOneBit << 1);
	}
}