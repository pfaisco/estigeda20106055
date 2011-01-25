package src;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;





public class Main {

	
	public static void main(String[] args) {
			
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\eda\\ana.jpg");
			img.binarization(150);

			TowPassesCComponentes c = new TowPassesCComponentes(img.wRast);
			
			double tempo = System.nanoTime();
			c.run();
			tempo = System.nanoTime() - tempo;
			System.out.println(tempo*0.000000001+" segundos");
			
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\ana_out.bmp");
		
			

	}

}
