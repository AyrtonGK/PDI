package br.edu.PDI;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.frank.math.Complex;
import com.frank.math.trans.DoubleFFT_2D;

public class TwoD_FFT {

	/**
	 * Perform the 2D Fast Fourier Transformation.
	 * 
	 * @param input the specified complex matrix, <code>x</code> must arranged as
	 *              <code>x[rows][columns]</code>
	 * @return the result of 2D Fast Fourier Transformation
	 */
	public static Complex[][] fft(Complex[][] input) {
		int nrows = input.length;
		int ncols = input[0].length;
		double[] data = new double[nrows * ncols * 2];

		for (int j = 0; j < nrows; j++)
			for (int i = 0; i < ncols; i++) {
				data[j * ncols * 2 + 2 * i] = input[j][i].real;
				data[j * ncols * 2 + 2 * i + 1] = input[j][i].imaginary;
			}

		new DoubleFFT_2D(nrows, ncols).complexForward(data);
		Complex[][] y = new Complex[nrows][ncols];

		for (int j = 0; j < nrows; j++)
			for (int i = 0; i < ncols; i++) {
				y[j][i] = new Complex(data[j * ncols * 2 + 2 * i], data[j * ncols * 2 + 2 * i + 1]);
			}
		return y;
	}

	public static Complex[][] ifft(Complex[][] input, boolean scale) {

		final int nrows = input.length;
		final int ncols = input[0].length;
		final int n = nrows * ncols;
		final double[] data = new double[n * 2];

		for (int j = 0; j < nrows; j++)
			for (int i = 0; i < ncols; i++) {
				data[j * ncols * 2 + 2 * i] = input[j][i].real;
				data[j * ncols * 2 + 2 * i + 1] = input[j][i].imaginary;
			}

		new DoubleFFT_2D(nrows, ncols).complexInverse(data, scale);
		Complex[][] x = new Complex[nrows][ncols];
		for (int j = 0; j < nrows; j++)
			for (int i = 0; i < ncols; i++)
				x[j][i] = new Complex(data[j * ncols * 2 + 2 * i], data[j * ncols * 2 + 2 * i + 1]);

		return x;
	}

	public static Complex[][] aplicaFiltro(Complex[][] origem, BufferedImage filtro) {

		final int nrows = filtro.getWidth();
		final int ncols = filtro.getHeight();

		double multiplicador[][] = new double[nrows][ncols];
		Complex[][] filtrado = new Complex[nrows][ncols];

		for (int i = 0; i < nrows; i++)
			for (int j = 0; j < ncols; j++) {
				multiplicador[i][j] = (new Color(filtro.getRGB(i, j)).getRed()) / 255D;
				System.out.print(multiplicador[i][j]);
			}

		multiplicador = Imagem.fTranslacao(multiplicador, nrows / 2, ncols / 2);

		for (int i = 0; i < multiplicador.length; i++)
			for (int j = 0; j < multiplicador[0].length; j++) {
				final double real = multiplicador[i][j] * origem[i][j].real;
				final double imaginario = origem[i][j].imaginary;
				filtrado[i][j] = new Complex(real, imaginario);
			}

		return filtrado;
	}
}