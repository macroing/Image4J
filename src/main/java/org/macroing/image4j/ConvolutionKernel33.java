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
import java.util.concurrent.ThreadLocalRandom;

/**
 * A {@code ConvolutionKernel33} is a convolution kernel with three rows and three columns.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConvolutionKernel33 {
	/**
	 * A {@code ConvolutionKernel33} instance that performs a box blur effect.
	 */
	public static ConvolutionKernel33 BOX_BLUR = new ConvolutionKernel33(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F / 9.0F, 0.0F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs an edge detection effect.
	 */
	public static ConvolutionKernel33 EDGE_DETECTION = new ConvolutionKernel33(-1.0F, -1.0F, -1.0F, -1.0F, 8.0F, -1.0F, -1.0F, -1.0F, -1.0F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs an emboss effect.
	 */
	public static ConvolutionKernel33 EMBOSS = new ConvolutionKernel33(-1.0F, -1.0F, 0.0F, -1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.5F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs a Gaussian blur effect.
	 */
	public static ConvolutionKernel33 GAUSSIAN_BLUR = new ConvolutionKernel33(1.0F, 2.0F, 1.0F, 2.0F, 4.0F, 2.0F, 1.0F, 2.0F, 1.0F, 1.0F / 16.0F, 0.0F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs a gradient effect in the horizontal direction.
	 */
	public static ConvolutionKernel33 GRADIENT_HORIZONTAL = new ConvolutionKernel33(-1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs a gradient effect in the vertical direction.
	 */
	public static ConvolutionKernel33 GRADIENT_VERTICAL = new ConvolutionKernel33(-1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F, -1.0F, 0.0F, 1.0F);
	
	/**
	 * A {@code ConvolutionKernel33} instance that performs a sharpen effect.
	 */
	public static ConvolutionKernel33 SHARPEN = new ConvolutionKernel33(-1.0F, -1.0F, -1.0F, -1.0F, 9.0F, -1.0F, -1.0F, -1.0F, -1.0F);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The bias associated with this {@code ConvolutionKernel33} instance.
	 */
	public final float bias;
	
	/**
	 * The element at index {@code 0} or row {@code 0} and column {@code 0}.
	 */
	public final float element00;
	
	/**
	 * The element at index {@code 1} or row {@code 0} and column {@code 1}.
	 */
	public final float element01;
	
	/**
	 * The element at index {@code 2} or row {@code 0} and column {@code 2}.
	 */
	public final float element02;
	
	/**
	 * The element at index {@code 3} or row {@code 1} and column {@code 0}.
	 */
	public final float element10;
	
	/**
	 * The element at index {@code 4} or row {@code 1} and column {@code 1}.
	 */
	public final float element11;
	
	/**
	 * The element at index {@code 5} or row {@code 1} and column {@code 2}.
	 */
	public final float element12;
	
	/**
	 * The element at index {@code 6} or row {@code 2} and column {@code 0}.
	 */
	public final float element20;
	
	/**
	 * The element at index {@code 7} or row {@code 2} and column {@code 1}.
	 */
	public final float element21;
	
	/**
	 * The element at index {@code 8} or row {@code 2} and column {@code 2}.
	 */
	public final float element22;
	
	/**
	 * The factor associated with this {@code ConvolutionKernel33} instance.
	 */
	public final float factor;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConvolutionKernel33} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new ConvolutionKernel33(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
	 * }
	 * </pre>
	 */
	public ConvolutionKernel33() {
		this(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code ConvolutionKernel33} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new ConvolutionKernel33(element00, element01, element02, element10, element11, element12, element20, element21, element22, 1.0F, 0.0F)
	 * }
	 * </pre>
	 * 
	 * @param element00 the element at index {@code 0} or row {@code 0} and column {@code 0}
	 * @param element01 the element at index {@code 1} or row {@code 0} and column {@code 1}
	 * @param element02 the element at index {@code 2} or row {@code 0} and column {@code 2}
	 * @param element10 the element at index {@code 3} or row {@code 1} and column {@code 0}
	 * @param element11 the element at index {@code 4} or row {@code 1} and column {@code 1}
	 * @param element12 the element at index {@code 5} or row {@code 1} and column {@code 2}
	 * @param element20 the element at index {@code 6} or row {@code 2} and column {@code 0}
	 * @param element21 the element at index {@code 7} or row {@code 2} and column {@code 1}
	 * @param element22 the element at index {@code 8} or row {@code 2} and column {@code 2}
	 */
	public ConvolutionKernel33(final float element00, final float element01, final float element02, final float element10, final float element11, final float element12, final float element20, final float element21, final float element22) {
		this(element00, element01, element02, element10, element11, element12, element20, element21, element22, 1.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code ConvolutionKernel33} instance.
	 * 
	 * @param element00 the element at index {@code 0} or row {@code 0} and column {@code 0}
	 * @param element01 the element at index {@code 1} or row {@code 0} and column {@code 1}
	 * @param element02 the element at index {@code 2} or row {@code 0} and column {@code 2}
	 * @param element10 the element at index {@code 3} or row {@code 1} and column {@code 0}
	 * @param element11 the element at index {@code 4} or row {@code 1} and column {@code 1}
	 * @param element12 the element at index {@code 5} or row {@code 1} and column {@code 2}
	 * @param element20 the element at index {@code 6} or row {@code 2} and column {@code 0}
	 * @param element21 the element at index {@code 7} or row {@code 2} and column {@code 1}
	 * @param element22 the element at index {@code 8} or row {@code 2} and column {@code 2}
	 * @param factor the factor to use
	 * @param bias the bias to use
	 */
	public ConvolutionKernel33(final float element00, final float element01, final float element02, final float element10, final float element11, final float element12, final float element20, final float element21, final float element22, final float factor, final float bias) {
		this.element00 = element00;
		this.element01 = element01;
		this.element02 = element02;
		this.element10 = element10;
		this.element11 = element11;
		this.element12 = element12;
		this.element20 = element20;
		this.element21 = element21;
		this.element22 = element22;
		this.factor = factor;
		this.bias = bias;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code ConvolutionKernel33} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConvolutionKernel33} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConvolutionKernel33(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", Float.toString(this.element00), Float.toString(this.element01), Float.toString(this.element02), Float.toString(this.element10), Float.toString(this.element11), Float.toString(this.element12), Float.toString(this.element20), Float.toString(this.element21), Float.toString(this.element22), Float.toString(this.factor), Float.toString(this.bias));
	}
	
	/**
	 * Compares {@code object} to this {@code ConvolutionKernel33} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernel33}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConvolutionKernel33} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernel33}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConvolutionKernel33)) {
			return false;
		} else if(Float.compare(this.bias, ConvolutionKernel33.class.cast(object).bias) != 0) {
			return false;
		} else if(Float.compare(this.element00, ConvolutionKernel33.class.cast(object).element00) != 0) {
			return false;
		} else if(Float.compare(this.element01, ConvolutionKernel33.class.cast(object).element01) != 0) {
			return false;
		} else if(Float.compare(this.element02, ConvolutionKernel33.class.cast(object).element02) != 0) {
			return false;
		} else if(Float.compare(this.element10, ConvolutionKernel33.class.cast(object).element10) != 0) {
			return false;
		} else if(Float.compare(this.element11, ConvolutionKernel33.class.cast(object).element11) != 0) {
			return false;
		} else if(Float.compare(this.element12, ConvolutionKernel33.class.cast(object).element12) != 0) {
			return false;
		} else if(Float.compare(this.element20, ConvolutionKernel33.class.cast(object).element20) != 0) {
			return false;
		} else if(Float.compare(this.element21, ConvolutionKernel33.class.cast(object).element21) != 0) {
			return false;
		} else if(Float.compare(this.element22, ConvolutionKernel33.class.cast(object).element22) != 0) {
			return false;
		} else if(Float.compare(this.factor, ConvolutionKernel33.class.cast(object).factor) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the bias associated with this {@code ConvolutionKernel33} instance.
	 * 
	 * @return the bias associated with this {@code ConvolutionKernel33} instance
	 */
	public float getBias() {
		return this.bias;
	}
	
	/**
	 * Returns the factor associated with this {@code ConvolutionKernel33} instance.
	 * 
	 * @return the factor associated with this {@code ConvolutionKernel33} instance
	 */
	public float getFactor() {
		return this.factor;
	}
	
	/**
	 * Returns the element at index {@code 0} or row {@code 0} and column {@code 0}.
	 * 
	 * @return the element at index {@code 0} or row {@code 0} and column {@code 0}
	 */
	public float getElement00() {
		return this.element00;
	}
	
	/**
	 * Returns the element at index {@code 1} or row {@code 0} and column {@code 1}.
	 * 
	 * @return the element at index {@code 1} or row {@code 0} and column {@code 1}
	 */
	public float getElement01() {
		return this.element01;
	}
	
	/**
	 * Returns the element at index {@code 2} or row {@code 0} and column {@code 2}.
	 * 
	 * @return the element at index {@code 2} or row {@code 0} and column {@code 2}
	 */
	public float getElement02() {
		return this.element02;
	}
	
	/**
	 * Returns the element at index {@code 3} or row {@code 1} and column {@code 0}.
	 * 
	 * @return the element at index {@code 3} or row {@code 1} and column {@code 0}
	 */
	public float getElement10() {
		return this.element10;
	}
	
	/**
	 * Returns the element at index {@code 4} or row {@code 1} and column {@code 1}.
	 * 
	 * @return the element at index {@code 4} or row {@code 1} and column {@code 1}
	 */
	public float getElement11() {
		return this.element11;
	}
	
	/**
	 * Returns the element at index {@code 5} or row {@code 1} and column {@code 2}.
	 * 
	 * @return the element at index {@code 5} or row {@code 1} and column {@code 2}
	 */
	public float getElement12() {
		return this.element12;
	}
	
	/**
	 * Returns the element at index {@code 6} or row {@code 2} and column {@code 0}.
	 * 
	 * @return the element at index {@code 6} or row {@code 2} and column {@code 0}
	 */
	public float getElement20() {
		return this.element20;
	}
	
	/**
	 * Returns the element at index {@code 7} or row {@code 2} and column {@code 1}.
	 * 
	 * @return the element at index {@code 7} or row {@code 2} and column {@code 1}
	 */
	public float getElement21() {
		return this.element21;
	}
	
	/**
	 * Returns the element at index {@code 8} or row {@code 2} and column {@code 2}.
	 * 
	 * @return the element at index {@code 8} or row {@code 2} and column {@code 2}
	 */
	public float getElement22() {
		return this.element22;
	}
	
	/**
	 * Returns a hash code for this {@code ConvolutionKernel33} instance.
	 * 
	 * @return a hash code for this {@code ConvolutionKernel33} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.bias), Float.valueOf(this.element00), Float.valueOf(this.element01), Float.valueOf(this.element02), Float.valueOf(this.element10), Float.valueOf(this.element11), Float.valueOf(this.element12), Float.valueOf(this.element20), Float.valueOf(this.element21), Float.valueOf(this.element22), Float.valueOf(this.factor));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ConvolutionKernel33} instance that performs a random effect.
	 * 
	 * @return a {@code ConvolutionKernel33} instance that performs a random effect
	 */
	public static ConvolutionKernel33 random() {
		final float element00 = doNextFloat() * 2.0F - 1.0F;
		final float element01 = doNextFloat() * 2.0F - 1.0F;
		final float element02 = doNextFloat() * 2.0F - 1.0F;
		final float element10 = doNextFloat() * 2.0F - 1.0F;
		final float element11 = 1.0F;
		final float element12 = doNextFloat() * 2.0F - 1.0F;
		final float element20 = doNextFloat() * 2.0F - 1.0F;
		final float element21 = doNextFloat() * 2.0F - 1.0F;
		final float element22 = doNextFloat() * 2.0F - 1.0F;
		
		final float elementTotal = element00 + element01 + element02 + element10 + element11 + element12 + element20 + element21 + element22;
		
		final boolean isFactorBasedOnElementTotal = doNextBoolean();
		final boolean isFactorBasedOnRandomFloat = doNextBoolean();
		final boolean isBiasBasedOnRandomFloat = doNextBoolean();
		
		final float factorBasedOnElementTotal = Float.compare(elementTotal, 0.0F) == 0 ? 1.0F : 1.0F / elementTotal;
		
		final float factor = isFactorBasedOnElementTotal ? factorBasedOnElementTotal : isFactorBasedOnRandomFloat ? doNextFloat() : 1.0F;
		final float bias = isBiasBasedOnRandomFloat ? doNextFloat() : 0.0F;
		
		return new ConvolutionKernel33(element00, element01, element02, element10, element11, element12, element20, element21, element22, factor, bias);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doNextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	private static float doNextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
}