package org.iMage.edge.detection.sobel.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ServiceLoader;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.iMage.edge.detection.base.EdgeDetectionImageFilter;
import org.iMage.edge.detection.sobel.filter.BlurFilter;
import org.iMage.edge.detection.sobel.filter.LowerThresholdFilter;
import org.iMage.edge.detection.sobel.gui.ChoosableFileFilters.JPGFileFilter;
import org.iMage.edge.detection.sobel.gui.ChoosableFileFilters.PNGFileFilter;

public class Gui implements ChangeListener, ItemListener, ActionListener {

	private final static String SERVICE_PROVIDER_PATH_PREFIX = "org.iMage.edge.detection.sobel.filter.";

	private final JFrame window = new JFrame();
	private BufferedImage o;
	private BufferedImage p;
	private final JLabel oLabel = new JLabel();
	private final JLabel pLabel = new JLabel();
	private final JMenuItem open = new JMenuItem("open");
	private final JMenuItem save = new JMenuItem("save");
	private final JComboBox<String> filters = new JComboBox<String>();
	private final ServiceLoader<EdgeDetectionImageFilter> sl = ServiceLoader.load(EdgeDetectionImageFilter.class);
	JCheckBox blurFilter;
	JCheckBox thresholdCB;
	JSlider thresholdSlider;

	/**
	 * Create the application.
	 */
	public Gui() {
		oLabel.setVerticalAlignment(JLabel.CENTER);
		oLabel.setHorizontalAlignment(JLabel.CENTER);
		pLabel.setVerticalAlignment(JLabel.CENTER);
		pLabel.setHorizontalAlignment(JLabel.CENTER);
		filters.setSize(100, 100);
		initialize(sl);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ServiceLoader<EdgeDetectionImageFilter> sl) {
		URL url = ClassLoader.getSystemResource("image/android.png");
		try {
			o = ImageIO.read(url);
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		p = o;
		oLabel.setIcon(new ImageIcon(resizeImage(300, o)));
		pLabel.setIcon(new ImageIcon(resizeImage(300, p)));

		window.setTitle("EDGuy");
		window.setResizable(false);
		window.setMinimumSize(new Dimension(600, 600));
		window.setPreferredSize(new Dimension(600, 600));
		window.setMaximumSize(new Dimension(600, 600));
		window.setBounds(200, 100, 450, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		open.addActionListener(this);

		fileMenu.add(open);

		save.addActionListener(this);

		fileMenu.add(save);

		JMenuItem exit = new JMenuItem("exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exit);

		JMenu about = new JMenu("About");
		menuBar.add(about);

		JMenuItem license = new JMenuItem("License");
		license.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String license = null;
				URL licenseUrl = ClassLoader.getSystemResource("license/license.txt");
				InputStream is = null;
				try {
					is = licenseUrl.openStream();
				}
				catch (IOException e1) {
					System.err.println("Can not load the license!");
				}
				Scanner scanner = new Scanner(is, "UTF-8");
				license = scanner.useDelimiter("\\A").next();
				JPanel licensePanel = new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Dimension getPreferredSize() {
						return new Dimension(400, 400);
					}
				};
				JTextArea textArea = new JTextArea(license);
				textArea.setEditable(false);
				licensePanel.setLayout(new BorderLayout());
				licensePanel.add(new JScrollPane(textArea));
				JOptionPane.showMessageDialog(window, licensePanel, "GNU License", JOptionPane.PLAIN_MESSAGE);
				scanner.close();
			}
		});
		about.add(license);
		window.getContentPane().setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));

		JPanel picturePanel = new JPanel();
		window.getContentPane().add(picturePanel);
		picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.X_AXIS));

		oLabel.setMaximumSize(new Dimension(300, 300));
		picturePanel.add(oLabel);

		pLabel.setMaximumSize(new Dimension(300, 300));
		picturePanel.add(pLabel);

		JPanel filterPanel = new JPanel();
		window.getContentPane().add(filterPanel);
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

		JPanel blurFilterPanel = new JPanel();
		blurFilterPanel.setLayout(new BorderLayout());
		blurFilter = new JCheckBox("BlurFilter");
		blurFilter.addChangeListener(this);
		blurFilterPanel.add(blurFilter, BorderLayout.WEST);
		filterPanel.add(blurFilterPanel, BorderLayout.NORTH);

		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BorderLayout());
		selectPanel.setPreferredSize(new Dimension(100, 100));
		filters.setMaximumSize(new Dimension(100, 100));
		LinkedList<String> items = new LinkedList<String>();
		for (final EdgeDetectionImageFilter edgeDetectionImageFilter : sl) {
			String itemName = edgeDetectionImageFilter.getClass().getName();
			itemName = itemName.substring(itemName.lastIndexOf(".") + 1, itemName.length());
			items.add(itemName);
		}
		Collections.sort(items);
		for (String name : items) {
			filters.addItem(name);
		}
		selectPanel.add(filters, BorderLayout.WEST);
		filterPanel.add(selectPanel, BorderLayout.CENTER);

		JPanel thresholdAdjustment = new JPanel();
		thresholdAdjustment.setLayout(new BoxLayout(thresholdAdjustment, BoxLayout.X_AXIS));
		thresholdCB = new JCheckBox("Threshold");
		thresholdCB.addChangeListener(this);
		thresholdAdjustment.add(thresholdCB);

		thresholdSlider = new JSlider();
		thresholdSlider.setMaximum(255);
		thresholdSlider.addChangeListener(this);
		thresholdAdjustment.add(thresholdSlider);

		filterPanel.add(thresholdAdjustment);

		blurFilter.setSelected(true);
		thresholdCB.setSelected(true);
		thresholdSlider.setValue(127);
		window.setVisible(true);
	}

	private void applyFilter() {
		boolean b = this.blurFilter.isSelected();
		String s = SERVICE_PROVIDER_PATH_PREFIX + String.valueOf(this.filters.getSelectedItem());
		boolean t = this.thresholdCB.isSelected();
		int v = this.thresholdSlider.getValue();
		this.p = this.o;
		if (b) {
			BlurFilter blurFilter = new BlurFilter();
			p = blurFilter.applyFilter(this.p);
		}
		for (final EdgeDetectionImageFilter edgeDetectionImageFilter : sl) {
			EdgeDetectionImageFilter newEdgeDetectionImageFilter = null;
			if (edgeDetectionImageFilter.getClass().getName().equals(s)) {
				try {
					newEdgeDetectionImageFilter = edgeDetectionImageFilter.getClass().newInstance();
				}
				catch (InstantiationException | IllegalAccessException e) {
					System.err.println("Filter loading error!");
				}
				newEdgeDetectionImageFilter.applyFilter(p);
				break;
			}
		}
		if (t) {
			LowerThresholdFilter lowerThresholdFilter = new LowerThresholdFilter(v);
			p = lowerThresholdFilter.applyFilter(this.p);
		}
		pLabel.setIcon(new ImageIcon(resizeImage(300, p)));
	}

	private BufferedImage resizeImage(int size, BufferedImage bufferedImage) {
		int actualWidth = bufferedImage.getWidth();
		int actualHeight = bufferedImage.getHeight();
		int resizedWidth = actualWidth;
		int resizedHeight = actualHeight;
		int max = Math.max(actualWidth, actualHeight);
		if (max > size) {
			if (actualWidth > actualHeight) {
				resizedWidth = size;
				resizedHeight = (int) Math.floor(((double) size / (double) actualWidth) * actualHeight);
			}
			else {
				resizedHeight = size;
				resizedWidth = (int) Math.floor(((double) size / (double) actualHeight) * actualWidth);
			}
			BufferedImage result = new BufferedImage(resizedWidth, resizedHeight, bufferedImage.getType());
			Graphics g = result.createGraphics();
			g.drawImage(bufferedImage, 0, 0, resizedWidth, resizedHeight, null);
			g.dispose();
			return result;
		}
		else {
			return bufferedImage;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == this.blurFilter || e.getSource() == thresholdCB || e.getSource() == thresholdSlider) {
			this.applyFilter();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.filters) {
			this.applyFilter();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.open) {
			final JFileChooser opener = new JFileChooser(System.getProperty("user.home"));
			opener.addChoosableFileFilter(new JPGFileFilter());
			opener.addChoosableFileFilter(new PNGFileFilter());
			opener.setAcceptAllFileFilterUsed(false);
			opener.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (opener.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = opener.getSelectedFile();
				try {
					o = ImageIO.read(file);
				}
				catch (IOException ioe) {
					System.err.println("Not valid image!");
				}
				p = o;
				oLabel.setIcon(new ImageIcon(resizeImage(300, o)));
				applyFilter();
			}
			return;
		}
		if (e.getSource() == this.save) {
			final JFileChooser saver = new JFileChooser(System.getProperty("user.home"));
			saver.addChoosableFileFilter(new JPGFileFilter());
			saver.addChoosableFileFilter(new PNGFileFilter());
			saver.setAcceptAllFileFilterUsed(false);
			saver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = saver.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String saveFormat = saver.getFileFilter().getDescription();
				if (!saveFormat.equals("jpg") && !saveFormat.equals("png")) {
					saveFormat = "png";
				}
				String saveName = saver.getSelectedFile().getName() + ".";
				if (saveName.contains(".")) {
					saveName = saveName.substring(0, saveName.indexOf("."));
				}
				String savePath = saver.getCurrentDirectory().toString() + "\\" + saveName + "." + saveFormat;
				try {
					ImageIO.write(p, saveFormat, new File(savePath));
				}
				catch (IOException ioe) {
					System.err.println("Saving process is interrupted!");
				}
			}
			return;
		}
	}
}
