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
 * A {@code PixelFunction} is a function that returns a {@link Color} for a given pixel.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface PixelFunction {
	/**
	 * Returns a {@link Color} for the pixel represented by {@code x} and {@code y}.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param oldColor the old {@code Color} at {@code x} and {@code y}
	 * @return a {@code Color} for the pixel represented by {@code x} and {@code y}
	 */
	Color apply(final float x, final float y, final Color oldColor);
}