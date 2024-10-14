package com.depositsolutions.posintegration.configuration;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ConfigurationManager {
	private final static String CONFIG_FILE = "application.yml";

	public Configuration loadConfigData() {
		Yaml yaml = new Yaml(new Constructor(Configuration.class));
		InputStream inputStream = this.getClass()
				.getClassLoader()
				.getResourceAsStream(CONFIG_FILE);

		if (inputStream == null) {
			throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
		}

		return yaml.load(inputStream);
	}
}
