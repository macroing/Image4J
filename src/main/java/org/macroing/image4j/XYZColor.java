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

import java.util.Objects;

import org.macroing.math4j.MathF;

/**
 * An {@code XYZColor} encapsulates a color in the CIE XYZ color space.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class XYZColor {
	/**
	 * The value of the X-component.
	 */
	public final float x;
	
	/**
	 * The value of the Y-component.
	 */
	public final float y;
	
	/**
	 * The value of the Z-component.
	 */
	public final float z;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code XYZColor} instance give {@code 0.0F}, {@code 0.0F} and {@code 0.0F}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new XYZColor(0.0F, 0.0F, 0.0F);
	 * }
	 * </pre>
	 */
	public XYZColor() {
		this(0.0F, 0.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code XYZColor} instance given {@code x}, {@code y} and {@code z}.
	 * 
	 * @param x the value of the X-component
	 * @param y the value of the Y-component
	 * @param z the value of the Z-component
	 */
	public XYZColor(final float x, final float y, final float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@link Color} representation of this {@code XYZColor} instance.
	 * 
	 * @return a {@code Color} representation of this {@code XYZColor} instance
	 */
	public Color toColor() {
		final float r = 3.240479F * this.x - 1.537150F * this.y - 0.498535F * this.z;
		final float g = -0.969256F * this.x + 1.875991F * this.y + 0.041556F * this.z;
		final float b = 0.055648F * this.x - 0.204043F * this.y + 1.057311F * this.z;
		
		return new Color(r, g, b);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code XYZColor} instance.
	 * 
	 * @return a {@code String} representation of this {@code XYZColor} instance
	 */
	@Override
	public String toString() {
		return String.format("new XYZColor(%s, %s, %s)", Float.toString(this.x), Float.toString(this.y), Float.toString(this.z));
	}
	
	/**
	 * Adds the component values of {@code color} to the component values of this {@code XYZColor} instance.
	 * <p>
	 * Returns a new {@code XYZColor} instance with the result of the addition.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the {@code XYZColor} to add
	 * @return a new {@code XYZColor} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public XYZColor add(final XYZColor color) {
		return new XYZColor(this.x + color.x, this.y + color.y, this.z + color.z);
	}
	
	/**
	 * Multiplies the component values of this {@code XYZColor} instance with the component values of {@code color}.
	 * <p>
	 * Returns a new {@code XYZColor} instance with the result of the multiplication.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the {@code XYZColor} to multiply with
	 * @return a new {@code XYZColor} instance with the result of the multiplication
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public XYZColor multiply(final XYZColor color) {
		return new XYZColor(this.x * color.x, this.y * color.y, this.z * color.z);
	}
	
	/**
	 * Multiplies the component values of this {@code XYZColor} instance with {@code s}.
	 * <p>
	 * Returns a new {@code XYZColor} instance with the result of the multiplication.
	 * 
	 * @param s the value to multiply the X-, Y- and Z component values with
	 * @return a new {@code XYZColor} instance with the result of the multiplication
	 */
	public XYZColor multiply(final float s) {
		return new XYZColor(this.x * s, this.y * s, this.z * s);
	}
	
	/**
	 * Normalizes the component values of this {@code XYZColor} instance.
	 * <p>
	 * Returns a new {@code XYZColor} instance with the result of the normalization, or this {@code XYZColor} instance if it cannot be normalized.
	 * 
	 * @return a new {@code XYZColor} instance with the result of the normalization, or this {@code XYZColor} instance if it cannot be normalized
	 */
	public XYZColor normalize() {
		final float sum = this.x + this.y + this.z;
		
		if(sum < 1.0e-6F) {
			return this;
		}
		
		final float sumReciprocal = 1.0F / sum;
		
		return new XYZColor(this.x * sumReciprocal, this.y * sumReciprocal, this.z * sumReciprocal);
	}
	
	/**
	 * Compares {@code object} to this {@code XYZColor} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code XYZColor}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code XYZColor} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code XYZColor}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof XYZColor)) {
			return false;
		} else if(!MathF.equals(this.x, XYZColor.class.cast(object).x)) {
			return false;
		} else if(!MathF.equals(this.y, XYZColor.class.cast(object).y)) {
			return false;
		} else if(!MathF.equals(this.z, XYZColor.class.cast(object).z)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the X-component.
	 * 
	 * @return the value of the X-component
	 */
	public float getX() {
		return this.x;
	}
	
	/**
	 * Returns the value of the Y-component.
	 * 
	 * @return the value of the Y-component
	 */
	public float getY() {
		return this.y;
	}
	
	/**
	 * Returns the value of the Z-component.
	 * 
	 * @return the value of the Z-component
	 */
	public float getZ() {
		return this.z;
	}
	
	/**
	 * Returns a hash code for this {@code XYZColor} instance.
	 * 
	 * @return a hash code for this {@code XYZColor} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z));
	}
}