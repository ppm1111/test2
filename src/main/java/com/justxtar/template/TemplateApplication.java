package com.justxtar.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.TimeZone;

@SpringBootApplication
public class TemplateApplication {

	public static void main(String[] args) {
		try {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Properties allProps = new Properties();

			// 先讀取 classpath 內的配置（較低優先級）
			try {
				Resource[] classpathResources = resolver.getResources("classpath:config/application-*.properties");
				if (classpathResources != null && classpathResources.length > 0) {
					System.out.println("=== Loading Classpath Properties Files ===");
					loadProperties(classpathResources, allProps);
				}
			} catch (Exception e) {
				System.out.println("No classpath config files found: " + e.getMessage());
			}

			// 再讀取外部配置（較高優先級，會覆蓋相同的設定）
			try {
				Resource[] externalResources = resolver.getResources("file:./config/application-*.properties");
				if (externalResources != null && externalResources.length > 0) {
					System.out.println("=== Loading External Properties Files ===");
					loadProperties(externalResources, allProps);
				}
			} catch (Exception e) {
				System.out.println("No external config files found: " + e.getMessage());
			}

			// 將所有設定放入系統屬性
			if (!allProps.isEmpty()) {
				allProps.forEach((key, value) -> {
					System.setProperty(key.toString(), value.toString());
				});
			} else {
				System.out.println("Warning: No properties files were loaded");
			}
		} catch (Exception e) {
			System.out.println("Warning: Error loading property files: " + e.getMessage());
		}
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(TemplateApplication.class, args);
	}

	private static void loadProperties(Resource[] resources, Properties allProps) throws IOException {
		for (Resource resource : resources) {
			if (resource.exists()) {
				Properties props = new Properties();
				props.load(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
				allProps.putAll(props);  // 後載入的會覆蓋先載入的
				System.out.println("Loaded: " + resource.getFilename());
			}
		}
	}

}
