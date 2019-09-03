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

import static org.macroing.image4j.Floats.max;
import static org.macroing.image4j.Floats.min;
import static org.macroing.image4j.Integers.max;
import static org.macroing.image4j.Integers.min;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.imageio.ImageIO;

/**
 * An {@code Image} is an image that can be drawn to.
 * <p>
 * This class stores individual colors as {@link Color} instances.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Image {
	private Color[] colors;
	private int resolution;
	private int resolutionX;
	private int resolutionY;
	private int[] sampleCounts;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code Color.BLACK}.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(800, 800);
	 * }
	 * </pre>
	 */
	public Image() {
		this(800, 800);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code Color.BLACK}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Image(resolutionX, resolutionY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 */
	public Image(final int resolutionX, final int resolutionY) {
		this(resolutionX, resolutionY, Color.BLACK);
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param color the {@link Color} to fill the {@code Image} with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color color) {
		this.resolutionX = Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		this.resolutionY = Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		this.resolution = Integers.requirePositiveIntValue(resolutionX * resolutionY, "(resolutionX * resolutionY)");
		this.colors = new Color[this.resolution];
		this.sampleCounts = new int[this.resolution];
		
		Arrays.fill(this.colors, Objects.requireNonNull(color, "color == null"));
	}
	
	/**
	 * Constructs a new {@code Image} instance filled with {@code Color}s from the array {@code colors}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0}, or {@code resolutionX * resolutionY != colors.length}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code colors} or at least one of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The array {@code colors} will be cloned.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param colors the {@link Color}s to fill the {@code Image} with
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0}, or {@code resolutionX * resolutionY != colors.length}
	 * @throws NullPointerException thrown if, and only if, either {@code colors} or at least one of its elements are {@code null}
	 */
	public Image(final int resolutionX, final int resolutionY, final Color[] colors) {
		this.resolutionX = Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		this.resolutionY = Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		this.resolution = Integers.requirePositiveIntValue(this.resolutionX * this.resolutionY, "(resolutionX * resolutionY)");
		this.colors = Arrays2.requireExactLength(Arrays2.requireDeepNonNull(colors, "colors"), this.resolution, "colors").clone();
		this.sampleCounts = new int[this.resolution];
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code BufferedImage} representation of this {@code Image} instance.
	 * 
	 * @return a {@code BufferedImage} representation of this {@code Image} instance
	 */
	public BufferedImage toBufferedImage() {
		final BufferedImage bufferedImage = new BufferedImage(this.resolutionX, this.resolutionY, BufferedImage.TYPE_INT_ARGB);
		
		final int[] dataSource = toIntArrayPackedForm();
		final int[] dataTarget = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		System.arraycopy(dataSource, 0, dataTarget, 0, dataSource.length);
		
		return bufferedImage;
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * This method performs bilinear interpolation on the four closest {@code Color}s.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor(x, y, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @return the {@code Color} of the pixel represented by {@code x} and {@code y}
	 */
	public Color getColor(final float x, final float y) {
		return getColor(x, y, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * This method performs bilinear interpolation on the four closest {@code Color}s.
	 * <p>
	 * If {@code pixelOperation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @return the {@code Color} of the pixel represented by {@code x} and {@code y}
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperation} is {@code null}
	 */
	public Color getColor(final float x, final float y, final PixelOperation pixelOperation) {
		final int minimumX = Integers.toInt(Floats.floor(x));
		final int maximumX = Integers.toInt(Floats.ceil(x));
		
		final int minimumY = Integers.toInt(Floats.floor(y));
		final int maximumY = Integers.toInt(Floats.ceil(y));
		
		if(minimumX == maximumX && minimumY == maximumY) {
			return getColor(minimumX, minimumY, pixelOperation);
		}
		
		final Color color00 = getColor(minimumX, minimumY, pixelOperation);
		final Color color01 = getColor(maximumX, minimumY, pixelOperation);
		final Color color10 = getColor(minimumX, maximumY, pixelOperation);
		final Color color11 = getColor(maximumX, maximumY, pixelOperation);
		
		final float xFactor = x - minimumX;
		final float yFactor = y - minimumY;
		
		final Color color = Color.blend(Color.blend(color00, color01, xFactor), Color.blend(color10, color11, xFactor), yFactor);
		
		return color;
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code index}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor(index, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param index the index of the pixel
	 * @return the {@code Color} of the pixel represented by {@code index}
	 */
	public Color getColor(final int index) {
		return getColor(index, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code index}.
	 * <p>
	 * If {@code pixelOperation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param index the index of the pixel
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @return the {@code Color} of the pixel represented by {@code index}
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperation} is {@code null}
	 */
	public Color getColor(final int index, final PixelOperation pixelOperation) {
		return doGetColorOrDefault(pixelOperation.getIndex(index, this.resolution), Color.BLACK);
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.getColor(x, y, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @return the {@code Color} of the pixel represented by {@code x} and {@code y}
	 */
	public Color getColor(final int x, final int y) {
		return getColor(x, y, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Returns the {@link Color} of the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * If {@code pixelOperation} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @return the {@code Color} of the pixel represented by {@code x} and {@code y}
	 * @throws NullPointerException thrown if, and only if, {@code pixelOperation} is {@code null}
	 */
	public Color getColor(final int x, final int y, final PixelOperation pixelOperation) {
		return doGetColorOrDefault(pixelOperation.getX(x, this.resolutionX), pixelOperation.getY(y, this.resolutionY), Color.BLACK);
	}
	
	/**
	 * Returns a copy of this {@code Image} instance.
	 * 
	 * @return a copy of this {@code Image} instance
	 */
	public Image copy() {
		final Image image = new Image(this.resolutionX, this.resolutionY);
		
		for(int i = 0; i < image.resolution; i++) {
			image.colors[i] = this.colors[i];
			image.sampleCounts[i] = this.sampleCounts[i];
		}
		
		return image;
	}
	
	/**
	 * Crops this {@code Image} instance.
	 * <p>
	 * Returns a new {@code Image} instance with the result of the crop operation.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.crop(aX, aY, bX, bY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param aX the minimum (inclusive) or maximum (exclusive) X-coordinate of the cropped region
	 * @param aY the minimum (inclusive) or maximum (exclusive) Y-coordinate of the cropped region
	 * @param bX the maximum (exclusive) or minimum (inclusive) X-coordinate of the cropped region
	 * @param bY the maximum (exclusive) or minimum (inclusive) Y-coordinate of the cropped region
	 * @return a new {@code Image} instance with the result of the crop operation
	 */
	public Image crop(final int aX, final int aY, final int bX, final int bY) {
		return crop(aX, aY, bX, bY, Color.BLACK);
	}
	
	/**
	 * Crops this {@code Image} instance.
	 * <p>
	 * Returns a new {@code Image} instance with the result of the crop operation.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.crop(aX, aY, bX, bY, color, false, false);
	 * }
	 * </pre>
	 * 
	 * @param aX the minimum (inclusive) or maximum (exclusive) X-coordinate of the cropped region
	 * @param aY the minimum (inclusive) or maximum (exclusive) Y-coordinate of the cropped region
	 * @param bX the maximum (exclusive) or minimum (inclusive) X-coordinate of the cropped region
	 * @param bY the maximum (exclusive) or minimum (inclusive) Y-coordinate of the cropped region
	 * @param color the {@link Color} that will be used to fill the regions of the cropped {@code Image} that are outside of the boundaries of this {@code Image} instance
	 * @return a new {@code Image} instance with the result of the crop operation
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image crop(final int aX, final int aY, final int bX, final int bY, final Color color) {
		return crop(aX, aY, bX, bY, color, false, false);
	}
	
	/**
	 * Crops this {@code Image} instance.
	 * <p>
	 * Returns a new {@code Image} instance with the result of the crop operation.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param aX the minimum (inclusive) or maximum (exclusive) X-coordinate of the cropped region
	 * @param aY the minimum (inclusive) or maximum (exclusive) Y-coordinate of the cropped region
	 * @param bX the maximum (exclusive) or minimum (inclusive) X-coordinate of the cropped region
	 * @param bY the maximum (exclusive) or minimum (inclusive) Y-coordinate of the cropped region
	 * @param color the {@link Color} that will be used to fill the regions of the cropped {@code Image} that are outside of the boundaries of this {@code Image} instance
	 * @param isRepeatingX {@code true} if, and only if, the regions of the cropped {@code Image} that are outside of the boundaries of this {@code Image} instance should be repeated along the X-axis, {@code false} otherwise
	 * @param isRepeatingY {@code true} if, and only if, the regions of the cropped {@code Image} that are outside of the boundaries of this {@code Image} instance should be repeated along the Y-axis, {@code false} otherwise
	 * @return a new {@code Image} instance with the result of the crop operation
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public Image crop(final int aX, final int aY, final int bX, final int bY, final Color color, final boolean isRepeatingX, final boolean isRepeatingY) {
		Objects.requireNonNull(color, "color == null");
		
		final Image imageA = this;
		
//		Calculate the boundaries of the region in Image A to crop - they can be smaller than, equal to or larger than the actual boundaries of Image A:
		final int imageAMinimumX = min(aX, bX);
		final int imageAMaximumX = max(aX, bX);
		final int imageAMinimumY = min(aY, bY);
		final int imageAMaximumY = max(aY, bY);
		
		final Image imageB = new Image(imageAMaximumX - imageAMinimumX, imageAMaximumY - imageAMinimumY);
		
//		Calculate the boundaries of Image B:
		final int imageBMinimumX = 0;
		final int imageBMaximumX = imageB.resolutionX;
		final int imageBMinimumY = 0;
		final int imageBMaximumY = imageB.resolutionY;
		
		if(imageAMinimumX >= 0 && imageAMaximumX <= imageA.resolutionX && imageAMinimumY >= 0 && imageAMaximumY <= imageA.resolutionY) {
//			In this case the cropped Image, Image B, is inside the original Image, Image A. This means we don't have to perform any repetition operations and therefore can use System.arraycopy(...), which is much faster.
			for(int imageAY = imageAMinimumY, imageBY = imageBMinimumY; imageAY < imageAMaximumY && imageBY < imageBMaximumY; imageAY++, imageBY++) {
				final int imageAIndex = imageAY * imageA.resolutionX + imageAMinimumX;
				final int imageBIndex = imageBY * imageB.resolutionX + imageBMinimumX;
				
				System.arraycopy(imageA.colors, imageAIndex, imageB.colors, imageBIndex, imageB.resolutionX);
				System.arraycopy(imageA.sampleCounts, imageAIndex, imageB.sampleCounts, imageBIndex, imageB.resolutionX);
			}
		} else {
//			In this case the cropped Image, Image B, is outside the original Image, Image A. This means we need to perform at least one repetition operation and therefore cannot use System.arraycopy(...), which is much faster.
			for(int imageAY = imageAMinimumY, imageBY = imageBMinimumY; imageAY < imageAMaximumY && imageBY < imageBMaximumY; imageAY++, imageBY++) {
				for(int imageAX = imageAMinimumX, imageBX = imageBMinimumX; imageAX < imageAMaximumX && imageBX < imageBMaximumX; imageAX++, imageBX++) {
					final int currentImageAX = isRepeatingX ? Integers.modulo(imageAX, imageA.resolutionX) : imageAX;
					final int currentImageAY = isRepeatingY ? Integers.modulo(imageAY, imageA.resolutionY) : imageAY;
					
					final Color currentColor = currentImageAX >= 0 && currentImageAX < imageA.resolutionX && currentImageAY >= 0 && currentImageAY < imageA.resolutionY ? imageA.colors[currentImageAY * imageA.resolutionX + currentImageAX] : color;
					
					final int currentSampleCount = currentImageAX >= 0 && currentImageAX < imageA.resolutionX && currentImageAY >= 0 && currentImageAY < imageA.resolutionY ? imageA.sampleCounts[currentImageAY * imageA.resolutionX + currentImageAX] : 1;
					
					final int imageBIndex = imageBY * imageB.resolutionX + imageBX;
					
					imageB.colors[imageBIndex] = currentColor;
					imageB.sampleCounts[imageBIndex] = currentSampleCount;
				}
			}
		}
		
		return imageB;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Image} instance.
	 * 
	 * @return a {@code String} representation of this {@code Image} instance
	 */
	@Override
	public String toString() {
		return String.format("new Image(%s, %s)", Integer.toString(this.resolutionX), Integer.toString(this.resolutionY));
	}
	
	/**
	 * Compares {@code object} to this {@code Image} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Image}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Image} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Image}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Image)) {
			return false;
		} else if(!Arrays.equals(this.colors, Image.class.cast(object).colors)) {
			return false;
		} else if(this.resolution != Image.class.cast(object).resolution) {
			return false;
		} else if(this.resolutionX != Image.class.cast(object).resolutionX) {
			return false;
		} else if(this.resolutionY != Image.class.cast(object).resolutionY) {
			return false;
		} else if(!Arrays.equals(this.sampleCounts, Image.class.cast(object).sampleCounts)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the resolution of this {@code Image} instance.
	 * <p>
	 * The resolution of {@code image} can be computed by:
	 * <pre>
	 * {@code
	 * int resolution = image.getResolutionX() * image.getResolutionY();
	 * }
	 * </pre>
	 * 
	 * @return the resolution of this {@code Image} instance
	 */
	public int getResolution() {
		return this.resolution;
	}
	
	/**
	 * Returns the resolution of the X-axis of this {@code Image} instance.
	 * <p>
	 * The resolution of the X-axis is also known as the width.
	 * 
	 * @return the resolution of the X-axis of this {@code Image} instance
	 */
	public int getResolutionX() {
		return this.resolutionX;
	}
	
	/**
	 * Returns the resolution of the Y-axis of this {@code Image} instance.
	 * <p>
	 * The resolution of the Y-axis is also known as the height.
	 * 
	 * @return the resolution of the Y-axis of this {@code Image} instance
	 */
	public int getResolutionY() {
		return this.resolutionY;
	}
	
	/**
	 * Returns a hash code for this {@code Image} instance.
	 * 
	 * @return a hash code for this {@code Image} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(Arrays.hashCode(this.colors)), Integer.valueOf(this.resolution), Integer.valueOf(this.resolutionX), Integer.valueOf(this.resolutionY), Integer.valueOf(Arrays.hashCode(this.sampleCounts)));
	}
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance in a packed form.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.toIntArrayPackedForm(PackedIntComponentOrder.ARGB);
	 * }
	 * </pre>
	 * 
	 * @return an {@code int[]} representation of this {@code Image} instance in a packed form
	 */
	public int[] toIntArrayPackedForm() {
		return toIntArrayPackedForm(PackedIntComponentOrder.ARGB);
	}
	
	/**
	 * Returns an {@code int[]} representation of this {@code Image} instance in a packed form.
	 * <p>
	 * If {@code packedIntComponentOrder} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param packedIntComponentOrder a {@link PackedIntComponentOrder}
	 * @return an {@code int[]} representation of this {@code Image} instance in a packed form
	 * @throws NullPointerException thrown if, and only if, {@code packedIntComponentOrder} is {@code null}
	 */
	public int[] toIntArrayPackedForm(final PackedIntComponentOrder packedIntComponentOrder) {
		Objects.requireNonNull(packedIntComponentOrder, "packedIntComponentOrder == null");
		
		final int[] intArray = new int[this.resolution];
		
		for(int i = 0; i < this.resolution; i++) {
			intArray[i] = this.colors[i].pack(packedIntComponentOrder);
		}
		
		return intArray;
	}
	
	/**
	 * Adds {@code color} as a sample at the pixel represented by {@code index}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.addColorSample(index, color, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param index the index of the pixel where {@code color} should be added as a sample
	 * @param color the {@link Color} to add as a sample
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void addColorSample(final int index, final Color color) {
		addColorSample(index, color, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Adds {@code color} as a sample at the pixel represented by {@code index}.
	 * <p>
	 * If either {@code color} or {@code pixelOperation} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link Color#addSample(Color, int)} to get a more detailed explanation for how this method works.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param index the index of the pixel where {@code color} should be added as a sample
	 * @param color the {@link Color} to add as a sample
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code pixelOperation} are {@code null}
	 */
	public void addColorSample(final int index, final Color color, final PixelOperation pixelOperation) {
		doAddColorSample(pixelOperation.getIndex(index, this.resolution), Objects.requireNonNull(color, "color == null"));
	}
	
	/**
	 * Adds {@code color} as a sample at the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.addColorSample(x, y, color, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the pixel where {@code color} should be added as a sample
	 * @param y the Y-coordinate of the pixel where {@code color} should be added as a sample
	 * @param color the {@link Color} to add as a sample
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void addColorSample(final int x, final int y, final Color color) {
		addColorSample(x, y, color, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Adds {@code color} as a sample at the pixel represented by {@code x} and {@code y}.
	 * <p>
	 * If either {@code color} or {@code pixelOperation} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link Color#addSample(Color, int)} to get a more detailed explanation for how this method works.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param x the X-coordinate of the pixel where {@code color} should be added as a sample
	 * @param y the Y-coordinate of the pixel where {@code color} should be added as a sample
	 * @param color the {@link Color} to add as a sample
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code pixelOperation} are {@code null}
	 */
	public void addColorSample(final int x, final int y, final Color color, final PixelOperation pixelOperation) {
		doAddColorSample(pixelOperation.getX(x, this.resolutionX), pixelOperation.getY(y, this.resolutionY), Objects.requireNonNull(color, "color == null"));
	}
	
	/**
	 * Clears this {@code Image} instance with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.clear(Color.BLACK);
	 * }
	 * </pre>
	 */
	public void clear() {
		clear(Color.BLACK);
	}
	
	/**
	 * Clears this {@code Image} instance with a {@link Color} of {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param color the {@code Color} to clear with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void clear(final Color color) {
		Objects.requireNonNull(color, "color == null");
		
		for(int i = 0; i < this.colors.length; i++) {
			this.colors[i] = color;
			this.sampleCounts[i] = 0;
		}
	}
	
	/**
	 * Copies the individual component values of the {@link Color}s in this {@code Image} instance to the {@code byte[]} {@code array}.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.copyTo(array, ArrayComponentOrder.BGRA);
	 * }
	 * </pre>
	 * 
	 * @param array the {@code byte[]} to copy the individual component values of the {@code Color}s in this {@code Image} instance to
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public void copyTo(final byte[] array) {
		copyTo(array, ArrayComponentOrder.BGRA);
	}
	
	/**
	 * Copies the individual component values of the {@link Color}s in this {@code Image} instance to the {@code byte[]} {@code array}.
	 * <p>
	 * If either {@code array} or {@code arrayComponentOrder} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param array the {@code byte[]} to copy the individual component values of the {@code Color}s in this {@code Image} instance to
	 * @param arrayComponentOrder an {@link ArrayComponentOrder} to copy the components to {@code array} in the correct order
	 * @throws IllegalArgumentException thrown if, and only if, {@code array.length != image.getResolution() * arrayComponentOrder.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code array} or {@code arrayComponentOrder} are {@code null}
	 */
	public void copyTo(final byte[] array, final ArrayComponentOrder arrayComponentOrder) {
		Objects.requireNonNull(array, "array == null");
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		Arrays2.requireExactLength(array, this.colors.length * arrayComponentOrder.getComponentCount(), "array");
		
		for(int i = 0, j = 0; i < this.colors.length; i++, j += arrayComponentOrder.getComponentCount()) {
			final Color color = this.colors[i];
			
			if(arrayComponentOrder.hasOffsetR()) {
				array[j + arrayComponentOrder.getOffsetR()] = color.getAsByteR();
			}
			
			if(arrayComponentOrder.hasOffsetG()) {
				array[j + arrayComponentOrder.getOffsetG()] = color.getAsByteG();
			}
			
			if(arrayComponentOrder.hasOffsetB()) {
				array[j + arrayComponentOrder.getOffsetB()] = color.getAsByteB();
			}
			
			if(arrayComponentOrder.hasOffsetA()) {
				array[j + arrayComponentOrder.getOffsetA()] = color.getAsByteA();
			}
		}
	}
	
	/**
	 * Draws a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawCircle(x, y, radius, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 */
	public void drawCircle(final int x, final int y, final int radius) {
		drawCircle(x, y, radius, Color.BLACK);
	}
	
	/**
	 * Draws a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawCircle(x, y, radius, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param color the {@code Color} to draw with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void drawCircle(final int x, final int y, final int radius, final Color color) {
		drawCircle(x, y, radius, PixelFunction.constant(color));
	}
	
	/**
	 * Draws a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void drawCircle(final int x, final int y, final int radius, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		for(int i = -radius; i <= radius; i++) {
			for(int j = -radius; j <= radius; j++) {
				if(j * j + i * i <= radius * radius && j * j + i * i > (radius - 1) * (radius - 1)) {
					final Color oldColor = getColor(x + j, y + i);
					final Color newColor = Objects.requireNonNull(pixelFunction.apply(x + j, y + i, oldColor));
					
					doSetColor(x + j, y + i, newColor);
				}
			}
		}
	}
	
	/**
	 * Draws {@code image} to this {@code Image} instance starting at {@code x} and {@code y}.
	 * <p>
	 * The coordinates {@code x} and {@code y} are allowed to be outside of the boundaries of this {@code Image} instance.
	 * <p>
	 * If {@code image} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method currently performs a simple alpha blending operation. See the code below. It might change in the future.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * currentImage.drawImage(x, y, image, (colorA, colorB) -> Color.blend(colorA, colorB, colorB.a));
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of this {@code Image} instance to draw to
	 * @param y the Y-coordinate of this {@code Image} instance to draw to
	 * @param image the {@code Image} to draw
	 * @throws NullPointerException thrown if, and only if, {@code image} is {@code null}
	 */
	public void drawImage(final int x, final int y, final Image image) {
		drawImage(x, y, image, (colorA, colorB) -> Color.blend(colorA, colorB, colorB.a));
	}
	
	/**
	 * Draws {@code image} to this {@code Image} instance starting at {@code x} and {@code y}.
	 * <p>
	 * The coordinates {@code x} and {@code y} are allowed to be outside of the boundaries of this {@code Image} instance.
	 * <p>
	 * In this documentation {@code colorA} refers to the {@code Color} of this {@code Image} instance and {@code colorB} refers to the {@code Color} of {@code image}.
	 * <p>
	 * If either {@code image} or {@code biFunction} are {@code null} or {@code biFunction.apply(colorA, colorB)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the X-coordinate of this {@code Image} instance to draw to
	 * @param y the Y-coordinate of this {@code Image} instance to draw to
	 * @param image the {@code Image} to draw
	 * @param biFunction a {@code BiFunction} that returns a {@code Color} based on its two parameters, the {@code Color} of this {@code Image} instance and the {@code Color} of {@code image}
	 * @throws NullPointerException thrown if, and only if, either {@code image} or {@code biFunction} are {@code null} or {@code biFunction.apply(colorA, colorB)} returns {@code null}
	 */
	public void drawImage(final int x, final int y, final Image image, final BiFunction<Color, Color, Color> biFunction) {
//		Initialize Image A and Image B, where Image A is the Image to render to and Image B is the Image to render:
		final Image imageA = this;
		final Image imageB = Objects.requireNonNull(image, "image == null");
		
		Objects.requireNonNull(biFunction, "biFunction == null");
		
//		Initialize the resolutions for Image A:
		final int imageAResolutionX = imageA.resolutionX;
		final int imageAResolutionY = imageA.resolutionY;
		
//		Initialize the resolutions for Image B:
		final int imageBResolutionX = imageB.resolutionX;
		final int imageBResolutionY = imageB.resolutionY;
		
//		Calculate the bounding boxes:
		final int[][] boundingBoxes = Geometry.calculateRectangleBoundingBoxes(0, 0, imageAResolutionX, imageAResolutionY, x, y, imageBResolutionX, imageBResolutionY);
		
//		Check that Image A and Image B overlaps:
		if(boundingBoxes[6].length == 4 && boundingBoxes[7].length == 4) {
//			Retrieve the minimum and maximum X- and Y-coordinates for Image A:
			final int imageAMinimumX = boundingBoxes[6][0];
			final int imageAMinimumY = boundingBoxes[6][1];
			final int imageAMaximumX = boundingBoxes[6][2];
			final int imageAMaximumY = boundingBoxes[6][3];
			
//			Retrieve the minimum and maximum X- and Y-coordinates for Image B:
			final int imageBMinimumX = boundingBoxes[7][0];
			final int imageBMinimumY = boundingBoxes[7][1];
			final int imageBMaximumX = boundingBoxes[7][2];
			final int imageBMaximumY = boundingBoxes[7][3];
			
			for(int imageAY = imageAMinimumY, imageBY = imageBMinimumY; imageAY < imageAMaximumY && imageBY < imageBMaximumY; imageAY++, imageBY++) {
				for(int imageAX = imageAMinimumX, imageBX = imageBMinimumX; imageAX < imageAMaximumX && imageBX < imageBMaximumX; imageAX++, imageBX++) {
//					Calculate the indices for Image A and Image B:
					final int imageAIndex = imageAY * imageAResolutionX + imageAX;
					final int imageBIndex = imageBY * imageBResolutionX + imageBX;
					
//					Calculate the resulting Color:
					final Color colorA = imageA.colors[imageAIndex];
					final Color colorB = imageB.colors[imageBIndex];
					final Color colorR = Objects.requireNonNull(biFunction.apply(colorA, colorB));
					
//					Retrieve the current Color and sample count from Image B and draw it to Image A:
					imageA.colors[imageAIndex] = colorR;
					imageA.sampleCounts[imageAIndex] = imageB.sampleCounts[imageBIndex];
				}
			}
		}
	}
	
	/**
	 * Draws a line from {@code startX} and {@code startY} to {@code endX} and {@code endY}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the line that are inside of boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawLine(startX, startY, endX, endY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param startX the X-coordinate to start the line at
	 * @param startY the Y-coordinate to start the line at
	 * @param endX the X-coordinate to end the line at
	 * @param endY the Y-coordinate to end the line at
	 */
	public void drawLine(final int startX, final int startY, final int endX, final int endY) {
		drawLine(startX, startY, endX, endY, Color.BLACK);
	}
	
	/**
	 * Draws a line from {@code startX} and {@code startY} to {@code endX} and {@code endY}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the line that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawLine(startX, startY, endX, endY, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param startX the X-coordinate to start the line at
	 * @param startY the Y-coordinate to start the line at
	 * @param endX the X-coordinate to end the line at
	 * @param endY the Y-coordinate to end the line at
	 * @param color the {@code Color} to draw with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void drawLine(final int startX, final int startY, final int endX, final int endY, final Color color) {
		drawLine(startX, startY, endX, endY, PixelFunction.constant(color));
	}
	
	/**
	 * Draws a line from {@code startX} and {@code startY} to {@code endX} and {@code endY}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the line that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param startX the X-coordinate to start the line at
	 * @param startY the Y-coordinate to start the line at
	 * @param endX the X-coordinate to end the line at
	 * @param endY the Y-coordinate to end the line at
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void drawLine(final int startX, final int startY, final int endX, final int endY, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		final int resolutionX = this.resolutionX;
		final int resolutionY = this.resolutionY;
		
		final int[][] scanLine = Rasterizer.rasterizeLine(startX, startY, endX, endY, resolutionX, resolutionY);
		
		for(int[] pixel : scanLine) {
			final int x = pixel[0];
			final int y = pixel[1];
			
			final Color oldColor = getColor(x, y);
			final Color newColor = Objects.requireNonNull(pixelFunction.apply(x, y, oldColor));
			
			doSetColor(x, y, newColor);
		}
	}
	
	/**
	 * Draws a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawRectangle(x, y, w, h, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 */
	public void drawRectangle(final int x, final int y, final int w, final int h) {
		drawRectangle(x, y, w, h, Color.BLACK);
	}
	
	/**
	 * Draws a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawRectangle(x, y, w, h, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 * @param color the {@code Color} to draw with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void drawRectangle(final int x, final int y, final int w, final int h, final Color color) {
		drawRectangle(x, y, w, h, PixelFunction.constant(color));
	}
	
	/**
	 * Draws a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void drawRectangle(final int x, final int y, final int w, final int h, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		for(int i = y; i < y + h; i++) {
			for(int j = x; j < x + w; j++) {
				if(i == y || i + 1 == y + h || j == x || j + 1 == x + w) {
					final Color oldColor = getColor(j, i);
					final Color newColor = Objects.requireNonNull(pixelFunction.apply(j, i, oldColor));
					
					doSetColor(j, i, newColor);
				}
			}
		}
	}
	
	/**
	 * Draws a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawTriangle(aX, aY, bX, bY, cX, cY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 */
	public void drawTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY) {
		drawTriangle(aX, aY, bX, bY, cX, cY, Color.BLACK);
	}
	
	/**
	 * Draws a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.drawTriangle(aX, aY, bX, bY, cX, cY, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param color the {@code Color} to draw with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void drawTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, final Color color) {
		drawTriangle(aX, aY, bX, bY, cX, cY, PixelFunction.constant(color));
	}
	
	/**
	 * Draws a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with the {@link Color}s {@code colorA}, {@code colorB} and {@code colorC}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If either {@code colorA}, {@code colorB} or {@code colorC} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param colorA the {@code Color} to draw line {@code A} with
	 * @param colorB the {@code Color} to draw line {@code B} with
	 * @param colorC the {@code Color} to draw line {@code C} with
	 * @throws NullPointerException thrown if, and only if, either {@code colorA}, {@code colorB} or {@code colorC} are {@code null}
	 */
	public void drawTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, final Color colorA, final Color colorB, final Color colorC) {
		drawLine(aX, aY, bX, bY, Objects.requireNonNull(colorA, "colorA == null"));
		drawLine(bX, bY, cX, cY, Objects.requireNonNull(colorB, "colorB == null"));
		drawLine(cX, cY, aX, aY, Objects.requireNonNull(colorC, "colorC == null"));
	}
	
	/**
	 * Draws a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be drawn.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void drawTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		drawLine(aX, aY, bX, bY, pixelFunction);
		drawLine(bX, bY, cX, cY, pixelFunction);
		drawLine(cX, cY, aX, aY, pixelFunction);
	}
	
	/**
	 * Fills a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillCircle(x, y, radius, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 */
	public void fillCircle(final int x, final int y, final int radius) {
		fillCircle(x, y, radius, Color.BLACK);
	}
	
	/**
	 * Fills a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillCircle(x, y, radius, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param color the {@code Color} to fill with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void fillCircle(final int x, final int y, final int radius, final Color color) {
		fillCircle(x, y, radius, PixelFunction.constant(color));
	}
	
	/**
	 * Fills a circle with a center point of {@code x} and {@code y} and a radius of {@code radius}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the circle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the X-coordinate of the center point of the circle
	 * @param y the Y-coordinate of the center point of the circle
	 * @param radius the radius of the circle
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void fillCircle(final int x, final int y, final int radius, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		for(int i = -radius; i <= radius; i++) {
			for(int j = -radius; j <= radius; j++) {
				if(j * j + i * i <= radius * radius) {
					final Color oldColor = getColor(x + j, y + i);
					final Color newColor = Objects.requireNonNull(pixelFunction.apply(x + j, y + i, oldColor));
					
					doSetColor(x + j, y + i, newColor);
				}
			}
		}
	}
	
	/**
	 * Fills a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRectangle(x, y, w, h, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 */
	public void fillRectangle(final int x, final int y, final int w, final int h) {
		fillRectangle(x, y, w, h, Color.BLACK);
	}
	
	/**
	 * Fills a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillRectangle(x, y, w, h, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 * @param color the {@code Color} to fill with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void fillRectangle(final int x, final int y, final int w, final int h, final Color color) {
		fillRectangle(x, y, w, h, PixelFunction.constant(color));
	}
	
	/**
	 * Fills a rectangle from {@code x} and {@code y} to {@code x + w - 1} and {@code y + h - 1}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the rectangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param x the X-coordinate to start the rectangle at
	 * @param y the Y-coordinate to start the rectangle at
	 * @param w the width of the rectangle
	 * @param h the height of the rectangle
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void fillRectangle(final int x, final int y, final int w, final int h, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		for(int i = y; i < y + h; i++) {
			for(int j = x; j < x + w; j++) {
				final Color oldColor = getColor(x, y);
				final Color newColor = Objects.requireNonNull(pixelFunction.apply(x, y, oldColor));
				
				doSetColor(x, y, newColor);
			}
		}
	}
	
	/**
	 * Fills a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with a {@link Color} of {@code Color.BLACK}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillTriangle(aX, aY, bX, bY, cX, cY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 */
	public void fillTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY) {
		fillTriangle(aX, aY, bX, bY, cX, cY, Color.BLACK);
	}
	
	/**
	 * Fills a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with a {@link Color} of {@code color}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.fillTriangle(aX, aY, bX, bY, cX, cY, PixelFunction.constant(color));
	 * }
	 * </pre>
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param color the {@code Color} to fill with
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void fillTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, Color color) {
		fillTriangle(aX, aY, bX, bY, cX, cY, PixelFunction.constant(color));
	}
	
	/**
	 * Fills a triangle from three points, denoted {@code A}, {@code B} and {@code C}, with {@link Color}s computed by {@code pixelFunction}.
	 * <p>
	 * Only the parts of the triangle that are inside the boundaries of this {@code Image} instance will be filled.
	 * <p>
	 * If either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param aX the X-coordinate of the point {@code A}
	 * @param aY the Y-coordinate of the point {@code A}
	 * @param bX the X-coordinate of the point {@code B}
	 * @param bY the Y-coordinate of the point {@code B}
	 * @param cX the X-coordinate of the point {@code C}
	 * @param cY the Y-coordinate of the point {@code C}
	 * @param pixelFunction a {@link PixelFunction} that returns a {@code Color} for a given pixel
	 * @throws NullPointerException thrown if, and only if, either {@code pixelFunction} is {@code null} or {@code pixelFunction.apply(x, y, oldColor)} returns {@code null}
	 */
	public void fillTriangle(final int aX, final int aY, final int bX, final int bY, final int cX, final int cY, final PixelFunction pixelFunction) {
		Objects.requireNonNull(pixelFunction, "pixelFunction == null");
		
		final int resolutionX = this.resolutionX;
		final int resolutionY = this.resolutionY;
		
		final int[][][] scanLines = Rasterizer.rasterizeTriangle(aX, aY, bX, bY, cX, cY, resolutionX, resolutionY);
		
		for(final int[][] scanLine : scanLines) {
			for(final int[] pixel : scanLine) {
				final int x = pixel[0];
				final int y = pixel[1];
				
				final Color oldColor = getColor(x, y);
				final Color newColor = Objects.requireNonNull(pixelFunction.apply(x, y, oldColor));
				
				doSetColor(x, y, newColor);
			}
		}
	}
	
	/**
	 * Flips this {@code Image} instance along the X- or Y-axes.
	 * 
	 * @param isFlippingX {@code true} if, and only if, this {@code Image} instance should be flipped along the X-axis, {@code false} otherwise
	 * @param isFlippingY {@code true} if, and only if, this {@code Image} instance should be flipped along the Y-axis, {@code false} otherwise
	 */
	public void flip(final boolean isFlippingX, final boolean isFlippingY) {
		final Image imageA = this;
		final Image imageB = copy();
		
		for(int imageAY = 0, imageBY = imageB.resolutionY - 1; imageAY < imageA.resolutionY && imageBY >= 0; imageAY++, imageBY--) {
			for(int imageAX = 0, imageBX = imageB.resolutionX - 1; imageAX < imageA.resolutionX && imageBX >= 0; imageAX++, imageBX--) {
				final int imageAIndex = imageAY * imageA.resolutionX + imageAX;
				final int imageBIndex = (isFlippingY ? imageBY : imageAY) * imageB.resolutionX + (isFlippingX ? imageBX : imageAX);
				
				imageA.colors[imageAIndex] = imageB.colors[imageBIndex];
				imageA.sampleCounts[imageAIndex] = imageB.sampleCounts[imageBIndex];
			}
		}
	}
	
	/**
	 * Multiplies this {@code Image} instance with {@code convolutionKernel}.
	 * <p>
	 * If {@code convolutionKernel} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param convolutionKernel a {@link ConvolutionKernel33}
	 * @throws NullPointerException thrown if, and only if, {@code convolutionKernel} is {@code null}
	 */
	public void multiply(final ConvolutionKernel33 convolutionKernel) {
		final Color factor = new Color(convolutionKernel.factor);
		final Color bias = new Color(convolutionKernel.bias);
		
		final Image image = copy();
		
		for(int y = 0; y < this.resolutionY; y++) {
			for(int x = 0; x < this.resolutionX; x++) {
				Color color = Color.BLACK;
				
//				Row #0:
				color = color.add(image.doGetColorOrDefault(x + -1, y + -1, Color.BLACK).multiply(convolutionKernel.element00));
				color = color.add(image.doGetColorOrDefault(x + +0, y + -1, Color.BLACK).multiply(convolutionKernel.element01));
				color = color.add(image.doGetColorOrDefault(x + +1, y + -1, Color.BLACK).multiply(convolutionKernel.element02));
				
//				Row #1:
				color = color.add(image.doGetColorOrDefault(x + -1, y + +0, Color.BLACK).multiply(convolutionKernel.element10));
				color = color.add(image.doGetColorOrDefault(x + +0, y + +0, Color.BLACK).multiply(convolutionKernel.element11));
				color = color.add(image.doGetColorOrDefault(x + +1, y + +0, Color.BLACK).multiply(convolutionKernel.element12));
				
//				Row #2:
				color = color.add(image.doGetColorOrDefault(x + -1, y + +1, Color.BLACK).multiply(convolutionKernel.element20));
				color = color.add(image.doGetColorOrDefault(x + +0, y + +1, Color.BLACK).multiply(convolutionKernel.element21));
				color = color.add(image.doGetColorOrDefault(x + +1, y + +1, Color.BLACK).multiply(convolutionKernel.element22));
				
//				Multiply with the factor and add the bias:
				color = color.multiply(factor).add(bias);
				color = color.minTo0().maxTo1();
				
				doSetColor(x, y, color);
			}
		}
	}
	
	/**
	 * Multiplies this {@code Image} instance with {@code convolutionKernel}.
	 * <p>
	 * If {@code convolutionKernel} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param convolutionKernel a {@link ConvolutionKernel55}
	 * @throws NullPointerException thrown if, and only if, {@code convolutionKernel} is {@code null}
	 */
	public void multiply(final ConvolutionKernel55 convolutionKernel) {
		final Color factor = new Color(convolutionKernel.factor);
		final Color bias = new Color(convolutionKernel.bias);
		
		final Image image = copy();
		
		for(int y = 0; y < this.resolutionY; y++) {
			for(int x = 0; x < this.resolutionX; x++) {
				Color color = Color.BLACK;
				
//				Row #0:
				color = color.add(image.doGetColorOrDefault(x + -2, y + -2, Color.BLACK).multiply(convolutionKernel.element00));
				color = color.add(image.doGetColorOrDefault(x + -1, y + -2, Color.BLACK).multiply(convolutionKernel.element01));
				color = color.add(image.doGetColorOrDefault(x + +0, y + -2, Color.BLACK).multiply(convolutionKernel.element02));
				color = color.add(image.doGetColorOrDefault(x + +1, y + -2, Color.BLACK).multiply(convolutionKernel.element03));
				color = color.add(image.doGetColorOrDefault(x + +2, y + -2, Color.BLACK).multiply(convolutionKernel.element04));
				
//				Row #1:
				color = color.add(image.doGetColorOrDefault(x + -2, y + -1, Color.BLACK).multiply(convolutionKernel.element10));
				color = color.add(image.doGetColorOrDefault(x + -1, y + -1, Color.BLACK).multiply(convolutionKernel.element11));
				color = color.add(image.doGetColorOrDefault(x + +0, y + -1, Color.BLACK).multiply(convolutionKernel.element12));
				color = color.add(image.doGetColorOrDefault(x + +1, y + -1, Color.BLACK).multiply(convolutionKernel.element13));
				color = color.add(image.doGetColorOrDefault(x + +2, y + -1, Color.BLACK).multiply(convolutionKernel.element14));
				
//				Row #2:
				color = color.add(image.doGetColorOrDefault(x + -2, y + +0, Color.BLACK).multiply(convolutionKernel.element20));
				color = color.add(image.doGetColorOrDefault(x + -1, y + +0, Color.BLACK).multiply(convolutionKernel.element21));
				color = color.add(image.doGetColorOrDefault(x + +0, y + +0, Color.BLACK).multiply(convolutionKernel.element22));
				color = color.add(image.doGetColorOrDefault(x + +1, y + +0, Color.BLACK).multiply(convolutionKernel.element23));
				color = color.add(image.doGetColorOrDefault(x + +2, y + +0, Color.BLACK).multiply(convolutionKernel.element24));
				
//				Row #3:
				color = color.add(image.doGetColorOrDefault(x + -2, y + +1, Color.BLACK).multiply(convolutionKernel.element30));
				color = color.add(image.doGetColorOrDefault(x + -1, y + +1, Color.BLACK).multiply(convolutionKernel.element31));
				color = color.add(image.doGetColorOrDefault(x + +0, y + +1, Color.BLACK).multiply(convolutionKernel.element32));
				color = color.add(image.doGetColorOrDefault(x + +1, y + +1, Color.BLACK).multiply(convolutionKernel.element33));
				color = color.add(image.doGetColorOrDefault(x + +2, y + +1, Color.BLACK).multiply(convolutionKernel.element34));
				
//				Row #4:
				color = color.add(image.doGetColorOrDefault(x + -2, y + +2, Color.BLACK).multiply(convolutionKernel.element40));
				color = color.add(image.doGetColorOrDefault(x + -1, y + +2, Color.BLACK).multiply(convolutionKernel.element41));
				color = color.add(image.doGetColorOrDefault(x + +0, y + +2, Color.BLACK).multiply(convolutionKernel.element42));
				color = color.add(image.doGetColorOrDefault(x + +1, y + +2, Color.BLACK).multiply(convolutionKernel.element43));
				color = color.add(image.doGetColorOrDefault(x + +2, y + +2, Color.BLACK).multiply(convolutionKernel.element44));
				
//				Multiply with the factor and add the bias:
				color = color.multiply(factor).add(bias);
				color = color.minTo0().maxTo1();
				
				doSetColor(x, y, color);
			}
		}
	}
	
	/**
	 * Redoes gamma correction on this {@code Image} instance using {@code RGBColorSpace.SRGB}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.redoGammaCorrection(RGBColorSpace.SRGB);
	 * }
	 * </pre>
	 */
	public void redoGammaCorrection() {
		redoGammaCorrection(RGBColorSpace.SRGB);
	}
	
	/**
	 * Redoes gamma correction on this {@code Image} instance using {@code colorSpace}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace the {@link RGBColorSpace} to use
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
	public void redoGammaCorrection(final RGBColorSpace colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		update(color -> color.redoGammaCorrection(colorSpace));
	}
	
	/**
	 * Saves this {@code Image} as a .PNG image to the file represented by {@code file}.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to save to
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public void save(final File file) {
		try {
			ImageIO.write(toBufferedImage(), "png", Objects.requireNonNull(file, "file == null"));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Saves this {@code Image} as a .PNG image to the file represented by the filename {@code filename}.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param filename a {@code String} that represents the filename of the file to save to
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public void save(final String filename) {
		save(new File(Objects.requireNonNull(filename, "filename == null")));
	}
	
	/**
	 * Sets the {@link Color} of the pixel represented by {@code index} to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor(index, color, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param index the index of the pixel
	 * @param color the {@code Color} to set
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void setColor(final int index, final Color color) {
		setColor(index, color, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Sets the {@link Color} of the pixel represented by {@code index} to {@code color}.
	 * <p>
	 * If either {@code color} or {@code pixelOperation} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param index the index of the pixel
	 * @param color the {@code Color} to set
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code pixelOperation} are {@code null}
	 */
	public void setColor(final int index, final Color color, final PixelOperation pixelOperation) {
		doSetColor(pixelOperation.getIndex(index, this.resolution), Objects.requireNonNull(color, "color == null"));
	}
	
	/**
	 * Sets the {@link Color} of the pixel represented by {@code x} and {@code y} to {@code color}.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setColor(x, y, color, PixelOperation.NO_CHANGE);
	 * }
	 * </pre>
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param color the {@code Color} to set
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void setColor(final int x, final int y, final Color color) {
		setColor(x, y, color, PixelOperation.NO_CHANGE);
	}
	
	/**
	 * Sets the {@link Color} of the pixel represented by {@code x} and {@code y} to {@code color}.
	 * <p>
	 * If either {@code color} or {@code pixelOperation} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * See the documentation for {@link PixelOperation} to get a more detailed explanation for different pixel operations.
	 * 
	 * @param x the X-coordinate of the pixel
	 * @param y the Y-coordinate of the pixel
	 * @param color the {@code Color} to set
	 * @param pixelOperation the {@code PixelOperation} to use
	 * @throws NullPointerException thrown if, and only if, either {@code color} or {@code pixelOperation} are {@code null}
	 */
	public void setColor(final int x, final int y, final Color color, final PixelOperation pixelOperation) {
		doSetColor(pixelOperation.getX(x, this.resolutionX), pixelOperation.getY(y, this.resolutionY), Objects.requireNonNull(color, "color == null"));
	}
	
	/**
	 * Sets the resolution of this {@code Image} instance.
	 * <p>
	 * The {@link Color}s in the region of the old boundaries that are inside the region of the new boundaries will be preserved. The others will be discarded. If the new boundaries of this {@code Image} instance is larger than the old
	 * boundaries, the regions outside the old boundaries will be filled with {@code Color.BLACK}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.setResolution(resolutionX, resolutionY, Color.BLACK);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 */
	public void setResolution(final int resolutionX, final int resolutionY) {
		setResolution(resolutionX, resolutionY, Color.BLACK);
	}
	
	/**
	 * Sets the resolution of this {@code Image} instance.
	 * <p>
	 * The {@link Color}s in the region of the old boundaries that are inside the region of the new boundaries will be preserved. The others will be discarded. If the new boundaries of this {@code Image} instance is larger than the old
	 * boundaries, the regions outside the old boundaries will be filled with {@code color}.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code color} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param color the {@code Color} to fill the regions of the {@code Image} that are outside the old boundaries
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, {@code color} is {@code null}
	 */
	public void setResolution(final int resolutionX, final int resolutionY, final Color color) {
		final int oldResolutionX = this.resolutionX;
		final int oldResolutionY = this.resolutionY;
		
		final Color[] oldColors = this.colors;
		
		final int[] oldSampleCounts = this.sampleCounts;
		
		final int newResolutionX = Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		final int newResolutionY = Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		final int newResolution = Integers.requirePositiveIntValue(resolutionX * resolutionY, "(resolutionX * resolutionY)");
		
		final Color[] newColors = new Color[newResolution];
		
		final int[] newSampleCounts = new int[newResolution];
		
		Arrays.fill(newColors, Objects.requireNonNull(color, "color == null"));
		
		final int minimumResolutionX = min(oldResolutionX, newResolutionX);
		final int minimumResolutionY = min(oldResolutionY, newResolutionY);
		
		for(int y = 0; y < minimumResolutionY; y++) {
			final int oldIndex = y * oldResolutionX + 0;
			final int newIndex = y * newResolutionX + 0;
			
			System.arraycopy(oldColors, oldIndex, newColors, newIndex, minimumResolutionX);
			System.arraycopy(oldSampleCounts, oldIndex, newSampleCounts, newIndex, minimumResolutionX);
		}
		
		this.resolution = newResolution;
		this.resolutionX = newResolutionX;
		this.resolutionY = newResolutionY;
		this.colors = newColors;
		this.sampleCounts = newSampleCounts;
	}
	
	/**
	 * Undoes gamma correction on this {@code Image} instance using {@code RGBColorSpace.SRGB}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.undoGammaCorrection(RGBColorSpace.SRGB);
	 * }
	 * </pre>
	 */
	public void undoGammaCorrection() {
		undoGammaCorrection(RGBColorSpace.SRGB);
	}
	
	/**
	 * Undoes gamma correction on this {@code Image} instance using {@code colorSpace}.
	 * <p>
	 * If {@code colorSpace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param colorSpace the {@link RGBColorSpace} to use
	 * @throws NullPointerException thrown if, and only if, {@code colorSpace} is {@code null}
	 */
	public void undoGammaCorrection(final RGBColorSpace colorSpace) {
		Objects.requireNonNull(colorSpace, "colorSpace == null");
		
		update(color -> color.redoGammaCorrection(colorSpace));
	}
	
	/**
	 * Updates this {@code Image} instance with new {@link Color}s by applying {@code function} to the old {@code Color}s.
	 * <p>
	 * If {@code function} is {@code null} or {@code function.apply(oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * image.update(function, 0, 0, image.getResolutionX(), image.getResolutionY());
	 * }
	 * </pre>
	 * 
	 * @param function a {@code Function} that updates the old {@code Color}s with new {@code Color}s
	 * @throws NullPointerException thrown if, and only if, {@code function} is {@code null} or {@code function.apply(oldColor)} returns {@code null}
	 */
	public void update(final Function<Color, Color> function) {
		update(function, 0, 0, getResolutionX(), getResolutionY());
	}
	
	/**
	 * Updates this {@code Image} instance with new {@link Color}s by applying {@code function} to the old {@code Color}s.
	 * <p>
	 * If {@code function} is {@code null} or {@code function.apply(oldColor)} returns {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param function a {@code Function} that updates the old {@code Color}s with new {@code Color}s
	 * @param aX the minimum (inclusive) or maximum (exclusive) X-coordinate of the region to be updated
	 * @param aY the minimum (inclusive) or maximum (exclusive) Y-coordinate of the region to be updated
	 * @param bX the maximum (exclusive) or minimum (inclusive) X-coordinate of the region to be updated
	 * @param bY the maximum (exclusive) or minimum (inclusive) Y-coordinate of the region to be updated
	 * @throws NullPointerException thrown if, and only if, {@code function} is {@code null} or {@code function.apply(oldColor)} returns {@code null}
	 */
	public void update(final Function<Color, Color> function, final int aX, final int aY, final int bX, final int bY) {
		Objects.requireNonNull(function, "function == null");
		
		final int minimumX = max(min(aX, bX), 0);
		final int minimumY = max(min(aY, bY), 0);
		final int maximumX = min(max(aX, bX), this.resolutionX);
		final int maximumY = min(max(aY, bY), this.resolutionY);
		
		for(int y = minimumY; y < maximumY; y++) {
			for(int x = minimumX; x < maximumX; x++) {
				final Color oldColor = doGetColorOrDefault(x, y, Color.BLACK);
				final Color newColor = Objects2.requireNonNull(function.apply(oldColor), "function.apply(%s) == null: x=%s, y=%s", oldColor, Integer.valueOf(x), Integer.valueOf(y));
				
				doSetColor(x, y, newColor);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Blends the two {@code Image}s {@code imageA} and {@code imageB} based on {@code factor}.
	 * <p>
	 * Returns a new {@code Image} instance with the result of the blend.
	 * <p>
	 * If either {@code imageA} or {@code imageB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param imageA one of the {@code Image}s to blend
	 * @param imageB one of the {@code Image}s to blend
	 * @param factor the factor to use in the blending process
	 * @return a new {@code Image} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code imageA} or {@code imageB} are {@code null}
	 */
	public static Image blend(final Image imageA, final Image imageB, final float factor) {
		return blend(imageA, imageB, factor, factor, factor);
	}
	
	/**
	 * Blends the two {@code Image}s {@code imageA} and {@code imageB} based on {@code factorR}, {@code factorG} and {@code factorB}.
	 * <p>
	 * Returns a new {@code Image} instance with the result of the blend.
	 * <p>
	 * If either {@code imageA} or {@code imageB} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param imageA one of the {@code Image}s to blend
	 * @param imageB one of the {@code Image}s to blend
	 * @param factorR the factor to use for the R-component values in the blending process
	 * @param factorG the factor to use for the G-component values in the blending process
	 * @param factorB the factor to use for the B-component values in the blending process
	 * @return a new {@code Image} instance with the result of the blend
	 * @throws NullPointerException thrown if, and only if, either {@code imageA} or {@code imageB} are {@code null}
	 */
	public static Image blend(final Image imageA, final Image imageB, final float factorR, final float factorG, final float factorB) {
		final int imageAResolutionX = imageA.resolutionX;
		final int imageAResolutionY = imageA.resolutionY;
		
		final int imageBResolutionX = imageB.resolutionX;
		final int imageBResolutionY = imageB.resolutionY;
		
		final int imageCResolutionX = max(imageAResolutionX, imageBResolutionX);
		final int imageCResolutionY = max(imageAResolutionY, imageBResolutionY);
		
		final Image imageC = new Image(imageCResolutionX, imageCResolutionY);
		
		for(int y = 0; y < imageCResolutionY; y++) {
			for(int x = 0; x < imageCResolutionX; x++) {
				final Color colorA = imageA.doGetColorOrDefault(x, y, Color.BLACK);
				final Color colorB = imageB.doGetColorOrDefault(x, y, Color.BLACK);
				final Color colorC = Color.blend(colorA, colorB, factorR, factorG, factorB);
				
				final int imageCIndex = y * imageCResolutionX + x;
				
				imageC.colors[imageCIndex] = colorC;
			}
		}
		
		return imageC;
	}
	
	/**
	 * Loads an {@code Image} from the file represented by {@code file}.
	 * <p>
	 * Returns a new {@code Image} instance.
	 * <p>
	 * If {@code file} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param file a {@code File} that represents the file to load from
	 * @return a new {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code file} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Image load(final File file) {
		final BufferedImage bufferedImage = doLoadBufferedImage(Objects.requireNonNull(file, "file == null"));
		
		final int resolutionX = bufferedImage.getWidth();
		final int resolutionY = bufferedImage.getHeight();
		
		final int[] intArray = DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData();
		
		final Color[] colorArray = doConvertIntArrayToColorArray(intArray, PackedIntComponentOrder.ARGB);
		
		final Image image = new Image(resolutionX, resolutionY);
		
		System.arraycopy(colorArray, 0, image.colors, 0, colorArray.length);
		
		return image;
	}
	
	/**
	 * Loads an {@code Image} from the file represented by the filename {@code filename}.
	 * <p>
	 * Returns a new {@code Image} instance.
	 * <p>
	 * If {@code filename} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param filename a {@code String} that represents the filename of the file to load from
	 * @return a new {@code Image} instance
	 * @throws NullPointerException thrown if, and only if, {@code filename} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O error occurs
	 */
	public static Image load(final String filename) {
		return load(new File(Objects.requireNonNull(filename, "filename == null")));
	}
	
	/**
	 * Returns a new {@code Image} instance filled with random {@link Color}s.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Image.random(800, 800);
	 * }
	 * </pre>
	 * 
	 * @return a new {@code Image} instance filled with random {@code Color}s
	 */
	public static Image random() {
		return random(800, 800);
	}
	
	/**
	 * Returns a new {@code Image} instance filled with random {@link Color}s.
	 * <p>
	 * If either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @return a new {@code Image} instance filled with random {@code Color}s
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX} is less than {@code 0}, {@code resolutionY} is less than {@code 0} or {@code resolutionX * resolutionY} is less than {@code 0}
	 */
	public static Image random(final int resolutionX, final int resolutionY) {
		final Image image = new Image(resolutionX, resolutionY);
		
		for(int i = 0; i < image.resolution; i++) {
			image.colors[i] = Color.random();
		}
		
		return image;
	}
	
	/**
	 * Returns an {@code Image} representation of {@code bufferedImage}.
	 * <p>
	 * If {@code bufferedImage} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bufferedImage a {@code BufferedImage} instance
	 * @return an {@code Image} representation of {@code bufferedImage}
	 * @throws NullPointerException thrown if, and only if, {@code bufferedImage} is {@code null}
	 */
	public static Image toImage(final BufferedImage bufferedImage) {
		final BufferedImage compatibleBufferedImage = doGetCompatibleBufferedImage(Objects.requireNonNull(bufferedImage, "bufferedImage == null"));
		
		final int resolutionX = compatibleBufferedImage.getWidth();
		final int resolutionY = compatibleBufferedImage.getHeight();
		
		final Color[] colors = doConvertIntArrayToColorArray(DataBufferInt.class.cast(bufferedImage.getRaster().getDataBuffer()).getData());
		
		return new Image(resolutionX, resolutionY, colors);
	}
	
	/**
	 * Returns an {@code Image} representation of {@code array}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY * 4}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Image.toImage(resolutionX, resolutionY, array, ArrayComponentOrder.BGRA);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param array a {@code byte[]} with {@code byte} values representing colors in unpacked form
	 * @return an {@code Image} representation of {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY * 4}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static Image toImage(final int resolutionX, final int resolutionY, final byte[] array) {
		return toImage(resolutionX, resolutionY, array, ArrayComponentOrder.BGRA);
	}
	
	/**
	 * Returns an {@code Image} representation of {@code array}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY * arrayComponentOrder.getComponentCount()}, an {@code IllegalArgumentException}
	 * will be thrown.
	 * <p>
	 * If either {@code array} or {@code arrayComponentOrder} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param array a {@code byte[]} with {@code byte} values representing colors in unpacked form
	 * @param arrayComponentOrder an {@link ArrayComponentOrder} to get the components from {@code array} in the correct order
	 * @return an {@code Image} representation of {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY *
	 * arrayComponentOrder.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code array} or {@code arrayComponentOrder} are {@code null}
	 */
	public static Image toImage(final int resolutionX, final int resolutionY, final byte[] array, final ArrayComponentOrder arrayComponentOrder) {
		Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		Integers.requirePositiveIntValue(resolutionX * resolutionY, "(resolutionX * resolutionY)");
		
		Objects.requireNonNull(array, "array == null");
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		Arrays2.requireExactLength(Objects.requireNonNull(array, "array == null"), resolutionX * resolutionY * arrayComponentOrder.getComponentCount(), "array");
		
		final Color[] colors = doConvertByteArrayToColorArray(array, arrayComponentOrder);
		
		return new Image(resolutionX, resolutionY, colors);
	}
	
	/**
	 * Returns an {@code Image} representation of {@code array}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code array} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * Image.toImage(resolutionX, resolutionY, array, PackedIntComponentOrder.ARGB);
	 * }
	 * </pre>
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param array an {@code int[]} with {@code int} values representing colors in packed form
	 * @return an {@code Image} representation of {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY}
	 * @throws NullPointerException thrown if, and only if, {@code array} is {@code null}
	 */
	public static Image toImage(final int resolutionX, final int resolutionY, final int[] array) {
		return toImage(resolutionX, resolutionY, array, PackedIntComponentOrder.ARGB);
	}
	
	/**
	 * Returns an {@code Image} representation of {@code array}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY * arrayComponentOrder.getComponentCount()}, an {@code IllegalArgumentException}
	 * will be thrown.
	 * <p>
	 * If either {@code array} or {@code arrayComponentOrder} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param array an {@code int[]} with {@code int} values representing colors in unpacked form
	 * @param arrayComponentOrder an {@link ArrayComponentOrder} to get the components from {@code array} in the correct order
	 * @return an {@code Image} representation of {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY *
	 * arrayComponentOrder.getComponentCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code array} or {@code arrayComponentOrder} are {@code null}
	 */
	public static Image toImage(final int resolutionX, final int resolutionY, final int[] array, final ArrayComponentOrder arrayComponentOrder) {
		Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		Integers.requirePositiveIntValue(resolutionX * resolutionY, "(resolutionX * resolutionY)");
		
		Objects.requireNonNull(array, "array == null");
		Objects.requireNonNull(arrayComponentOrder, "arrayComponentOrder == null");
		
		Arrays2.requireExactLength(array, resolutionX * resolutionY * arrayComponentOrder.getComponentCount(), "array");
		
		final Color[] colors = doConvertIntArrayToColorArray(array, arrayComponentOrder);
		
		return new Image(resolutionX, resolutionY, colors);
	}
	
	/**
	 * Returns an {@code Image} representation of {@code array}.
	 * <p>
	 * If either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code array} or {@code packedIntComponentOrder} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param resolutionX the resolution of the X-axis
	 * @param resolutionY the resolution of the Y-axis
	 * @param array an {@code int[]} with {@code int} values representing colors in packed form
	 * @param packedIntComponentOrder a {@link PackedIntComponentOrder} to get the components from the {@code int} values of {@code array} in the correct order
	 * @return an {@code Image} representation of {@code array}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code resolutionX}, {@code resolutionY} or {@code resolutionX * resolutionY} are less than {@code 0} or {@code array.length != resolutionX * resolutionY}
	 * @throws NullPointerException thrown if, and only if, either {@code array} or {@code packedIntComponentOrder} are {@code null}
	 */
	public static Image toImage(final int resolutionX, final int resolutionY, final int[] array, final PackedIntComponentOrder packedIntComponentOrder) {
		Integers.requirePositiveIntValue(resolutionX, "resolutionX");
		Integers.requirePositiveIntValue(resolutionY, "resolutionY");
		Integers.requirePositiveIntValue(resolutionX * resolutionY, "(resolutionX * resolutionY)");
		
		Objects.requireNonNull(array, "array == null");
		Objects.requireNonNull(packedIntComponentOrder, "packedIntComponentOrder == null");
		
		Arrays2.requireExactLength(array, resolutionX * resolutionY, "array");
		
		final Color[] colors = doConvertIntArrayToColorArray(array, packedIntComponentOrder);
		
		return new Image(resolutionX, resolutionY, colors);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Color doGetColorOrDefault(final int index, final Color color) {
		return index >= 0 && index < this.resolution ? this.colors[index] : color;
	}
	
	private Color doGetColorOrDefault(final int x, final int y, final Color color) {
		return x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY ? this.colors[y * this.resolutionX + x] : color;
	}
	
	private void doAddColorSample(final int index, final Color color) {
		if(index >= 0 && index < this.resolution) {
			final int oldSampleCount = this.sampleCounts[index];
			final int newSampleCount = oldSampleCount + 1;
			
			final Color oldAverageColor = this.colors[index];
			final Color newAverageColor = oldAverageColor.addSample(color, newSampleCount);
			
			this.colors[index] = newAverageColor;
			this.sampleCounts[index] = newSampleCount;
		}
	}
	
	private void doAddColorSample(final int x, final int y, final Color color) {
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			doAddColorSample(y * this.resolutionX + x, color);
		}
	}
	
	private void doSetColor(final int index, final Color color) {
		if(index >= 0 && index < this.resolution) {
			this.colors[index] = color;
			this.sampleCounts[index] = 1;
		}
	}
	
	private void doSetColor(final int x, final int y, final Color color) {
		if(x >= 0 && x < this.resolutionX && y >= 0 && y < this.resolutionY) {
			doSetColor(y * this.resolutionX + x, color);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static BufferedImage doGetCompatibleBufferedImage(final BufferedImage bufferedImage) {
		final int compatibleType = BufferedImage.TYPE_INT_ARGB;
		
		if(bufferedImage.getType() == compatibleType) {
			return bufferedImage;
		}
		
		final BufferedImage compatibleBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), compatibleType);
		
		final
		Graphics2D graphics2D = compatibleBufferedImage.createGraphics();
		graphics2D.drawImage(bufferedImage, 0, 0, null);
		
		return compatibleBufferedImage;
	}
	
	private static BufferedImage doLoadBufferedImage(final File file) {
		try {
			return doGetCompatibleBufferedImage(ImageIO.read(Objects.requireNonNull(file, "file == null")));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private static Color[] doConvertByteArrayToColorArray(final byte[] byteArray, final ArrayComponentOrder arrayComponentOrder) {
		final Color[] colorArray = new Color[byteArray.length / arrayComponentOrder.getComponentCount()];
		
		for(int i = 0; i < colorArray.length; i++) {
			final int r = arrayComponentOrder.readR(byteArray, i * arrayComponentOrder.getComponentCount());
			final int g = arrayComponentOrder.readG(byteArray, i * arrayComponentOrder.getComponentCount());
			final int b = arrayComponentOrder.readB(byteArray, i * arrayComponentOrder.getComponentCount());
			final int a = arrayComponentOrder.readA(byteArray, i * arrayComponentOrder.getComponentCount());
			
			colorArray[i] = new Color(r, g, b, a);
		}
		
		return colorArray;
	}
	
	private static Color[] doConvertIntArrayToColorArray(final int[] intArray) {
		return doConvertIntArrayToColorArray(intArray, PackedIntComponentOrder.ARGB);
	}
	
	private static Color[] doConvertIntArrayToColorArray(final int[] intArray, final ArrayComponentOrder arrayComponentOrder) {
		final Color[] colorArray = new Color[intArray.length / arrayComponentOrder.getComponentCount()];
		
		for(int i = 0; i < colorArray.length; i++) {
			final int r = arrayComponentOrder.readR(intArray, i * arrayComponentOrder.getComponentCount());
			final int g = arrayComponentOrder.readG(intArray, i * arrayComponentOrder.getComponentCount());
			final int b = arrayComponentOrder.readB(intArray, i * arrayComponentOrder.getComponentCount());
			final int a = arrayComponentOrder.readA(intArray, i * arrayComponentOrder.getComponentCount());
			
			colorArray[i] = new Color(r, g, b, a);
		}
		
		return colorArray;
	}
	
	private static Color[] doConvertIntArrayToColorArray(final int[] intArray, final PackedIntComponentOrder packedIntComponentOrder) {
		final Color[] colorArray = new Color[intArray.length];
		
		for(int i = 0; i < colorArray.length; i++) {
			colorArray[i] = new Color(intArray[i], packedIntComponentOrder);
		}
		
		return colorArray;
	}
}