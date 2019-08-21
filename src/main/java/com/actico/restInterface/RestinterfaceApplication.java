package com.actico.restInterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Build the REST architecture on the localhost:8080 from which ACTICO RuleServices are called.</p>
 *
 * Creating our own REST on a different port than the one used by Actico (i.e. port 8087)
 * gives us the possibility to taylor the endpoints to our  needs.<br>
 * Moreover, Actico server provides endpoints which are specific to the executions of
 * the RuleServices {@link RequestResource}.<br>
 * Those endpoints can be accessed on the Help Section of Actico Server WebPage.
 * @deprecated
 */
@SpringBootApplication
public class RestinterfaceApplication {
	/**
	 * <b>Main method of the project</b><br>
	 * Run the localhost:8080 and make the REST endpoints accessible.
	 * @param args this method doesn't require argument
	 */
	public static void main(String[] args) {
		SpringApplication.run(RestinterfaceApplication.class, args);
	}
}
