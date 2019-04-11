package br.edu.PDI;
 
public class TwoD_FFT 
{
	/**
	   this clsas provides the methods for the 2D-Fourier transformation
	 */

	public class Fourier2D {

	    /** size of matrix in M-direction */
	    private int M_2P;

	    /** size of matrix in N-direction  */
	    private int N_2P;

	    /** largest exponent, so that 2^maxPotM <= (M_2P-1) */
	    private int maxPotM;

	    /** largest exponent, so that 2^maxPotN <= (N_2P-1) */
	    private int maxPotN;


	    /** constructor for the use of the 2D transformation routines */ 
	    public Fourier2D(int M, int N) {
	        int M_tmp, N_tmp;

	        // check for power of 2
	        M_tmp = M;
	        N_tmp = N;
	        while( ((M_tmp >>= 1) != 0) && ((N_tmp >>= 1) != 0) ) {
	            if( ((M_tmp & 0x1) == 0x1) && (M_tmp>>1 != 0) &&
	                ((N_tmp & 0x1) == 0x1) && (N_tmp>>1 != 0) ) {
	                System.out.println("Fourier: both dimensions must be powers of 2");
	                System.exit(1);
	            } 
	        }  
	        M_2P = M;
	        N_2P = N;
	        maxPotM=0;
	        while ( (1<<maxPotM) <= (M_2P-1) ) {
	            maxPotM++;
	        }
	        maxPotM--;
	        maxPotN=0;
	        while ( (1<<maxPotN) <= (N_2P-1) ) {
	            maxPotN++;
	        }
	        maxPotN--;
	    }


	    /** genearl 2D-DFT */
	    public Complex[][] dft (Complex[][] G) {

	        Complex[][] GF;
	        Complex gfz;    

	        GF = new Complex[M_2P][N_2P];
	        gfz = new Complex();
	        for (int u = 0; u < M_2P; u++) {
	            for (int v = 0; v < N_2P; v++) {
	                GF[u][v] = new Complex();
	                for (int m = 0; m < M_2P; m++) {
	                    gfz.real = 0.0; gfz.imag = 0.0;
	                    for (int n = 0; n < N_2P; n++) {
	                        gfz.add(Complex.mult(G[m][n],
	                        Complex.expi(-2.0*Math.PI*n*v/N_2P)));
	                    }           
	                    (GF[u][v]).add(gfz.mult(Complex.expi(-2.0*Math.PI*m*u/M_2P)));
	                }
	                (GF[u][v]).mult(1.0/(M_2P*N_2P));
	            }
	        }
	        return GF;
	    }


	    /** general 2D-IDFT */
	    public Complex[][] idft (Complex[][] GF) {

	        Complex[][] G;
	        Complex gz;

	        G = new Complex[M_2P][N_2P];
	        gz = new Complex();
	        for (int m = 0; m < M_2P; m++) {
	            for (int n = 0; n < N_2P; n++) {
	                G[m][n] = new Complex();
	                for (int u = 0; u < M_2P; u++) {
	                    gz.real = 0.0; gz.imag = 0.0;
	                    for (int v = 0; v < N_2P; v++) {
	                        gz.add(Complex.mult(GF[u][v], Complex.expi(2.0*Math.PI*n*v/N_2P)));
	                    }           
	                    (G[m][n]).add(gz.mult(Complex.expi(2.0*Math.PI*m*u/M_2P)));
	                }
	            }
	        }
	        return G;
	    }


	    /** 2D-FFT, based on 1D FFT */
	    public Complex[][] fft (Complex[][] G) {

	        Complex[][] GF; 
	        Complex[] v,vf;
	        Fourier1D fft1D;

	        GF = new Complex[M_2P][N_2P];

	        // transform first dimension
	        fft1D = new Fourier1D(N_2P);
	        v = new Complex[N_2P]; 
	        for (int m = 0; m < M_2P; m++) {
	            for (int n = 0; n < N_2P; n++)
	                v[n] = new Complex(G[m][n]);
	            vf = fft1D.fft(v); 
	            for (int n = 0; n < N_2P; n++)
	                GF[m][n] = new Complex(vf[n]);
	        }

	        // transform second dimension
	        fft1D = new Fourier1D(M_2P);
	        v = new Complex[M_2P];
	        for (int n = 0; n < N_2P; n++) {
	            for (int m = 0; m < M_2P; m++)
	                v[m] = new Complex(GF[m][n]);
	            vf = fft1D.fft(v); 
	            for (int m = 0; m < M_2P; m++)
	                GF[m][n] = new Complex(vf[m]);
	        }

	        return GF; 
	    }   


	    /** 2D-IFFT, based on 1D IFFT */
	    public Complex[][] ifft (Complex[][] G) {

	        Complex[][] GF; 
	        Complex[] v,vf;
	        Fourier1D ifft1D;

	        GF = new Complex[M_2P][N_2P];

	        // transform first dimension
	        ifft1D = new Fourier1D(N_2P);
	        v = new Complex[N_2P]; 
	        for (int m = 0; m < M_2P; m++) {
	            for (int n = 0; n < N_2P; n++)
	                v[n] = new Complex(G[m][n]);
	            vf = ifft1D.ifft(v); 
	            for (int n = 0; n < N_2P; n++)
	                GF[m][n] = new Complex(vf[n]);
	        }

	        // transform second dimension
	        ifft1D = new Fourier1D(M_2P);
	        v = new Complex[M_2P];
	        for (int n = 0; n < N_2P; n++) {
	            for (int m = 0; m < M_2P; m++)
	                v[m] = new Complex(GF[m][n]);
	            vf = ifft1D.ifft(v); 
	            for (int m = 0; m < M_2P; m++)
	                GF[m][n] = new Complex(vf[m]);
	        }

	        return GF; 
	    }   
	}
	
	
/* void twoDfft(double[][] inputData, double[][] realOut,
 
            double[][] complexOut) 
    {
        int height = inputData.length;
        int width = inputData[0].length;
 
        // Two outer loops iterate on output data.
        for (int yWave = 0; yWave < height; yWave++) 
        {
            for (int xWave = 0; xWave < width; xWave++) 
            {
                // Two inner loops iterate on input data.
                for (int ySpace = 0; ySpace < height; ySpace++) 
                {
                    for (int xSpace = 0; xSpace < width; xSpace++) 
                    {
                        // Compute real, imag, and ampltude.
                        realOut[yWave][xWave] += (inputData[ySpace][xSpace] * Math.cos(2 * Math.PI * ((1.0 * xWave * xSpace / width) + (1.0 * yWave * ySpace / height)))) / Math.sqrt(width * height);
                        complexOut[yWave][xWave] -= (inputData[ySpace][xSpace] * Math.sin(2 * Math.PI * ((1.0 * xWave * xSpace / width) + (1.0 * yWave * ySpace / height)))) / Math.sqrt(width * height);
                        /*amplitudeOut[yWave][xWave] = Math.sqrt(realOut[yWave][xWave]* realOut[yWave][xWave] + complexOut[yWave][xWave] * complexOut[yWave][xWave]);*/
                    /*}
                    System.out.println(realOut[yWave][xWave] + " + "
                            + complexOut[yWave][xWave] + " i");
                }
            }
        }
    }
 
    public static void main(String args[]) 
    {
        System.out.println("Enter the size: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double[][] input = new double[n][n];
        double[][] real = new double[n][n];
        double[][] img = new double[n][n];
        double[][] amplitutude = new double[n][n];
        System.out.println("Enter the 2D elements ");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                input[i][j] = sc.nextDouble();
 
        twoDfft(input, real, img, amplitutude);
 
        sc.close();
    }*/
}