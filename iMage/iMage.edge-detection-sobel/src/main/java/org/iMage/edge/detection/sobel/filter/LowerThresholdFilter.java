package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Filters all pixels that have a grayscale color below a certain threshold and sets them to 0 (makes them black).
 * Pixels above the threshold are converted to grayscale normally (as defined in {@link GrayScaleFilter}).
 */
public class LowerThresholdFilter implements ImageFilter {

	/** The filter we want to decorate. */
	ImageFilter decoratedFilter = new NullFilter();

	/**
	 * The threshold to use.
	 */
	private int threshold;

	/**
	 * Creates a new threshold filter with the default threshold.
	 * 
	 * @param filter
	 *            The filter that has to be decorated
	 */
	public LowerThresholdFilter(ImageFilter filter) {
		this(127, filter);
	}

	/**
	 * Creates a new threshold filter with the default threshold, without a base filter
	 */
	public LowerThresholdFilter() {
		this(127, new NullFilter());
	}

	/**
	 * Creates a new threshold filter with the given threshold.
	 *
	 * @param threshold
	 *            the threshold to use
	 * 
	 * @param filter
	 *            The filter that has to be decorated
	 */
	public LowerThresholdFilter(int threshold, ImageFilter filter) {
		this.threshold = threshold;
		decoratedFilter = filter;
	}

	/**
	 * Creates a new threshold filter with the given threshold.
	 *
	 * @param threshold
	 *            the threshold to use
	 */
	public LowerThresholdFilter(int threshold) {
		this(threshold, new NullFilter());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.kit.ipd.swt1.jmjrst.popart.filter.ImageFilter#applyFilter(java.awt .image.BufferedImage)
	 */
	@Override
	public BufferedImage applyFilter(final BufferedImage image) {
		BufferedImage inputImage = decoratedFilter.applyFilter(image);
		BufferedImage resultImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
		WritableRaster resultRaster = resultImage.getRaster();

		int w = inputImage.getWidth();
		int h = inputImage.getHeight();

		Raster raster = inputImage.getRaster();

		int bandwidth = (inputImage.getColorModel().hasAlpha()) ? 4 : 3;

		// We need 3 or 4 integers (for R,G,B color values and possibly an
		// alpha channel) per pixel.
		int[] pixels = new int[w * h * bandwidth];
		raster.getPixels(0, 0, w, h, pixels);

		// Process 3 or 4 ints at a time for each pixel.
		// Each pixel has 3 RGB colors in array the alpha channel should stay
		// intact
		for (int i = 0; i < pixels.length; i += bandwidth) {
			int r = pixels[i];
			int g = pixels[i + 1];
			int b = pixels[i + 2];

			int gry = (r + g + b) / 3;
			r = (gry > threshold) ? gry : 0;
			g = (gry > threshold) ? gry : 0;
			b = (gry > threshold) ? gry : 0;

			pixels[i] = r;
			pixels[i + 1] = g;
			pixels[i + 2] = b;
		}

		resultRaster.setPixels(0, 0, w, h, pixels);

		return resultImage;
	}
}
