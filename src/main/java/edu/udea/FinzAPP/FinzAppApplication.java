package edu.udea.FinzAPP;

import edu.udea.FinzAPP.security.SuccessGoogle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

//(exclude = {SecurityAutoConfiguration.class })

@SpringBootApplication //@EnableAutoConfiguration(exclude={UserDetailsServiceAutoConfiguration.class})
public class FinzAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinzAppApplication.class, args);
	}

	/*@Bean
	public SuccessGoogle(){
		return new SuccessGoogle();
	}
	*/




}
