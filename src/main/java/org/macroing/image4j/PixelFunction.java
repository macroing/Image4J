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
package org.macroing.image4j;

import static org.macroing.math4j.MathF.max;
import static org.macroing.math4j.MathF.min;

import java.util.Objects;

import org.macroing.math4j.NoiseGeneratorF;

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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code PixelFunction} that computes and returns {@link Color}s interpolated with Barycentric coordinates.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * PixelFunction.barycentricInterpolation(aX, aY, bX, bY, cX, cY, Color.RED, Color.GREEN, Color.BLUE);
	 * }
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @return a new {@code PixelFunction} that computes and returns {@code Color}s interpolated with Barycentric coordinates
	 */
	static PixelFunction barycentricInterpolation(final float aX, final float aY, final float bX, final float bY, final float cX, final float cY) {
		return barycentricInterpolation(aX, aY, bX, bY, cX, cY, Color.RED, Color.GREEN, Color.BLUE);
	}
	
	/**
	 * Returns a new {@code PixelFunction} that computes and returns {@link Color}s interpolated with Barycentric coordinates.
	 * <p>
	 * If either {@code colorA}, {@code colorB} or {@code colorC} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param colorA the {@code Color} of the point {@code A}
	 * @param colorB the {@code Color} of the point {@code B}
	 * @param colorC the {@code Color} of the point {@code C}
	 * @return a new {@code PixelFunction} that computes and returns {@code Color}s interpolated with Barycentric coordinates
	 * @throws NullPointerException thrown if, and only if, either {@code colorA}, {@code colorB} or {@code colorC} are {@code null}
	 */
	static PixelFunction barycentricInterpolation(final float aX, final float aY, final float bX, final float bY, final float cX, final float cY, final Color colorA, final Color colorB, final Color colorC) {
		Objects.requireNonNull(colorA, "colorA == null");
		Objects.requireNonNull(colorB, "colorB == null");
		Objects.requireNonNull(colorC, "colorC == null");
		
		return (x, y, oldColor) -> {
			final float[] barycentricCoordinates = Geometry.barycentricCoordinates(aX, aY, bX, bY, cX, cY, x, y);
			
			final float u = barycentricCoordinates[0];
			final float v = barycentricCoordinates[1];
			final float w = barycentricCoordinates[2];
			
			return colorA.multiply(u).add(colorB.multiply(v)).add(colorC.multiply(w)).minTo0().maxTo1();
		};
	}
	
	/**
	 * Returns a new {@code PixelFunction} that returns a constant {@link Color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the constant {@code Color} to return
	 * @return a new {@code PixelFunction} that returns a constant {@code Color}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	static PixelFunction constant(final Color color) {
		Objects.requireNonNull(color, "color == null");
		
		return (x, y, oldColor) -> color;
	}
	
	/**
	 * Returns a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * PixelFunction.simplexFractionalBrownianMotion(color, 800.0F, 800.0F);
	 * }
	 * </pre>
	 * 
	 * @param color the base {@link Color}
	 * @return a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	static PixelFunction simplexFractionalBrownianMotion(final Color color) {
		return simplexFractionalBrownianMotion(color, 800.0F, 800.0F);
	}
	
	/**
	 * Returns a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * PixelFunction.simplexFractionalBrownianMotion(color, edgeAX, edgeAY, 0.0F, 0.0F);
	 * }
	 * </pre>
	 * 
	 * @param color the base {@link Color}
	 * @param edgeAX the minimum or maximum X-coordinate
	 * @param edgeAY the minimum or maximum Y-coordinate
	 * @return a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	static PixelFunction simplexFractionalBrownianMotion(final Color color, final float edgeAX, final float edgeAY) {
		return simplexFractionalBrownianMotion(color, edgeAX, edgeAY, 0.0F, 0.0F);
	}
	
	/**
	 * Returns a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * PixelFunction.simplexFractionalBrownianMotion(color, edgeAX, edgeAY, edgeBX, edgeBY, 5.0F, 0.5F, 16);
	 * }
	 * </pre>
	 * 
	 * @param color the base {@link Color}
	 * @param edgeAX the minimum or maximum X-coordinate
	 * @param edgeAY the minimum or maximum Y-coordinate
	 * @param edgeBX the maximum or minimum X-coordinate
	 * @param edgeBY the maximum or minimum Y-coordinate
	 * @return a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	static PixelFunction simplexFractionalBrownianMotion(final Color color, final float edgeAX, final float edgeAY, final float edgeBX, final float edgeBY) {
		return simplexFractionalBrownianMotion(color, edgeAX, edgeAY, edgeBX, edgeBY, 5.0F, 0.5F, 16);
	}
	
	/**
	 * Returns a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the base {@link Color}
	 * @param edgeAX the minimum or maximum X-coordinate
	 * @param edgeAY the minimum or maximum Y-coordinate
	 * @param edgeBX the maximum or minimum X-coordinate
	 * @param edgeBY the maximum or minimum Y-coordinate
	 * @param frequency the frequency to use
	 * @param gain the gain to use
	 * @param octaves the octaves to use
	 * @return a new {@code PixelFunction} that returns {@code color} multiplies by Simplex fractional Brownian motion (fBm) noise
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	static PixelFunction simplexFractionalBrownianMotion(final Color color, final float edgeAX, final float edgeAY, final float edgeBX, final float edgeBY, final float frequency, final float gain, final int octaves) {
		Objects.requireNonNull(color, "color == null");
		
		final float minimumX = min(edgeAX, edgeBX);
		final float minimumY = min(edgeAY, edgeBY);
		final float maximumX = max(edgeAX, edgeBX);
		final float maximumY = max(edgeAY, edgeBY);
		
		return (x, y, oldColor) -> {
			final float noiseX = (x - minimumX) / (maximumX - minimumX);
			final float noiseY = (y - minimumY) / (maximumY - minimumY);
			
			final float noise = NoiseGeneratorF.simplexFractionalBrownianMotion(noiseX, noiseY, frequency, gain, 0.0F, 1.0F, octaves);
			
			return color.multiply(noise).minTo0().maxTo1().redoGammaCorrection();
		};
	}
}