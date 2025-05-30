package com.justxtar.template;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpringBootTest
class TemplateApplicationTests {

	@BeforeAll
	static void setup() {
		try {
			// 先嘗試載入外部配置
			Resource[] resources = new PathMatchingResourcePatternResolver()
					.getResources("file:./config/application-*.properties");

			Properties allProps = new Properties();
			for (Resource resource : resources) {
				Properties props = new Properties();
				props.load(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
				allProps.putAll(props);
			}

			// 將配置設定到系統屬性
			allProps.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void contextLoads() {
	}

}
