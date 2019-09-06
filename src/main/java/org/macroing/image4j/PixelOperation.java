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

import static org.macroing.math4j.MathI.modulo;

/**
 * A {@code PixelOperation} provides a set of operations to perform on a pixel when it is outside the boundaries of an {@link Image} instance.
 * <p>
 * A pixel can be defined by, or represented as, a pair of X- and Y-coordinates or an index.
 * <p>
 * The operations that can be performed on a pixel are the no-change operation and the wrap-around operation. More operations may be added in the future.
 * <p>
 * <strong>No-Change Operation</strong>
 * <p>
 * The no-change operation, {@link PixelOperation#NO_CHANGE}, does not change the pixel if it is outside the boundaries of the {@code Image} instance.
 * <p>
 * The following list shows what will happen with different methods if the pixel is outside the boundaries of the {@code Image} instance:
 * <ul>
 * <li>{@code Return} {@code Color}: The method returns {@code Color.BLACK}</li>
 * <li>{@code Update} {@code Color}: The method updates nothing</li>
 * </ul>
 * <p>
 * <strong>Wrap-Around Operation</strong>
 * <p>
 * The wrap-around operation, {@link PixelOperation#WRAP_AROUND}, will change the pixel if it is outside the boundaries of the {@code Image} instance by wrapping its X- and Y-coordinates or index around.
 * <p>
 * The wrap-around operation is performed on the X- and Y-coordinates individually or on the index. The X-coordinate is wrapped around {@link Image#getResolutionX()}, the Y-coordinate is wrapped around {@link Image#getResolutionY()} and the index is
 * wrapped around {@link Image#getResolution()}.
 * <p>
 * The following list shows what will happen with different methods if the pixel is outside the boundaries of the {@code Image} instance:
 * <ul>
 * <li>{@code Return} {@code Color}: The method returns the {@code Color} of the changed pixel</li>
 * <li>{@code Update} {@code Color}: The method updates the {@code Color} of the changed pixel</li>
 * </ul>
 * <p>
 * The following code snippet shows how the wrap-around operation works, given the two variables {@code value} and {@code maximumValue}:
 * <pre>
 * {@code
 * int changedValue = value < 0 ? (value % maximumValue + maximumValue) % maximumValue : value % maximumValue;
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public enum PixelOperation {
	/**
	 * The no-change operation.
	 */
	NO_CHANGE,
	
	/**
	 * The wrap-around operation.
	 */
	WRAP_AROUND;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private PixelOperation() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Applies this operation to {@code index}.
	 * <p>
	 * Returns the index that results from applying this operation to {@code index}.
	 * 
	 * @param index the index of the pixel
	 * @param resolution the resolution
	 * @return the index that results from applying this operation to {@code index}
	 */
	public int getIndex(final int index, final int resolution) {
		switch(this) {
			case NO_CHANGE:
				return index;
			case WRAP_AROUND:
				return modulo(index, resolution);
			default:
				return index;
		}
	}
	
	/**
	 * Applies this operation to {@code x}.
	 * <p>
	 * Returns the X-coordinate that results from applying this operation to {@code x}.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param resolutionX the resolution of the X-axis
	 * @return the X-coordinate that results from applying this operation to {@code x}
	 */
	public int getX(final int x, final int resolutionX) {
		switch(this) {
			case NO_CHANGE:
				return x;
			case WRAP_AROUND:
				return modulo(x, resolutionX);
			default:
				return x;
		}
	}
	
	/**
	 * Applies this operation to {@code y}.
	 * <p>
	 * Returns the Y-coordinate that results from applying this operation to {@code y}.
	 * 
	 * @param y the Y-coordinate of the pixel
	 * @param resolutionY the resolution of the Y-axis
	 * @return the Y-coordinate that results from applying this operation to {@code y}
	 */
	public int getY(final int y, final int resolutionY) {
		switch(this) {
			case NO_CHANGE:
				return y;
			case WRAP_AROUND:
				return modulo(y, resolutionY);
			default:
				return y;
		}
	}
}