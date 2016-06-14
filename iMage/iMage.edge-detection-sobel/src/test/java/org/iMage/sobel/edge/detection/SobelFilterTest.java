package org.iMage.sobel.edge.detection;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import org.iMage.edge.detection.sobel.filter.NullFilter;
import org.iMage.edge.detection.sobel.filter.SobelFilter;
import org.junit.Test;

/**
 * Tests the SobelFilter using the default origin image.
 * 
 * @author Christopher Guckes (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class SobelFilterTest extends ImageFilterTest {
    /**
     * Creates a new test case using the {@link SobelFilter} and it's respective
     * result images.
     */
    public SobelFilterTest() {
        super(new SobelFilter(new SobelFilter(new NullFilter())), new File("src/test/resources/sobel.png"),
                new File("src/test/resources/camera_obscura.png"));
    }

    /**
     * Tests if the alpha channel is preserved during filtering.
     */
    @Test
    public void testAlpha() {
        BufferedImage in = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
        in.setRGB(0, 0, 0xFF000000);
        in.setRGB(0, 1, 0xFF000000);
        in.setRGB(0, 2, new Color(1,1,1).getRGB());
        in.setRGB(1, 0, 0xFF000000);
        in.setRGB(1, 1, 0xFF000000);
        in.setRGB(1, 2, new Color(1,1,1).getRGB());
        in.setRGB(2, 0, new Color(1,1,1).getRGB());
        in.setRGB(2, 1, new Color(1,1,1).getRGB());
        in.setRGB(2, 2, new Color(1,1,1).getRGB());

        BufferedImage expected = new BufferedImage(3, 3,
                BufferedImage.TYPE_INT_ARGB);
        expected.setRGB(0, 0, 0xFF000000);
        expected.setRGB(0, 1, 0xFF000000);
        expected.setRGB(0, 2, new Color(1,1,1).getRGB());
        expected.setRGB(1, 0, 0xFF000000);
        expected.setRGB(1, 1, new Color(4,4,4).getRGB());
        expected.setRGB(1, 2, new Color(1,1,1).getRGB());
        expected.setRGB(2, 0, new Color(1,1,1).getRGB());
        expected.setRGB(2, 1, new Color(1,1,1).getRGB());
        expected.setRGB(2, 2, new Color(1,1,1).getRGB());

        SobelFilter f = new SobelFilter(new NullFilter());
        BufferedImage filtered = f.applyFilter(in);

        ImageFilterTest.compareImages(expected, filtered);
    }

    /**
     * Tests the blur filter on an image without alpha channel.
     */
    @Test
    public void testNoAlpha() {
        BufferedImage in = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
        in.setRGB(0, 0, 0x000000);
        in.setRGB(0, 1, 0x000000);
        in.setRGB(0, 2, new Color(1,1,1).getRGB());
        in.setRGB(1, 0, 0x000000);
        in.setRGB(1, 1, 0x000000);
        in.setRGB(1, 2, new Color(1,1,1).getRGB());
        in.setRGB(2, 0, new Color(1,1,1).getRGB());
        in.setRGB(2, 1, new Color(1,1,1).getRGB());
        in.setRGB(2, 2, new Color(1,1,1).getRGB());

        BufferedImage expected = new BufferedImage(3, 3,
            BufferedImage.TYPE_INT_RGB);
        expected.setRGB(0, 0, 0x000000);
        expected.setRGB(0, 1, 0x000000);
        expected.setRGB(0, 2, new Color(1,1,1).getRGB());
        expected.setRGB(1, 0, 0x000000);
        expected.setRGB(1, 1, new Color(4,4,4).getRGB());
        expected.setRGB(1, 2, new Color(1,1,1).getRGB());
        expected.setRGB(2, 0, new Color(1,1,1).getRGB());
        expected.setRGB(2, 1, new Color(1,1,1).getRGB());
        expected.setRGB(2, 2, new Color(1,1,1).getRGB());

        SobelFilter f = new SobelFilter(new NullFilter());
        BufferedImage filtered = f.applyFilter(in);

        ImageFilterTest.compareImages(expected, filtered);
    }
}
