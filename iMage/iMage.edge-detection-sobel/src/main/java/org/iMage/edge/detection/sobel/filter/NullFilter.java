package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Identity filter that does not do anything.
 */
public class NullFilter implements ImageFilter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.kit.ipd.swt1.jmjrst.popart.filter.ImageFilter#applyFilter(java.awt
	 * .image.BufferedImage)
	 */
	@Override
	public BufferedImage applyFilter(BufferedImage image) {
		// create a clone of BufferedImage image
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
