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
}