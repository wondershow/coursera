package class2.hw;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class MySeamCarver {
	private int W, H;
	private Color[][] colorMatrix;
	private double[][] energyMatrix;
	private boolean isHorizontal; 
	private int[] edgeTo;
	private double[] distanceTo;
	private int[] distanceToSquare;
	private int[][] squareMatrix;
	
	public static void main(String[] args) {
		MySeamCarver sc = new MySeamCarver(new Picture("./src/hw2/10x10.png"));
		int[] res = sc.findVerticalSeam();
	}
	
	public MySeamCarver(Picture picture) {// create a seam carver object based on the given picture
		if (picture == null) throw new java.lang.NullPointerException("method called with null parameter");
		
		W = picture.width();   //Columns
		H = picture.height(); // Rows
		colorMatrix = new Color[H][W];
		energyMatrix = new double[H][W];
		squareMatrix = new int[H][W];
		
		for (int i = 0; i < H; i++)
			for (int j = 0; j < W; j++)
				colorMatrix[i][j] = picture.get(j, i);	
		
		for (int i = 0; i < H; i++)
			for (int j = 0; j < W; j++)
				updateEnergyMattrix(i,j);
		
		isHorizontal = true;
	}
	   
	public Picture picture()	{                      // current picture
		if (!isHorizontal) transpose();
		
		Picture res = new Picture(W,H);
		for (int i = 0; i < H; i++)
			for (int j = 0; j < W; j++)
				res.set(j, i, colorMatrix[i][j]);
		return res;
	}
	   
	public int width() {                         // width of current picture
		return W;
	}
	   
	public int height()  {                        // height of current picture
		return H;
	}
	
	//NOTE that COLUMN x and ROW y
	public double energy(int x, int y)  { // energy of pixel at column x and row y
		if (x < 0 || y < 0 || x >= W || y >= H)
			throw new java.lang.IndexOutOfBoundsException();
		return energyMatrix[y][x];
	}
	   
	public int[] findHorizontalSeam()  { // sequence of indices for horizontal seam
		if (isHorizontal) transpose();
		return  findVSeam();
	}
	
	public int[]  findVerticalSeam () {
		if (!isHorizontal) transpose();
		return findVSeam();
	}
	
	private void transpose() {
		 Color[][] newClrMatrix = new Color[W][H];
		 double[][] newEnryMatrix = new double[W][H];
		 
		 for (int i = 0; i < W; i++) 
			 for (int j = 0; j < H; j++) 
			 {
				 newClrMatrix[i][j] = colorMatrix[j][i];
				 newEnryMatrix[i][j] = energyMatrix[j][i];
			 }
		 
		 colorMatrix = newClrMatrix;
		 energyMatrix = newEnryMatrix;
		 
		 int tmp = H;
		 H = W;
		 W = tmp;
		 
		 isHorizontal = !isHorizontal;
		  
	}
	
	private int[] findVSeam() { // sequence of indices for vertical seam
		edgeTo = new int[W*H+1];// add one more virtual node
		distanceTo = new double [W*H+1];
		distanceToSquare = new int[W*H+1];
		Arrays.fill(distanceTo, Double.MAX_VALUE);
		Arrays.fill(distanceToSquare, Integer.MAX_VALUE);
		Arrays.fill(edgeTo, -1);
		
		int source = W*H;
		distanceTo[source] = 0;
		
		//for the first row, edgeTo[] is set to be the virtual node
		for (int i = 0; i < W; i++) {
			edgeTo[xyToV(i, 0)] = source;
			distanceTo[xyToV(i, 0)] = 0;
			distanceToSquare[xyToV(i, 0)] = 0;
		}
		
		//update the distanceTo and edgeTo
		for (int row = 0; row < H - 1; row++) 
			for (int col = 0; col < W; col++) {
				int cur = xyToV(col, row);
				if (col != 0 && col < W) relax(cur, xyToV(col - 1, row +1));
				relax(cur, xyToV(col, row +1));
				if (col != W-1 && col >= 0) relax(cur, xyToV(col + 1, row +1));
			}
		
		for (int row = 0; row < H; row++) {
			for (int col = 0; col < W; col++) {
				int v = xyToV(col,row);
				int parent = edgeTo[v];
				System.out.printf("%1d: %8.2f (%3d) (%7d)|", col, (double)Math.round( distanceTo[v] *100) /100, vToCol(parent), distanceToSquare[xyToV(col,row)]);
				//System.out.printf("%1d:(%d) (%7d)|", col, vToCol(parent), distanceToSquare[xyToV(col,row)]);
				//double delta = (double) distanceToSquare[xyToV(col,row)] - distanceTo[v]*distanceTo[v];
				//System.out.printf("%8.2f ,", delta);
			}
			System.out.println();
		}
		
		int minSeamV = -1;
		double minEnergey = Double.MAX_VALUE;
		for (int col = 0; col<W; col++){
			int v = xyToV(col, H-1);
			if (distanceTo[v]  < minEnergey) {
				minEnergey = distanceTo[v];
				minSeamV = v;
			}
		}
		
		//Stack<Integer> s = new Stack<Integer>();
		int[] res = new int[H];
		int row = H -1;
		while (minSeamV != W*H) {
			//s.push(vToRow(minSeamV));
			res[row] = vToCol(minSeamV);
			minSeamV = edgeTo[minSeamV];
			row--;
		}
		return res;
	}
	
	// relax from node parent to node v
	private void relax(int parent, int v) {
		int x = vToRow(v), y = vToCol(v);
		if (distanceTo[parent] + energyMatrix[x][y] < distanceTo[v] )
		{	
			distanceTo[v] = distanceTo[parent] + energyMatrix[x][y]; 
			distanceToSquare[v] = distanceToSquare[parent] + squareMatrix[x][y];
			edgeTo[v] = parent;
		}
	}
	
	public void removeHorizontalSeam(int[] seam) {  // remove horizontal seam from current picture
		if (!this.isHorizontal) { removeVerticalSeam(seam); return; }
		
		
		if (seam == null) throw new java.lang.NullPointerException();
		if (H == 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != W) throw new java.lang.IllegalArgumentException(); 
		for (int i = 0; i < seam.length-1; i++) {
			if (Math.abs(seam[i+1]-seam[i]) >1) throw new java.lang.IllegalArgumentException(); 
		}
		for (int i = 0; i < seam.length; i++) 
			if (seam[i] <0 || seam[i] >=H) throw new java.lang.IllegalArgumentException();
		
		
		for (int col = 0; col < W; col++) {
			int row = seam[col];
			for (int j = row; j < H - 1; j++) {
				colorMatrix[j][col] = colorMatrix[j+1][col];
				energyMatrix[j][col] = energyMatrix[j+1][col];
			}
		}
		H--;
		for (int col = 0; col < seam.length; col++) {
			if (seam[col] > 0) updateEnergyMattrix(seam[col] - 1, col);
			if (seam[col] < H) updateEnergyMattrix(seam[col], col);
		}
	}
	   
	public void removeVerticalSeam(int[] seam) {    // remove vertical seam from current picture
		if (!this.isHorizontal) {removeHorizontalSeam(seam);return; }
		
		if (seam == null) throw new java.lang.NullPointerException();
		if (W == 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != H) throw new java.lang.IllegalArgumentException(); 
		for (int i = 0; i < seam.length-1; i++)
			if (Math.abs(seam[i+1]-seam[i]) >1) throw new java.lang.IllegalArgumentException(); 
		for (int i = 0; i < seam.length; i++) 
			if (seam[i] <0 || seam[i] >=W) throw new java.lang.IllegalArgumentException(); 
		
		
		for (int row = 0; row < seam.length; row++) {
			System.arraycopy(colorMatrix[row],  seam[row] + 1, colorMatrix[row], seam[row], W - 1 - seam[row]);
			System.arraycopy(energyMatrix[row], seam[row] + 1, energyMatrix[row], seam[row], W -1 - seam[row]);
		}
		W--;
		for (int row = 0; row < seam.length; row++) {
			if (seam[row] > 0)	updateEnergyMattrix(row, seam[row] - 1);
			if (seam[row] < W)  updateEnergyMattrix(row, seam[row]);
		}
	}
	
	private int energeySquare(Color c1, Color c2) {
		int d1 = c1.getBlue() - c2.getBlue();
		int d2 = c1.getGreen() - c2.getGreen();
		int d3 = c1.getRed() - c2.getRed();
		return d1 * d1 + d2 * d2 + d3 * d3;
	}

	//return the vertex number given row and col
	private int xyToV(int col, int row) {
		return col + row * W;
	}
	
	//given vertex number, return its row
	private int vToRow(int v) {
		return v / W;
	}
	
	private int vToCol(int v) {
		return v % W;
	}
	
	private void updateEnergyMattrix(int row, int col) {
		if (col == 0 || row == 0 || col == W-1 || row == H-1) {
			energyMatrix[row][col] = 1000.00;
			squareMatrix[row][col] = 1000 * 1000;
		}
		else {
			double res = Math.sqrt(energeySquare(colorMatrix[row][col - 1],colorMatrix[row][col + 1])
					+ energeySquare(colorMatrix[row - 1][col],colorMatrix[row + 1][col]));
			energyMatrix[row][col] = res;
			squareMatrix[row][col] = energeySquare(colorMatrix[row][col - 1],colorMatrix[row][col + 1])
			+ energeySquare(colorMatrix[row - 1][col],colorMatrix[row + 1][col]);
		}
	}
}
