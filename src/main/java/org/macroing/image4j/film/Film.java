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
package org.macroing.image4j.film;

import static org.macroing.math4j.MathF.abs;
import static org.macroing.math4j.MathF.ceil;
import static org.macroing.math4j.MathF.floor;
import static org.macroing.math4j.MathF.max;
import static org.macroing.math4j.MathF.min;
import static org.macroing.math4j.MathI.min;
import static org.macroing.math4j.MathI.toInt;

import java.util.Objects;

import org.macroing.image4j.Color;
import org.macroing.image4j.Image;
import org.macroing.image4j.XYZColor;
import org.macroing.image4j.filter.Filter;
import org.macroing.image4j.filter.MitchellFilter;
import org.macroing.math4j.MathF;

/**
 * A {@code Film} represents a camera film that can collect color samples and render them to an {@link Image} instance.
 * <p>
 * Almost all methods in this class will check if the resolution of its associated {@code Image} instance has changed. If it has changed, the resolution of the {@code Film} instance will be updated. This update will also clear the {@code Film}
 * instance, which means that all color samples that have been collected will be discarded.
 * <p>
 * When you update the resolution of the {@code Image} instance, you can update the resolution of the {@code Film} instance manually by calling {@link #setImage(Image)}. This will also clear the {@code Film} instance. If you only want to clear the
 * {@code Film} instance, you can call {@link #clear()}.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Film {
	private final static int FILTER_TABLE_SIZE = 16;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Filter filter;
	private Image image;
	private Pixel[] pixels;
	private float[] filterTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Film} instance.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Film(800, 800);
	 * }
	 * </pre>
	 */
	public Film() {
		this(800, 800);
	}
	
	/**
	 * Constructs a new {@code Film} instance given {@code image}.
	 * <p>
	 * If {@code image} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Film(image, new MitchellFilter());
	 * }
	 * </pre>
	 * 
	 * @param image an {@link Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code image} is {@code null}
	 */
	public Film(final Image image) {
		this(image, new MitchellFilter());
	}
	
	/**
	 * Constructs a new {@code Film} instance given {@code image} and {@code filter}.
	 * <p>
	 * If either {@code image} or {@code filter} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param image an {@link Image} instance
	 * @param filter a {@link Filter} instance
	 * @throws NullPointerException thrown if, and only if, either {@code image} or {@code filter} are {@code null}
	 */
	public Film(final Image image, final Filter filter) {
		setFilter(filter);
		setImage(image);
	}
	
	/**
	 * Constructs a new {@code Film} instance given {@code resolutionX} and {@code resolutionY}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an IllegalArgumentException will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Film(resolutionX, resolutionY, new MitchellFilter());
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 */
	public Film(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, new MitchellFilter());
	}
	
	/**
	 * Constructs a new {@code Film} instance given {@code resolutionX}, {@code resolutionY} and {@code filter}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an IllegalArgumentException will be thrown.
	 * <p>
	 * If {@code filter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Film(new Image(resolutionX, resolutionY), filter);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param filter a {@link Filter} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, either {@code filter} is {@code null}
	 */
	public Film(final int resolutionX, final int resolutionY, final Filter filter) {
		this(new Image(resolutionX, resolutionY), filter);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link Image} used by this {@code Film} instance.
	 * 
	 * @return the {@code Image} used by this {@code Film} instance
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Returns the resolution of the X-axis of this {@code Film} instance.
	 * <p>
	 * The resolution of the X-axis is also known as the width.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * film.getImage().getResolutionX();
	 * }
	 * </pre>
	 * 
	 * @return the resolution of the X-axis of this {@code Film} instance
	 */
	public int getResolutionX() {
		return this.image.getResolutionX();
	}
	
	/**
	 * Returns the resolution of the Y-axis of this {@code Film} instance.
	 * <p>
	 * The resolution of the Y-axis is also known as the height.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * film.getImage().getResolutionY();
	 * }
	 * </pre>
	 * 
	 * @return the resolution of the Y-axis of this {@code Film} instance
	 */
	public int getResolutionY() {
		return this.image.getResolutionY();
	}
	
	/**
	 * Adds {@code color} to the pixel represented by {@code x} and {@code y}, but only if {@code x} and {@code y} are inside the boundaries of this {@code Film} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will check if the resolution of the associated {@link Image} instance has changed. If it has changed, the resolution of this {@code Film} instance will be updated. This update will also clear this {@code Film} instance, which means
	 * that all color samples that have been collected will be discarded.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * film.add(x, y, color, 1.0F);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param color the {@link XYZColor} to add
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void add(final float x, final float y, final XYZColor color) {
		add(x, y, color, 1.0F);
	}
	
	/**
	 * Adds {@code color} to the pixel represented by {@code x} and {@code y}, but only if {@code x} and {@code y} are inside the boundaries of this {@code Film} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will check if the resolution of the associated {@link Image} instance has changed. If it has changed, the resolution of this {@code Film} instance will be updated. This update will also clear this {@code Film} instance, which means
	 * that all color samples that have been collected will be discarded.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param color the {@link XYZColor} to add
	 * @param sampleWeight the sample weight
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void add(final float x, final float y, final XYZColor color, final float sampleWeight) {
		doUpdateVariablesIfImageHasChanged();
		
		Objects.requireNonNull(color, "color == null");
		
		final Filter filter = this.filter;
		
		final Pixel[] pixels = this.pixels;
		
		final float[] filterTable = this.filterTable;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		final float filterResolutionX = filter.getResolutionX();
		final float filterResolutionY = filter.getResolutionY();
		
		final float filterResolutionXReciprocal = filter.getResolutionXReciprocal();
		final float filterResolutionYReciprocal = filter.getResolutionYReciprocal();
		
		final float deltaX = x - 0.5F;
		final float deltaY = y - 0.5F;
		
		final int minimumFilterX = toInt(max(ceil(deltaX - filterResolutionX), 0));
		final int maximumFilterX = toInt(min(floor(deltaX + filterResolutionX), resolutionX - 1));
		final int minimumFilterY = toInt(max(ceil(deltaY - filterResolutionY), 0));
		final int maximumFilterY = toInt(min(floor(deltaY + filterResolutionY), resolutionY - 1));
		
		if(maximumFilterX - minimumFilterX >= 0 && maximumFilterY - minimumFilterY >= 0) {
			final int[] filterOffsetX = new int[maximumFilterX - minimumFilterX + 1];
			final int[] filterOffsetY = new int[maximumFilterY - minimumFilterY + 1];
			
			for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
				filterOffsetX[filterX - minimumFilterX] = min(toInt(floor(abs((filterX - deltaX) * filterResolutionXReciprocal * FILTER_TABLE_SIZE))), FILTER_TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				filterOffsetY[filterY - minimumFilterY] = min(toInt(floor(abs((filterY - deltaY) * filterResolutionYReciprocal * FILTER_TABLE_SIZE))), FILTER_TABLE_SIZE - 1);
			}
			
			for(int filterY = minimumFilterY; filterY <= maximumFilterY; filterY++) {
				for(int filterX = minimumFilterX; filterX <= maximumFilterX; filterX++) {
					final int filterTableOffset = filterOffsetY[filterY - minimumFilterY] * FILTER_TABLE_SIZE + filterOffsetX[filterX - minimumFilterX];
					final int pixelOffset = filterY * resolutionX + filterX;
					
					final float filterWeight = filterTable[filterTableOffset];
					
					final
					Pixel pixel = pixels[pixelOffset];
					pixel.setColor(pixel.getColor().add(color.multiply(sampleWeight).multiply(filterWeight)));
					pixel.setFilterWeightSum(pixel.getFilterWeightSum() + filterWeight);
				}
			}
		}
	}
	
	/**
	 * Clears this {@code Film} instance, which means that all color samples that have been collected will be discarded.
	 * <p>
	 * This method will check if the resolution of the associated {@link Image} instance has changed. If it has changed, the resolution of this {@code Film} instance will be updated. This update will also clear this {@code Film} instance, which means
	 * that all color samples that have been collected will be discarded.
	 */
	public void clear() {
		doUpdateVariablesIfImageHasChanged();
		
		for(final Pixel pixel : this.pixels) {
			pixel.clear();
		}
	}
	
	/**
	 * Renders the color samples collected by this {@code Film} instance to the associated {@link Image} instance.
	 * <p>
	 * This method will check if the resolution of the associated {@link Image} instance has changed. If it has changed, the resolution of this {@code Film} instance will be updated. This update will also clear this {@code Film} instance, which means
	 * that all color samples that have been collected will be discarded.
	 * 
	 * @param splatScale the splat scale to use
	 */
	public void renderToImage(final float splatScale) {
		doUpdateVariablesIfImageHasChanged();
		
		final Image image = this.image;
		
		final Pixel[] pixels = this.pixels;
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		for(int y = 0; y < resolutionY; y++) {
			for(int x = 0; x < resolutionX; x++) {
				final Pixel pixel = pixels[y * resolutionX + x];
				
				final float filterWeightSum = pixel.getFilterWeightSum();
				
				final XYZColor colorXYZ = pixel.getColor();
				final XYZColor splatXYZ = pixel.getSplat();
				
				Color colorRGB = colorXYZ.toColor();
				Color splatRGB = splatXYZ.toColor();
				
				if(!MathF.equals(filterWeightSum, 0.0F)) {
					final float filterWeightSumReciprocal = 1.0F / filterWeightSum;
					
					final float r = max(0.0F, colorRGB.r * filterWeightSumReciprocal);
					final float g = max(0.0F, colorRGB.g * filterWeightSumReciprocal);
					final float b = max(0.0F, colorRGB.b * filterWeightSumReciprocal);
					
					colorRGB = new Color(r, g, b);
				}
				
				colorRGB = colorRGB.add(splatRGB.multiply(splatScale));
				
				image.setColor(x, y, colorRGB.redoGammaCorrectionPBRT());
			}
		}
	}
	
	/**
	 * Sets the {@link Filter} of this {@code Film} instance to {@code filter}.
	 * <p>
	 * If {@code filter} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param filter the new {@code Filter} instance
	 * @throws NullPointerException thrown if, and only if, {@code filter} is {@code null}
	 */
	public void setFilter(final Filter filter) {
		this.filter = Objects.requireNonNull(filter, "filter == null");
		this.filterTable = doCreateFilterTable(filter);
	}
	
	/**
	 * Sets the associated {@link Image} of this {@code Film} instance to {@code image}.
	 * <p>
	 * If {@code image} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code image} is already associated with this {@code Film} instance and its resolution has not changed, nothing will happen. Otherwise the resolution of this {@code Film} instance will be updated. This update will clear this {@code Film}
	 * instance, which means that all color samples that have been collected will be discarded.
	 * 
	 * @param image the new {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code image} is {@code null}
	 */
	public void setImage(final Image image) {
		if(this.image != image || this.pixels == null || this.pixels.length != image.getResolution()) {
			this.image = image;
			this.pixels = new Pixel[image.getResolution()];
			
			for(int i = 0; i < this.pixels.length; i++) {
				this.pixels[i] = new Pixel();
			}
		}
	}
	
	/**
	 * Splats {@code color} on the pixel represented by {@code x} and {@code y}, but only if {@code x} and {@code y} are inside the boundaries of this {@code Film} instance.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will check if the resolution of the associated {@link Image} instance has changed. If it has changed, the resolution of this {@code Film} instance will be updated. This update will also clear this {@code Film} instance, which means
	 * that all color samples that have been collected will be discarded.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param color the {@link XYZColor} to splat
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void splat(final float x, final float y, final XYZColor color) {
		doUpdateVariablesIfImageHasChanged();
		
		Objects.requireNonNull(color, "color == null");
		
		final Pixel[] pixels = this.pixels;
		
		final int currentX = toInt(floor(x));
		final int currentY = toInt(floor(y));
		
		final int resolutionX = getResolutionX();
		final int resolutionY = getResolutionY();
		
		if(currentX >= 0 && currentX < resolutionX && currentY >= 0 && currentY < resolutionY) {
			final int pixelOffset = currentY * resolutionX + currentX;
			
			final
			Pixel pixel = pixels[pixelOffset];
			pixel.setSplat(pixel.getSplat().add(color));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void doUpdateVariablesIfImageHasChanged() {
		final Image image = this.image;
		
		final Pixel[] pixels = this.pixels;
		
		if(pixels == null || pixels.length != image.getResolution()) {
			setImage(image);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static float[] doCreateFilterTable(final Filter filter) {
		final float filterResolutionX = filter.getResolutionX();
		final float filterResolutionY = filter.getResolutionY();
		final float filterTableSizeReciprocal = 1.0F / FILTER_TABLE_SIZE;
		
		final float[] filterTable = new float[FILTER_TABLE_SIZE * FILTER_TABLE_SIZE];
		
		for(int i = 0, y = 0; y < FILTER_TABLE_SIZE; y++) {
			for(int x = 0; x < FILTER_TABLE_SIZE; x++) {
				final float filterX = (x + 0.5F) * filterResolutionX * filterTableSizeReciprocal;
				final float filterY = (y + 0.5F) * filterResolutionY * filterTableSizeReciprocal;
				
				filterTable[i++] = filter.evaluate(filterX, filterY);
			}
		}
		
		return filterTable;
	}
}