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

import static org.macroing.image4j.Floats.exp;
import static org.macroing.image4j.Integers.toInt;

import java.util.Objects;

/**
 * A {@code Color} encapsulates a color in the default {@code sRGB} color space.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * <p>
 * All constructors of this class will check that the component values are valid. If at least one of the component values are invalid, an {@code IllegalArgumentException} will be thrown. Only finite {@code float} values are valid. Checks for
 * {@code Float.isInfinite(float)} and {@code Float.isNaN(float)} are made.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Color {
	/**
	 * A {@code Color} denoting the color black.
	 */
	public static final Color BLACK = new Color();
	
	/**
	 * A {@code Color} denoting the color blue.
	 */
	public static final Color BLUE = new Color(0.0F, 0.0F, 1.0F);
	
	/**
	 * A {@code Color} denoting the color cyan.
	 */
	public static final Color CYAN = new Color(0.0F, 1.0F, 1.0F);
	
	/**
	 * A {@code Color} denoting the color gray.
	 */
	public static final Color GRAY = new Color(0.5F, 0.5F, 0.5F);
	
	/**
	 * A {@code Color} denoting the color green.
	 */
	public static final Color GREEN = new Color(0.0F, 1.0F, 0.0F);
	
	/**
	 * A {@code Color} denoting the color magenta.
	 */
	public static final Color MAGENTA = new Color(1.0F, 0.0F, 1.0F);
	
	/**
	 * A {@code Color} denoting the color orange.
	 */
	public static final Color ORANGE = new Color(1.0F, 0.5F, 0.0F);
	
	/**
	 * A {@code Color} denoting the color red.
	 */
	public static final Color RED = new Color(1.0F, 0.0F, 0.0F);
	
	/**
	 * A {@code Color} denoting the color white.
	 */
	public static final Color WHITE = new Color(1.0F, 1.0F, 1.0F);
	
	/**
	 * A {@code Color} denoting the color yellow.
	 */
	public static final Color YELLOW = new Color(1.0F, 1.0F, 0.0F);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The value of the alpha component (A).
	 */
	public final float a;
	
	/**
	 * The value of the blue component (B).
	 */
	public final float b;
	
	/**
	 * The value of the green component (G).
	 */
	public final float g;
	
	/**
	 * The value of the red component (R).
	 */
	public final float r;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Color} instance denoting the color black.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(0.0F);
	 * }
	 * </pre>
	 */
	public Color() {
		this(0.0F);
	}
	
	/**
	 * Constructs a new {@code Color} instance denoting a gray color.
	 * <p>
	 * If {@code gray} is invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(gray, gray, gray);
	 * }
	 * </pre>
	 * 
	 * @param gray the value of the red component (R), green component (G) and blue component (B)
	 * @throws IllegalArgumentException thrown if, and only if, {@code gray} is invalid
	 */
	public Color(final float gray) {
		this(gray, gray, gray);
	}
	
	/**
	 * Constructs a new {@code Color} instance denoting a gray color.
	 * <p>
	 * If either {@code gray} or {@code a} are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(gray, gray, gray, a);
	 * }
	 * </pre>
	 * 
	 * @param gray the value of the red component (R), green component (G) and blue component (B)
	 * @param a the value of the alpha component (A)
	 * @throws IllegalArgumentException thrown if, and only if, either {@code gray} or {@code a} are invalid
	 */
	public Color(final float gray, final float a) {
		this(gray, gray, gray, a);
	}
	
	/**
	 * Constructs a new {@code Color} instance given the component values {@code r}, {@code g} and {@code b} with an alpha component of {@code 1.0F}.
	 * <p>
	 * If either {@code r}, {@code g} or {@code b} are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(r, g, b, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component (R)
	 * @param g the value of the green component (G)
	 * @param b the value of the blue component (B)
	 * @throws IllegalArgumentException thrown if, and only if, either {@code r}, {@code g} or {@code b} are invalid
	 */
	public Color(final float r, final float g, final float b) {
		this(r, g, b, 1.0F);
	}
	
	/**
	 * Constructs a new {@code Color} instance given the component values {@code r}, {@code g}, {@code b} and {@code a}.
	 * <p>
	 * If either {@code r}, {@code g}, {@code b} or {@code a} are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param r the value of the red component (R)
	 * @param g the value of the green component (G)
	 * @param b the value of the blue component (B)
	 * @param a the value of the alpha component (A)
	 * @throws IllegalArgumentException thrown if, and only if, either {@code r}, {@code g}, {@code b} or {@code a} are invalid
	 */
	public Color(final float r, final float g, final float b, final float a) {
		this.r = Floats.requireFiniteFloatValue(r, "r");
		this.g = Floats.requireFiniteFloatValue(g, "g");
		this.b = Floats.requireFiniteFloatValue(b, "b");
		this.a = Floats.requireFiniteFloatValue(a, "a");
	}
	
	/**
	 * Constructs a new {@code Color} instance given an {@code int} with the color in packed form.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(color, PackedIntComponentOrder.ARGB);
	 * }
	 * </pre>
	 * 
	 * @param color an {@code int} with the color in packed form
	 */
	public Color(final int color) {
		this(color, PackedIntComponentOrder.ARGB);
	}
	
	/**
	 * Constructs a new {@code Color} instance given an {@code int} with the color in packed form and {@code packedIntComponentOrder} to unpack the component values with.
	 * <p>
	 * If {@code packedIntComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color an {@code int} with the color in packed form
	 * @param packedIntComponentOrder the {@link PackedIntComponentOrder} to unpack the component values with
	 * @throws NullPointerException thrown if, and only if, {@code packedIntComponentOrder} is {@code null}
	 */
	public Color(final int color, final PackedIntComponentOrder packedIntComponentOrder) {
		this.r = doConvertComponentValueFromIntToFloat(packedIntComponentOrder.unpackR(color));
		this.g = doConvertComponentValueFromIntToFloat(packedIntComponentOrder.unpackG(color));
		this.b = doConvertComponentValueFromIntToFloat(packedIntComponentOrder.unpackB(color));
		this.a = doConvertComponentValueFromIntToFloat(packedIntComponentOrder.unpackA(color));
	}
	
	/**
	 * Constructs a new {@code Color} instance given the component values {@code r}, {@code g} and {@code b} with an alpha component of {@code 255}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Color(r, g, b, 255);
	 * }
	 * </pre>
	 * 
	 * @param r the value of the red component (R)
	 * @param g the value of the green component (G)
	 * @param b the value of the blue component (B)
	 */
	public Color(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}
	
	/**
	 * Constructs a new {@code Color} instance given the component values {@code r}, {@code g}, {@code b} and {@code a}.
	 * 
	 * @param r the value of the red component (R)
	 * @param g the value of the green component (G)
	 * @param b the value of the blue component (B)
	 * @param a the value of the alpha component (A)
	 */
	public Color(final int r, final int g, final int b, final int a) {
		this.r = doConvertComponentValueFromIntToFloat(r & 0xFF);
		this.g = doConvertComponentValueFromIntToFloat(g & 0xFF);
		this.b = doConvertComponentValueFromIntToFloat(b & 0xFF);
		this.a = doConvertComponentValueFromIntToFloat(a & 0xFF);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds the component values of {@code color} to the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the addition.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * currentColor.add(color.r, color.g, color.b);
	 * }
	 * </pre>
	 * 
	 * @param color the {@code Color} to add
	 * @return a new {@code Color} instance with the result of the addition
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color add(final Color color) {
		return add(color.r, color.g, color.b);
	}
	
	/**
	 * Adds {@code gray} to the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the addition.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.add(gray, gray, gray);
	 * }
	 * </pre>
	 * 
	 * @param gray the value to add to the R-, G- and B component values
	 * @return a new {@code Color} instance with the result of the addition
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color add(final float gray) {
		return add(gray, gray, gray);
	}
	
	/**
	 * Adds {@code r}, {@code g} and {@code b} to the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the addition.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param r the value to add to the R component value
	 * @param g the value to add to the G component value
	 * @param b the value to add to the B component value
	 * @return a new {@code Color} instance with the result of the addition
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color add(final float r, final float g, final float b) {
		return new Color(this.r + r, this.g + g, this.b + b, this.a);
	}
	
	/**
	 * Adds the component values of {@code color} to the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the addition.
	 * <p>
	 * This method differs from {@link #add(Color)} in that it assumes this {@code Color} instance to be an average color sample. It uses a stable moving average algorithm to compute a new average color sample as a result of adding {@code color}. This
	 * method is suitable for Monte Carlo-method based algorithms.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param color the {@code Color} to add
	 * @param sampleCount the current sample count
	 * @return a new {@code Color} instance with the result of the addition
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color addSample(final Color color, final int sampleCount) {
		final float r = this.r + ((color.r - this.r) / sampleCount);
		final float g = this.g + ((color.g - this.g) / sampleCount);
		final float b = this.b + ((color.b - this.b) / sampleCount);
		final float a = this.a;
		
		return new Color(r, g, b, a);
	}
	
	/**
	 * Divides the component values of this {@code Color} instance with the component values of {@code color} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the division.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * currentColor.divide(color.r, color.g, color.b);
	 * }
	 * </pre>
	 * 
	 * @param color the {@code Color} to divide with
	 * @return a new {@code Color} instance with the result of the division
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color divide(final Color color) {
		return divide(color.r, color.g, color.b);
	}
	
	/**
	 * Divides the component values of this {@code Color} instance with {@code gray} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the division.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.divide(gray, gray, gray);
	 * }
	 * </pre>
	 * 
	 * @param gray the value to divide the R-, G- and B component values with
	 * @return a new {@code Color} instance with the result of the division
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color divide(final float gray) {
		return divide(gray, gray, gray);
	}
	
	/**
	 * Divides the component values of this {@code Color} instance with {@code r}, {@code g} and {@code b} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the division.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param r the value to divide the R component value with
	 * @param g the value to divide the G component value with
	 * @param b the value to divide the B component value with
	 * @return a new {@code Color} instance with the result of the division
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color divide(final float r, final float g, final float b) {
		return new Color(this.r / r, this.g / g, this.b / b, this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the average component value of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the average component value of this {@code Color} instance
	 */
	public Color grayAverage() {
		return new Color(average(), this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the value of the B component of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the value of the B component of this {@code Color} instance
	 */
	public Color grayB() {
		return new Color(this.b, this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the value of the G component of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the value of the G component of this {@code Color} instance
	 */
	public Color grayG() {
		return new Color(this.g, this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the lightness of this {@code Color} instance.
	 * <p>
	 * The lightness is calculated as the average value of the smallest and largest component values.
	 * 
	 * @return a new gray {@code Color} instance based on the lightness of this {@code Color} instance
	 */
	public Color grayLightness() {
		return new Color((max() + min()) / 2.0F, this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the luminance of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the luminance of this {@code Color} instance
	 */
	public Color grayLuminance() {
		return new Color(luminance(), this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the value of the largest component of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the value of the largest component of this {@code Color} instance
	 */
	public Color grayMax() {
		return new Color(max(), this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the value of the smallest component of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the value of the smallest component of this {@code Color} instance
	 */
	public Color grayMin() {
		return new Color(min(), this.a);
	}
	
	/**
	 * Returns a new gray {@code Color} instance based on the value of the R component of this {@code Color} instance.
	 * 
	 * @return a new gray {@code Color} instance based on the value of the R component of this {@code Color} instance
	 */
	public Color grayR() {
		return new Color(this.r, this.a);
	}
	
	/**
	 * Returns a new inverted {@code Color} instance based on this {@code Color} instance.
	 * <p>
	 * This method assumes the R-, G- and B component values lie in the range {@code [0.0F, 1.0F]}.
	 * 
	 * @return a new inverted {@code Color} instance based on this {@code Color} instance
	 */
	public Color invert() {
		return new Color(1.0F - this.r, 1.0F - this.g, 1.0F - this.b, this.a);
	}
	
	/**
	 * Restricts the component values of this {@code Color} instance by dividing them with the maximum component value that is greater than {@code 1.0}.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the division, or this {@code Color} instance if no division occurred.
	 * <p>
	 * This method can overflow if the delta between the minimum and maximum component values are large.
	 * <p>
	 * If at least one of the component values are negative, consider calling {@link #minTo0()} before calling this method.
	 * <p>
	 * To use this method consider the following example:
	 * <pre>
	 * {@code
	 * Color a = new Color(0.0F, 1.0F, 2.0F);
	 * Color b = a.maxTo1();
	 * 
	 * //a.r = 0.0F, a.g = 1.0F, a.b = 2.0F, a.a = 1.0F
	 * //b.r = 0.0F, b.g = 0.5F, b.b = 1.0F, b.a = 1.0F
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Color} instance with the result of the division, or this {@code Color} instance if no division occurred
	 */
	public Color maxTo1() {
		final float maximumComponentValue = max();
		
		if(maximumComponentValue > 1.0F) {
			final float r = this.r / maximumComponentValue;
			final float g = this.g / maximumComponentValue;
			final float b = this.b / maximumComponentValue;
			final float a = this.a;
			
			return new Color(r, g, b, a);
		}
		
		return this;
	}
	
	/**
	 * Restricts the component values of this {@code Color} instance by adding the minimum component value that is less than {@code 0.0} to them.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the addition, or this {@code Color} instance if no addition occurred.
	 * <p>
	 * This method can overflow if the delta between the minimum and maximum component values are large.
	 * <p>
	 * Consider calling {@link #maxTo1()} after a call to this method.
	 * <p>
	 * To use this method consider the following example:
	 * <pre>
	 * {@code
	 * Color a = new Color(-2.0F, 0.0F, 1.0F);
	 * Color b = a.minTo0();
	 * 
	 * //a.r = -2.0F, a.g = 0.0F, a.b = 1.0F, a.a = 1.0F
	 * //b.r =  0.0F, b.g = 2.0F, b.b = 3.0F, b.a = 1.0F
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Color} instance with the result of the addition, or this {@code Color} instance if no addition occurred
	 */
	public Color minTo0() {
		final float minimumComponentValue = Floats.min(this.r, this.g, this.b);
		
		if(minimumComponentValue < 0.0F) {
			final float r = this.r + -minimumComponentValue;
			final float g = this.g + -minimumComponentValue;
			final float b = this.b + -minimumComponentValue;
			final float a = this.a;
			
			return new Color(r, g, b, a);
		}
		
		return this;
	}
	
	/**
	 * Multiplies the component values of this {@code Color} instance with the component values of {@code color} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the multiplication.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * currentColor.multiply(color.r, color.g, color.b);
	 * }
	 * </pre>
	 * 
	 * @param color the {@code Color} to multiply with
	 * @return a new {@code Color} instance with the result of the multiplication
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color multiply(final Color color) {
		return multiply(color.r, color.g, color.b);
	}
	
	/**
	 * Multiplies the component values of this {@code Color} instance with {@code gray} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the multiplication.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.multiply(gray, gray, gray);
	 * }
	 * </pre>
	 * 
	 * @param gray the value to multiply the R-, G- and B component values with
	 * @return a new {@code Color} instance with the result of the multiplication
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color multiply(final float gray) {
		return multiply(gray, gray, gray);
	}
	
	/**
	 * Multiplies the component values of this {@code Color} instance with {@code r}, {@code g} and {@code b} while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the multiplication.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param r the value to multiply the R component value with
	 * @param g the value to multiply the G component value with
	 * @param b the value to multiply the B component value with
	 * @return a new {@code Color} instance with the result of the multiplication
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color multiply(final float r, final float g, final float b) {
		return new Color(this.r * r, this.g * g, this.b * b, this.a);
	}
	
	/**
	 * Returns a new {@code Color} instance with the R-, G- and B component values negated.
	 * 
	 * @return a new {@code Color} instance with the R-, G- and B component values negated
	 */
	public Color negate() {
		return new Color(-this.r, -this.g, -this.b, this.a);
	}
	
	/**
	 * Redoes gamma correction on this {@code Color} instance using {@code RGBColorSpace.SRGB}.
	 * <p>
	 * Returns a new {@code Color} instance with gamma correction redone.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.redoGammaCorrection(RGBColorSpace.SRGB);
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Color} instance with gamma correction redone
	 */
	public Color redoGammaCorrection() {
		return redoGammaCorrection(RGBColorSpace.SRGB);
	}
	
	/**
	 * Redoes gamma correction on this {@code Color} instance using {@code colorSpace}.
	 * <p>
	 * Returns a new {@code Color} instance with gamma correction redone.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace the {@link RGBColorSpace} to use
	 * @return a new {@code Color} instance with gamma correction redone
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
	public Color redoGammaCorrection(final RGBColorSpace colorSpace) {
		final float r = colorSpace.redoGammaCorrection(this.r);
		final float g = colorSpace.redoGammaCorrection(this.g);
		final float b = colorSpace.redoGammaCorrection(this.b);
		final float a = this.a;
		
		return new Color(r, g, b, a);
	}
	
	/**
	 * Saturates this {@code Color} instance, such that each component value will lie in the range {@code [0.0F, 1.0F]}.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the saturation.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.saturate(0.0F, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Color} instance with the result of the saturation
	 */
	public Color saturate() {
		return saturate(0.0F, 1.0F);
	}
	
	/**
	 * Saturates this {@code Color} instance, such that each component value will lie in the range {@code [min(edgeA, edgeB), max(edgeA, edgeB)]}.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the saturation.
	 * 
	 * @param edgeA the minimum or maximum value
	 * @param edgeB the maximum or minimum value
	 * @return a new {@code Color} instance with the result of the saturation
	 */
	public Color saturate(final float edgeA, final float edgeB) {
		final float r = Floats.saturate(this.r, edgeA, edgeB);
		final float g = Floats.saturate(this.g, edgeA, edgeB);
		final float b = Floats.saturate(this.b, edgeA, edgeB);
		final float a = this.a;
		
		return new Color(r, g, b, a);
	}
	
	/**
	 * Returns a new sepia {@code Color} instance based on this {@code Color} instance.
	 * 
	 * @return a new sepia {@code Color} instance based on this {@code Color} instance
	 */
	public Color sepia() {
		final float r = this.r * 0.393F + this.g * 0.769F + this.b * 0.189F;
		final float g = this.r * 0.349F + this.g * 0.686F + this.b * 0.168F;
		final float b = this.r * 0.272F + this.g * 0.534F + this.b * 0.131F;
		final float a = this.a;
		
		return new Color(r, g, b, a);
	}
	
	/**
	 * Subtracts the component values of {@code color} from the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the subtraction.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * currentColor.subtract(color.r, color.g, color.b);
	 * }
	 * </pre>
	 * 
	 * @param color the {@code Color} to subtract
	 * @return a new {@code Color} instance with the result of the subtraction
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Color subtract(final Color color) {
		return subtract(color.r, color.g, color.b);
	}
	
	/**
	 * Subtracts {@code gray} from the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the subtraction.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.subtract(gray, gray, gray);
	 * }
	 * </pre>
	 * 
	 * @param gray the value to subtract from the R-, G- and B component values
	 * @return a new {@code Color} instance with the result of the subtraction
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color subtract(final float gray) {
		return subtract(gray, gray, gray);
	}
	
	/**
	 * Subtracts {@code r}, {@code g} and {@code b} from the component values of this {@code Color} instance while preserving the alpha component value.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the subtraction.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param r the value to subtract from the R component value
	 * @param g the value to subtract from the G component value
	 * @param b the value to subtract from the B component value
	 * @return a new {@code Color} instance with the result of the subtraction
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 */
	public Color subtract(final float r, final float g, final float b) {
		return new Color(this.r - r, this.g - g, this.b - b, this.a);
	}
	
	/**
	 * Applies an ACES filmic curve tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.toneMappingFilmicCurve(exposure, a, b, c, d, e, 0.0F, Float.MIN_VALUE)
	 * }
	 * </pre>
	 * 
	 * @param exposure the exposure to use
	 * @param a a {@code float} value
	 * @param b a {@code float} value
	 * @param c a {@code float} value
	 * @param d a {@code float} value
	 * @param e a {@code float} value
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingFilmicCurve(final float exposure, final float a, final float b, final float c, final float d, final float e) {
		return toneMappingFilmicCurve(exposure, a, b, c, d, e, 0.0F, Float.MIN_VALUE);
	}
	
	/**
	 * Applies an ACES filmic curve tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * 
	 * @param exposure the exposure to use
	 * @param a a {@code float} value
	 * @param b a {@code float} value
	 * @param c a {@code float} value
	 * @param d a {@code float} value
	 * @param e a {@code float} value
	 * @param subtract a value to subtract from each R-, G- and B component value before performing the tone mapping operation
	 * @param minimum the minimum value allowed for each R-, G- and B component value
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingFilmicCurve(final float exposure, final float a, final float b, final float c, final float d, final float e, final float subtract, final float minimum) {
		final float oldR = Floats.max(this.r * exposure - subtract, minimum);
		final float oldG = Floats.max(this.g * exposure - subtract, minimum);
		final float oldB = Floats.max(this.b * exposure - subtract, minimum);
		final float oldA = this.a;
		
		final float newR = Floats.saturate((oldR * (a * oldR + b)) / (oldR * (c * oldR + d) + e));
		final float newG = Floats.saturate((oldG * (a * oldG + b)) / (oldG * (c * oldG + d) + e));
		final float newB = Floats.saturate((oldB * (a * oldB + b)) / (oldB * (c * oldB + d) + e));
		final float newA = oldA;
		
		return new Color(newR, newG, newB, newA);
	}
	
	/**
	 * Applies a modified ACES filmic curve tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * <p>
	 * To use the original ACES filmic curve, set {@code exposure} to {@code 0.6F}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.toneMappingFilmicCurve(exposure, 2.51F, 0.03F, 2.43F, 0.59F, 0.14F);
	 * }
	 * </pre>
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingFilmicCurveACES2(final float exposure) {
//		Source: https://knarkowicz.wordpress.com/2016/01/06/aces-filmic-tone-mapping-curve/
		return toneMappingFilmicCurve(exposure, 2.51F, 0.03F, 2.43F, 0.59F, 0.14F);
	}
	
	/**
	 * Applies a filmic curve tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * <p>
	 * This tone mapping operator also performs gamma correction with a gamma of 2.2. So, do not use gamma correction if this tone mapping operator is used.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.toneMappingFilmicCurve(exposure, 6.2F, 0.5F, 6.2F, 1.7F, 0.06F, 0.004F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingFilmicCurveGammaCorrection22(final float exposure) {
//		Source: http://filmicworlds.com/blog/why-a-filmic-curve-saturates-your-blacks/
		return toneMappingFilmicCurve(exposure, 6.2F, 0.5F, 6.2F, 1.7F, 0.06F, 0.004F, 0.0F);
	}
	
	/**
	 * Applies a Reinhard tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingReinhard(final float exposure) {
//		Source: https://www.shadertoy.com/view/WdjSW3
		
		final float oldR = this.r * exposure;
		final float oldG = this.g * exposure;
		final float oldB = this.b * exposure;
		final float oldA = this.a;
		
		final float newR = oldR / (1.0F + oldR);
		final float newG = oldG / (1.0F + oldG);
		final float newB = oldB / (1.0F + oldB);
		final float newA = oldA;
		
		return new Color(newR, newG, newB, newA);
	}
	
	/**
	 * Applies a modified Reinhard tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingReinhardModifiedV1(final float exposure) {
//		Source: https://www.shadertoy.com/view/WdjSW3
		
		final float lWhite = 4.0F;
		
		final float oldR = this.r * exposure;
		final float oldG = this.g * exposure;
		final float oldB = this.b * exposure;
		final float oldA = this.a;
		
		final float newR = oldR * (1.0F + oldR / (lWhite * lWhite)) / (1.0F + oldR);
		final float newG = oldG * (1.0F + oldG / (lWhite * lWhite)) / (1.0F + oldG);
		final float newB = oldB * (1.0F + oldB / (lWhite * lWhite)) / (1.0F + oldB);
		final float newA = oldA;
		
		return new Color(newR, newG, newB, newA);
	}
	
	/**
	 * Applies a modified Reinhard tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingReinhardModifiedV2(final float exposure) {
		final float oldR = this.r * exposure;
		final float oldG = this.g * exposure;
		final float oldB = this.b * exposure;
		final float oldA = this.a;
		
		final float newR = 1.0F - exp(-oldR * exposure);
		final float newG = 1.0F - exp(-oldG * exposure);
		final float newB = 1.0F - exp(-oldB * exposure);
		final float newA = oldA;
		
		return new Color(newR, newG, newB, newA);
	}
	
	/**
	 * Applies an Unreal 3 tone mapping operator to this {@code Color} instance.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the tone mapping operation.
	 * <p>
	 * This tone mapping operator also performs gamma correction with a gamma of 2.2. So, do not use gamma correction if this tone mapping operator is used.
	 * 
	 * @param exposure the exposure to use
	 * @return a new {@code Color} instance with the result of the tone mapping operation
	 */
	public Color toneMappingUnreal3(final float exposure) {
//		Source: https://www.shadertoy.com/view/WdjSW3
		
		final float oldR = this.r * exposure;
		final float oldG = this.g * exposure;
		final float oldB = this.b * exposure;
		final float oldA = this.a;
		
		final float newR = oldR / (oldR + 0.155F) * 1.019F;
		final float newG = oldG / (oldG + 0.155F) * 1.019F;
		final float newB = oldB / (oldB + 0.155F) * 1.019F;
		final float newA = oldA;
		
		return new Color(newR, newG, newB, newA);
	}
	
	/**
	 * Undoes gamma correction on this {@code Color} instance using {@code RGBColorSpace.SRGB}.
	 * <p>
	 * Returns a new {@code Color} instance with gamma correction undone.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.undoGammaCorrection(RGBColorSpace.SRGB);
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Color} instance with gamma correction undone
	 */
	public Color undoGammaCorrection() {
		return undoGammaCorrection(RGBColorSpace.SRGB);
	}
	
	/**
	 * Undoes gamma correction on this {@code Color} instance using {@code colorSpace}.
	 * <p>
	 * Returns a new {@code Color} instance with gamma correction undone.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace the {@link RGBColorSpace} to use
	 * @return a new {@code Color} instance with gamma correction undone
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
	public Color undoGammaCorrection(final RGBColorSpace colorSpace) {
		final float r = colorSpace.undoGammaCorrection(this.r);
		final float g = colorSpace.undoGammaCorrection(this.g);
		final float b = colorSpace.undoGammaCorrection(this.b);
		final float a = this.a;
		
		return new Color(r, g, b, a);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Color} instance.
	 * 
	 * @return a {@code String} representation of this {@code Color} instance
	 */
	@Override
	public String toString() {
		return String.format("new Color(%s, %s, %s, %s)", Float.toString(this.r), Float.toString(this.g), Float.toString(this.b), Float.toString(this.a));
	}
	
	/**
	 * Compares {@code object} to this {@code Color} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Color}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Color} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Color}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Color)) {
			return false;
		} else if(Float.compare(this.a, Color.class.cast(object).a) != 0) {
			return false;
		} else if(Float.compare(this.b, Color.class.cast(object).b) != 0) {
			return false;
		} else if(Float.compare(this.g, Color.class.cast(object).g) != 0) {
			return false;
		} else if(Float.compare(this.r, Color.class.cast(object).r) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the alpha component (A) as a {@code byte}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the alpha component (A) as a {@code byte}
	 */
	public byte getAsByteA() {
		return (byte)(getAsIntA() & 0xFF);
	}
	
	/**
	 * Returns the value of the blue component (B) as a {@code byte}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the blue component (B) as a {@code byte}
	 */
	public byte getAsByteB() {
		return (byte)(getAsIntB() & 0xFF);
	}
	
	/**
	 * Returns the value of the green component (G) as a {@code byte}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the green component (G) as a {@code byte}
	 */
	public byte getAsByteG() {
		return (byte)(getAsIntG() & 0xFF);
	}
	
	/**
	 * Returns the value of the red component (R) as a {@code byte}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the red component (R) as a {@code byte}
	 */
	public byte getAsByteR() {
		return (byte)(getAsIntR() & 0xFF);
	}
	
	/**
	 * Returns the average component value of this {@code Color} instance.
	 * 
	 * @return the average component value of this {@code Color} instance
	 */
	public float average() {
		return (this.r + this.g + this.b) / 3.0F;
	}
	
	/**
	 * Returns the value of the alpha component (A).
	 * 
	 * @return the value of the alpha component (A)
	 */
	public float getA() {
		return this.a;
	}
	
	/**
	 * Returns the value of the blue component (B).
	 * 
	 * @return the value of the blue component (B)
	 */
	public float getB() {
		return this.b;
	}
	
	/**
	 * Returns the value of the green component (G).
	 * 
	 * @return the value of the green component (G)
	 */
	public float getG() {
		return this.g;
	}
	
	/**
	 * Returns the value of the red component (R).
	 * 
	 * @return the value of the red component (R)
	 */
	public float getR() {
		return this.r;
	}
	
	/**
	 * Returns the relative luminance of this {@code Color} instance.
	 * <p>
	 * The algorithm used is only suitable for linear {@code Color}s.
	 * 
	 * @return the relative luminance of this {@code Color} instance
	 */
	public float luminance() {
		return this.r * 0.212671F + this.g * 0.715160F + this.b * 0.072169F;
	}
	
	/**
	 * Returns the largest R-, G- or B-component value.
	 * 
	 * @return the largest R-, G- or B-component value
	 */
	public float max() {
		return Floats.max(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the smallest R-, G- or B-component value.
	 * 
	 * @return the smallest R-, G- or B-component value
	 */
	public float min() {
		return Floats.min(this.r, this.g, this.b);
	}
	
	/**
	 * Returns the value of the alpha component (A) as an {@code int}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the alpha component (A) as an {@code int}
	 */
	public int getAsIntA() {
		return doConvertComponentValueFromFloatToInt(this.a);
	}
	
	/**
	 * Returns the value of the blue component (B) as an {@code int}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the blue component (B) as an {@code int}
	 */
	public int getAsIntB() {
		return doConvertComponentValueFromFloatToInt(this.b);
	}
	
	/**
	 * Returns the value of the green component (G) as an {@code int}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the green component (G) as an {@code int}
	 */
	public int getAsIntG() {
		return doConvertComponentValueFromFloatToInt(this.g);
	}
	
	/**
	 * Returns the value of the red component (R) as an {@code int}.
	 * <p>
	 * This method assumes the component value to be within the range {@code [0.0, 1.0]}. A component value outside of this range will be clamped.
	 * <p>
	 * If the component values are outside the range {@code [0.0, 1.0]}, you may want to call {@link #minTo0()} followed by {@link #maxTo1()}, in order to preserve the ratios between the component values.
	 * 
	 * @return the value of the red component (R) as an {@code int}
	 */
	public int getAsIntR() {
		return doConvertComponentValueFromFloatToInt(this.r);
	}
	
	/**
	 * Returns a hash code for this {@code Color} instance.
	 * 
	 * @return a hash code for this {@code Color} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.a), Float.valueOf(this.b), Float.valueOf(this.g), Float.valueOf(this.r));
	}
	
	/**
	 * Returns an {@code int} with the component values in a packed form.
	 * <p>
	 * This method assumes that the component values are within the range {@code [0.0, 1.0]}. Any component value outside of this range will be clamped.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * color.pack(PackedIntComponentOrder.ARGB);
	 * }
	 * </pre>
	 * 
	 * @return an {@code int} with the component values in a packed form
	 */
	public int pack() {
		return pack(PackedIntComponentOrder.ARGB);
	}
	
	/**
	 * Returns an {@code int} with the component values in a packed form.
	 * <p>
	 * This method assumes that the component values are within the range {@code [0.0, 1.0]}. Any component value outside of this range will be clamped.
	 * <p>
	 * If {@code packedIntComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param packedIntComponentOrder the {@link PackedIntComponentOrder} to pack the component values with
	 * @return an {@code int} with the component values in a packed form
	 * @throws NullPointerException thrown if, and only if, {@code packedIntComponentOrder} is {@code null}
	 */
	public int pack(final PackedIntComponentOrder packedIntComponentOrder) {
		final int r = doConvertComponentValueFromFloatToInt(this.r);
		final int g = doConvertComponentValueFromFloatToInt(this.g);
		final int b = doConvertComponentValueFromFloatToInt(this.b);
		final int a = doConvertComponentValueFromFloatToInt(this.a);
		
		return packedIntComponentOrder.pack(r, g, b, a);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Blends the component values of the two {@code Color}s {@code colorA} and {@code colorB} based on {@code factor}.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the blend.
	 * <p>
	 * If either {@code colorA} or {@code colorB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param colorA one of the {@code Color}s to blend
	 * @param colorB one of the {@code Color}s to blend
	 * @param factor the factor to use in the blending process
	 * @return a new {@code Color} instance with the result of the blend
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, either {@code color0} or {@code color1} are {@code null}
	 */
	public static Color blend(final Color colorA, final Color colorB, final float factor) {
		return blend(colorA, colorB, factor, factor, factor);
	}
	
	/**
	 * Blends the component values of the two {@code Color}s {@code colorA} and {@code colorB} based on {@code factorR}, {@code factorG} and {@code factorB}, respectively.
	 * <p>
	 * Returns a new {@code Color} instance with the result of the blend.
	 * <p>
	 * If either {@code colorA} or {@code colorB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If at least one of the resulting component values are invalid, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param colorA one of the {@code Color}s to blend
	 * @param colorB one of the {@code Color}s to blend
	 * @param factorR the factor to use for the R-component values in the blending process
	 * @param factorG the factor to use for the G-component values in the blending process
	 * @param factorB the factor to use for the B-component values in the blending process
	 * @return a new {@code Color} instance with the result of the blend
	 * @throws IllegalArgumentException thrown if, and only if, at least one of the resulting component values are invalid
	 * @throws NullPointerException thrown if, and only if, either {@code color0} or {@code color1} are {@code null}
	 */
	public static Color blend(final Color colorA, final Color colorB, final float factorR, final float factorG, final float factorB) {
		final float r = Floats.lerp(colorA.r, colorB.r, factorR);
		final float g = Floats.lerp(colorA.g, colorB.g, factorG);
		final float b = Floats.lerp(colorA.b, colorB.b, factorB);
		
		return new Color(r, g, b);
	}
	
	/**
	 * Returns a random {@code Color} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.random(1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a random {@code Color} instance
	 */
	public static Color random() {
		return random(1.0F);
	}
	
	/**
	 * Returns a random {@code Color} instance.
	 * 
	 * @param a the value of the alpha component (A)
	 * @return a random {@code Color} instance
	 */
	public static Color random(final float a) {
		return new Color(Floats.nextFloat(), Floats.nextFloat(), Floats.nextFloat(), a);
	}
	
	/**
	 * Returns a random blue {@code Color} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.randomBlue(1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a random blue {@code Color} instance
	 */
	public static Color randomBlue() {
		return randomBlue(1.0F);
	}
	
	/**
	 * Returns a random blue {@code Color} instance.
	 * 
	 * @param a the value of the alpha component (A)
	 * @return a random blue {@code Color} instance
	 */
	public static Color randomBlue(final float a) {
		return new Color(0.0F, 0.0F, Floats.nextFloat(), a);
	}
	
	/**
	 * Returns a random gray {@code Color} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.randomGray(1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a random gray {@code Color} instance
	 */
	public static Color randomGray() {
		return randomGray(1.0F);
	}
	
	/**
	 * Returns a random gray {@code Color} instance.
	 * 
	 * @param a the value of the alpha component (A)
	 * @return a random gray {@code Color} instance
	 */
	public static Color randomGray(final float a) {
		return new Color(Floats.nextFloat(), a);
	}
	
	/**
	 * Returns a random green {@code Color} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.randomGreen(1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a random green {@code Color} instance
	 */
	public static Color randomGreen() {
		return randomGreen(1.0F);
	}
	
	/**
	 * Returns a random green {@code Color} instance.
	 * 
	 * @param a the value of the alpha component (A)
	 * @return a random green {@code Color} instance
	 */
	public static Color randomGreen(final float a) {
		return new Color(0.0F, Floats.nextFloat(), 0.0F, a);
	}
	
	/**
	 * Returns a random red {@code Color} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Color.randomRed(1.0F);
	 * }
	 * </pre>
	 * 
	 * @return a random red {@code Color} instance
	 */
	public static Color randomRed() {
		return randomRed(1.0F);
	}
	
	/**
	 * Returns a random red {@code Color} instance.
	 * 
	 * @param a the value of the alpha component (A)
	 * @return a random red {@code Color} instance
	 */
	public static Color randomRed(final float a) {
		return new Color(Floats.nextFloat(), 0.0F, 0.0F, a);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static float doConvertComponentValueFromIntToFloat(final int componentValue) {
		return Integers.saturate(componentValue) / 255.0F;
	}
	
	private static int doConvertComponentValueFromFloatToInt(final float componentValue) {
		return toInt(Floats.saturate(componentValue) * 255.0F + 0.5F);
	}
}