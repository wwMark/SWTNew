package org.iMage.hallo_plugin;

import javax.swing.JOptionPane;

import org.iMage.plugins.JmjrstPlugin;
import org.jis.Main;
import org.kohsuke.MetaInfServices;

@MetaInfServices(JmjrstPlugin.class)
public class HelloPlugin extends JmjrstPlugin {

	private Main jmjrst;

	@Override
	public String getMenuText() {
		return "HalloPlugin";
	}

	@Override
	public void init(Main main) {
		jmjrst = main;
		System.out.println("Ich initialisiere mich fleißig");
	}

	@Override
	public void run() {
		System.err.println("iMage - nur echt mit JMJRST!");
	}

	@Override
	public boolean isConfigurable() {
		return true;
	}

	@Override
	public void configure() {
		JOptionPane.showMessageDialog(jmjrst, "Hallo SWT-1-Fenster", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public String getName() {
		return "Hallo-SWT1-Plugin";
	}
}
