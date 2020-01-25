/**
 * Copyright 2019 - 2020 J&#246;rgen Lundgren
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
package org.macroing.image4j.filter;

/**
 * A {@code Filter} represents a filter.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Filter {
	private final float resolutionX;
	private final float resolutionXReciprocal;
	private final float resolutionY;
	private final float resolutionYReciprocal;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Filter} instance given {@code resolutionX} and {@code resolutionY}.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 */
	protected Filter(final float resolutionX, final float resolutionY) {
		this.resolutionX = resolutionX;
		this.resolutionY = resolutionY;
		this.resolutionXReciprocal = 1.0F / resolutionX;
		this.resolutionYReciprocal = 1.0F / resolutionY;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Evaluates this {@code Filter} instance given {@code x} and {@code y}.
	 * <p>
	 * Returns the evaluated value.
	 * 
	 * @param x the X-coordinate
	 * @param y the Y-coordinate
	 * @return the evaluated value
	 */
	public abstract float evaluate(final float x, final float y);
	
	/**
	 * Returns the resolution of the X-axis.
	 * 
	 * @return the resolution of the X-axis
	 */
	public final float getResolutionX() {
		return this.resolutionX;
	}
	
	/**
	 * Returns the reciprocal (or inverse) value of the resolution of the X-axis.
	 * 
	 * @return the reciprocal (or inverse) value of the resolution of the X-axis
	 */
	public final float getResolutionXReciprocal() {
		return this.resolutionXReciprocal;
	}
	
	/**
	 * Returns the resolution of the Y-axis.
	 * 
	 * @return the resolution of the Y-axis
	 */
	public final float getResolutionY() {
		return this.resolutionY;
	}
	
	/**
	 * Returns the reciprocal (or inverse) value of the resolution of the Y-axis.
	 * 
	 * @return the reciprocal (or inverse) value of the resolution of the Y-axis
	 */
	public final float getResolutionYReciprocal() {
		return this.resolutionYReciprocal;
	}
}