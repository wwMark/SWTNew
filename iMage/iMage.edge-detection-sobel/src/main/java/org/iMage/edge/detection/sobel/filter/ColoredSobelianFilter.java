package org.iMage.edge.detection.sobel.filter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.kohsuke.MetaInfServices;

/**
 * Detects the edges in an image and displays only the pixels that are actual
 * parts of said edges.
 */
@MetaInfServices(EdgeDetectionImageFilter.class)
public class ColoredSobelianFilter implements EdgeDetectionImageFilter {

  /**
   * The edge detector to use.
   */
  private SobelianFilter edgeDetector;

  /**
   * Creates a new colored edge detector with the default edge filter.
   */
  public ColoredSobelianFilter() {
    this(new SobelFilter(new BlurFilter()));
  }

  /**
   * Creates a new colored edge detector with the given edge filter.
   *
   * @param edgeDetector the edge detector to use.
   */
  public ColoredSobelianFilter(SobelianFilter edgeDetector) {
    this.edgeDetector = edgeDetector;
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
    LowerThresholdFilter thresholdFilter = new LowerThresholdFilter(150);

    BufferedImage edges = thresholdFilter.applyFilter(edgeDetector.applyFilter(image));


    return overlayEdgesAndColors(image, edges);
  }

  /**
   * Filtes the edges of an image and returns the colored edge image
   *
   * @param image the original image
   * @param edges the gradient image
   * @return the colored edge image
   */
  private BufferedImage overlayEdgesAndColors(BufferedImage image,
                                              BufferedImage edges) {
    BufferedImage resultImage = new BufferedImage(image.getWidth(),
        image.getHeight(), image.getType());

    WritableRaster resultRaster = resultImage.getRaster();

    int w = image.getWidth();
    int h = image.getHeight();

    Raster raster = image.getRaster();
    Raster edgeRaster = edges.getRaster();

    int bandwidth = (image.getColorModel().hasAlpha()) ? 4 : 3;

    // We need 3 or 4 integers (for R,G,B color values and possibly an
    // alpha channel) per pixel.
    int[] pixels = new int[w * h * bandwidth];
    int[] edgePixels = new int[w * h * bandwidth];
    raster.getPixels(0, 0, w, h, pixels);
    edgeRaster.getPixels(0, 0, w, h, edgePixels);

    // Process 3 or 4 ints at a time for each pixel.
    // Each pixel has 3 RGB colors in array the alpha channel should stay
    // intact
    for (int i = 0; i < pixels.length; i += bandwidth) {
      int r = (edgePixels[i] != 0) ? pixels[i] : 0;
      int g = (edgePixels[i + 1] != 0) ? pixels[i + 1] : 0;
      int b = (edgePixels[i + 2] != 0) ? pixels[i + 2] : 0;

      pixels[i] = r;
      pixels[i + 1] = g;
      pixels[i + 2] = b;
    }

    resultRaster.setPixels(0, 0, w, h, pixels);
    return resultImage;
  }
}