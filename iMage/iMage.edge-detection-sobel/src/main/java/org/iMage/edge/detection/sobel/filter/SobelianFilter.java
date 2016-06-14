package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.iMage.edge.detection.base.ImageFilter;

/**
 * Abstract class that unifies all filters that convolute two matrices in order to alter the image.
 */
public abstract class SobelianFilter implements EdgeDetectionImageFilter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * edu.kit.ipd.swt1.jmjrst.popart.filter.ImageFilter#applyFilter(java.awt
	 * .image.BufferedImage)
	 */
  @Override
	public BufferedImage applyFilter(final BufferedImage image) {
		// Convert image to grayscale
		ImageFilter grayscale = new GrayScaleFilter();
		BufferedImage resultImage = grayscale.applyFilter(image);

		// Horizontal edge detection
		int[][] sobelX = getMatrixX();

		// Vertical edge detection
		int[][] sobelY = getMatrixY();
		
		final int matrixWidth = (sobelY.length - 1) / 2;

		for (int x = matrixWidth; x < image.getWidth() - matrixWidth; x++) {
			for (int y = matrixWidth; y < image.getHeight() - matrixWidth; y++) {
				// @formatter:off
			  int[][] relevantRectangle = new int[][] {
			    { image.getRGB(x-1, y+1), image.getRGB(x, y+1), image.getRGB(x+1, y+1) },
			    { image.getRGB(x-1, y  ), image.getRGB(x, y  ), image.getRGB(x+1, y  ) },
			    { image.getRGB(x-1, y-1), image.getRGB(x, y-1), image.getRGB(x+1, y-1) }
			  };

			  int blueX = sobelX[0][0] * (relevantRectangle[0][0] & 0x000000ff)
							    + sobelX[0][1] * (relevantRectangle[0][1] & 0x000000ff)
							    + sobelX[0][2] * (relevantRectangle[0][2] & 0x000000ff)
							
							    + sobelX[1][0] * (relevantRectangle[1][0] & 0x000000ff)
							    + sobelX[1][1] * (relevantRectangle[1][1] & 0x000000ff)
							    + sobelX[1][2] * (relevantRectangle[1][2] & 0x000000ff)
							
							    + sobelX[2][0] * (relevantRectangle[2][0] & 0x000000ff)
							    + sobelX[2][1] * (relevantRectangle[2][1] & 0x000000ff)
							    + sobelX[2][2] * (relevantRectangle[2][2] & 0x000000ff);
				
        int blueY = sobelY[0][0] * (relevantRectangle[0][0] & 0x000000ff)
                  + sobelY[0][1] * (relevantRectangle[0][1] & 0x000000ff)
                  + sobelY[0][2] * (relevantRectangle[0][2] & 0x000000ff)
             
                  + sobelY[1][0] * (relevantRectangle[1][0] & 0x000000ff)
                  + sobelY[1][1] * (relevantRectangle[1][1] & 0x000000ff)
                  + sobelY[1][2] * (relevantRectangle[1][2] & 0x000000ff)
             
                  + sobelY[2][0] * (relevantRectangle[2][0] & 0x000000ff)
                  + sobelY[2][1] * (relevantRectangle[2][1] & 0x000000ff)
                  + sobelY[2][2] * (relevantRectangle[2][2] & 0x000000ff);
				// @formatter:on

				int resultBlue = (int) (Math.sqrt((blueX * blueX) + (blueY * blueY)));

				int rgba = resultBlue + (resultBlue << 8) + (resultBlue << 16) + (image.getRGB(x, y) & 0xff000000);

				resultImage.setRGB(x, y, rgba);
			}
		}
		
		return resultImage;
	}

	/**
	 * Forces inheriting classes to provide matrices.
	 * @return the first filter matrix
	 */
	protected abstract int[][] getMatrixX();

	/**
	 * Forces inheriting classes to provide matrices.
	 * @return the second filter matrix
	 */
	protected abstract int[][] getMatrixY();
}