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
		this.wrOut = null;
		this.nextLable = 1;
	}
	
	public void firstPass()
	{
		for(int l = 0; l < this.wr.getWidth(); l++)
		{
			for(int c = 0; c < this.wr.getHeight(); c++)
			{
				this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0];					//img mono cromatica rgb sao iguais
				if(cValue > this.background)
				{
					cValue = this.checkNeighbors(c, l);
					if(cValue == 0)
					{
						this.color [0] = this.nextLable;
						this.color [1] = this.nextLable;
						this.color [2] = this.nextLable;
						this.wrOut.setPixel(c, l, this.color);
						
						this.nextLable++;
						
					}
					else
					this.color [0] = cValue;
					this.color [1] = cValue;
					this.color [2] = cValue;
					this.wrOut.setPixel(c, l, this.color);
				}
				else
					this.color [0] = this.background;
					this.color [1] = this.background;
					this.color [2] = this.background;
					this.wrOut.setPixel(l, c, this.color);
			}
		}
	}
	
	public int checkNeighbors(int x, int y)
	{	
		int max = 0;
		this.wr.getPixel(x - 1, y, this.color);
		if(this.color[0] > max)
		{
			max =this.color[0];
		}
		this.wr.getPixel(x + 1, y - 1, this.color);
		if(this.color[0] > max)
		{
			max =this.color[0];
		}
		this.wr.getPixel(x, y - 1, this.color);
		if(this.color[0] > max)
		{
			max =this.color[0];
		}
		this.wr.getPixel(x - 1, y - 1, this.color);
		if(this.color[0] > max)
		{
			max =this.color[0];
		}
		return max;
	}
	
	/*algorithm TwoPass(data)
	   linked = []
	   labels = structure with dimensions of data, initialized with the value of Background
	   
	   First pass
	   
	   for row in data:
	       for column in row:
	           if data[row][col] is not Background
	               
	               neighbors = connected elements with the current element's label
	               
	               if neighbors is empty
	                   linked[NextLabel] = set containing NextLabel                    
	                   labels[row][column] = NextLabel
	                   NextLabel += 1
	               
	               else
	                   
	                   Find the smallest label
	                   
	                   L = neighbors labels
	                   labels[row][column] = min(L)
	                   for label in L
	                       linked[label] = union(linked[label], L)
	   
	   Second pass
	   
	   for row in data
	       for column in row
	           if labels[row][column] is not Background		
	               labels[row][column] = min(linked[labels[row][column]])     
	      
	   return labels*/
}
