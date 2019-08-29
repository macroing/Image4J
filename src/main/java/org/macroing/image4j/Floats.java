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

import java.util.concurrent.ThreadLocalRandom;

final class Floats {
	private Floats() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static float exp(final float a) {
		return (float)(Math.exp(a));
	}
	
	public static float lerp(final float a, final float b, final float t) {
		return (1.0F - t) * a + t * b;
	}
	
	public static float max(final float a, final float b) {
		return a > b ? a : b;
	}
	
	public static float max(final float a, final float b, final float c) {
		return a > b && a > c ? a : b > c ? b : c;
	}
	
	public static float min(final float a, final float b) {
		return a < b ? a : b;
	}
	
	public static float min(final float a, final float b, final float c) {
		return a < b && a < c ? a : b < c ? b : c;
	}
	
	public static float nextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
	
	public static float pow(final float base, final float exponent) {
		return (float)(Math.pow(base, exponent));
	}
	
	public static float requireFiniteFloatValue(final float value, final String variableName) {
		if(Float.isInfinite(value)) {
			throw new IllegalArgumentException(String.format("Float.isInfinite(%s) == true: %s=%s", variableName, variableName, Float.toString(value)));
		} else if(Float.isNaN(value)) {
			throw new IllegalArgumentException(String.format("Float.isNaN(%s) == true: %s=%s", variableName, variableName, Float.toString(value)));
		}
		
		return value;
	}
	
	public static float saturate(final float value) {
		return saturate(value, 0.0F, 1.0F);
	}
	
	public static float saturate(final float value, final float edgeA, final float edgeB) {
		final float minimumValue = edgeA < edgeB ? edgeA : edgeB;
		final float maximumValue = edgeA > edgeB ? edgeA : edgeB;
		
		return value < minimumValue ? minimumValue : value > maximumValue ? maximumValue : value;
	}
}