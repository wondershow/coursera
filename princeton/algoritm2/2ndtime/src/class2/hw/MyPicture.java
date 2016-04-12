package class2.hw;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
public class MyPicture {
	
	
	
	
	public static void main(String[] args) {
		
		Color c = new Color(1,2,4);
		int color = c.getRGB();
		System.out.println((color >> 16) & 0xFF);
		
	}
	
	
	public static void setR() {
		
		
	}
	
	/**
	 * line number starts from 0
	 * */
	public static Picture removeVerticalLine(Picture p, int lineNumber) {
		Picture res;
		int w = p.width();
		int h = p.height();
		res = new Picture (w-1, h);
		
		for (int row = 0; row < h; row++)
			for (int col = 0; col < w-1; col++)
				if (col < lineNumber) res.set(col, row, p.get(col, row));
				else res.set(col, row, p.get(col + 1, row));
		
		return res;
	}
	
	
	/**
	 * line number starts from 0
	 * */
	public static Picture removeHLine(Picture p, int lineNumber) {
		Picture res;
		int w = p.width();
		int h = p.height();
		res = new Picture (w, h-1);
		
		for (int row = 0; row < h-1; row++)
			for (int col = 0; col < w; col++)
				if (row < lineNumber) res.set(col, row, p.get(col, row));
				else res.set(col, row, p.get(col, row+1));
		
		return res;
	}
	
}
