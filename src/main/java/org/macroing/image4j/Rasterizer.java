/**
 * Copyright 2019 - 2020 J&#246;rgen Lundgren
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

import static org.macroing.math4j.MathF.toFloat;
import static org.macroing.math4j.MathI.abs;
import static org.macroing.math4j.MathI.toInt;

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
	
	/**
	 * Performs rasterization on the line from {@code startX} and {@code startY} to {@code endX} and {@code endY}.
	 * <p>
	 * Returns a scan line that contains X- and Y-coordinates for each individual pixel.
	 * <p>
	 * This method uses Bresenham's line algorithm, but it also performs clipping to the intervals {@code [0, resolutionX)} and {@code [0, resolutionY)}.
	 * <p>
	 * The following example demonstrates how you can use this method:
	 * <pre>
	 * <code>
	 * int[][] scanLine = Rasterizer.rasterizeLine(startX, startY, endX, endY, resolutionX, resolutionY);
	 * 
	 * for(int[] pixel : scanLine) {
	 *     int x = pixel[0];
	 *     int y = pixel[1];
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param startX the X-coordinate to start from
	 * @param startY the Y-coordinate to start from
	 * @param endX the X-coordinate to end at
	 * @param endY the Y-coordinate to end at
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @return a scan line that contains X- and Y-coordinates for each individual pixel
	 */
	public static int[][] rasterizeLine(final int startX, final int startY, final int endX, final int endY, final int resolutionX, final int resolutionY) {
		final int clippedStartX = startX < 0 ? 0 : startX >= resolutionX ? resolutionX - 1 : startX;
		final int clippedStartY = startY < 0 ? 0 : startY >= resolutionY ? resolutionY - 1 : startY;
		final int clippedEndX = endX < 0 ? 0 : endX >= resolutionX ? resolutionX - 1 : endX;
		final int clippedEndY = endY < 0 ? 0 : endY >= resolutionY ? resolutionY - 1 : endY;
		
		final int w = clippedEndX - clippedStartX;
		final int h = clippedEndY - clippedStartY;
		
		final int wAbs = abs(w);
		final int hAbs = abs(h);
		
		final int dAX = w < 0 ? -1 : w > 0 ? 1 : 0;
		final int dAY = h < 0 ? -1 : h > 0 ? 1 : 0;
		final int dBX = wAbs > hAbs ? dAX : 0;
		final int dBY = wAbs > hAbs ? 0 : dAY;
		
		final int l = wAbs > hAbs ? wAbs : hAbs;
		final int s = wAbs > hAbs ? hAbs : wAbs;
		
		final int[][] scanLine = new int[l + 1][];
		
		int n = l >> 1;
		
		int x = clippedStartX;
		int y = clippedStartY;
		
		for(int i = 0; i <= l; i++) {
			scanLine[i] = new int[] {x, y};
			
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
		
		return scanLine;
	}
	
	/**
	 * Performs rasterization on the triangle defined by {@code aX}, {@code aY}, {@code bX}, {@code bY}, {@code cX} and {@code cY}.
	 * <p>
	 * Returns a list of scan lines.
	 * <p>
	 * The following example demonstrates how you can use this method:
	 * <pre>
	 * <code>
	 * int[][][] scanLines = Rasterizer.rasterizeTriangle(aX, aY, bX, bY, cX, cY, resolutionX, resolutionY);
	 * 
	 * for(int[][] scanLine : scanLines) {
	 *     for(int[] pixel : scanLine) {
	 *         int x = pixel[0];
	 *         int y = pixel[1];
	 *     }
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @return a list of scan lines
	 */
	public static int[][][] rasterizeTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, final int resolutionX, final int resolutionY) {
		final int[][] vertices = new int[][] {{aX, aY}, {bX, bY}, {cX, cY}};
		
		doSortVerticesAscendingByY(vertices);
		
		final int[] vertexA = vertices[0];
		final int[] vertexB = vertices[1];
		final int[] vertexC = vertices[2];
		
		if(vertexB[1] == vertexC[1]) {
			return doRasterizeTriangleTopDown(vertexA, vertexB, vertexC, resolutionX, resolutionY);
		} else if(vertexA[1] == vertexB[1]) {
			return doRasterizeTriangleBottomUp(vertexA, vertexB, vertexC, resolutionX, resolutionY);
		} else {
			final int[] vertexD = {toInt(vertexA[0] + (toFloat(vertexB[1] - vertexA[1]) / (vertexC[1] - vertexA[1])) * (vertexC[0] - vertexA[0])), vertexB[1]};
			
			final int[][][] scanLinesTopDown = doRasterizeTriangleTopDown(vertexA, vertexB, vertexD, resolutionX, resolutionY);
			final int[][][] scanLinesBottomUp = doRasterizeTriangleBottomUp(vertexB, vertexD, vertexC, resolutionX, resolutionY);
			final int[][][] scanLines = new int[scanLinesTopDown.length + scanLinesBottomUp.length][][];
			
			for(int i = 0; i < scanLinesTopDown.length; i++) {
				scanLines[i] = scanLinesTopDown[i];
			}
			
			for(int i = scanLinesTopDown.length, j = 0; i < scanLines.length && j < scanLinesBottomUp.length; i++, j++) {
				scanLines[i] = scanLinesBottomUp[j];
			}
			
			return scanLines;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int[][][] doRasterizeTriangleBottomUp(final int[] vertexA, final int[] vertexB, final int[] vertexC, final int resolutionX, final int resolutionY) {
		final float slope0 = toFloat(vertexC[0] - vertexA[0]) / (vertexC[1] - vertexA[1]);
		final float slope1 = toFloat(vertexC[0] - vertexB[0]) / (vertexC[1] - vertexB[1]);
		
		float x0 = vertexC[0];
		float x1 = vertexC[0] + 0.5F;
		
		final int yStart = vertexC[1];
		final int yEnd = vertexA[1];
		final int yLength = yStart - yEnd;
		
		final int[][][] scanLines = new int[yLength][][];
		
		for(int i = 0, y = yStart; y > yEnd; i++, y--) {
			scanLines[i] = rasterizeLine(toInt(x0), y, toInt(x1), y, resolutionX, resolutionY);
			
			x0 -= slope0;
			x1 -= slope1;
		}
		
		return scanLines;
	}
	
	private static int[][][] doRasterizeTriangleTopDown(final int[] vertexA, final int[] vertexB, final int[] vertexC, final int resolutionX, final int resolutionY) {
		final float slope0 = toFloat(vertexB[0] - vertexA[0]) / (vertexB[1] - vertexA[1]);
		final float slope1 = toFloat(vertexC[0] - vertexA[0]) / (vertexC[1] - vertexA[1]);
		
		float x0 = vertexA[0];
		float x1 = vertexA[0] + 0.5F;
		
		final int yStart = vertexA[1];
		final int yEnd = vertexB[1];
		final int yLength = yEnd - yStart + 1;
		
		final int[][][] scanLines = new int[yLength][][];
		
		for(int i = 0, y = yStart; y <= yEnd; i++, y++) {
			scanLines[i] = rasterizeLine(toInt(x0), y, toInt(x1), y, resolutionX, resolutionY);
			
			x0 += slope0;
			x1 += slope1;
		}
		
		return scanLines;
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