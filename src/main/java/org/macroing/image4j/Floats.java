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

/**
 * A class that consists exclusively of static methods that returns or performs various operations on {@code float}s and various constants.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Floats {
	/**
	 * A {@code float} representation of pi.
	 */
	public static final float PI = toFloat(Math.PI);
	
	/**
	 * A {@code float} representation of pi multiplied by {@code 2.0F}.
	 */
	public static final float PI_MULTIPLIED_BY_TWO = PI * 2.0F;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Floats() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the absolute version of {@code value}.
	 * 
	 * @param value a {@code float} value
	 * @return the absolute version of {@code value}
	 */
	public static float abs(final float value) {
		return toFloat(Math.abs(value));
	}
	
	/**
	 * Performs a bilinear interpolation operation on the supplied values.
	 * <p>
	 * Returns the bilinearly interpolated value.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Floats.lerp(Floats.lerp(value00, value01, tX), Floats.lerp(value10, value11, tX), tY);
	 * }
	 * </pre>
	 * 
	 * @param value00 a {@code float} value
	 * @param value01 a {@code float} value
	 * @param value10 a {@code float} value
	 * @param value11 a {@code float} value
	 * @param tX the X-axis factor
	 * @param tY the Y-axis factor
	 * @return the bilinearly interpolated value
	 */
	public static float blerp(final float value00, final float value01, final float value10, final float value11, final float tX, final float tY) {
		return lerp(lerp(value00, value01, tX), lerp(value10, value11, tX), tY);
	}
	
	/**
	 * Returns Euler's number {@code e} raised to the power of {@code exponent}.
	 * 
	 * @param exponent the exponent to raise {@code e} to
	 * @return Euler's number {@code e} raised to the power of {@code exponent}
	 */
	public static float exp(final float exponent) {
		return toFloat(Math.exp(exponent));
	}
	
	/**
	 * Returns the largest (closest to positive infinity) {@code float} value that is less than or equal to {@code value} and is equal to a mathematical integer.
	 * 
	 * @param value a value
	 * @return the largest (closest to positive infinity) {@code float} value that is less than or equal to {@code value} and is equal to a mathematical integer
	 */
	public static float floor(final float value) {
		return toFloat(Math.floor(value));
	}
	
	/**
	 * Performs a linear interpolation operation on the supplied values.
	 * <p>
	 * Returns the linearly interpolated value.
	 * 
	 * @param value0 a {@code float} value
	 * @param value1 a {@code float} value
	 * @param t the factor
	 * @return the linearly interpolated value
	 */
	public static float lerp(final float value0, final float value1, final float t) {
		return (1.0F - t) * value0 + t * value1;
	}
	
	/**
	 * Returns the greater value of {@code a} and {@code b}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the greater value of {@code a} and {@code b}
	 */
	public static float max(final float a, final float b) {
		return a > b ? a : b;
	}
	
	/**
	 * Returns the greater value of {@code a}, {@code b} and {@code c}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the greater value of {@code a}, {@code b} and {@code c}
	 */
	public static float max(final float a, final float b, final float c) {
		return a > b && a > c ? a : b > c ? b : c;
	}
	
	/**
	 * Returns the smaller value of {@code a} and {@code b}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the smaller value of {@code a} and {@code b}
	 */
	public static float min(final float a, final float b) {
		return a < b ? a : b;
	}
	
	/**
	 * Returns the smaller value of {@code a}, {@code b} and {@code c}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the smaller value of {@code a}, {@code b} and {@code c}
	 */
	public static float min(final float a, final float b, final float c) {
		return a < b && a < c ? a : b < c ? b : c;
	}
	
	/**
	 * Returns a pseudorandom {@code float} value between {@code 0.0F} (inclusive) and {@code 1.0F} (exclusive).
	 * 
	 * @return a pseudorandom {@code float} value between {@code 0.0F} (inclusive) and {@code 1.0F} (exclusive)
	 */
	public static float nextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
	
	/**
	 * Returns {@code base} raised to the power of {@code exponent}.
	 * 
	 * @param base the base
	 * @param exponent the exponent
	 * @return {@code base} raised to the power of {@code exponent}
	 */
	public static float pow(final float base, final float exponent) {
		return toFloat(Math.pow(base, exponent));
	}
	
	/**
	 * Checks that {@code value} is valid.
	 * <p>
	 * Returns {@code value}, if it is valid.
	 * <p>
	 * If either {@code Float.isInfinite(value)} or {@code Float.isNaN(value)} returns {@code true}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value a {@code float} value
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code value}, if it is valid
	 * @throws IllegalArgumentException thrown if, and only if, either {@code Float.isInfinite(value)} or {@code Float.isNaN(value)} returns {@code true}
	 */
	public static float requireFiniteFloatValue(final float value, final String variableName) {
		if(Float.isInfinite(value)) {
			throw new IllegalArgumentException(String.format("Float.isInfinite(%s) == true: %s=%s", variableName, variableName, Float.toString(value)));
		} else if(Float.isNaN(value)) {
			throw new IllegalArgumentException(String.format("Float.isNaN(%s) == true: %s=%s", variableName, variableName, Float.toString(value)));
		}
		
		return value;
	}
	
	/**
	 * Returns a saturated (or clamped) value based on {@code value}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Floats.saturate(value, 0.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param value the value to saturate (or clamp)
	 * @return a saturated (or clamped) value based on {@code value}
	 */
	public static float saturate(final float value) {
		return saturate(value, 0.0F, 1.0F);
	}
	
	/**
	 * Returns a saturated (or clamped) value based on {@code value}.
	 * <p>
	 * If {@code value} is less than {@code min(edgeA, edgeB)}, {@code min(edgeA, edgeB)} will be returned. If {@code value} is greater than {@code max(edgeA, edgeB)}, {@code max(edgeA, edgeB)} will be returned. Otherwise {@code value} will be
	 * returned.
	 * 
	 * @param value the value to saturate (or clamp)
	 * @param edgeA the minimum or maximum value
	 * @param edgeB the maximum or minimum value
	 * @return a saturated (or clamped) value based on {@code value}
	 */
	public static float saturate(final float value, final float edgeA, final float edgeB) {
		final float minimumValue = edgeA < edgeB ? edgeA : edgeB;
		final float maximumValue = edgeA > edgeB ? edgeA : edgeB;
		
		return value < minimumValue ? minimumValue : value > maximumValue ? maximumValue : value;
	}
	
	/**
	 * Performs a smoothstep operation on {@code value} and the edges {@code edgeA} and {@code edgeB}.
	 * <p>
	 * Returns a {@code float} value.
	 * 
	 * @param value a {@code float} value
	 * @param edgeA one of the edges
	 * @param edgeB one of the edges
	 * @return a {@code float} value
	 */
	public static float smoothstep(final float value, final float edgeA, final float edgeB) {
		final float minimumValue = min(edgeA, edgeB);
		final float maximumValue = max(edgeA, edgeB);
		
		final float x = saturate((value - minimumValue) / (maximumValue - minimumValue), 0.0F, 1.0F);
		
		return x * x * (3.0F - 2.0F * x);
	}
	
	/**
	 * Returns an approximately equivalent angle measured in degrees from an angle measured in radians.
	 * 
	 * @param angleInRadians an angle, in radians
	 * @return an approximately equivalent angle measured in degrees from an angle measured in radians
	 */
	public static float toDegrees(final float angleInRadians) {
		return toFloat(Math.toDegrees(angleInRadians));
	}
	
	/**
	 * Returns a {@code float} representation of a {@code double} value.
	 * 
	 * @param value a {@code double} value
	 * @return a {@code float} representation of a {@code double} value
	 */
	public static float toFloat(final double value) {
		return (float)(value);
	}
	
	/**
	 * Returns a {@code float} representation of an {@code int} value.
	 * 
	 * @param value an {@code int} value
	 * @return a {@code float} representation of an {@code int} value
	 */
	public static float toFloat(final int value) {
		return value;
	}
	
	/**
	 * Returns an approximately equivalent angle measured in radians from an angle measured in degrees.
	 * 
	 * @param angleInDegrees an angle, in degrees
	 * @return an approximately equivalent angle measured in radians from an angle measured in degrees
	 */
	public static float toRadians(final float angleInDegrees) {
		return toFloat(Math.toRadians(angleInDegrees));
	}
	
	/**
	 * Returns {@code value} or its wrapped around representation.
	 * <p>
	 * If {@code value} is greater than or equal to {@code min(a, b)} and less than or equal to {@code max(a, b)}, {@code value} will be returned. Otherwise it will wrap around on either side until it is contained in the interval
	 * {@code [min(a, b), max(a, b)]}.
	 * 
	 * @param value the value to potentially wrap around
	 * @param a one of the values in the interval to wrap around
	 * @param b one of the values in the interval to wrap around
	 * @return {@code value} or its wrapped around representation
	 */
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