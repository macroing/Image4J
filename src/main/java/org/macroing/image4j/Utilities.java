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

import java.text.DecimalFormat;
import java.util.IllegalFormatException;

/**
 * A class that consists exclusively of static utility methods.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Utilities {
	private static final DecimalFormat DECIMAL_FORMAT = doCreateDecimalFormat();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Utilities() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of {@code value} without scientific notation.
	 * 
	 * @param value a {@code float} value
	 * @return a {@code String} representation of {@code value} without scientific notation
	 */
	public static String toNonScientificNotation(final float value) {
		return DECIMAL_FORMAT.format(value).replace(',', '.');
	}
	
	/**
	 * Checks that {@code object} is not {@code null}.
	 * <p>
	 * Returns {@code object}, if it is not {@code null}.
	 * <p>
	 * If {@code object} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The message to the {@code NullPointerException} is created by {@code String.format(format, args)}. This only happens when {@code object} is {@code null}.
	 * <p>
	 * If {@code String.format(format, args)} fails, an {@code IllegalFormatException} will be thrown.
	 * 
	 * @param <T> the generic type of {@code object}
	 * @param object the {@code Object} to check
	 * @param format a format string
	 * @param args arguments referenced by the format specifiers in the format string
	 * @return {@code object}, if it is not {@code null}
	 * @throws IllegalFormatException thrown if, and only if, {@code String.format(format, args)} fails
	 * @throws NullPointerException thrown if, and only if, {@code object} is {@code null}
	 */
	public static <T> T requireNonNull(final T object, final String format, final Object... args) {
		if(object == null) {
			throw new NullPointerException(String.format(format, args));
		}
		
		return object;
	}
	
	/**
	 * Checks that {@code array} and all of its elements are not {@code null}.
	 * <p>
	 * Returns {@code array}, if it is not {@code null} and it contains no elements that are.
	 * <p>
	 * If either {@code array} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param array the array to check
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code array}, if it is not {@code null} and it contains no elements that are
	 * @throws NullPointerException thrown if, and only if, either {@code array} or any of its elements are {@code null}
	 */
	public static <T> T[] requireDeepNonNull(final T[] array, final String variableName) {
		if(array == null) {
			throw new NullPointerException(String.format("%s == null", variableName));
		}
		
		for(int i = 0; i < array.length; i++) {
			if(array[i] == null) {
				throw new NullPointerException(String.format("%s[%s] == null", variableName, Integer.toString(i)));
			}
		}
		
		return array;
	}
	
	/**
	 * Checks that {@code array.length == expectedLength}.
	 * <p>
	 * Returns {@code array}.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != expectedLength}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param array the array to check
	 * @param expectedLength the expected length of {@code array}
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != expectedLength}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static <T> T[] requireExactLength(final T[] array, final int expectedLength, final String variableName) {
		if(array.length != expectedLength) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(expectedLength)));
		}
		
		return array;
	}
	
	/**
	 * Checks that {@code array.length == expectedLength}.
	 * <p>
	 * Returns {@code array}.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != expectedLength}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param array the array to check
	 * @param expectedLength the expected length of {@code array}
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != expectedLength}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static byte[] requireExactLength(final byte[] array, final int expectedLength, final String variableName) {
		if(array.length != expectedLength) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(expectedLength)));
		}
		
		return array;
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
	 * Checks that {@code array.length == expectedLength}.
	 * <p>
	 * Returns {@code array}.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != expectedLength}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param array the array to check
	 * @param expectedLength the expected length of {@code array}
	 * @param variableName the name of the variable to check, which will be part of the message supplied to the {@code IllegalArgumentException}
	 * @return {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != expectedLength}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static int[] requireExactLength(final int[] array, final int expectedLength, final String variableName) {
		if(array.length != expectedLength) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(expectedLength)));
		}
		
		return array;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static DecimalFormat doCreateDecimalFormat() {
		final
		DecimalFormat decimalFormat = new DecimalFormat("#");
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setMaximumFractionDigits(8);
		decimalFormat.setMinimumFractionDigits(1);
		decimalFormat.setMinimumIntegerDigits(1);
		
		return decimalFormat;
	}
}