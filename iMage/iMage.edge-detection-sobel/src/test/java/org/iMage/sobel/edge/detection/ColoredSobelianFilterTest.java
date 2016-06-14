package org.iMage.sobel.edge.detection;

import java.io.File;

import org.iMage.edge.detection.sobel.filter.BlurFilter;
import org.iMage.edge.detection.sobel.filter.ColoredSobelianFilter;
import org.iMage.edge.detection.sobel.filter.SobelFilter;

/**
 * Tests the ColoredSobelianFilter using the default origin image.
 * 
 * @author Christopher Guckes (swt1@ipd.kit.edu)
 * @version 1.0
 */
public class ColoredSobelianFilterTest extends ImageFilterTest {
    /**
     * Creates a new test case using the {@link ColoredSobelianFilter} and it's
     * respective result images.
     */
    public ColoredSobelianFilterTest() {
        super(new ColoredSobelianFilter(new SobelFilter(new BlurFilter())), new
                File("src/test/resources/blur-sobel-colored.png"),
                new File("src/test/resources/cables.png"));
    }
}
