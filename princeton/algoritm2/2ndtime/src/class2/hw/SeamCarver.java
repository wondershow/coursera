package class2.hw;

import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
	
	private int[][] RGB;
	private int W, H;
	private double[][] energeyMatrix;
	private final double BOARDER_ENERGY = 1000;
	private boolean isHorizontal;
	
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture)  {
		if (picture == null) throw new java.lang.NullPointerException("constructor called with null parameter");
		
		W = picture.width();
		H = picture.height();
		int N = Math.max(W, H);
		
		RGB = new int[N][N];
		
		energeyMatrix = new double[N][N];
		
		Color color;
		for (int row = 0; row < H; row++)
			for (int col = 0; col< W; col++) {
				color = picture.get(col, row);
				RGB[row][col] = color.getRGB();
			}
		
		for (int row = 0; row < H; row++)
			for (int col = 0; col< W; col++)
				updateEnergy(row, col);
		
		isHorizontal = true;
	}
	
	// current picture
	public Picture picture()        {
		int pW = W, pH = H;
		if (!isHorizontal) {	pW = H;	pH = W;	}
		Picture res = new Picture(pW, pH);
		for (int row = 0; row < pH; row++)
			for (int col = 0; col< pW; col++) {
				if (isHorizontal) res.set(col, row, new Color(RGB[row][col]));
				else res.set(col, row, new Color(RGB[col][row]));
			}
		return res;
	}
	
	private void transpose() {
		int min = Math.min(W, H);
		int tmp;
		double tmp1;
		for (int row = 0; row < H; row++)
			for (int col = 0; col < W; col++){
				if (col < min && row < min) {
					if (col != row && row > col) {
						tmp = RGB[row][col]; RGB[row][col] = RGB[col][row]; RGB[col][row] = tmp;
						tmp1 = energeyMatrix[row][col];
						energeyMatrix[row][col] = energeyMatrix[col][row];
						energeyMatrix[col][row] = tmp1;
					}
				} else {
					RGB[col][row] = RGB[row][col];
					energeyMatrix[col][row] = energeyMatrix[row][col];
				}
			}
		isHorizontal = !isHorizontal;
		
		int t = W;
		W = H;
		H = t;
	}
	
	// width of current picture
	public int width()  { if (!isHorizontal) return H; else return W;}
	
	// height of current picture
	public	int height() { 	if (!isHorizontal) return W; else return H;}
	   
	// energy of pixel at column x and row y
	public double energy(int col, int row) {
		if (!this.isHorizontal) {
			int tmp = col;
			col = row;
			row = tmp;
		}
		
		if (col < 0 || row < 0 || col >= W || row >= H)
			throw new java.lang.IndexOutOfBoundsException(" row = " + row + ", col = " + col + ", (W = " + W + ", H = " + H + ")" );
		return energeyMatrix[row][col];
	}     
	
	// sequence of indices for horizontal seam
	public  int[] findHorizontalSeam() {
		if (isHorizontal) transpose();
		return seamHelper();
	}     
	   
	// sequence of indices for vertical seam
	public   int[] findVerticalSeam() {
		if (!isHorizontal) transpose();
		return seamHelper();
	}   
	
	//do a shortest path, only work on vertical 
	private int[] seamHelper() {
		int[] res = new int[H];
		double[][] distanceTo = new double[H][W];
		int[][] edgeTo = new int[H][W];
		
		for (int i = 1; i < H; i++) Arrays.fill(distanceTo[i], Integer.MAX_VALUE);
		Arrays.fill(distanceTo[0], 0);
		for (int i = 0; i < H; i++) Arrays.fill(edgeTo[i], -1);
		
		
		for (int row = 0; row < H - 1; row++)
			for (int col = 0; col < W; col++)
				relax(row, col, distanceTo, edgeTo);
		
		double minEnergy = Double.MAX_VALUE;
		int seamCol = -1;
		for (int col = 0; col < W; col++) {
			if (distanceTo[H-1][col] < minEnergy){
				minEnergy = distanceTo[H-1][col];
				seamCol = col;
			}
		}
		
		res[H-1] = seamCol;
		int parentCol = edgeTo[H-1][seamCol];
		for (int row = H-2; row >=0; row--) {
			res[row] = parentCol;
			parentCol =edgeTo[row][parentCol];
		}
		
		return res;
	}
	
	// do a relax on a node
	private void relax(int row, int col, double[][] distanceTo, int[][] edgeTo ) {
		if (col > 0 &&   distanceTo[row][col] + energeyMatrix[row + 1][col - 1] < distanceTo[row + 1][col - 1]) {
			distanceTo[row + 1][col - 1] = distanceTo[row][col] + energeyMatrix[row + 1][col - 1];
			edgeTo[row + 1][col - 1] = col;
		}
		
		if ( distanceTo[row][col] + energeyMatrix[row + 1][col] < distanceTo[row+1][col]  ) {
			distanceTo[row+1][col] = distanceTo[row][col] + energeyMatrix[row + 1][col];
			edgeTo[row+1][col] = col;
		}
		
		if (col < W-1 && distanceTo[row][col] + energeyMatrix[row + 1][col+1] < distanceTo[row+1][col+1]) {
			distanceTo[row+1][col+1] = distanceTo[row][col] + energeyMatrix[row + 1][col+1];
			edgeTo[row+1][col+1] = col;
		}
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam)   {
		int width = W, height = H;
		if (!isHorizontal) { width = H; height = W;}
		
		if (seam == null) throw new java.lang.NullPointerException();
		if (height == 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != width) throw new java.lang.IllegalArgumentException(); 
		for (int i = 0; i < seam.length-1; i++) {
			if (Math.abs(seam[i+1]-seam[i]) >1) throw new java.lang.IllegalArgumentException("seam[i] = " + seam[i]); 
		}
		for (int i = 0; i < seam.length; i++) 
			if (seam[i] <0 || seam[i] >=height) throw new java.lang.IllegalArgumentException("seam[i] = " + seam[i] + ", height = " + height);
		if (isHorizontal) transpose();
		removeVSeamHelper(seam);
	}
	
	private void removeVSeamHelper(int[] seam) {
		for (int row = 0; row < H; row++) {
			int col = seam[row];
			System.arraycopy(RGB[row], col + 1 , RGB[row], col, W-1 - col);
			System.arraycopy(energeyMatrix[row], col + 1, energeyMatrix[row], col, W-1 - col);
		}
	
		W--;
	
		for (int row = 0; row < H; row++) {
			int col = seam[row];
			if (col > 0) updateEnergy(row, col - 1);
			if (col < W) updateEnergy(row, col);
		}
	}
	   
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam)     {
		int width = W, height = H;
		if (!isHorizontal) { width = H; height = W;}
		
		if (seam == null) throw new java.lang.NullPointerException();
		if (width == 1) throw new java.lang.IllegalArgumentException();
		if (seam.length != height) throw new java.lang.IllegalArgumentException(); 
		for (int i = 0; i < seam.length-1; i++) {
			if (Math.abs(seam[i+1]-seam[i]) >1) throw new java.lang.IllegalArgumentException(); 
		}
		for (int i = 0; i < seam.length; i++) 
			if (seam[i] <0 || seam[i] >=width) throw new java.lang.IllegalArgumentException();
		
		if (!this.isHorizontal) transpose();
		removeVSeamHelper(seam);
	}
	
	private void updateEnergy(int row, int col) {
		if (row == 0 || row == H-1 || col == 0 || col == W-1)
			energeyMatrix[row][col] = BOARDER_ENERGY;
		else
			energeyMatrix[row][col] = Math.sqrt(energeySquare(row + 1,col, row -1, col)
									  + energeySquare(row, col+1, row, col-1));
	}
	
	private int energeySquare(int row1, int col1, int row2, int col2) {
		Color tmp1 = new Color(RGB[row1][col1]);
		Color tmp2 = new Color(RGB[row2][col2]);
		int deltaR = tmp1.getRed() - tmp2.getRed();//           R[row1][col1] -  R[row2][col2];
		int deltaG = tmp1.getGreen() - tmp2.getGreen();//G[row1][col1] -  G[row2][col2];
		int deltaB = tmp1.getBlue() - tmp2.getBlue();//B[row1][col1] -  B[row2][col2];
		return deltaR*deltaR + deltaG*deltaG + deltaB*deltaB;
	}
}