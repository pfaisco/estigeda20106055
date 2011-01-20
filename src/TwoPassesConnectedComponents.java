package src;

import java.awt.image.WritableRaster;
import java.util.HashMap;
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
	private HashMap<Integer, Integer> conjuntos;

	public TwoPassesConnectedComponents(WritableRaster wr) {
		this.wr = wr;
		this.color = new int[3];
		this.wrOut = wr;
		this.nextLable = 1;
		this.listaEquivalencias = new LinkedList<HashSet<Integer>>();
		this.conjuntos = new HashMap<Integer, Integer>();
	}

	public void marcarPixel(int c, int l, int[] marca) {
		this.wrOut.setPixel(c, l, marca);
	}

	public void marcarPixel(int c, int l, int marca) {

		this.color[0] = marca;
		this.color[1] = marca;
		this.color[2] = marca;
		this.wrOut.setPixel(c, l, this.color);

	}

	public void adicionaConjunto(int[] marca) {
		for (int i = 0; i < 4; i++) {
			if (marca[i] > 0 && marca[i] != marca[5])
				this.conjuntos.put(marca[i], marca[4]);
		}
	}

	public void firstPass() {

		for (int l = 1; l < this.wr.getHeight() - 1; l++) {
			for (int c = 1; c < this.wr.getWidth() - 1; c++) {
				this.color = this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0]; // img mono cromatica rgb sao iguais
				int[] vizinhos = this.checkNeighbors(c, l);

				if (cValue > this.background) {

					if (vizinhos[5] < 1) // vizinhos[5] = numero de vizinhos
											// maior q zero
					{
						this.marcarPixel(c, l, this.nextLable);
						this.nextLable++;

					} else { // com vizinhos

						this.marcarPixel(c, l, vizinhos[4]);// vizinhos[4]=
															// marca mais baixa

						if (vizinhos[5] > 1) {
							this.marcarPixel(c, l, vizinhos[4]);// vizinhos[4]=
																// marca mais
																// baixa
							this.adicionaConjunto(vizinhos);
						}
					}
				} else { // qnd preto fica preto na imagem de saida
					this.marcarPixel(c, l, this.background);
				}
				System.out.print(color[0]);
			}System.out.println("!!");

		}
		System.out.println(this.conjuntos);
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
		int newColor[] = this.randomColor();
		for (int l = 1; l < this.wr.getHeight() - 1; l++) {
			for (int c = 1; c < this.wr.getWidth() - 1; c++) {

				this.color = this.wr.getPixel(c, l, this.color);
				int cValue = this.color[0];
				if (cValue > 0) {
					if (this.conjuntos.containsKey(cValue)) {

						if (((Integer) this.conjuntos.get(cValue)).equals(last)) {
							this.marcarPixel(c, l, newColor);
						
						}else{
							this.marcarPixel(c, l, newColor);
							Object obj = this.conjuntos.get(cValue);
							last = ((Integer)obj).intValue();
						}
							
					} else {
						
						newColor = this.randomColor();
						this.marcarPixel(c, l, newColor);// -1 random

					}
				}
			}
		}
	}

	public int[] checkNeighbors(int c, int l) {

		int[] neighbors = new int[6];// v1,v2,v3,v4,vmin,nºv>0
		this.color = this.wr.getPixel(c - 1, l, this.color);
		neighbors[0] = this.color[0];
		this.color = this.wr.getPixel(c + 1, l - 1, this.color);
		neighbors[1] = this.color[0];
		this.color = this.wr.getPixel(c, l - 1, this.color);
		neighbors[2] = this.color[0];
		this.color = this.wr.getPixel(c - 1, l - 1, this.color);
		neighbors[3] = this.color[0];

		neighbors[4] = this.min(neighbors);

		neighbors[5] = this.numNeighbors(neighbors);
		return neighbors;
	}

	public int numNeighbors(int[] k) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 4; i++) {
			if (k[i] > 0)
				set.add(k[i]);
		}
		return set.size();
	}

	public int min(int[] k) {
		int min = Integer.MAX_VALUE;
		for (int i : k)
			if (i > 0) {
				if (i < min) {
					min = i;
				}
			}
		return min;
	}

}
