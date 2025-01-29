package com.springueo.the_java_spring_bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.springueo.the_java_spring_bank")
@OpenAPIDefinition(
		info = @Info(
				title = "The Java Academy Bank App",
				description = "Backed Rest API's for TJA Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Max Dzenik",
						email = "makc.dzen@gmail.com",
						url = "https://github.com/Eoicq/the-java-academy-bank/tree/master"
				),
				license = @License(
						name = "The Java Academy",
						url = "https://github.com/Eoicq/the-java-academy-bank/tree/master"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "The Java Academy Bank App Documentation",
				url = "https://github.com/Eoicq/the-java-academy-bank/tree/master"
		)
)
public class TheJavaAcademyBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(TheJavaAcademyBankApplication.class, args);
	}

}
