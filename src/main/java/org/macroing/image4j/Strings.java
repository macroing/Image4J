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

import java.text.DecimalFormat;

final class Strings {
	private static final DecimalFormat DECIMAL_FORMAT = doCreateDecimalFormat();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Strings() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String toNonScientificNotation(final float value) {
		return DECIMAL_FORMAT.format(value).replace(',', '.');
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static DecimalFormat doCreateDecimalFormat() {
		final
		DecimalFormat decimalFormat = new DecimalFormat("#");
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setMaximumFractionDigits(8);
		decimalFormat.setMinimumFractionDigits(1);
		decimalFormat.setMinimumIntegerDigits(1);
		
		return decimalFormat;
	}
}