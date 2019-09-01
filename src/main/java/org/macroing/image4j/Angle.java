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

import static org.macroing.image4j.Floats.PI_MULTIPLIED_BY_TWO;
import static org.macroing.image4j.Floats.max;
import static org.macroing.image4j.Floats.min;
import static org.macroing.image4j.Floats.toDegrees;
import static org.macroing.image4j.Floats.toRadians;
import static org.macroing.image4j.Floats.wrapAround;

import java.util.Objects;

/**
 * An {@code Angle} encapsulates angles in forms such as degrees and radians.
 * <p>
 * This class is immutable and therefore thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Angle {
	private static final float DEGREES_MAXIMUM = 360.0F;
	private static final float DEGREES_MINIMUM = 0.0F;
	private static final float RADIANS_MAXIMUM = PI_MULTIPLIED_BY_TWO;
	private static final float RADIANS_MINIMUM = 0.0F;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * The angle in degrees.
	 */
	public final float degrees;
	
	/**
	 * The maximum angle in degrees.
	 */
	public final float degreesMaximum;
	
	/**
	 * The minimum angle in degrees.
	 */
	public final float degreesMinimum;
	
	/**
	 * The angle in radians.
	 */
	public final float radians;
	
	/**
	 * The maximum angle in radians.
	 */
	public final float radiansMaximum;
	
	/**
	 * The minimum angle in radians.
	 */
	public final float radiansMinimum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Angle(final float degrees, final float degreesMinimum, final float degreesMaximum, final float radians, final float radiansMinimum, final float radiansMaximum) {
		this.degrees = degrees;
		this.degreesMinimum = degreesMinimum;
		this.degreesMaximum = degreesMaximum;
		this.radians = radians;
		this.radiansMinimum = radiansMinimum;
		this.radiansMaximum = radiansMaximum;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Adds {@code angle} to this {@code Angle} instance.
	 * <p>
	 * Returns a new {@code Angle} instance with the result of the addition.
	 * <p>
	 * If {@code angle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param angle the {@code Angle} to add
	 * @return a new {@code Angle} instance with the result of the addition
	 * @throws NullPointerException thrown if, and only if, {@code angle} is {@code null}
	 */
	public Angle add(final Angle angle) {
		final float degreesMinimum = this.degreesMinimum;
		final float degreesMaximum = this.degreesMaximum;
		final float degrees = wrapAround(this.degrees + angle.degrees, degreesMinimum, degreesMaximum);
		
		return degrees(degrees, degreesMinimum, degreesMaximum);
	}
	
	/**
	 * Returns a new {@code Angle} instance that represents half of this {@code Angle} instance.
	 * 
	 * @return a new {@code Angle} instance that represents half of this {@code Angle} instance
	 */
	public Angle half() {
		final float degreesMinimum = this.degreesMinimum;
		final float degreesMaximum = this.degreesMaximum;
		final float degrees = wrapAround(this.degrees * 0.5F, degreesMinimum, degreesMaximum);
		
		return degrees(degrees, degreesMinimum, degreesMaximum);
	}
	
	/**
	 * Subtracts {@code angle} from this {@code Angle} instance.
	 * <p>
	 * Returns a new {@code Angle} instance with the result of the subtraction.
	 * <p>
	 * If {@code angle} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param angle the {@code Angle} to subtract
	 * @return a new {@code Angle} instance with the result of the subtraction
	 * @throws NullPointerException thrown if, and only if, {@code angle} is {@code null}
	 */
	public Angle subtract(final Angle angle) {
		final float degreesMinimum = this.degreesMinimum;
		final float degreesMaximum = this.degreesMaximum;
		final float degrees = wrapAround(this.degrees - angle.degrees, degreesMinimum, degreesMaximum);
		
		return degrees(degrees, degreesMinimum, degreesMaximum);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Angle} instance.
	 * 
	 * @return a {@code String} representation of this {@code Angle} instance
	 */
	@Override
	public String toString() {
		return String.format("Angle.degrees(%s, %s, %s)", Strings.toNonScientificNotation(this.degrees), Strings.toNonScientificNotation(this.degreesMinimum), Strings.toNonScientificNotation(this.degreesMaximum));
	}
	
	/**
	 * Compares {@code object} to this {@code Angle} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Angle}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Angle} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Angle}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Angle)) {
			return false;
		} else if(Float.compare(this.degrees, Angle.class.cast(object).degrees) != 0) {
			return false;
		} else if(Float.compare(this.degreesMaximum, Angle.class.cast(object).degreesMaximum) != 0) {
			return false;
		} else if(Float.compare(this.degreesMinimum, Angle.class.cast(object).degreesMinimum) != 0) {
			return false;
		} else if(Float.compare(this.radians, Angle.class.cast(object).radians) != 0) {
			return false;
		} else if(Float.compare(this.radiansMaximum, Angle.class.cast(object).radiansMaximum) != 0) {
			return false;
		} else if(Float.compare(this.radiansMinimum, Angle.class.cast(object).radiansMinimum) != 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the angle in degrees.
	 * 
	 * @return the angle in degrees
	 */
	public float getDegrees() {
		return this.degrees;
	}
	
	/**
	 * Returns the maximum angle in degrees.
	 * 
	 * @return the maximum angle in degrees
	 */
	public float getDegreesMaximum() {
		return this.degreesMaximum;
	}
	
	/**
	 * Returns the minimum angle in degrees.
	 * 
	 * @return the minimum angle in degrees
	 */
	public float getDegreesMinimum() {
		return this.degreesMinimum;
	}
	
	/**
	 * Returns the angle in radians.
	 * 
	 * @return the angle in radians
	 */
	public float getRadians() {
		return this.radians;
	}
	
	/**
	 * Returns the maximum angle in radians.
	 * 
	 * @return the maximum angle in radians
	 */
	public float getRadiansMaximum() {
		return this.radiansMaximum;
	}
	
	/**
	 * Returns the minimum angle in radians.
	 * 
	 * @return the minimum angle in radians
	 */
	public float getRadiansMinimum() {
		return this.radiansMinimum;
	}
	
	/**
	 * Returns a hash code for this {@code Angle} instance.
	 * 
	 * @return a hash code for this {@code Angle} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Float.valueOf(this.degrees), Float.valueOf(this.degreesMaximum), Float.valueOf(this.degreesMinimum), Float.valueOf(this.radians), Float.valueOf(this.radiansMaximum), Float.valueOf(this.radiansMinimum));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code Angle} instance based on an angle in degrees.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Angle.degrees(degrees, 0.0F, 360.0F)
	 * }
	 * </pre>
	 * 
	 * @param degrees the angle in degrees
	 * @return a new {@code Angle} instance based on an angle in degrees
	 */
	public static Angle degrees(final float degrees) {
		return degrees(degrees, DEGREES_MINIMUM, DEGREES_MAXIMUM);
	}
	
	/**
	 * Returns a new {@code Angle} instance based on an angle in degrees and an interval of valid degrees.
	 * 
	 * @param degrees the angle in degrees
	 * @param degreesA the degrees that represents one of the ends of the interval of valid degrees
	 * @param degreesB the degrees that represents one of the ends of the interval of valid degrees
	 * @return a new {@code Angle} instance based on an angle in degrees and an interval of valid degrees
	 */
	public static Angle degrees(final float degrees, final float degreesA, final float degreesB) {
		final float newDegreesMinimum = min(degreesA, degreesB);
		final float newDegreesMaximum = max(degreesA, degreesB);
		final float newDegrees = wrapAround(degrees, newDegreesMinimum, newDegreesMaximum);
		
		final float newRadians = toRadians(newDegrees);
		final float newRadiansMinimum = toRadians(newDegreesMinimum);
		final float newRadiansMaximum = toRadians(newDegreesMaximum);
		
		return new Angle(newDegrees, newDegreesMinimum, newDegreesMaximum, newRadians, newRadiansMinimum, newRadiansMaximum);
	}
	
	/**
	 * Returns a new {@code Angle} instance based on an angle in radians.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Angle.radians(radians, 0.0F, PI * 2.0F)
	 * }
	 * </pre>
	 * 
	 * @param radians the angle in radians
	 * @return a new {@code Angle} instance based on an angle in radians
	 */
	public static Angle radians(final float radians) {
		return radians(radians, RADIANS_MINIMUM, RADIANS_MAXIMUM);
	}
	
	/**
	 * Returns a new {@code Angle} instance based on an angle in radians and an interval of valid radians.
	 * 
	 * @param radians the angle in radians
	 * @param radiansA the radians that represents one of the ends of the interval of valid radians
	 * @param radiansB the radians that represents one of the ends of the interval of valid radians
	 * @return a new {@code Angle} instance based on an angle in radians and an interval of valid radians
	 */
	public static Angle radians(final float radians, final float radiansA, final float radiansB) {
		final float newRadiansMinimum = min(radiansA, radiansB);
		final float newRadiansMaximum = max(radiansA, radiansB);
		final float newRadians = wrapAround(radians, newRadiansMinimum, newRadiansMaximum);
		
		final float newDegrees = toDegrees(newRadians);
		final float newDegreesMinimum = toDegrees(newRadiansMinimum);
		final float newDegreesMaximum = toDegrees(newRadiansMaximum);
		
		return new Angle(newDegrees, newDegreesMinimum, newDegreesMaximum, newRadians, newRadiansMinimum, newRadiansMaximum);
	}
}