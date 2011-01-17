package src;



public class Main {

	
	public static void main(String[] args) {
		
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\chave.jpg");
			img.binarization(200);
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\chave.jpg");
			TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(img.wRast);
			c.firstPass();
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\teste_out.jpg");
			

	}

}
