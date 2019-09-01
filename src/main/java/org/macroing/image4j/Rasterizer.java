/**
 * Copyright 2019 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.image4j.
 * 
 * org.macroing.image4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.image4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.image4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.image4j;

import static org.macroing.image4j.Integers.abs;

/**
 * A class that performs rasterization on lines and triangles.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Rasterizer {
	private Rasterizer() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static int[][] rasterizeLine(final int startX, final int startY, final int endX, final int endY) {
//		Rasterizes a line using Bresenham's line algorithm.
		
		final int w = endX - startX;
		final int h = endY - startY;
		
		final int wAbs = abs(w);
		final int hAbs = abs(h);
		
		final int dAX = w < 0 ? -1 : w > 0 ? 1 : 0;
		final int dAY = h < 0 ? -1 : h > 0 ? 1 : 0;
		final int dBX = wAbs > hAbs ? dAX : 0;
		final int dBY = wAbs > hAbs ? 0 : dAY;
		
		final int l = wAbs > hAbs ? wAbs : hAbs;
		final int s = wAbs > hAbs ? hAbs : wAbs;
		
		final int[][] line = new int[l + 1][];
		
		int n = l >> 1;
		
		int x = startX;
		int y = startY;
		
		for(int i = 0; i <= l; i++) {
			line[i] = new int[] {x, y};
			
			n += s;
			
			if(n >= l) {
				n -= l;
				
				x += dAX;
				y += dAY;
			} else {
				x += dBX;
				y += dBY;
			}
		}
		
		return line;
	}
	
//	TODO: Add Javadocs!
	public static int[][][] rasterizeTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY) {
		final int[][] vertices = new int[][] {{aX, aY}, {bX, bY}, {cX, cY}};
		
		doSortVerticesAscendingByY(vertices);
		
		final int[] vertexA = vertices[0];
		final int[] vertexB = vertices[1];
		final int[] vertexC = vertices[2];
		
		if(vertexB[1] == vertexC[1]) {
			return doRasterizeTriangleTopDown(vertexA, vertexB, vertexC);
		} else if(vertexA[1] == vertexB[1]) {
			return doRasterizeTriangleBottomUp(vertexA, vertexB, vertexC);
		} else {
			final int[] vertexD = {(int)(vertexA[0] + ((float)(vertexB[1] - vertexA[1]) / (float)(vertexC[1] - vertexA[1])) * (vertexC[0] - vertexA[0])), vertexB[1]};
			
			final int[][][] linesTopDown = doRasterizeTriangleTopDown(vertexA, vertexB, vertexD);
			final int[][][] linesBottomUp = doRasterizeTriangleBottomUp(vertexB, vertexD, vertexC);
			final int[][][] lines = new int[linesTopDown.length + linesBottomUp.length][][];
			
			for(int i = 0; i < linesTopDown.length; i++) {
				lines[i] = linesTopDown[i];
			}
			
			for(int i = linesTopDown.length, j = 0; i < lines.length && j < linesBottomUp.length; i++, j++) {
				lines[i] = linesBottomUp[j];
			}
			
			return lines;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int[][][] doRasterizeTriangleBottomUp(final int[] vertexA, final int[] vertexB, final int[] vertexC) {
		final float slope0 = (float)(vertexC[0] - vertexA[0]) / (float)(vertexC[1] - vertexA[1]);
		final float slope1 = (float)(vertexC[0] - vertexB[0]) / (float)(vertexC[1] - vertexB[1]);
		
		float x0 = vertexC[0];
		float x1 = vertexC[0] + 0.5F;
		
		final int yStart = vertexC[1];
		final int yEnd = vertexA[1];
		final int yLength = yStart - yEnd;
		
		final int[][][] lines = new int[yLength][][];
		
		for(int i = 0, y = yStart; y > yEnd; i++, y--) {
			final int[][] line = rasterizeLine((int)(x0), y, (int)(x1), y);
			
			lines[i] = line;
			
			x0 -= slope0;
			x1 -= slope1;
		}
		
		return lines;
	}
	
	private static int[][][] doRasterizeTriangleTopDown(final int[] vertexA, final int[] vertexB, final int[] vertexC) {
		final float slope0 = (float)(vertexB[0] - vertexA[0]) / (float)(vertexB[1] - vertexA[1]);
		final float slope1 = (float)(vertexC[0] - vertexA[0]) / (float)(vertexC[1] - vertexA[1]);
		
		float x0 = vertexA[0];
		float x1 = vertexA[0] + 0.5F;
		
		final int yStart = vertexA[1];
		final int yEnd = vertexB[1];
		final int yLength = yEnd - yStart + 1;
		
		final int[][][] lines = new int[yLength][][];
		
		for(int i = 0, y = yStart; y <= yEnd; i++, y++) {
			final int[][] line = rasterizeLine((int)(x0), y, (int)(x1), y);
			
			lines[i] = line;
			
			x0 += slope0;
			x1 += slope1;
		}
		
		return lines;
	}
	
	private static void doSortVerticesAscendingByY(final int[][] vertices) {
		if(vertices[0][1] > vertices[1][1]) {
			final int[] a = vertices[0];
			final int[] b = vertices[1];
			
			vertices[0] = b;
			vertices[1] = a;
		}
		
		if(vertices[0][1] > vertices[2][1]) {
			final int[] a = vertices[0];
			final int[] c = vertices[2];
			
			vertices[0] = c;
			vertices[2] = a;
		}
		
		if(vertices[1][1] > vertices[2][1]) {
			final int[] b = vertices[1];
			final int[] c = vertices[2];
			
			vertices[1] = c;
			vertices[2] = b;
		}
	}
}