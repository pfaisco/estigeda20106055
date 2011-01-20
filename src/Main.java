package src;



public class Main {

	
	public static void main(String[] args) {
		
			ImageOperations img = new ImageOperations();
			img.open("C:\\Users\\Pedro\\Downloads\\chave.bmp");
			img.binarization(200);
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\chave.bmp");
			TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(img.wRast);
//			int[]k = {0,0,0,0,2,5};
//			System.out.println(c.numNeighbours(k));
			c.firstPass();
			c.secondPass();
			img.wRast = c.wrOut;
			img.save("C:\\Users\\Pedro\\Downloads\\eda\\teste_out.bmp");
			

	}

}
