package com.progressivecoder.productapplication;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}
}

@RestController
//@RefreshScope
class MessageController {

	// @Value("${msg: Config server not working}")
	// private String msg;

	@Autowired
	KieContainer kieContainer;

	@GetMapping(value = "getSla")
	public Test getMsg(@RequestParam String type) {
		Test test = new Test();
		test.setType(type);

		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(test);
		kieSession.fireAllRules();
		kieSession.dispose();

		return test;
	}

}
