package src;



public class Main {

	
	public static void main(String[] args) {
		
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\teste.jpg");
//			img.binarization(250);
//			img.save("C:\\Users\\Pedro\\Downloads\\t_bw.jpg");
			TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(img.wRast);
			c.firstPass();
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\teste_out.jpg");
			

	}

}
