package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Implements the GrayScaleFilter as requested on worksheet 2.
 *
 * @author Mathias Landhäußer (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class GrayScaleFilter implements ImageFilter {

	@Override
	public BufferedImage applyFilter(BufferedImage image) {
		BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		WritableRaster resultRaster = resultImage.getRaster();

		int w = image.getWidth();
		int h = image.getHeight();

		Raster raster = image.getRaster();

		int bandwidth = (image.getColorModel().hasAlpha()) ? 4 : 3;

		// We need 3 or 4 integers (for R,G,B color values and possibly an
		// alpha channel) per pixel.
		int[] pixels = new int[w * h * bandwidth];

		// copy the source image's pixels to the pixels array
		raster.getPixels(0, 0, w, h, pixels);

		// Process 3 or 4 ints at a time for each pixel.
		// Each pixel has 3 RGB colors in array; the alpha channel should stay intact
		for (int i = 0; i < pixels.length; i += bandwidth) {
			int r = pixels[i];
			int g = pixels[i + 1];
			int b = pixels[i + 2];

			int gry = (r + g + b) / 3;
			r = gry;
			g = gry;
			b = gry;

			pixels[i] = r;
			pixels[i + 1] = g;
			pixels[i + 2] = b;
		}

		resultRaster.setPixels(0, 0, w, h, pixels);

		return resultImage;
	}
}
