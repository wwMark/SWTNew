package org.iMage.sobel.edge.detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.iMage.edge.detection.sobel.filter.BlurFilter;
import org.iMage.edge.detection.sobel.filter.NullFilter;
import org.junit.Test;

/**
 * Tests the blur filter using the default origin image.
 * 
 * @author Christopher Guckes (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class NullFilterTest extends ImageFilterTest {
    /**
     * Creates a new test case using the {@link BlurFilter} and it's respective
     * result images.
     */
    public NullFilterTest() {
        super(new NullFilter(), new File("src/test/resources/walter-kopf-transparent.png"),
                new File("src/test/resources/walter-kopf-transparent.png"));
    }

    /**
     * Tests if the alpha channel is preserved during filtering.
     */
    @Test
    public void testAlpha() {
        BufferedImage in = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        int rgb = new Color(0, 0, 0).getRGB();
        in.setRGB(0, 0, rgb);
        in.setRGB(0, 1, rgb);
        in.setRGB(0, 2, rgb);
        in.setRGB(1, 0, rgb);
        in.setRGB(1, 1, new Color(0, 0, 1, 128).getRGB());
        in.setRGB(1, 2, rgb);
        in.setRGB(2, 0, rgb);
        in.setRGB(2, 1, rgb);
        in.setRGB(2, 2, rgb);

        BufferedImage expected = new BufferedImage(3, 3,
                BufferedImage.TYPE_INT_ARGB);
        expected.setRGB(0, 0, rgb);
        expected.setRGB(0, 1, rgb);
        expected.setRGB(0, 2, rgb);
        expected.setRGB(1, 0, rgb);
        expected.setRGB(1, 1, new Color(0, 0, 1, 128).getRGB());
        expected.setRGB(1, 2, rgb);
        expected.setRGB(2, 0, rgb);
        expected.setRGB(2, 1, rgb);
        expected.setRGB(2, 2, rgb);

        NullFilter f = new NullFilter();
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
        in.setRGB(1, 1, new Color(128, 128, 128).getRGB());
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
        expected.setRGB(1, 1, new Color(128, 128, 128).getRGB());
        expected.setRGB(1, 2, rgb);
        expected.setRGB(2, 0, rgb);
        expected.setRGB(2, 1, rgb);
        expected.setRGB(2, 2, rgb);

        NullFilter f = new NullFilter();
        BufferedImage filtered = f.applyFilter(in);

        ImageFilterTest.compareImages(expected, filtered);
    }
}
