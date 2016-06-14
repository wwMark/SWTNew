package org.iMage.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Knows all available plugins and is responsible for using the service loader
 * API to detect them.
 *
 */
public final class PluginManager {

	/**
	 * No constructor for utility class.
	 */
	private PluginManager() {
	}

	/**
	 * @return all available plugins sorted alphabetically by their name in
	 *         ascending order.
	 */
	public static List<JmjrstPlugin> getPlugins() {
		ServiceLoader<JmjrstPlugin> serviceLoader = ServiceLoader.load(JmjrstPlugin.class);

		List<JmjrstPlugin> p = new ArrayList<>();
		for (final JmjrstPlugin plugin : serviceLoader) {
			p.add(plugin);
		}

		// Sortieren mit dem Comparator aus JmjrstPlugin
		Collections.sort(p);
		return p;
	}
}
