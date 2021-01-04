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
package org.macroing.image4j.film;

import java.util.Objects;

import org.macroing.image4j.XYZColor;

final class Pixel {
	private XYZColor color;
	private XYZColor splat;
	private float filterWeightSum;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Pixel() {
		this.color = new XYZColor();
		this.splat = new XYZColor();
		this.filterWeightSum = 0.0F;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public XYZColor getColor() {
		return this.color;
	}
	
	public XYZColor getSplat() {
		return this.splat;
	}
	
	public float getFilterWeightSum() {
		return this.filterWeightSum;
	}
	
	public void clear() {
		this.color = new XYZColor();
		this.splat = new XYZColor();
		this.filterWeightSum = 0.0F;
	}
	
	public void setColor(final XYZColor color) {
		this.color = Objects.requireNonNull(color, "color == null");
	}
	
	public void setFilterWeightSum(final float filterWeightSum) {
		this.filterWeightSum = filterWeightSum;
	}
	
	public void setSplat(final XYZColor splat) {
		this.splat = Objects.requireNonNull(splat, "splat == null");
	}
}