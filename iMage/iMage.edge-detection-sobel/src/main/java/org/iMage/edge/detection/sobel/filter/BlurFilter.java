package org.iMage.edge.detection.sobel.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.iMage.edge.detection.base.ImageFilter;

/**
 * Implements the blur filter as requested on worksheet 2.
 * 
 * @author Mathias Landhäußer (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class BlurFilter implements ImageFilter {
  // final float[] matrix = { 1 / 16f, 1 / 8f, 1 / 16f, 1 / 8f, 1 / 4f, 1 / 8f,
  // 1 / 16f, 1 / 8f, 1 / 16f, };
  final double[] matrix = { 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, };
  final int mLength = (int) Math.sqrt(matrix.length) / 2;

  /*
   * (non-Javadoc)
   *
   * @see
   * edu.kit.ipd.swt1.jmjrst.popart.filter.ImageFilter#applyFilter(java.awt
   * .image.BufferedImage)
   */
  @Override
  public BufferedImage applyFilter(BufferedImage image) {
    BufferedImage result =
        new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

    for (int x = mLength; x < image.getWidth() - mLength; x++) {
      for (int y = mLength; y < image.getHeight() - mLength; y++) {
        result.setRGB(x, y, getRGB(x, y, image, matrix));
      }
    }

    for (int c = 0; c < result.getWidth(); c++) {
      for (int i = 0; i < mLength; i++) {
        result.setRGB(c, i, image.getRGB(c, 0));
        result.setRGB(c, image.getHeight() - 1 - i,
            image.getRGB(c, image.getHeight() - 1 - i));
      }
    }
    for (int c = 0; c < result.getHeight(); c++) {
      for (int i = 0; i < mLength; i++) {
        result.setRGB(i, c, image.getRGB(0, c));
        result.setRGB(image.getWidth() - 1 - i, c,
            image.getRGB(image.getWidth() - 1 - i, c));
      }
    }

    return result;
  }

  private int getRGB(int x, int y, BufferedImage image, double[] matrix) {

    int minX = x - (int) Math.floor(mLength);
    int maxX = x + (int) Math.floor(mLength);

    int minY = y - (int) Math.floor(mLength);
    int maxY = y + (int) Math.floor(mLength);

    double r = 0;
    double g = 0;
    double b = 0;
    double a = 0;

    int count = 0;
    for (int xn = minX; xn <= maxX; xn++) {
      for (int yn = minY; yn <= maxY; yn++) {

        double mult = matrix[count++];
        Color c = new Color(image.getRGB(xn, yn), true);

        a += c.getAlpha() * mult;
        r += c.getRed() * mult;
        g += c.getGreen() * mult;
        b += c.getBlue() * mult;
      }
    }

    a = Math.min(a, 255);
    r = Math.min(r, 255);
    g = Math.min(g, 255);
    b = Math.min(b, 255);

    int rgb = 0;
    if (image.getColorModel().hasAlpha()) {
      rgb = new Color((int) r, (int) g, (int) b, (int) a).getRGB();
    } else {
      rgb = new Color((int) r, (int) g, (int) b).getRGB();
    }

    return rgb;
  }
}
