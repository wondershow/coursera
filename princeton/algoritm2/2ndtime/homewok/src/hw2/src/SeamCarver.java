import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

import edu.princeton.cs.algs4.IndexMinPQ;

public class SeamCarver {
	private final int BOUNDARY_ENERGY = 1000;
	private boolean transposed = false;
	
	
	private int[][] mattrixR;
	private int[][] mattrixG;
	private int[][] mattrixB;
	
	
	private double[][] energyM;
	private int Pwidth, Pheight;
	private final Picture P;
	
	public static void main(String[] args) {
		
	}
	
	//TODO to get a local copy of picture
	public SeamCarver(Picture picture) {               // create a seam carver object based on the given picture
		checkNull(picture);
		P = picture;
		//int dim = Math.max(P.width(), P.height());
		
		mattrixR = new int[P.height()][P.width()];
		mattrixG = new int[P.height()][P.width()];
		mattrixB = new int[P.height()][P.width()];
		
		energyM = new double[P.height()][P.width()];
		
		Pwidth = P.width();
		Pheight = P.height();
		
		//System.out.println(" Pwidth = " + Pwidth + ", Pheight = " + Pheight);
		
		
		for (int i = 0; i < P.height(); i++) {
			for (int j = 0; j < P.width(); j++) {
				mattrixR[i][j] = P.get(j, i).getRed();
				mattrixG[i][j] = P.get(j, i).getGreen();
				mattrixB[i][j] = P.get(j, i).getBlue();
				
				//System.out.print("(" + mattrixR[i][j] +","+ mattrixG[i][j] +","+ mattrixB[i][j] +","     + ") ");
				//StdOut.printf("%8.0f ", energy(j, i));
			}
			//System.out.println();
		}
		
		
		for (int i = 0; i < P.height(); i++) {
			for (int j = 0; j < P.width(); j++) {
				//System.out.print("(" + mattrixR[i][j] +","+ mattrixG[i][j] +","+ mattrixB[i][j] +","     + ") ");
				energyM[i][j] = calEnergy(j, i);
				//StdOut.printf("%8.0f ", energyM[i][j]);
				//StdOut.printf("%8.0f ", energy(j, i));
			}
		}
	
		
		
		//System.out.println("\n energyM");
		//transpose();
	}
	
	public Picture picture()  {                        // current picture
		Picture res;
		if (this.transposed) 
			res = new Picture(this.Pheight, this.Pwidth);
		else 
			res = new Picture(this.Pwidth, this.Pheight);
		
		for (int i = 0; i<this.Pheight; i++)
			for (int j = 0; j < this.Pwidth; j++)
				if (this.transposed) 
					res.set(i, j, new Color(this.mattrixR[i][j], this.mattrixG[i][j], this.mattrixB[i][j] ));
				else 
					res.set(j, i, new Color(this.mattrixR[i][j], this.mattrixG[i][j], this.mattrixB[i][j] ));
		return res;
	}
	
	public int width() { // width of current picture
		if (this.transposed) return this.Pheight;
		return this.Pwidth;
	}
	
	public	int height() { // height of current picture
		if (this.transposed) return this.Pwidth;
		return this.Pheight;
	}
	
	
	private double calEnergy(int col, int row) {
		if (!checkImgBoundary(col, row)) 
			throw new java.lang.IndexOutOfBoundsException("col is " + col + ", row is " + row);
		if (col == 0 || row == 0 || col == this.Pwidth - 1 || row == this.Pheight - 1)
			return BOUNDARY_ENERGY;
		double res = Math.sqrt( deltaX(col, row) + deltaY(col, row));
		/*
		if (x == 1 && y == 1)
			System.out.println("energy = " + res);*/
		return res;
	}
	
	/***
	 * Col x  
	 * Row y
	 ***/
	public double energy(int col, int row) {           // energy of pixel at column x and row y
		if (this.transposed) { //switch row and col if transposed;
			int tmp = col;
			col = row;
			row = tmp;
		}
		return calEnergy(col, row);
	}	
	
	public int[] findHorizontalSeam()  {             // sequence of indices for horizontal seam
		if (!transposed) transpose();
		//System.out.println("Pwidth = " + this.Pwidth + ", Pheight = " + this.Pheight);
		//int res[] = getVerticalSeam();
		return getVerticalSeam();
	}
	
	
	
	private int[] getVerticalSeam() {
		//if (transposed) transpose();
		double[] distTo = new double[dagSize()];
		int[] vertexTo = new int[dagSize()];
		int source = dagSourceIndex();
		//System.out.println("source = " + source);
		
		for (int v = 0; v < dagSize(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
			vertexTo[v] = -1;
		}
		
		distTo[source] = 0.0;
		vertexTo[source] = source;
		
		Iterable<Integer> topoOrder = this.topologicalOrder();
		/*
		for (int v : topoOrder)
			System.out.println(v);*/
		
		for (int v : topoOrder)
			relax(v, distTo, vertexTo);
		
		int[] res = new int[this.Pheight];
		
		int v = vertexTo[this.dagSinkIndex()];
		int[] xy;
		for (int i = this.Pheight - 1; i >= 0; i-- ) {
			xy = this.index2XY(v);
			res[i] = xy[0]; //
			v = vertexTo[v];
		}
		/*
		for (int i = 0; i < res.length; i++)
			System.out.println(i + ":" + res[i]);*/
		
		//this.printEneryMatrix("for vertical energy");
		//this.printRGBMatrix("for vertical RGB");
		return res;
	}
	
	public int[] findVerticalSeam() {         // sequence of indices for vertical seam
		if (transposed) transpose();
		return getVerticalSeam();
	}
	
	/**
	 * To relax a vertex v
	 * */
	private void relax(int v, double[] distTo, int[] vTo) {
		int[] vPointTos = this.getDescendants(v);// the next level neighbors of this vertex
		if (vPointTos == null) return; // v is the sink
		
		/*System.out.print("descendants of " + v + " : ");
		for (int i = 0; i < vPointTos.length; i++) 
			System.out.print(vPointTos[i] + ",");
		System.out.println();*/
		for (int i = 0; i < vPointTos.length; i++) {
			double edgeWeight = getEdgeWeight(v, vPointTos[i]);
			if ( distTo[vPointTos[i]] > distTo[v] +  edgeWeight) {
				distTo[vPointTos[i]] = distTo[v] +  edgeWeight;
				vTo[vPointTos[i]] = v;
			}
		}
	}
	
	public void removeHorizontalSeam(int[] seam){  // remove horizontal seam from current picture
		checkNull(seam);
		
		if (!this.transposed) {
			if (this.Pheight <= 1) throw new java.lang.IllegalArgumentException();
			if (seam.length != this.Pwidth) throw new java.lang.IllegalArgumentException();
		} else {
			if (this.Pwidth <= 1) throw new java.lang.IllegalArgumentException();
			if (seam.length != this.Pheight) throw new java.lang.IllegalArgumentException();
		}
		if (!validateSeam(seam)) throw new java.lang.IllegalArgumentException(); 
		if (!transposed) transpose();
		delVerticalSeam(seam);
		//this.printEneryMatrix("after removeHorizontalSeam");
	}
	
	private void delVerticalSeam(int[] seam) {
		double[][] newEnergyM = new double[this.Pheight][this.Pwidth];
		int[][] newMatrrixR = new int[this.Pheight][this.Pwidth];
		int[][] newMatrrixG = new int[this.Pheight][this.Pwidth];
		int[][] newMatrrixB = new int[this.Pheight][this.Pwidth];
		
		for (int i = 0; i < seam.length; i++) {
			if (seam[i] > 0) {
				System.arraycopy(this.energyM[i], 0, newEnergyM[i], 0, seam[i]);
				System.arraycopy(this.mattrixR[i], 0, newMatrrixR[i], 0, seam[i]);
				System.arraycopy(this.mattrixG[i], 0, newMatrrixG[i], 0, seam[i]);
				System.arraycopy(this.mattrixB[i], 0, newMatrrixB[i], 0, seam[i]);
			}
			if (seam[i] < this.Pwidth - 1) {
				System.arraycopy(this.energyM[i], seam[i] + 1, newEnergyM[i], seam[i], this.Pwidth - seam[i] - 1);
				System.arraycopy(this.mattrixR[i], seam[i] + 1, newMatrrixR[i], seam[i], this.Pwidth - seam[i] - 1);
				System.arraycopy(this.mattrixG[i], seam[i] + 1, newMatrrixG[i], seam[i], this.Pwidth - seam[i] - 1);
				System.arraycopy(this.mattrixB[i], seam[i] + 1, newMatrrixB[i], seam[i], this.Pwidth - seam[i] - 1);
			}
		}
		
		this.energyM = newEnergyM;
		this.mattrixR = newMatrrixR;
		this.mattrixG = newMatrrixG;
		this.mattrixB = newMatrrixB;
		
		this.Pwidth--;
		
		//update Energy mattrix;
		for (int i = 1; i < this.Pheight - 1; i++) {
			if (seam[i] < this.Pwidth)
				this.energyM[i][seam[i]] = this.calEnergy(seam[i], i);
			//System.out.println("updating  " + i + "row" + "" )
			
			if (seam[i] >= 1)
				this.energyM[i][seam[i] - 1] = this.calEnergy(seam[i] - 1, i);
			/*
			if (seam[i] < this.Pwidth - 2)
				this.energyM[i][seam[i] + 1] = this.energy(seam[i] + 1, i);*/
		} //*/
		//this.printEneryMatrix("after removeVerticalSeam, Pheight " + this.Pheight + ", Pwidth = " + this.Pwidth);
	}
	
	
	
	public void removeVerticalSeam(int[] seam){     // remove vertical seam from current picture
		if (this.transposed) this.transpose();
		checkNull(seam);
		//if (this.transposed) transpose();
		if (this.Pwidth <= 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != this.Pheight) throw new java.lang.IllegalArgumentException(); 
		if (!validateSeam(seam)) throw new java.lang.IllegalArgumentException(); 
		
		delVerticalSeam(seam);
		/*
		String tmpStr = "";
		for (int i=0; i<seam.length; i++)
			tmpStr += seam[i] + " ";
		System.out.println("seams are : " + tmpStr);*/
	}
	
	private void checkNull(Object o) {
		if (o == null) throw new java.lang.NullPointerException();
	}
	
	private boolean checkImgBoundary(int col, int row) {
		if (col < 0 || col > this.Pwidth - 1 || row < 0 || row > this.Pheight -1)
			return false;
		return true;
	}
	
	//return the square of deltaX
	private int deltaX(int col, int row) {
		int res = 0;
		
		
		
		res += (mattrixR[row][col - 1] - mattrixR[row][col + 1])
				* (mattrixR[row][col - 1] - mattrixR[row][col + 1]);
		
		res += (mattrixG[row][col - 1] - mattrixG[row][col + 1])
				* (mattrixG[row][col - 1] - mattrixG[row][col + 1]);
		
		res += (mattrixB[row][col - 1] - mattrixB[row][col + 1])
				* (mattrixB[row][col - 1] - mattrixB[row][col + 1]);
		
		/*
		if (col == 1 && row == 1)
			System.out.println("X :" + res);*/
		return res;
	}
	
	//return the square of deltaY
	private int deltaY(int col, int row) {
		int res = 0;
		res += (mattrixR[row - 1][col] - mattrixR[row + 1] [col])
				* (mattrixR[row - 1][col] - mattrixR[row + 1] [col]);
		res += (mattrixG[row - 1][col] - mattrixG[row + 1] [col])
				* (mattrixG[row - 1][col] - mattrixG[row + 1] [col]);
		res += (mattrixB[row - 1][col] - mattrixB[row + 1] [col])
				* (mattrixB[row - 1][col] - mattrixB[row + 1] [col]);
		/*
		if (col == 1 && row == 1)
			System.out.println("Y :" + res + "," + (mattrixR[row][col - 1] - mattrixR[row][col + 1]) 
					+ "," + (mattrixG[row][col - 1] - mattrixG[row][col + 1]) + ","
					+ (mattrixB[row][col - 1] - mattrixB[row][col + 1]));*/
		return res;
	}
	
	private void transpose() {
		
		int[][] mattrixRTranspose = new int[Pwidth][Pheight];
		int[][] mattrixGTranspose = new int[Pwidth][Pheight];
		int[][] mattrixBTranspose = new int[Pwidth][Pheight];
		double[][] energyMTranspose = new double[Pwidth][Pheight];
		
		
		for (int i = 0; i < Pheight; i ++)
			for (int j = 0; j < Pwidth; j++) {
				mattrixRTranspose[j][i] = this.mattrixR[i][j];
				mattrixGTranspose[j][i] = this.mattrixG[i][j];
				mattrixBTranspose[j][i] = this.mattrixB[i][j];
				energyMTranspose[j][i] = this.energyM[i][j];
			}
		
		int tmp = Pheight;
		Pheight = Pwidth;
		Pwidth = tmp;
		
		this.mattrixR = mattrixRTranspose;
		this.mattrixG = mattrixGTranspose;
		this.mattrixB = mattrixBTranspose;
		this.energyM = energyMTranspose;
		
		transposed = !transposed;
	}
	
	//To transfrom the element on row, col to 
	//a one-dimension value which is used in a digraph
	private int XY2Index(int col, int row) {
		return row * Pwidth + col;
	}
	
	private int[] index2XY(int i) {
		int[] res = new int[2];
		int col = i % Pwidth;
		int row = i / Pwidth;
		res[0] = col;
		res[1] = row;
		return res;
	}
	
	//to return the descendants of a given index
	private int[] getDescendants(int i) {
		int res[];
		if (i == dagSourceIndex() ) { // the virtual source
			res = new int[this.Pwidth];
			for (int j = 0; j < this.Pwidth; j++)
				res[j] = XY2Index(j, 0);
			return res;
		} else if (i == dagSinkIndex()) { // the virtual sink
			res = null;
			return res;
		}
		
		int[] xy = index2XY(i);
		int col = xy[0];
		int row = xy[1];
		
		if (row == Pheight - 1) {
			// last row return the virtual sink
			res = new int[1];
			res[0] = dagSinkIndex();
		} else if (col == this.Pwidth - 1 && col == 0) { // only one column
			res = new int[1];
			res[0] = XY2Index(col, row + 1);
		} else if (col == 0) { // left most column
			res = new int[2];
			res[0] = XY2Index(col, row + 1);
			res[1] = XY2Index(col + 1, row + 1);
		} else if (col == this.Pwidth - 1) {// right most column
			res = new int[2];
			res[0] = XY2Index(col, row + 1);
			res[1] = XY2Index(col - 1, row + 1);
		} else {
			res = new int[3];
			res[0] = XY2Index(col, row + 1);
			res[1] = XY2Index(col - 1, row + 1);
			res[2] = XY2Index(col + 1, row + 1);
		}
		return res;
	}
	
	//return the index of the virtual source
	private int dagSourceIndex() { return this.XY2Index(this.Pwidth - 1, this.Pheight - 1) + 1;}
	
	//return the index of the vitual sink
	private int dagSinkIndex() { return dagSourceIndex() + 1;}
	
	//the size of the current pricture's corresponding dag
	private int dagSize() { return this.Pwidth * this.Pheight + 2;}
	
	private Iterable<Integer> topologicalOrder() {
		Queue<Integer> res = new Queue<Integer>();
		res.enqueue(this.dagSourceIndex());
		for (int i = 0; i < this.Pheight; i++) // ith row 
			for (int j = 0; j < this.Pwidth; j++) //jth col
				res.enqueue(this.XY2Index(j, i));
		res.enqueue(this.dagSinkIndex());
		return res;
	}
	
	//get the arc weight from v to w
	private double getEdgeWeight(int v, int w) {
		//System.out.println("w = " + w + ", v is " + v + ", from v to w");
		if (v == dagSourceIndex()) return this.BOUNDARY_ENERGY;
		if (w == dagSinkIndex()) return 0;
		int[] xy = this.index2XY(w);
		//System.out.println(xy[0] + "," + xy[1]);
		return this.energyM[xy[1]][xy[0]];
	}
	
	private void printEneryMatrix(String s) {
		System.out.println(s);
		for (int i = 0; i < Pheight; i++) {
			for (int j = 0; j < Pwidth; j++) {
				StdOut.printf("%8.0f ",energyM[i][j]);
			}
			System.out.println("");
		}
	}
	
	private void printRGBMatrix(String s) {
		System.out.println(s);
		for (int i = 0; i < Pheight; i++) {
			for (int j = 0; j < Pwidth; j++) {
				StdOut.printf("(%4d,%4d,%4d) ", this.mattrixR[i][j], this.mattrixG[i][j], this.mattrixB[i][j]);
				//System.out.print("("+this.mattrixR[i][j] + "," + this.mattrixG[i][j] + "," + this.mattrixB[i][j] + ")");
			}
			System.out.println("");
		}
	}
	
	private boolean validateSeam(int[] seam) {
		for (int i = 0; i< seam.length; i++) {
			if (seam[i] < 0 || seam[i] >= this.Pwidth) return false;
			if (i>0 && Math.abs(seam[i] - seam[i-1]) > 1) return false;
		}
		return true;
	}
}