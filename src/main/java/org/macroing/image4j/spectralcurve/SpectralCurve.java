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
package org.macroing.image4j.spectralcurve;

import org.macroing.image4j.XYZColor;

/**
 * A {@code SpectralCurve} is used for sampled or analytic spectral data.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class SpectralCurve {
	private static final float[] CIE_X_BAR = {0.000129900000F, 0.000232100000F, 0.000414900000F, 0.000741600000F, 0.001368000000F, 0.002236000000F, 0.004243000000F, 0.007650000000F, 0.014310000000F, 0.023190000000F, 0.043510000000F, 0.077630000000F, 0.134380000000F, 0.214770000000F, 0.283900000000F, 0.328500000000F, 0.348280000000F, 0.348060000000F, 0.336200000000F, 0.318700000000F, 0.290800000000F, 0.251100000000F, 0.195360000000F, 0.142100000000F, 0.095640000000F, 0.057950010000F, 0.032010000000F, 0.014700000000F, 0.004900000000F, 0.002400000000F, 0.009300000000F, 0.029100000000F, 0.063270000000F, 0.109600000000F, 0.165500000000F, 0.225749900000F, 0.290400000000F, 0.359700000000F, 0.433449900000F, 0.512050100000F, 0.594500000000F, 0.678400000000F, 0.762100000000F, 0.842500000000F, 0.916300000000F, 0.978600000000F, 1.026300000000F, 1.056700000000F, 1.062200000000F, 1.045600000000F, 1.002600000000F, 0.938400000000F, 0.854449900000F, 0.751400000000F, 0.642400000000F, 0.541900000000F, 0.447900000000F, 0.360800000000F, 0.283500000000F, 0.218700000000F, 0.164900000000F, 0.121200000000F, 0.087400000000F, 0.063600000000F, 0.046770000000F, 0.032900000000F, 0.022700000000F, 0.015840000000F, 0.011359160000F, 0.008110916000F, 0.005790346000F, 0.004106457000F, 0.002899327000F, 0.002049190000F, 0.001439971000F, 0.000999949300F, 0.000690078600F, 0.000476021300F, 0.000332301100F, 0.000234826100F, 0.000166150500F, 0.000117413000F, 0.000083075270F, 0.000058706520F, 0.000041509940F, 0.000029353260F, 0.000020673830F, 0.000014559770F, 0.000010253980F, 0.000007221456F, 0.000005085868F, 0.000003581652F, 0.000002522525F, 0.000001776509F, 0.000001251141F};
	private static final float[] CIE_Y_BAR = {0.000003917000F, 0.000006965000F, 0.000012390000F, 0.000022020000F, 0.000039000000F, 0.000064000000F, 0.000120000000F, 0.000217000000F, 0.000396000000F, 0.000640000000F, 0.001210000000F, 0.002180000000F, 0.004000000000F, 0.007300000000F, 0.011600000000F, 0.016840000000F, 0.023000000000F, 0.029800000000F, 0.038000000000F, 0.048000000000F, 0.060000000000F, 0.073900000000F, 0.090980000000F, 0.112600000000F, 0.139020000000F, 0.169300000000F, 0.208020000000F, 0.258600000000F, 0.323000000000F, 0.407300000000F, 0.503000000000F, 0.608200000000F, 0.710000000000F, 0.793200000000F, 0.862000000000F, 0.914850100000F, 0.954000000000F, 0.980300000000F, 0.994950100000F, 1.000000000000F, 0.995000000000F, 0.978600000000F, 0.952000000000F, 0.915400000000F, 0.870000000000F, 0.816300000000F, 0.757000000000F, 0.694900000000F, 0.631000000000F, 0.566800000000F, 0.503000000000F, 0.441200000000F, 0.381000000000F, 0.321000000000F, 0.265000000000F, 0.217000000000F, 0.175000000000F, 0.138200000000F, 0.107000000000F, 0.081600000000F, 0.061000000000F, 0.044580000000F, 0.032000000000F, 0.023200000000F, 0.017000000000F, 0.011920000000F, 0.008210000000F, 0.005723000000F, 0.004102000000F, 0.002929000000F, 0.002091000000F, 0.001484000000F, 0.001047000000F, 0.000740000000F, 0.000520000000F, 0.000361100000F, 0.000249200000F, 0.000171900000F, 0.000120000000F, 0.000084800000F, 0.000060000000F, 0.000042400000F, 0.000030000000F, 0.000021200000F, 0.000014990000F, 0.000010600000F, 0.000007465700F, 0.000005257800F, 0.000003702900F, 0.000002607800F, 0.000001836600F, 0.000001293400F, 0.000000910930F, 0.000000641530F, 0.000000451810F};
	private static final float[] CIE_Z_BAR = {0.000606100000F, 0.001086000000F, 0.001946000000F, 0.003486000000F, 0.006450001000F, 0.010549990000F, 0.020050010000F, 0.036210000000F, 0.067850010000F, 0.110200000000F, 0.207400000000F, 0.371300000000F, 0.645600000000F, 1.039050100000F, 1.385600000000F, 1.622960000000F, 1.747060000000F, 1.782600000000F, 1.772110000000F, 1.744100000000F, 1.669200000000F, 1.528100000000F, 1.287640000000F, 1.041900000000F, 0.812950100000F, 0.616200000000F, 0.465180000000F, 0.353300000000F, 0.272000000000F, 0.212300000000F, 0.158200000000F, 0.111700000000F, 0.078249990000F, 0.057250010000F, 0.042160000000F, 0.029840000000F, 0.020300000000F, 0.013400000000F, 0.008749999000F, 0.005749999000F, 0.003900000000F, 0.002749999000F, 0.002100000000F, 0.001800000000F, 0.001650001000F, 0.001400000000F, 0.001100000000F, 0.001000000000F, 0.000800000000F, 0.000600000000F, 0.000340000000F, 0.000240000000F, 0.000190000000F, 0.000100000000F, 0.000049999990F, 0.000030000000F, 0.000020000000F, 0.000010000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F, 0.000000000000F};
	private static final int WAVELENGTH_MAX = 830;
	private static final int WAVELENGTH_MIN = 360;
	private static final int WAVELENGTH_STEP = (WAVELENGTH_MAX - WAVELENGTH_MIN) / (CIE_X_BAR.length - 1);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SpectralCurve} instance.
	 */
	protected SpectralCurve() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a sample based on the wavelength {@code lambda} in nanometers.
	 * 
	 * @param lambda the wavelength in nanometers
	 * @return a sample based on the wavelength {@code lambda} in nanometers
	 */
	public abstract float sample(final float lambda);
	
	/**
	 * Returns an {@link XYZColor}.
	 * 
	 * @return an {@code XYZColor}
	 */
	public final XYZColor toXYZ() {
		float x = 0.0F;
		float y = 0.0F;
		float z = 0.0F;
		
		for(int i = 0, j = WAVELENGTH_MIN; i < CIE_X_BAR.length; i++, j += WAVELENGTH_STEP) {
			final float s = sample(j);
			
			x += s * CIE_X_BAR[i];
			y += s * CIE_Y_BAR[i];
			z += s * CIE_Z_BAR[i];
		}
		
		x *= WAVELENGTH_STEP;
		y *= WAVELENGTH_STEP;
		z *= WAVELENGTH_STEP;
		
		return new XYZColor(x, y, z);
	}
}