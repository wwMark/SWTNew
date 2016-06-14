package org.iMage.edge.detection.sobel.gui.ChoosableFileFilters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JPGFileFilter extends FileFilter{
	
	String description = "jpg";

	@Override
	public boolean accept(File f) {
		if (f.getName().endsWith(".jpg")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
}
