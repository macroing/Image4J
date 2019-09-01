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
 * A class that consists exclusively of static methods that returns or performs various operations on {@code int}s.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Integers {
	private Integers() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the absolute version of {@code value}.
	 * 
	 * @param value an {@code int} value
	 * @return the absolute version of {@code value}
	 */
	public static int abs(final int value) {
		return Math.abs(value);
	}
	
	/**
	 * Returns the greater value of {@code a} and {@code b}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the greater value of {@code a} and {@code b}
	 */
	public static int max(final int a, final int b) {
		return a >= b ? a : b;
	}
	
	/**
	 * Returns the greater value of {@code a}, {@code b} and {@code c}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the greater value of {@code a}, {@code b} and {@code c}
	 */
	public static int max(final int a, final int b, final int c) {
		return a >= b && a >= c ? a : b >= c ? b : c;
	}
	
	/**
	 * Returns the smaller value of {@code a} and {@code b}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @return the smaller value of {@code a} and {@code b}
	 */
	public static int min(final int a, final int b) {
		return a <= b ? a : b;
	}
	
	/**
	 * Returns the smaller value of {@code a}, {@code b} and {@code c}.
	 * 
	 * @param a a value
	 * @param b a value
	 * @param c a value
	 * @return the smaller value of {@code a}, {@code b} and {@code c}
	 */
	public static int min(final int a, final int b, final int c) {
		return a <= b && a <= c ? a : b <= c ? b : c;
	}
	
	/**
	 * Returns an {@code int} representation of a {@code float} value.
	 * 
	 * @param value a {@code float} value
	 * @return an {@code int} representation of a {@code float} value
	 */
	public static int toInt(final float value) {
		return (int)(value);
	}
	
	/**
	 * Performs a modulo operation on {@code value} given {@code maximumValue}.
	 * <p>
	 * Returns {@code value} or a wrapped around version of it.
	 * <p>
	 * The modulo operation performed by this method differs slightly from the modulo operator in Java.
	 * <p>
	 * If {@code value} is positive, the following occurs:
	 * <pre>
	 * {@code
	 * int changedValue = value % maximumValue;
	 * }
	 * </pre>
	 * If {@code value} is negative, the following occurs:
	 * <pre>
	 * {@code
	 * int changedValue = (value % maximumValue + maximumValue) % maximumValue;
	 * }
	 * </pre>
	 * 
	 * @param value an {@code int} value
	 * @param maximumValue the maximum value
	 * @return {@code value} or a wrapped around version of it
	 */
	public static int modulo(final int value, final int maximumValue) {
		return value < 0 ? (value % maximumValue + maximumValue) % maximumValue : value % maximumValue;
	}
	
	/**
	 * Checks that {@code value} is positive.
	 * <p>
	 * Returns {@code value}, if it is positive.
	 * <p>
	 * If {@code value < 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value an {@code int} value
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code value}, if it is positive
	 * @throws IllegalArgumentException thrown if, and only if, {@code value < 0}
	 */
	public static int requirePositiveIntValue(final int value, final String variableName) {
		if(value < 0) {
			throw new IllegalArgumentException(String.format("%s < 0: %s=%s", variableName, variableName, Integer.toString(value)));
		}
		
		return value;
	}
	
	/**
	 * Returns a saturated (or clamped) value based on {@code value}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Integers.saturate(value, 0, 255)
	 * }
	 * </pre>
	 * 
	 * @param value the value to saturate (or clamp)
	 * @return a saturated (or clamped) value based on {@code value}
	 */
	public static int saturate(final int value) {
		return saturate(value, 0, 255);
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
	public static int saturate(final int value, final int edgeA, final int edgeB) {
		final int minimumValue = edgeA < edgeB ? edgeA : edgeB;
		final int maximumValue = edgeA > edgeB ? edgeA : edgeB;
		
		return value < minimumValue ? minimumValue : value > maximumValue ? maximumValue : value;
	}
}