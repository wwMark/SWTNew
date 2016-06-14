package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.iMage.edge.detection.base.ImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects edges via the Prewitt filter operator.
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class PrewittFilter extends SobelianFilter {

	/** The filter to decorate. */
	ImageFilter decoratedFilter = new NullFilter();

	/**
	 * Default Prewitt filter
	 */
	public PrewittFilter() {
		this(new NullFilter());
	}

	/**
	 * Use this filter after another filter - i.e. decorate the other filters
	 * result with the Prewitt filter.
	 * 
	 * @param filter
	 *            Decorated filter
	 */
	public PrewittFilter(ImageFilter filter) {
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
    return new int[][] { { -1, 0, 1 }, { -1, 0, 1 }, { -1, 0, 1 } };
  }

	/*
   * (non-Javadoc)
   *
   * @see
   * org.iMage.edge.detection.sobel.filter.SobelFilter.getMatrixY()
   */
  @Override
  protected int[][] getMatrixY() {
    return new int[][] { { -1, -1, -1 }, { 0, 0, 0 }, { 1, 1, 1 } };
  }
}
