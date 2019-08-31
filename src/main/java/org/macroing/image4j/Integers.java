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

final class Integers {
	private Integers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int abs(final int a) {
		return Math.abs(a);
	}
	
	public static int max(final int a, final int b) {
		return a >= b ? a : b;
	}
	
	public static int max(final int a, final int b, final int c) {
		return a >= b && a >= c ? a : b >= c ? b : c;
	}
	
	public static int min(final int a, final int b) {
		return a <= b ? a : b;
	}
	
	public static int min(final int a, final int b, final int c) {
		return a <= b && a <= c ? a : b <= c ? b : c;
	}
	
	public static int toInt(final float value) {
		return (int)(value);
	}
	
	public static int modulo(final int x, final int n) {
		return x < 0 ? (x % n + n) % n : x % n;
	}
	
	public static int requirePositiveIntValue(final int value, final String variableName) {
		if(value < 0) {
			throw new IllegalArgumentException(String.format("%s < 0: %s=%s", variableName, variableName, Integer.toString(value)));
		}
		
		return value;
	}
	
	public static int saturate(final int value) {
		return saturate(value, 0, 255);
	}
	
	public static int saturate(final int value, final int edgeA, final int edgeB) {
		final int minimumValue = edgeA < edgeB ? edgeA : edgeB;
		final int maximumValue = edgeA > edgeB ? edgeA : edgeB;
		
		return value < minimumValue ? minimumValue : value > maximumValue ? maximumValue : value;
	}
}