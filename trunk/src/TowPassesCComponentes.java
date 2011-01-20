package src;

import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class TowPassesCComponentes {

	
	private WritableRaster wrIn;
	private WritableRaster wrOut;
	private LinkedList<HashSet<Integer>> eqList;
	private HashMap<Integer, Integer> colors;
	private int[] neighbours;
	
	public TowPassesCComponentes(WritableRaster wr){
		this.colors = new HashMap<Integer, Integer>();
		this.eqList = new LinkedList<HashSet<Integer>>();
		this.neighbours = new int[4];
		this.wrIn = wr;
		this.wrOut = 
	}
}
