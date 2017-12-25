package com.github.qwazer.springmetricsissue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication(exclude = JmsAutoConfiguration.class)
@ImportResource({
		"classpath:META-INF/spring/jmx-config.xml",
		"classpath:META-INF/spring/integration/si-flow.xml"
}
)
public class SpringMetricsIssueApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringMetricsIssueApplication.class, args);
	}
}
