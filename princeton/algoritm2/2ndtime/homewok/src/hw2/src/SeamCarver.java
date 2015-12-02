import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Queue;
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
		int dim = Math.max(P.width(), P.height());
		mattrixR = new int[dim][dim];
		mattrixG = new int[dim][dim];
		mattrixB = new int[dim][dim];
		energyM = new double[dim][dim];
		Pwidth = P.width();
		Pheight = P.height();
		
		System.out.println(" Pwidth = " + Pwidth + ", Pheight = " + Pheight);
		
		for (int i = 0; i < P.height(); i++) {
			for (int j = 0; j < P.width(); j++) {
				mattrixR[i][j] = P.get(j, i).getRed();
				mattrixG[i][j] = P.get(j, i).getGreen();
				mattrixB[i][j] = P.get(j, i).getBlue();
				System.out.print("(" + mattrixR[i][j] +","+ mattrixG[i][j] +","+ mattrixB[i][j] +","     + ") ");
				
			}
			System.out.println();
		}
		
		transpose();
	}
	
	public Picture picture()  {                        // current picture
		return P;
	}
	
	public int width() {                        // width of current picture
		return P.width();
	}
	
	public	int height() {                        // height of current picture
		return P.height();
	}
	
	/***
	 * Col x  
	 * Row y
	 ***/
	public double energy(int x, int y) {            // energy of pixel at column x and row y
		if (!checkImgBoundary(x, y)) throw new java.lang.IndexOutOfBoundsException();
		if (x == 0 || y == 0 || x == P.width() - 1 || y == P.height() - 1)
			return BOUNDARY_ENERGY;
		double res = Math.sqrt( deltaX(x, y) + deltaY(x, y));
		/*
		if (x == 1 && y == 1)
			System.out.println("energy = " + res);*/
		return res;
	}	
	
	public int[] findHorizontalSeam()  {             // sequence of indices for horizontal seam
		return new int[1];
	}
	
	public int[] findVerticalSeam() {             // sequence of indices for vertical seam
		double[] distTo = new double[dagSize()];
		int[] vertexTo = new int[dagSize()];
		int source = dagSourceIndex();
		System.out.println("source = " + source);
		
		for (int v = 0; v < dagSize() - 1; v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		
		distTo[source] = 0.0;
		vertexTo[source] = source;
		
		Iterable<Integer> topoOrder = this.topologicalOrder();
		
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
		
		
		for (int i = 0; i < res.length; i++)
			System.out.println(i + ":" + res[i]);
		return res;
	}
	
	/**
	 * To relax a vertex v
	 * */
	private void relax(int v, double[] distTo, int[] vTo) {
		int[] vPointTos = this.getDescendants(v);// the next level neighbors of this vertex
		System.out.println("v from is " + v);
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
		if (P.width() <= 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != P.width()) throw new java.lang.IllegalArgumentException();
		//if (!checkImgBoundary())
	}
	
	public void removeVerticalSeam(int[] seam){     // remove vertical seam from current picture
		checkNull(seam);
		if (P.height() <= 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != P.height()) throw new java.lang.IllegalArgumentException(); 
		
	}
	
	private void checkNull(Object o) {
		if (o == null) throw new java.lang.NullPointerException();
	}
	
	private boolean checkImgBoundary(int col, int row) {
		if (col < 0 || col > P.width() - 1 || row < 0 || row > P.height() -1)
			return false;
		return true;
	}
	
	//return the square of deltaX
	private int deltaX(int col, int row) {
		int res = 0;
		if (transposed) {
			res += (mattrixR[col][row - 1] - mattrixR[col] [row + 1])
					* (mattrixR[col][row - 1] - mattrixR[col] [row + 1]);
			res += (mattrixG[col][row - 1] - mattrixG[col] [row + 1])
					* (mattrixG[col][row - 1] - mattrixG[col] [row + 1]);
			res += (mattrixB[col][row - 1] - mattrixB[col] [row + 1])
					* (mattrixB[col][row - 1] - mattrixB[col] [row + 1]);
		} else {
			res += (mattrixR[row - 1][col] - mattrixR[row + 1] [col])
					* (mattrixR[row - 1][col] - mattrixR[row + 1] [col]);
			res += (mattrixG[row - 1][col] - mattrixG[row + 1] [col])
					* (mattrixG[row - 1][col] - mattrixG[row + 1] [col]);
			res += (mattrixB[row - 1][col] - mattrixB[row + 1] [col])
					* (mattrixB[row - 1][col] - mattrixB[row + 1] [col]);
		}
		/*
		if (col == 1 && row == 1)
			System.out.println("X :" + res);*/
		return res;
	}
	
	//return the square of deltaY
	private int deltaY(int col, int row) {
		int res = 0;
		if (transposed) {
			res += (mattrixR[col - 1][row] - mattrixR[col + 1][row])
					* (mattrixR[col - 1][row] - mattrixR[col + 1][row]);
			res += (mattrixG[col - 1][row] - mattrixG[col + 1][row])
					* (mattrixG[col - 1][row] - mattrixG[col + 1][row]);
			res += (mattrixB[col - 1][row] - mattrixB[col + 1][row])
					* (mattrixB[col - 1][row] - mattrixB[col + 1][row]);
			
		} else {
			res += (mattrixR[row][col - 1] - mattrixR[row][col + 1])
					* (mattrixR[row][col - 1] - mattrixR[row][col + 1]);
			res += (mattrixG[row][col - 1] - mattrixG[row][col + 1])
					* (mattrixG[row][col - 1] - mattrixG[row][col + 1]);
			res += (mattrixB[row][col - 1] - mattrixB[row][col + 1])
					* (mattrixB[row][col - 1] - mattrixB[row][col + 1]);
		}
		/*
		if (col == 1 && row == 1)
			System.out.println("Y :" + res);*/
		return res;
	}
	
	private void transpose() {
		int dim = Math.max(Pwidth, Pheight);
		for (int i = 0; i < dim; i ++)
			for (int j = i + 1; j < dim; j++) {
				int tmp = mattrixR[i][j];
				mattrixR[i][j] = mattrixR[j][i];
				mattrixR[j][i] = tmp;
				
				tmp = mattrixG[i][j];
				mattrixG[i][j] = mattrixG[j][i];
				mattrixG[j][i] = tmp;
				
				tmp = mattrixB[i][j];
				mattrixB[i][j] = mattrixB[j][i];
				mattrixB[j][i] = tmp;
				
				double tmpd = energyM[i][j];
				energyM[i][j] = energyM[j][i];
				energyM[j][i] = tmpd;
			}
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
		}
		
		int[] xy = index2XY(i);
		int col = xy[0];
		int row = xy[1];
		
		if (row == Pheight - 1) {
			// last row return the virtual sink
			res = new int[1];
			res[0] = dagSinkIndex();
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
		System.out.println("w = " + w);
		if (v == dagSourceIndex()) return this.BOUNDARY_ENERGY;
		if (w == dagSinkIndex()) return 0;
		int[] xy = this.index2XY(w);
		return this.energyM[xy[1]][xy[0]];
	}
}

/*
class MyAcyclicSP {
	
	private 
	
	
}*/
