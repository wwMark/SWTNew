package org.iMage.sobel.edge.detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.iMage.edge.detection.sobel.filter.GrayScaleFilter;
import org.junit.Test;

/**
 * Tests the GrayScaleFilter using the default origin image.
 * 
 * @author Christopher Guckes (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class GrayScaleFilterTest extends ImageFilterTest {
    /**
     * Creates a new test case using the {@link GrayScaleFilter} and it's respective
     * result images.
     */
    public GrayScaleFilterTest() {
		super(new GrayScaleFilter(), new File("src/test/resources/grayscale.png"),
                new File("src/test/resources/walter-kopf-transparent.png"));
    }

    /**
     * Tests if the alpha channel is preserved during filtering.
     */
    @Test
    public void testAlpha() {
        BufferedImage in = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        in.setRGB(0, 0, 0);
        in.setRGB(0, 1, 0);
        in.setRGB(0, 2, 0);
        in.setRGB(1, 0, 0);
        in.setRGB(1, 1, new Color(150, 100, 50).getRGB());
        in.setRGB(1, 2, 0);
        in.setRGB(2, 0, 0);
        in.setRGB(2, 1, 0);
        in.setRGB(2, 2, 0);

        BufferedImage expected = new BufferedImage(3, 3,
                BufferedImage.TYPE_INT_ARGB);
        expected.setRGB(0, 0, 0);
        expected.setRGB(0, 1, 0);
        expected.setRGB(0, 2, 0);
        expected.setRGB(1, 0, 0);
        expected.setRGB(1, 1, new Color(100, 100, 100).getRGB());
        expected.setRGB(1, 2, 0);
        expected.setRGB(2, 0, 0);
        expected.setRGB(2, 1, 0);
        expected.setRGB(2, 2, 0);

        GrayScaleFilter f = new GrayScaleFilter();
        BufferedImage filtered = f.applyFilter(in);

        ImageFilterTest.compareImages(expected, filtered);
    }

    /**
     * Tests the blur filter on an image without alpha channel.
     */
    @Test
    public void testNoAlpha() {
        BufferedImage in = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        int rgb = new Color(0, 0, 0).getRGB();
        in.setRGB(0, 0, rgb);
        in.setRGB(0, 1, rgb);
        in.setRGB(0, 2, rgb);
        in.setRGB(1, 0, rgb);
        in.setRGB(1, 1, new Color(150, 100, 50).getRGB());
        in.setRGB(1, 2, rgb);
        in.setRGB(2, 0, rgb);
        in.setRGB(2, 1, rgb);
        in.setRGB(2, 2, rgb);

        BufferedImage expected = new BufferedImage(3, 3,
                BufferedImage.TYPE_INT_RGB);
        expected.setRGB(0, 0, rgb);
        expected.setRGB(0, 1, rgb);
        expected.setRGB(0, 2, rgb);
        expected.setRGB(1, 0, rgb);
        expected.setRGB(1, 1, new Color(100, 100, 100).getRGB());
        expected.setRGB(1, 2, rgb);
        expected.setRGB(2, 0, rgb);
        expected.setRGB(2, 1, rgb);
        expected.setRGB(2, 2, rgb);

        GrayScaleFilter f = new GrayScaleFilter();
        BufferedImage filtered = f.applyFilter(in);

        ImageFilterTest.compareImages(expected, filtered);
    }
}
