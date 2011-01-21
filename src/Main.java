package src;



public class Main {

	
	public static void main(String[] args) {
		
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\eda\\chave.jpg");
			img.binarization(180);
//			img.save("C:\\Users\\Pedro\\Downloads\\eda\\canetas1bp.bmp");
			TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(img.wRast);
			double tempo = System.nanoTime();
			c.firstPass();
//			img.save("C:\\Users\\Pedro\\Downloads\\eda\\canetas1fp.bmp");
//			c.secondPass();
			tempo = System.nanoTime() - tempo;
			System.out.println(tempo*0.000000001+" segundos");
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\chave_out.bmp");
			

	}

}
