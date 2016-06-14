package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.iMage.edge.detection.base.ImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Scharr filter operator.
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class ScharrFilter extends SobelianFilter {

	/** The filter we want to decorate. */
	ImageFilter decoratedFilter = new NullFilter();

	/**
	 * Default Scharr filter
	 */
	public ScharrFilter() {
		this(new NullFilter());
	}

	/**
	 * Use this filter after another filter - i.e. decorate the other filters result with the Scharr filter.
	 * 
	 * @param filter
	 *            Decorated filter
	 */
	public ScharrFilter(ImageFilter filter) {
		decoratedFilter = filter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.kit.ipd.swt1.jmjrst.popart.filter.ImageFilter#applyFilter(java.awt
	 * .image.BufferedImage)
	 */
	@Override
	public BufferedImage applyFilter(BufferedImage image) {
	  return (super.applyFilter(decoratedFilter.applyFilter(image)));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.iMage.edge.detection.sobel.filter.SobelFilter.getMatrixX()
	 */
	@Override
	protected int[][] getMatrixX() {
		return new int[][] { { -3, 0, 3 }, { -10, 0, 10 }, { -3, 0, 3 } };
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.iMage.edge.detection.sobel.filter.SobelFilter.getMatrixY()
	 */
	@Override
	protected int[][] getMatrixY() {
		return new int[][] { { -3, -10, -3 }, { 0, 0, 0 }, { 3, 10, 3 } };
	}

}
