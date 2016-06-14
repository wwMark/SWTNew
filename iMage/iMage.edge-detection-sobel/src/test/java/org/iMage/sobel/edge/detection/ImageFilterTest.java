package org.iMage.sobel.edge.detection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.iMage.edge.detection.base.ImageFilter;
import org.junit.Test;

/**
 * Abstract test that allows easier testing by allowing very stubby tests of
 * concrete filter classes. Lazyness ftw.
 * 
 * @author Christopher Guckes (swt1@ipd.kit.edu)
 * @version 1.0
 */
public abstract class ImageFilterTest {
    private ImageFilter toTest;
    private File expectedResult;
    private File original;

    /**
     * Creates a new testcase for the given filter, comparing the generated
     * result to the expected result.
     * 
     * @param toTest
     *            the filter to test
     * @param expectedResult
     *            the result to expect
     * @param original
     *            the generated result to compare to the expected one
     */
    public ImageFilterTest(ImageFilter toTest, File expectedResult,
            File original) {
        this.toTest = toTest;
        this.expectedResult = expectedResult;
        this.original = original;
    }

    /**
     * Tests the apply filter function by comparing both images. Fails if it
     * can't load both of the images or the expected result and the processed
     * image differ.
     */
    @Test
    public void testApplyFilter() {
        try {
            BufferedImage expected = ImageIO.read(this.expectedResult);
            BufferedImage actual = ImageIO.read(this.original);
            /*ImageIO.write(this.toTest.applyFilter(actual), "png", this
                .expectedResult);*/

            compareImages(expected, this.toTest.applyFilter(actual));
        } catch (IOException ioe) {
            fail("Failed to load test images: " + ioe.getLocalizedMessage());
        }
    }

    /**
     * Compares two images using JUnit assertions to elaborate test failures
     * beyond "equal" and "not equal".
     * 
     * @param expected
     *            the expected result
     * @param actual
     *            the modified original
     */
    public static void compareImages(BufferedImage expected,
            BufferedImage actual) {
        assertEquals("Images have different width.", expected.getWidth(),
                actual.getWidth());
        assertEquals("Images have different height.", expected.getHeight(),
                actual.getHeight());

        for (int x = 0; x < expected.getWidth(); x++) {
            for (int y = 0; y < expected.getHeight(); y++) {
                assertEquals("Pixel (" + x + ":" + y + ") differs in RED",
                        new Color(expected.getRGB(x, y), true).getRed(),
                        new Color(actual.getRGB(x, y), true).getRed());
                assertEquals("Pixel (" + x + ":" + y + ") differs in GREEN",
                        new Color(expected.getRGB(x, y), true).getGreen(),
                        new Color(actual.getRGB(x, y), true).getGreen());
                assertEquals("Pixel (" + x + ":" + y + ") differs in BLUE",
                        new Color(expected.getRGB(x, y), true).getBlue(),
                        new Color(actual.getRGB(x, y), true).getBlue());
                assertEquals("Pixel (" + x + ":" + y + ") differs in ALPHA",
                        new Color(expected.getRGB(x, y), true).getAlpha(),
                        new Color(actual.getRGB(x, y), true).getAlpha());
            }
        }
    }

}
