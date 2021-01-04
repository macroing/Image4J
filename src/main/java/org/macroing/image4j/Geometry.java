/**
 * Copyright 2019 - 2021 J&#246;rgen Lundgren
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

import static org.macroing.math4j.MathI.max;
import static org.macroing.math4j.MathI.min;

/**
 * A class that consists exclusively of static methods that performs various operations on geometric primitives.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Geometry {
	private Geometry() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code float[]} with Barycentric coordinates.
	 * <p>
	 * The U-, V- and W-coordinates of the returned {@code float[]} can be accessed by the indices {@code 0}, {@code 1} and {@code 2}, respectively.
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param pX the X-coordinate of the point {@code P}, which is the point for which the Barycentric coordinates are calculated
	 * @param pY the Y-coordinate of the point {@code P}, which is the point for which the Barycentric coordinates are calculated
	 * @return a {@code float[]} with Barycentric coordinates
	 */
	public static float[] barycentricCoordinates(final float aX, final float aY, final float bX, final float bY, final float cX, final float cY, final float pX, final float pY) {
		final float edgeABX = bX - aX;
		final float edgeABY = bY - aY;
		final float edgeACX = cX - aX;
		final float edgeACY = cY - aY;
		final float edgeAPX = pX - aX;
		final float edgeAPY = pY - aY;
		
		final float denominator = edgeABX * edgeACY - edgeACX * edgeABY;
		final float denominatorReciprocal = 1.0F / denominator;
		
		final float v = (edgeAPX * edgeACY - edgeACX * edgeAPY) * denominatorReciprocal;
		final float w = (edgeABX * edgeAPY - edgeAPX * edgeABY) * denominatorReciprocal;
		final float u = 1.0F - v - w;
		
		return new float[] {u, v, w};
	}
	
	/**
	 * Calculates a list of bounding boxes for the two rectangles {@code A} and {@code B}.
	 * <p>
	 * Returns an {@code int[][]} with the calculated bounding boxes.
	 * <p>
	 * This method calculates at least four bounding boxes. If the two rectangles overlap, the remaining four bounding boxes will be calculated.
	 * <p>
	 * Each bounding box consists of the minimum X- and Y-coordinates followed by the maximum X- and Y-coordinates.
	 * <p>
	 * The following list shows the different bounding boxes that are calculated:
	 * <ol>
	 * <li>The global bounding box for rectangle {@code A}</li>
	 * <li>The local bounding box for rectangle {@code A}</li>
	 * <li>The global bounding box for rectangle {@code B}</li>
	 * <li>The local bounding box for rectangle {@code B}</li>
	 * <li>The global bounding box for rectangle {@code C}</li>
	 * <li>The local bounding box for rectangle {@code C}</li>
	 * <li>The local bounding box for rectangle {@code C}, but in the same coordinate system as the local bounding box for rectangle {@code A}</li>
	 * <li>The local bounding box for rectangle {@code C}, but in the same coordinate system as the local bounding box for rectangle {@code B}</li>
	 * </ol>
	 * <p>
	 * A global bounding box is expressed in the global coordinate system. A local bounding box is expressed in a local coordinate system.
	 * <p>
	 * The rectangle {@code C} is the rectangle that overlaps the rectangles {@code A} and {@code B}.
	 * 
	 * @param aX the minimum X-coordinate of rectangle {@code A}, in the global coordinate space
	 * @param aY the minimum Y-coordinate of rectangle {@code A}, in the global coordinate space
	 * @param aW the width of rectangle {@code A}
	 * @param aH the height of rectangle {@code A}
	 * @param bX the minimum X-coordinate of rectangle {@code B}, in the global coordinate space
	 * @param bY the minimum Y-coordinate of rectangle {@code B}, in the global coordinate space
	 * @param bW the width of rectangle {@code B}
	 * @param bH the height of rectangle {@code B}
	 * @return an {@code int[][]} with the calculated bounding boxes
	 */
	public static int[][] calculateRectangleBoundingBoxes(final int aX, final int aY, final int aW, final int aH, final int bX, final int bY, final int bW, final int bH) {
//		Calculate the global bounding box for A:
		final int aGlobalMinimumX = aX;
		final int aGlobalMinimumY = aY;
		final int aGlobalMaximumX = aX + aW;
		final int aGlobalMaximumY = aY + aH;
		
//		Calculate the local bounding box for A:
		final int aLocalMinimumX = 0;
		final int aLocalMinimumY = 0;
		final int aLocalMaximumX = aW;
		final int aLocalMaximumY = aH;
		
//		Calculate the global bounding box for B:
		final int bGlobalMinimumX = bX;
		final int bGlobalMinimumY = bY;
		final int bGlobalMaximumX = bX + bW;
		final int bGlobalMaximumY = bY + bH;
		
//		Calculate the local bounding box for B:
		final int bLocalMinimumX = 0;
		final int bLocalMinimumY = 0;
		final int bLocalMaximumX = bW;
		final int bLocalMaximumY = bH;
		
//		Create the 2D-array to hold all bounding boxes:
		final int[][] boundingBoxes = new int[8][];
		
//		Fill the 2D-array with the bounding boxes that have been calculated at this point:
		boundingBoxes[0] = new int[] {aGlobalMinimumX, aGlobalMinimumY, aGlobalMaximumX, aGlobalMaximumY};
		boundingBoxes[1] = new int[] {aLocalMinimumX, aLocalMinimumY, aLocalMaximumX, aLocalMaximumY};
		boundingBoxes[2] = new int[] {bGlobalMinimumX, bGlobalMinimumY, bGlobalMaximumX, bGlobalMaximumY};
		boundingBoxes[3] = new int[] {bLocalMinimumX, bLocalMinimumY, bLocalMaximumX, bLocalMaximumY};
		boundingBoxes[4] = new int[] {};
		boundingBoxes[5] = new int[] {};
		boundingBoxes[6] = new int[] {};
		boundingBoxes[7] = new int[] {};
		
//		Perform an overlap check in the global coordinate space:
		final boolean isAInsideB = aGlobalMinimumX >= bGlobalMinimumX && aGlobalMaximumX <= bGlobalMaximumX && aGlobalMinimumY >= bGlobalMinimumY && aGlobalMaximumY <= bGlobalMaximumY;
		final boolean isBInsideA = bGlobalMinimumX >= aGlobalMinimumX && bGlobalMaximumX <= aGlobalMaximumX && bGlobalMinimumY >= aGlobalMinimumY && bGlobalMaximumY <= aGlobalMaximumY;
		final boolean isAOverlappingBX = aGlobalMinimumX <= bGlobalMinimumX && aGlobalMaximumX >= bGlobalMinimumX || aGlobalMinimumX <= bGlobalMaximumX && aGlobalMaximumX >= bGlobalMaximumX;
		final boolean isAOverlappingBY = aGlobalMinimumY <= bGlobalMinimumY && aGlobalMaximumY >= bGlobalMinimumY || aGlobalMinimumY <= bGlobalMaximumY && aGlobalMaximumY >= bGlobalMaximumY;
		final boolean isOverlapping = isAInsideB || isBInsideA || isAOverlappingBX && isAOverlappingBY;
		
		if(isOverlapping) {
//			Calculate the global bounding box for C:
			final int cGlobalMinimumX = max(aGlobalMinimumX, bGlobalMinimumX);
			final int cGlobalMinimumY = max(aGlobalMinimumY, bGlobalMinimumY);
			final int cGlobalMaximumX = min(aGlobalMaximumX, bGlobalMaximumX);
			final int cGlobalMaximumY = min(aGlobalMaximumY, bGlobalMaximumY);
			
//			Calculate the local bounding box for C:
			final int cLocalMinimumX = 0;
			final int cLocalMinimumY = 0;
			final int cLocalMaximumX = cGlobalMaximumX - cGlobalMinimumX;
			final int cLocalMaximumY = cGlobalMaximumY - cGlobalMinimumY;
			
//			Calculate the local bounding box for C, but in the same coordinate system as the local bounding box of A:
			final int caLocalMinimumX = cGlobalMinimumX - (aGlobalMinimumX - aLocalMinimumX);
			final int caLocalMinimumY = cGlobalMinimumY - (aGlobalMinimumY - aLocalMinimumY);
			final int caLocalMaximumX = cGlobalMaximumX - (aGlobalMaximumX - aLocalMaximumX);
			final int caLocalMaximumY = cGlobalMaximumY - (aGlobalMaximumY - aLocalMaximumY);
			
//			Calculate the local bounding box for C, but in the same coordinate system as the local bounding box of B:
			final int cbLocalMinimumX = cGlobalMinimumX - (bGlobalMinimumX - bLocalMinimumX);
			final int cbLocalMinimumY = cGlobalMinimumY - (bGlobalMinimumY - bLocalMinimumY);
			final int cbLocalMaximumX = cGlobalMaximumX - (bGlobalMaximumX - bLocalMaximumX);
			final int cbLocalMaximumY = cGlobalMaximumY - (bGlobalMaximumY - bLocalMaximumY);
			
//			Fill the 2D-array with the remaining bounding boxes:
			boundingBoxes[4] = new int[] {cGlobalMinimumX, cGlobalMinimumY, cGlobalMaximumX, cGlobalMaximumY};
			boundingBoxes[5] = new int[] {cLocalMinimumX, cLocalMinimumY, cLocalMaximumX, cLocalMaximumY};
			boundingBoxes[6] = new int[] {caLocalMinimumX, caLocalMinimumY, caLocalMaximumX, caLocalMaximumY};
			boundingBoxes[7] = new int[] {cbLocalMinimumX, cbLocalMinimumY, cbLocalMaximumX, cbLocalMaximumY};
		}
		
		return boundingBoxes;
	}
}