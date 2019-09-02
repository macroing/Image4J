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

import java.util.IllegalFormatException;

/**
 * A class that consists exclusively of static methods that returns or performs various operations on {@code Object}s.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class Objects2 {
	private Objects2() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
}