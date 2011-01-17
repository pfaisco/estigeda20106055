package src;


import java.awt.image.WritableRaster;
import java.util.HashSet;
import java.util.LinkedList;


public class TwoPassesConnectedComponents extends ConnectedComponents{
	private WritableRaster wr;
	public WritableRaster wrOut;
	private int [] color;
	private final int background = 0x00;
	private LinkedList<HashSet<Integer> > listaEquivalencias;
	private int nextLable;
	
	public TwoPassesConnectedComponents(WritableRaster wr)
	{
		this.wr = wr;
		this.color = new int[3];
		this.wrOut = wr;
		this.nextLable = 1;
	}
	
	public void firstPass()
	{
		System.out.println(this.wr.getHeight()+" "+this.wr.getWidth());
		for(int l = 0; l < this.wr.getHeight(); l++)
		{
			for(int c = 0; c < this.wr.getWidth(); c++)
			{
				this.color = this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0];					//img mono cromatica rgb sao iguais
//				System.out.print("orginal- ");
//				for(int i : this.color)				//print teste
//				{
//					System.out.print(i+";");
//					
//				}System.out.print(" ");
				if(cValue > this.background)
				{
					cValue = this.checkNeighbors(c, l);
					if(cValue == 0)							// novo pixel sem vizinhos
					{
						this.color [0] = this.nextLable;
						this.color [1] = this.nextLable;
						this.color [2] = this.nextLable;
						this.wrOut.setPixel(c, l, this.color);
						
						this.nextLable++;
						for(int i : this.color)				//print teste
						{
							System.out.print(i);
							
						}
						System.out.print(" " );	
					}
					else{								//com vizinhos
						
					this.color [0] = cValue;
					this.color [1] = cValue;
					this.color [2] = cValue;
					this.wrOut.setPixel(c, l, this.color);
					for(int i : this.color)				//print teste
					{
						System.out.print(i);
						
					}System.out.print(" ");}
				}
				else{									// qnd preto fica preto na imagem de saida
					this.color [0] = this.background;
					this.color [1] = this.background;
					this.color [2] = this.background;
					this.wrOut.setPixel(l, c, this.color);
					for(int i : this.color)				//print teste
					{
						System.out.print(i);
						
					}System.out.print(" ");
			}
			}System.out.println("//");
		}
	}
	 
	
	public int checkNeighbors(int x, int y) {
		int min = Integer.MAX_VALUE;
		
		if (x > 0) {
			this.color = this.wr.getPixel(x - 1, y, this.color);
			if (this.color[0] < min && this.color[0] != 0) {

				min = this.color[0];
			}
		}
		if (y > 0 && x < this.wr.getWidth()-1) {
			this.color = this.wr.getPixel(x + 1, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				min = this.color[0];
			}
		}
		if (y > 0){
			this.color = this.wr.getPixel(x, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				min = this.color[0];
			}
		}
		if (x > 0 && y>0){
			this.color = this.wr.getPixel(x - 1, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				min = this.color[0];
			}
		}
		if(min == Integer.MAX_VALUE)
			return 0;
		else 
			return min;
	}
	public void addToEquivalents(){
		
	}
	
}
