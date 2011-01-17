package src;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


public class ImageOperations {

	private BufferedImage bImg;
	public WritableRaster wRast;
	
	public ImageOperations()
	{
		this.bImg = null;
        this.wRast = null;
		
	}
	   

	public void open(String path) 	{			
		File file = new File(path);
		try {
			this.bImg = ImageIO.read(file);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	    this.wRast = this.bImg.getRaster();
    }

    public void save(String nome_ficheiro){
        File ficheiro;
        ficheiro = new File(nome_ficheiro);
        try {
        	ImageIO.write(this.bImg, "jpg", ficheiro);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getWidth(){
        return bImg.getWidth();
    }

    public int getHeight(){
        return bImg.getHeight();
    }

    public void binarization(int th){
        int red, green, blue;
        int coluna, linha;
        int nivel_cinzento;

        for(coluna=0; coluna < this.wRast.getWidth(); coluna++){
        	for(linha=0; linha < this.wRast.getHeight(); linha++){
                int [] color = new int[3];
                color = this.wRast.getPixel(coluna, linha, color);

                red = color[0];
                green = color[1];
                blue = color[2];
                
                nivel_cinzento = (int) ((red + green + blue) / 3.0);
                
                if(nivel_cinzento < th){
                	
                	color[0] = 0xff;
                    color[1] = 0xff;
                    color[2] = 0xff;

                    this.wRast.setPixel(coluna, linha, color);
                }
                else{
                    color[0] = 0x00;
                    color[1] = 0x00;
                    color[2] = 0x00;

                    this.wRast.setPixel(coluna, linha, color);
                    
            	}
                
				
        	}//System.nanoTime();
        }
        
    }
}