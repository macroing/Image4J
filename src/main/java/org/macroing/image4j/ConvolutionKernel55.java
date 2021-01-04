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

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A {@code ConvolutionKernel55} is a convolution kernel with five rows and five columns.
 * <p>
 * This class is immutable and therefore suitable for concurrent use without external synchronization.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConvolutionKernel55 {
	/**
	 * A {@code ConvolutionKernel55} instance that performs a Gaussian blur effect.
	 */
	public static final ConvolutionKernel55 GAUSSIAN_BLUR = new ConvolutionKernel55(1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, 36.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 1.0F / 256.0F, 0.0F);
	
	/**
	 * A {@code ConvolutionKernel55} instance that performs an unsharp masking effect.
	 */
	public static final ConvolutionKernel55 UNSHARP_MASKING = new ConvolutionKernel55(1.0F, 4.0F, 6.0F, 4.0F, 1.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 6.0F, 24.0F, -476.0F, 24.0F, 6.0F, 4.0F, 16.0F, 24.0F, 16.0F, 4.0F, 1.0F, 4.0F, 6.0F, 4.0F, 1.0F, -1.0F / 256.0F, 0.0F);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The bias associated with this {@code ConvolutionKernel33} instance.
	 * <p>
	 * This is the same as Offset in Gimp.
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
	 * The element at index {@code 3} or row {@code 0} and column {@code 3}.
	 */
	public final float element03;
	
	/**
	 * The element at index {@code 4} or row {@code 0} and column {@code 4}.
	 */
	public final float element04;
	
	/**
	 * The element at index {@code 5} or row {@code 1} and column {@code 0}.
	 */
	public final float element10;
	
	/**
	 * The element at index {@code 6} or row {@code 1} and column {@code 1}.
	 */
	public final float element11;
	
	/**
	 * The element at index {@code 7} or row {@code 1} and column {@code 2}.
	 */
	public final float element12;
	
	/**
	 * The element at index {@code 8} or row {@code 1} and column {@code 3}.
	 */
	public final float element13;
	
	/**
	 * The element at index {@code 9} or row {@code 1} and column {@code 4}.
	 */
	public final float element14;
	
	/**
	 * The element at index {@code 10} or row {@code 2} and column {@code 0}.
	 */
	public final float element20;
	
	/**
	 * The element at index {@code 11} or row {@code 2} and column {@code 1}.
	 */
	public final float element21;
	
	/**
	 * The element at index {@code 12} or row {@code 2} and column {@code 2}.
	 */
	public final float element22;
	
	/**
	 * The element at index {@code 13} or row {@code 2} and column {@code 3}.
	 */
	public final float element23;
	
	/**
	 * The element at index {@code 14} or row {@code 2} and column {@code 4}.
	 */
	public final float element24;
	
	/**
	 * The element at index {@code 15} or row {@code 3} and column {@code 0}.
	 */
	public final float element30;
	
	/**
	 * The element at index {@code 16} or row {@code 3} and column {@code 1}.
	 */
	public final float element31;
	
	/**
	 * The element at index {@code 17} or row {@code 3} and column {@code 2}.
	 */
	public final float element32;
	
	/**
	 * The element at index {@code 18} or row {@code 3} and column {@code 3}.
	 */
	public final float element33;
	
	/**
	 * The element at index {@code 19} or row {@code 3} and column {@code 4}.
	 */
	public final float element34;
	
	/**
	 * The element at index {@code 20} or row {@code 4} and column {@code 0}.
	 */
	public final float element40;
	
	/**
	 * The element at index {@code 21} or row {@code 4} and column {@code 1}.
	 */
	public final float element41;
	
	/**
	 * The element at index {@code 22} or row {@code 4} and column {@code 2}.
	 */
	public final float element42;
	
	/**
	 * The element at index {@code 23} or row {@code 4} and column {@code 3}.
	 */
	public final float element43;
	
	/**
	 * The element at index {@code 24} or row {@code 4} and column {@code 4}.
	 */
	public final float element44;
	
	/**
	 * The factor associated with this {@code ConvolutionKernel33} instance.
	 * <p>
	 * This is the same as the reciprocal value of the Divisor in Gimp.
	 */
	public final float factor;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConvolutionKernel55} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new ConvolutionKernel55(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
	 * }
	 * </pre>
	 */
	public ConvolutionKernel55() {
		this(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code ConvolutionKernel55} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following ({@code element} is replaced with {@code e} to keep it short):
	 * <pre>
	 * {@code
	 * new ConvolutionKernel55(e00, e01, e02, e03, e04, e10, e11, e12, e13, e14, e20, e21, e22, e23, e24, e30, e31, e32, e33, e34, e40, e41, e42, e43, e44, 1.0F, 0.0F)
	 * }
	 * </pre>
	 * 
	 * @param element00 the element at index {@code 0} or row {@code 0} and column {@code 0}
	 * @param element01 the element at index {@code 1} or row {@code 0} and column {@code 1}
	 * @param element02 the element at index {@code 2} or row {@code 0} and column {@code 2}
	 * @param element03 the element at index {@code 3} or row {@code 0} and column {@code 3}
	 * @param element04 the element at index {@code 4} or row {@code 0} and column {@code 4}
	 * @param element10 the element at index {@code 5} or row {@code 1} and column {@code 0}
	 * @param element11 the element at index {@code 6} or row {@code 1} and column {@code 1}
	 * @param element12 the element at index {@code 7} or row {@code 1} and column {@code 2}
	 * @param element13 the element at index {@code 8} or row {@code 1} and column {@code 3}
	 * @param element14 the element at index {@code 9} or row {@code 1} and column {@code 4}
	 * @param element20 the element at index {@code 10} or row {@code 2} and column {@code 0}
	 * @param element21 the element at index {@code 11} or row {@code 2} and column {@code 1}
	 * @param element22 the element at index {@code 12} or row {@code 2} and column {@code 2}
	 * @param element23 the element at index {@code 13} or row {@code 2} and column {@code 3}
	 * @param element24 the element at index {@code 14} or row {@code 2} and column {@code 4}
	 * @param element30 the element at index {@code 15} or row {@code 3} and column {@code 0}
	 * @param element31 the element at index {@code 16} or row {@code 3} and column {@code 1}
	 * @param element32 the element at index {@code 17} or row {@code 3} and column {@code 2}
	 * @param element33 the element at index {@code 18} or row {@code 3} and column {@code 3}
	 * @param element34 the element at index {@code 19} or row {@code 3} and column {@code 4}
	 * @param element40 the element at index {@code 20} or row {@code 4} and column {@code 0}
	 * @param element41 the element at index {@code 21} or row {@code 4} and column {@code 1}
	 * @param element42 the element at index {@code 22} or row {@code 4} and column {@code 2}
	 * @param element43 the element at index {@code 23} or row {@code 4} and column {@code 3}
	 * @param element44 the element at index {@code 24} or row {@code 4} and column {@code 4}
	 */
	public ConvolutionKernel55(final float element00, final float element01, final float element02, final float element03, final float element04, final float element10, final float element11, final float element12, final float element13, final float element14, final float element20, final float element21, final float element22, final float element23, final float element24, final float element30, final float element31, final float element32, final float element33, final float element34, final float element40, final float element41, final float element42, final float element43, final float element44) {
		this(element00, element01, element02, element03, element04, element10, element11, element12, element13, element14, element20, element21, element22, element23, element24, element30, element31, element32, element33, element34, element40, element41, element42, element43, element44, 1.0F, 0.0F);
	}
	
	/**
	 * Constructs a new {@code ConvolutionKernel55} instance.
	 * 
	 * @param element00 the element at index {@code 0} or row {@code 0} and column {@code 0}
	 * @param element01 the element at index {@code 1} or row {@code 0} and column {@code 1}
	 * @param element02 the element at index {@code 2} or row {@code 0} and column {@code 2}
	 * @param element03 the element at index {@code 3} or row {@code 0} and column {@code 3}
	 * @param element04 the element at index {@code 4} or row {@code 0} and column {@code 4}
	 * @param element10 the element at index {@code 5} or row {@code 1} and column {@code 0}
	 * @param element11 the element at index {@code 6} or row {@code 1} and column {@code 1}
	 * @param element12 the element at index {@code 7} or row {@code 1} and column {@code 2}
	 * @param element13 the element at index {@code 8} or row {@code 1} and column {@code 3}
	 * @param element14 the element at index {@code 9} or row {@code 1} and column {@code 4}
	 * @param element20 the element at index {@code 10} or row {@code 2} and column {@code 0}
	 * @param element21 the element at index {@code 11} or row {@code 2} and column {@code 1}
	 * @param element22 the element at index {@code 12} or row {@code 2} and column {@code 2}
	 * @param element23 the element at index {@code 13} or row {@code 2} and column {@code 3}
	 * @param element24 the element at index {@code 14} or row {@code 2} and column {@code 4}
	 * @param element30 the element at index {@code 15} or row {@code 3} and column {@code 0}
	 * @param element31 the element at index {@code 16} or row {@code 3} and column {@code 1}
	 * @param element32 the element at index {@code 17} or row {@code 3} and column {@code 2}
	 * @param element33 the element at index {@code 18} or row {@code 3} and column {@code 3}
	 * @param element34 the element at index {@code 19} or row {@code 3} and column {@code 4}
	 * @param element40 the element at index {@code 20} or row {@code 4} and column {@code 0}
	 * @param element41 the element at index {@code 21} or row {@code 4} and column {@code 1}
	 * @param element42 the element at index {@code 22} or row {@code 4} and column {@code 2}
	 * @param element43 the element at index {@code 23} or row {@code 4} and column {@code 3}
	 * @param element44 the element at index {@code 24} or row {@code 4} and column {@code 4}
	 * @param factor the factor to use
	 * @param bias the bias to use
	 */
	public ConvolutionKernel55(final float element00, final float element01, final float element02, final float element03, final float element04, final float element10, final float element11, final float element12, final float element13, final float element14, final float element20, final float element21, final float element22, final float element23, final float element24, final float element30, final float element31, final float element32, final float element33, final float element34, final float element40, final float element41, final float element42, final float element43, final float element44, final float factor, final float bias) {
		this.element00 = element00;
		this.element01 = element01;
		this.element02 = element02;
		this.element03 = element03;
		this.element04 = element04;
		this.element10 = element10;
		this.element11 = element11;
		this.element12 = element12;
		this.element13 = element13;
		this.element14 = element14;
		this.element20 = element20;
		this.element21 = element21;
		this.element22 = element22;
		this.element23 = element23;
		this.element24 = element24;
		this.element30 = element30;
		this.element31 = element31;
		this.element32 = element32;
		this.element33 = element33;
		this.element34 = element34;
		this.element40 = element40;
		this.element41 = element41;
		this.element42 = element42;
		this.element43 = element43;
		this.element44 = element44;
		this.factor = factor;
		this.bias = bias;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code String} representation of this {@code ConvolutionKernel55} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConvolutionKernel55} instance
	 */
	@Override
	public String toString() {
		final String element00 = Float.toString(this.element00);
		final String element01 = Float.toString(this.element01);
		final String element02 = Float.toString(this.element02);
		final String element03 = Float.toString(this.element03);
		final String element04 = Float.toString(this.element04);
		final String element10 = Float.toString(this.element10);
		final String element11 = Float.toString(this.element11);
		final String element12 = Float.toString(this.element12);
		final String element13 = Float.toString(this.element13);
		final String element14 = Float.toString(this.element14);
		final String element20 = Float.toString(this.element20);
		final String element21 = Float.toString(this.element21);
		final String element22 = Float.toString(this.element22);
		final String element23 = Float.toString(this.element23);
		final String element24 = Float.toString(this.element24);
		final String element30 = Float.toString(this.element30);
		final String element31 = Float.toString(this.element31);
		final String element32 = Float.toString(this.element32);
		final String element33 = Float.toString(this.element33);
		final String element34 = Float.toString(this.element34);
		final String element40 = Float.toString(this.element40);
		final String element41 = Float.toString(this.element41);
		final String element42 = Float.toString(this.element42);
		final String element43 = Float.toString(this.element43);
		final String element44 = Float.toString(this.element44);
		final String factor = Float.toString(this.factor);
		final String bias = Float.toString(this.bias);
		
		return String.format("new ConvolutionKernel55(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)", element00, element01, element02, element03, element04, element10, element11, element12, element13, element14, element20, element21, element22, element23, element24, element30, element31, element32, element33, element34, element40, element41, element42, element43, element44, factor, bias);
	}
	
	/**
	 * Compares {@code object} to this {@code ConvolutionKernel55} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernel55}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConvolutionKernel55} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConvolutionKernel55}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConvolutionKernel55)) {
			return false;
		} else if(Float.compare(this.bias, ConvolutionKernel55.class.cast(object).bias) != 0) {
			return false;
		} else if(Float.compare(this.element00, ConvolutionKernel55.class.cast(object).element00) != 0) {
			return false;
		} else if(Float.compare(this.element01, ConvolutionKernel55.class.cast(object).element01) != 0) {
			return false;
		} else if(Float.compare(this.element02, ConvolutionKernel55.class.cast(object).element02) != 0) {
			return false;
		} else if(Float.compare(this.element03, ConvolutionKernel55.class.cast(object).element03) != 0) {
			return false;
		} else if(Float.compare(this.element04, ConvolutionKernel55.class.cast(object).element04) != 0) {
			return false;
		} else if(Float.compare(this.element10, ConvolutionKernel55.class.cast(object).element10) != 0) {
			return false;
		} else if(Float.compare(this.element11, ConvolutionKernel55.class.cast(object).element11) != 0) {
			return false;
		} else if(Float.compare(this.element12, ConvolutionKernel55.class.cast(object).element12) != 0) {
			return false;
		} else if(Float.compare(this.element13, ConvolutionKernel55.class.cast(object).element13) != 0) {
			return false;
		} else if(Float.compare(this.element14, ConvolutionKernel55.class.cast(object).element14) != 0) {
			return false;
		} else if(Float.compare(this.element20, ConvolutionKernel55.class.cast(object).element20) != 0) {
			return false;
		} else if(Float.compare(this.element21, ConvolutionKernel55.class.cast(object).element21) != 0) {
			return false;
		} else if(Float.compare(this.element22, ConvolutionKernel55.class.cast(object).element22) != 0) {
			return false;
		} else if(Float.compare(this.element23, ConvolutionKernel55.class.cast(object).element23) != 0) {
			return false;
		} else if(Float.compare(this.element24, ConvolutionKernel55.class.cast(object).element24) != 0) {
			return false;
		} else if(Float.compare(this.element30, ConvolutionKernel55.class.cast(object).element30) != 0) {
			return false;
		} else if(Float.compare(this.element31, ConvolutionKernel55.class.cast(object).element31) != 0) {
			return false;
		} else if(Float.compare(this.element32, ConvolutionKernel55.class.cast(object).element32) != 0) {
			return false;
		} else if(Float.compare(this.element33, ConvolutionKernel55.class.cast(object).element33) != 0) {
			return false;
		} else if(Float.compare(this.element34, ConvolutionKernel55.class.cast(object).element34) != 0) {
			return false;
		} else if(Float.compare(this.element40, ConvolutionKernel55.class.cast(object).element40) != 0) {
			return false;
		} else if(Float.compare(this.element41, ConvolutionKernel55.class.cast(object).element41) != 0) {
			return false;
		} else if(Float.compare(this.element42, ConvolutionKernel55.class.cast(object).element42) != 0) {
			return false;
		} else if(Float.compare(this.element43, ConvolutionKernel55.class.cast(object).element43) != 0) {
			return false;
		} else if(Float.compare(this.element44, ConvolutionKernel55.class.cast(object).element44) != 0) {
			return false;
		} else if(Float.compare(this.factor, ConvolutionKernel55.class.cast(object).factor) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the bias associated with this {@code ConvolutionKernel55} instance.
	 * <p>
	 * This is the same as Offset in Gimp.
	 * 
	 * @return the bias associated with this {@code ConvolutionKernel55} instance
	 */
	public float getBias() {
		return this.bias;
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
	 * Returns the element at index {@code 3} or row {@code 0} and column {@code 3}.
	 * 
	 * @return the element at index {@code 3} or row {@code 0} and column {@code 3}
	 */
	public float getElement03() {
		return this.element03;
	}
	
	/**
	 * Returns the element at index {@code 4} or row {@code 0} and column {@code 4}.
	 * 
	 * @return the element at index {@code 4} or row {@code 0} and column {@code 4}
	 */
	public float getElement04() {
		return this.element04;
	}
	
	/**
	 * Returns the element at index {@code 5} or row {@code 1} and column {@code 0}.
	 * 
	 * @return the element at index {@code 5} or row {@code 1} and column {@code 0}
	 */
	public float getElement10() {
		return this.element10;
	}
	
	/**
	 * Returns the element at index {@code 6} or row {@code 1} and column {@code 1}.
	 * 
	 * @return the element at index {@code 6} or row {@code 1} and column {@code 1}
	 */
	public float getElement11() {
		return this.element11;
	}
	
	/**
	 * Returns the element at index {@code 7} or row {@code 1} and column {@code 2}.
	 * 
	 * @return the element at index {@code 7} or row {@code 1} and column {@code 2}
	 */
	public float getElement12() {
		return this.element12;
	}
	
	/**
	 * Returns the element at index {@code 8} or row {@code 1} and column {@code 3}.
	 * 
	 * @return the element at index {@code 8} or row {@code 1} and column {@code 3}
	 */
	public float getElement13() {
		return this.element13;
	}
	
	/**
	 * Returns the element at index {@code 9} or row {@code 1} and column {@code 4}.
	 * 
	 * @return the element at index {@code 9} or row {@code 1} and column {@code 4}
	 */
	public float getElement14() {
		return this.element14;
	}
	
	/**
	 * Returns the element at index {@code 10} or row {@code 2} and column {@code 0}.
	 * 
	 * @return the element at index {@code 10} or row {@code 2} and column {@code 0}
	 */
	public float getElement20() {
		return this.element20;
	}
	
	/**
	 * Returns the element at index {@code 11} or row {@code 2} and column {@code 1}.
	 * 
	 * @return the element at index {@code 11} or row {@code 2} and column {@code 1}
	 */
	public float getElement21() {
		return this.element21;
	}
	
	/**
	 * Returns the element at index {@code 12} or row {@code 2} and column {@code 2}.
	 * 
	 * @return the element at index {@code 12} or row {@code 2} and column {@code 2}
	 */
	public float getElement22() {
		return this.element22;
	}
	
	/**
	 * Returns the element at index {@code 13} or row {@code 2} and column {@code 3}.
	 * 
	 * @return the element at index {@code 13} or row {@code 2} and column {@code 3}
	 */
	public float getElement23() {
		return this.element23;
	}
	
	/**
	 * Returns the element at index {@code 14} or row {@code 2} and column {@code 4}.
	 * 
	 * @return the element at index {@code 14} or row {@code 2} and column {@code 4}
	 */
	public float getElement24() {
		return this.element24;
	}
	
	/**
	 * Returns the element at index {@code 15} or row {@code 3} and column {@code 0}.
	 * 
	 * @return the element at index {@code 15} or row {@code 3} and column {@code 0}
	 */
	public float getElement30() {
		return this.element30;
	}
	
	/**
	 * Returns the element at index {@code 16} or row {@code 3} and column {@code 1}.
	 * 
	 * @return the element at index {@code 16} or row {@code 3} and column {@code 1}
	 */
	public float getElement31() {
		return this.element31;
	}
	
	/**
	 * Returns the element at index {@code 17} or row {@code 3} and column {@code 2}.
	 * 
	 * @return the element at index {@code 17} or row {@code 3} and column {@code 2}
	 */
	public float getElement32() {
		return this.element32;
	}
	
	/**
	 * Returns the element at index {@code 18} or row {@code 3} and column {@code 3}.
	 * 
	 * @return the element at index {@code 18} or row {@code 3} and column {@code 3}
	 */
	public float getElement33() {
		return this.element33;
	}
	
	/**
	 * Returns the element at index {@code 19} or row {@code 3} and column {@code 4}.
	 * 
	 * @return the element at index {@code 19} or row {@code 3} and column {@code 4}
	 */
	public float getElement34() {
		return this.element34;
	}
	
	/**
	 * Returns the element at index {@code 20} or row {@code 4} and column {@code 0}.
	 * 
	 * @return the element at index {@code 20} or row {@code 4} and column {@code 0}
	 */
	public float getElement40() {
		return this.element40;
	}
	
	/**
	 * Returns the element at index {@code 21} or row {@code 4} and column {@code 1}.
	 * 
	 * @return the element at index {@code 21} or row {@code 4} and column {@code 1}
	 */
	public float getElement41() {
		return this.element41;
	}
	
	/**
	 * Returns the element at index {@code 22} or row {@code 4} and column {@code 2}.
	 * 
	 * @return the element at index {@code 22} or row {@code 4} and column {@code 2}
	 */
	public float getElement42() {
		return this.element42;
	}
	
	/**
	 * Returns the element at index {@code 23} or row {@code 4} and column {@code 3}.
	 * 
	 * @return the element at index {@code 23} or row {@code 4} and column {@code 3}
	 */
	public float getElement43() {
		return this.element43;
	}
	
	/**
	 * Returns the element at index {@code 24} or row {@code 4} and column {@code 4}.
	 * 
	 * @return the element at index {@code 24} or row {@code 4} and column {@code 4}
	 */
	public float getElement44() {
		return this.element44;
	}
	
	/**
	 * Returns the factor associated with this {@code ConvolutionKernel55} instance.
	 * <p>
	 * This is the same as the reciprocal value of the Divisor in Gimp.
	 * 
	 * @return the factor associated with this {@code ConvolutionKernel55} instance
	 */
	public float getFactor() {
		return this.factor;
	}
	
	/**
	 * Returns a hash code for this {@code ConvolutionKernel55} instance.
	 * 
	 * @return a hash code for this {@code ConvolutionKernel55} instance
	 */
	@Override
	public int hashCode() {
		final Float bias = Float.valueOf(this.bias);
		
		final Float element00 = Float.valueOf(this.element00);
		final Float element01 = Float.valueOf(this.element01);
		final Float element02 = Float.valueOf(this.element02);
		final Float element03 = Float.valueOf(this.element03);
		final Float element04 = Float.valueOf(this.element04);
		
		final Float element10 = Float.valueOf(this.element10);
		final Float element11 = Float.valueOf(this.element11);
		final Float element12 = Float.valueOf(this.element12);
		final Float element13 = Float.valueOf(this.element13);
		final Float element14 = Float.valueOf(this.element14);
		
		final Float element20 = Float.valueOf(this.element20);
		final Float element21 = Float.valueOf(this.element21);
		final Float element22 = Float.valueOf(this.element22);
		final Float element23 = Float.valueOf(this.element23);
		final Float element24 = Float.valueOf(this.element24);
		
		final Float element30 = Float.valueOf(this.element30);
		final Float element31 = Float.valueOf(this.element31);
		final Float element32 = Float.valueOf(this.element32);
		final Float element33 = Float.valueOf(this.element33);
		final Float element34 = Float.valueOf(this.element34);
		
		final Float element40 = Float.valueOf(this.element40);
		final Float element41 = Float.valueOf(this.element41);
		final Float element42 = Float.valueOf(this.element42);
		final Float element43 = Float.valueOf(this.element43);
		final Float element44 = Float.valueOf(this.element44);
		
		final Float factor = Float.valueOf(this.factor);
		
		return Objects.hash(bias, element00, element01, element02, element03, element04, element10, element11, element12, element13, element14, element20, element21, element22, element23, element24, element30, element31, element32, element33, element34, element40, element41, element42, element43, element44, factor);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code ConvolutionKernel55} instance that performs a random effect.
	 * 
	 * @return a {@code ConvolutionKernel55} instance that performs a random effect
	 */
	public static ConvolutionKernel55 random() {
		final float element00 = doNextFloat() * 2.0F - 1.0F;
		final float element01 = doNextFloat() * 2.0F - 1.0F;
		final float element02 = doNextFloat() * 2.0F - 1.0F;
		final float element03 = doNextFloat() * 2.0F - 1.0F;
		final float element04 = doNextFloat() * 2.0F - 1.0F;
		final float element10 = doNextFloat() * 2.0F - 1.0F;
		final float element11 = doNextFloat() * 2.0F - 1.0F;
		final float element12 = doNextFloat() * 2.0F - 1.0F;
		final float element13 = doNextFloat() * 2.0F - 1.0F;
		final float element14 = doNextFloat() * 2.0F - 1.0F;
		final float element20 = doNextFloat() * 2.0F - 1.0F;
		final float element21 = doNextFloat() * 2.0F - 1.0F;
		final float element22 = 1.0F;
		final float element23 = doNextFloat() * 2.0F - 1.0F;
		final float element24 = doNextFloat() * 2.0F - 1.0F;
		final float element30 = doNextFloat() * 2.0F - 1.0F;
		final float element31 = doNextFloat() * 2.0F - 1.0F;
		final float element32 = doNextFloat() * 2.0F - 1.0F;
		final float element33 = doNextFloat() * 2.0F - 1.0F;
		final float element34 = doNextFloat() * 2.0F - 1.0F;
		final float element40 = doNextFloat() * 2.0F - 1.0F;
		final float element41 = doNextFloat() * 2.0F - 1.0F;
		final float element42 = doNextFloat() * 2.0F - 1.0F;
		final float element43 = doNextFloat() * 2.0F - 1.0F;
		final float element44 = doNextFloat() * 2.0F - 1.0F;
		
		final float elementTotal = element00 + element01 + element02 + element03 + element04 + element10 + element11 + element12 + element13 + element14 + element20 + element21 + element22 + element23 + element24 + element30 + element31 + element32 + element33 + element34 + element40 + element41 + element42 + element43 + element44;
		
		final boolean isFactorBasedOnElementTotal = doNextBoolean();
		final boolean isFactorBasedOnRandomFloat = doNextBoolean();
		final boolean isBiasBasedOnRandomFloat = doNextBoolean();
		
		final float factorBasedOnElementTotal = Float.compare(elementTotal, 0.0F) == 0 ? 1.0F : 1.0F / elementTotal;
		
		final float factor = isFactorBasedOnElementTotal ? factorBasedOnElementTotal : isFactorBasedOnRandomFloat ? doNextFloat() : 1.0F;
		final float bias = isBiasBasedOnRandomFloat ? doNextFloat() : 0.0F;
		
		return new ConvolutionKernel55(element00, element01, element02, element03, element04, element10, element11, element12, element13, element14, element20, element21, element22, element23, element24, element30, element31, element32, element33, element34, element40, element41, element42, element43, element44, factor, bias);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean doNextBoolean() {
		return ThreadLocalRandom.current().nextBoolean();
	}
	
	private static float doNextFloat() {
		return ThreadLocalRandom.current().nextFloat();
	}
}