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
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	public static <T> T[] requireExactLength(final T[] array, final int length, final String variableName) {
		if(array.length != length) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(length)));
		}
		
		return array;
	}
	
//	TODO: Add Javadocs!
	public static byte[] requireExactLength(final byte[] array, final int length, final String variableName) {
		if(array.length != length) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(length)));
		}
		
		return array;
	}
	
//	TODO: Add Javadocs!
	public static int[] requireExactLength(final int[] array, final int length, final String variableName) {
		if(array.length != length) {
			throw new IllegalArgumentException(String.format("%s.length != %s", variableName, Integer.toString(length)));
		}
		
		return array;
	}
}