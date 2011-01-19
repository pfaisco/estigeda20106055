package src;

import java.awt.image.WritableRaster;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

public class TwoPassesConnectedComponents extends ConnectedComponents {
	private WritableRaster wr;
	public WritableRaster wrOut;
	private int[] color;
	private final int background = 0x00;
	private LinkedList<HashSet<Integer>> listaEquivalencias;
	private int nextLable;

	public TwoPassesConnectedComponents(WritableRaster wr) {
		this.wr = wr;
		this.color = new int[3];
		this.wrOut = wr;
		this.nextLable = 1;
		this.listaEquivalencias = new LinkedList<HashSet<Integer>>();
	}

	public void firstPass() {
		System.out.println(this.wr.getWidth() + " " + this.wr.getHeight());
		for (int l = 0; l < this.wr.getHeight(); l++) {
			for (int c = 0; c < this.wr.getWidth(); c++) {
				this.color = this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0]; // img mono cromatica rgb sao iguais
				// System.out.print("orginal- ");
				// for(int i : this.color) //print teste
				// {
				// System.out.print(i+";");
				//
				// }System.out.print(" ");
				if (cValue > this.background) {
					cValue = this.checkNeighbors(c, l);
					if (cValue == 0) // novo pixel sem vizinhos
					{
						this.color[0] = this.nextLable;
						this.color[1] = this.nextLable;
						this.color[2] = this.nextLable;
						this.wrOut.setPixel(c, l, this.color);

						this.nextLable++;
//						for (int i : this.color) // print teste
//						{
//							System.out.print(i);
//						}
//						System.out.print(" ");
					} else { // com vizinhos

						this.color[0] = cValue;
						this.color[1] = cValue;
						this.color[2] = cValue;
						this.wrOut.setPixel(c, l, this.color);
//						for (int i : this.color) // print teste
//						{
//							System.out.print(i);
//						}
//						System.out.print(" ");
					}
				} else { // qnd preto fica preto na imagem de saida
					this.color[0] = this.background;
					this.color[1] = this.background;
					this.color[2] = this.background;
					this.wrOut.setPixel(c, l, this.color);
//					for (int i : this.color) // print teste
//					{
//						System.out.print(i);
//					}
//					System.out.print(" ");
				}
				// System.out.println(c + " " + l);
			}
//			System.out.println("//");
		}
		for(HashSet<Integer> list: this.listaEquivalencias ){
			for( Integer i : list)  {
				System.out.print(i + ",");
			}
			System.out.println(";");
		} this.secondPass();
	}

	public int[] randomColor() {
		int[] color = new int[3];
		Random rdm = new Random();

		color[0] = rdm.nextInt(256);
		color[1] = rdm.nextInt(256);
		color[2] = rdm.nextInt(256);
		return color;
	}

	public void secondPass() {
		int last = 0;
		int[] color = null;
		HashSet<Integer> set = null;
		for (int l = 1; l < this.wr.getHeight()-1; l++) {
			for (int c = 1; c < this.wr.getWidth()-1; c++) {

				this.color = this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0];

//				if (cValue > 0) {
//					set = this.findSet( cValue);
//					if(!set.isEmpty() && !set.contains(cValue)){
//						color = this.randomColor();
//						this.wrOut.setPixel(c, l, color);
//						set = this.findSet( cValue);
//					} else {
//						this.wrOut.setPixel(c, l, color);
//						
//					}
					
					
//					set = this.findSet(this.listaEquivalencias, cValue);
					if (cValue == last) {
						this.wrOut.setPixel(c, l, color);
					} else {
						color = this.randomColor();
						this.wrOut.setPixel(c, l, color);
						last = cValue;
					}
					this.wrOut.setPixel(c, l, color);
					last = cValue;
				}
			}
		}

//	}
//	public int[] marcasVizinhas(int c, int l){
//		int [] marcas = new int[4];
//
//		if (c > 0) {
//			this.color = this.wr.getPixel(c - 1, l, this.color);
//			if (this.color[0] < min && this.color[0] != 0) {
//				
//				marcas[0] = this.color[0];
//			}
//		}
//		if (l > 0 && c < this.wr.getWidth() - 1) {
//			this.color = this.wr.getPixel(c + 1, l - 1, this.color);
//			if (this.color[0] < min && this.color[0] != 0) {
//				
//				min = this.color[0];
//			}
//		}
//		if (l > 0) {
//			this.color = this.wr.getPixel(c, l - 1, this.color);
//			if (this.color[0] < min && this.color[0] != 0) {
//				
//				min = this.color[0];
//			}
//		}
//		if (c > 0 && l > 0) {
//			this.color = this.wr.getPixel(c - 1, l - 1, this.color);
//			if (this.color[0] < min && this.color[0] != 0) {
//				s
//				min = this.color[0];
//			}
//		}
//
//		if (min == Integer.MAX_VALUE)
//			return 0;
//		else
//			return min;
//	
//		
//	}
	public int checkNeighbors(int x, int y) {
		int min = Integer.MAX_VALUE;

		HashSet<Integer> set = null;

		if (x > 0) {
			this.color = this.wr.getPixel(x - 1, y, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				set = this.findSet( this.color[0]);
				set.add(this.color[0]);
				min = this.color[0];
			}
		}
		if (y > 0 && x < this.wr.getWidth() - 1) {
			this.color = this.wr.getPixel(x + 1, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				set = this.findSet( this.color[0]);
				set.add(this.color[0]);
				min = this.color[0];
			}
		}
		if (y > 0) {
			this.color = this.wr.getPixel(x, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				set = this.findSet(this.color[0]);
				set.add(this.color[0]);
				min = this.color[0];
			}
		}
		if (x > 0 && y > 0) {
			this.color = this.wr.getPixel(x - 1, y - 1, this.color);
			if (this.color[0] < min && this.color[0] != 0) {
				set = this.findSet( this.color[0]);
				set.add(this.color[0]);
				min = this.color[0];
			}
		}

		if (min == Integer.MAX_VALUE)
			return 0;
		else
			return min;
	}

	public HashSet<Integer> findSet( int num) {

		if (this.listaEquivalencias.isEmpty()) {
			HashSet<Integer> set = new HashSet<Integer>();
			this.listaEquivalencias.add(set);
			return set;
		} else {
			for (int i = 0; i < this.listaEquivalencias.size(); i++) {
				if (this.listaEquivalencias.get(i).contains(num)) {
					return this.listaEquivalencias.get(i);
				}
			}
		}
		HashSet<Integer> set = new HashSet<Integer>();
		this.listaEquivalencias.add(set);
		return set;
	}

}
