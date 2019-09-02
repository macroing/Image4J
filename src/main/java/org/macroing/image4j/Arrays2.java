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
 * A class that consists exclusively of static methods that returns or performs various operations on arrays.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Arrays2 {
	private Arrays2() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
}