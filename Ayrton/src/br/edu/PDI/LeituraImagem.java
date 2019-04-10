package br.edu.PDI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LeituraImagem {

	public void jButton1ActionPerformed() {
		BufferedImage image;

		String str = "Origem.jpg";
		try {
			image = ImageIO.read(new File(str));
		} catch (IOException ex) {
		}
	}
}