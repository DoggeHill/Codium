package sk.hyll.patrik.codium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Main
 */
@SpringBootApplication
public class BankApplication {
	public static void main(String[] args) {
		// TODO: optimalize maven dependencies
		ApplicationContext context = SpringApplication.run(BankApplication.class, args);
	}

}
