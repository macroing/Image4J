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
	public static final float PI = toFloat(Math.PI);
	public static final float PI_MULTIPLIED_BY_TWO = PI * 2.0F;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Floats() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static float abs(final float a) {
		return toFloat(Math.abs(a));
	}
	
	public static float blerp(final float value00, final float value01, final float value10, final float value11, final float tX, final float tY) {
		return lerp(lerp(value00, value01, tX), lerp(value10, value11, tX), tY);
	}
	
	public static float exp(final float a) {
		return toFloat(Math.exp(a));
	}
	
	public static float floor(final float a) {
		return toFloat(Math.floor(a));
	}
	
	public static float lerp(final float value0, final float value1, final float t) {
		return (1.0F - t) * value0 + t * value1;
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
		return toFloat(Math.pow(base, exponent));
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
	
	public static float smoothstep(final float value, final float a, final float b) {
		final float minimumValue = min(a, b);
		final float maximumValue = max(a, b);
		
		final float x = saturate((value - minimumValue) / (maximumValue - minimumValue), 0.0F, 1.0F);
		
		return x * x * (3.0F - 2.0F * x);
	}
	
	public static float toDegrees(final float angleInRadians) {
		return toFloat(Math.toDegrees(angleInRadians));
	}
	
	public static float toFloat(final double value) {
		return (float)(value);
	}
	
	public static float toRadians(final float angleInDegrees) {
		return toFloat(Math.toRadians(angleInDegrees));
	}
	
	public static float wrapAround(final float value, final float a, final float b) {
		final float minimumValue = min(a, b);
		final float maximumValue = max(a, b);
		
		float currentValue = value;
		
		while(currentValue < minimumValue || currentValue > maximumValue) {
			if(currentValue < minimumValue) {
				currentValue = maximumValue - (minimumValue - currentValue);
			} else if(currentValue > maximumValue) {
				currentValue = minimumValue + (currentValue - maximumValue);
			}
		}
		
		return currentValue;
	}
}