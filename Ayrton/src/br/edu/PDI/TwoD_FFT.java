package br.edu.PDI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.opencv.imgproc.*;

public class TwoD_FFT {
	/**
	 * this clsas provides the methods for the 2D-Fourier transformation
	 * 
	 * @param amplitutude
	 */
	static void twoDDft(double[][] inputData, double[][] realOut,

			double[][] complexOut, double[][] amplitudeOut) {
		int height = inputData.length;
		int width = inputData[0].length;

		// Two outer loops iterate on output data.
		for (int yWave = 0; yWave < height; yWave++) {
			for (int xWave = 0; xWave < width; xWave++) {
				// Two inner loops iterate on input data.
				for (int ySpace = 0; ySpace < height; ySpace++) {
					for (int xSpace = 0; xSpace < width; xSpace++) {
						// Compute real, imag, and ampltude.
						realOut[yWave][xWave] += (inputData[ySpace][xSpace] * Math
								.cos(2 * Math.PI * ((1.0 * xWave * xSpace / width) + (1.0 * yWave * ySpace / height))))
								/ Math.sqrt(width * height);
						complexOut[yWave][xWave] -= (inputData[ySpace][xSpace] * Math
								.sin(2 * Math.PI * ((1.0 * xWave * xSpace / width) + (1.0 * yWave * ySpace / height))))
								/ Math.sqrt(width * height);
						amplitudeOut[yWave][xWave] = Math.sqrt(realOut[yWave][xWave] * realOut[yWave][xWave]
								+ complexOut[yWave][xWave] * complexOut[yWave][xWave]);
					}
					System.out.println(amplitudeOut[yWave][xWave]);

				}
			}
		}
	}

	@SuppressWarnings("null")
	static void twoDfft(double[][] inputData, double[][] realOut, double[][] complexOut, double[][] amplitudeOut) {
		int height = inputData.length;
		int width = inputData[0].length;
		double[][] real = new double[width][height], complex = new double[width][height];

		// Two outer loops iterate on output data.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Two inner loops iterate on input data.
				for (int u = 0; u < height; u++) {

					// Compute real, imag, and ampltude.
					real[x][y] += (inputData[x][u]
							* Math.sin(2 * Math.PI * ((1.0 * x * u / height))));
					complex[x][y] -= (inputData[x][u]
							* Math.cos(2 * Math.PI * ((1.0 * x * u / height))));
				}
			}
		}
		height = realOut[0].length;
		width = realOut[0].length;
		
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				// Two inner loops iterate on input data.
				for (int v = 0; v < height; v++) {

					// Compute real, imag, and ampltude.
					realOut[x][y] += (real[v][y] * Math.sin(2 * Math.PI * ((1.0 * y * v / width))));
					complexOut[x][y] -= (complex[v][y] * Math.cos(2 * Math.PI * ((1.0 * y * v / width))));
				}
				System.out.print(realOut[x][y]+"\n");
			}
		}
		
	}

	public void jButton1ActionPerformed(Double[][] img) {

		JLabel imageLabel = new JLabel();
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		System.out.println("Enter the size: ");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		double[][] input = new double[n][n];
		double[][] real = new double[n*2][n*2];
		double[][] img = new double[n*2][n*2];
		double[][] amplitude = new double[n*2][n*2];
		System.out.println("Enter the 2D elements ");
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				input[i][j] = sc.nextDouble();

		twoDfft(input, real, img, amplitude);

		sc.close();
	}
}