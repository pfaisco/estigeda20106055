package src;



public class Main {

	
	public static void main(String[] args) {
		
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\eda\\teste.jpg");
			img.binarization(200);
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\t_bw.jpg");
			TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(img.wRast);
			c.firstPass();
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\teste_out.jpg");
			

	}

}
